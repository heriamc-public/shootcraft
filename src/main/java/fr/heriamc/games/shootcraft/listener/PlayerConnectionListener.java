package fr.heriamc.games.shootcraft.listener;

import fr.heriamc.games.engine.event.player.GamePlayerJoinEvent;
import fr.heriamc.games.engine.event.player.GamePlayerLeaveEvent;
import fr.heriamc.games.engine.event.player.GamePlayerSpectateEvent;
import fr.heriamc.games.engine.utils.NameTag;
import fr.heriamc.games.engine.utils.concurrent.VirtualThreading;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import fr.heriamc.games.shootcraft.data.ShootCraftDataManager;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayer;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public record PlayerConnectionListener(ShootCraftDataManager dataManager) implements Listener {

    @EventHandler
    public void onJoin(GamePlayerJoinEvent<ShootCraftGame, ShootCraftPlayer> event) {
        var game = event.getGame();
        var player = event.getPlayer();
        var gamePlayer = event.getGamePlayer();

        NameTag.setNameTag(player, "§7", " ", 999);

        switch (game.getState()) {
            case WAIT, STARTING -> game.getWaitingRoom().processJoin(gamePlayer);
            case IN_GAME, END -> {
                var rank = gamePlayer.getHeriaPlayer().getRank();

                /*if (rank.getPower() >= 40 && gamePlayer.isSpectator()) {
                    NameTag.setNameTag(player, rank.getPrefix(), " ", rank.getTabPriority());
                    return;
                }*/

                /*
                 GIVE SPECTATOR ITEM OR do interface SpectatorState ...
                 class JumpScadePlayer implements SpectatorState {

                    @Override
                    public void giveSpectatorItem() {
                        ......
                    }
                 }
                 */
                gamePlayer.setSpectator(true);
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    @EventHandler
    public void onSpectate(GamePlayerSpectateEvent<ShootCraftGame, ShootCraftPlayer> event) {

    }

    @EventHandler
    public void onLeave(GamePlayerLeaveEvent<ShootCraftGame, ShootCraftPlayer> event) {
        var game = event.getGame();
        var gamePlayer = event.getGamePlayer();

        switch (game.getState()) {
            case WAIT, STARTING -> game.getWaitingRoom().processLeave(gamePlayer);
            case IN_GAME -> {
                // SECURITY CHECK
                if (game.oneTeamAlive() && !game.getEndGameTask().isStarted())
                    game.getEndGameTask().run();

                savePlayerData(gamePlayer);
            }
            case END -> savePlayerData(gamePlayer);
        }
    }

    private void savePlayerData(ShootCraftPlayer gamePlayer) {
        VirtualThreading.execute(() -> {
            var gamePlayerData = dataManager.createOrLoad(gamePlayer.getUuid());

            gamePlayerData.updateStats(gamePlayer);

            dataManager.save(gamePlayerData);
            dataManager.saveInPersistant(gamePlayerData);
        });
    }

}