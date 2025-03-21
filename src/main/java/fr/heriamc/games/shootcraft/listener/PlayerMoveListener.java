package fr.heriamc.games.shootcraft.listener;

import fr.heriamc.games.api.pool.GameManager;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public record PlayerMoveListener(GameManager<ShootCraftGame> gameManager) implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        var player = event.getPlayer();
        var game = gameManager.getNullableGame(player);

        if (game == null) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null || gamePlayer.isSpectator()) return;

        if (event.getTo().getY() >= 0) return;

        switch (game.getState()) {
            case WAIT, STARTING -> game.getWaitingRoom().teleport(gamePlayer);
            case END -> System.out.println("TELEPORT TO SPECTATOR SPAWNPOINT");
        }
    }

}