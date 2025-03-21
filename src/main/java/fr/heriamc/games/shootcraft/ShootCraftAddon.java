package fr.heriamc.games.shootcraft;

import fr.heriamc.games.api.addon.GameAddon;
import fr.heriamc.games.engine.waitingroom.gui.GameTeamSelectorGui;
import fr.heriamc.games.shootcraft.data.ShootCraftDataManager;
import fr.heriamc.games.shootcraft.listener.*;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayer;
import fr.heriamc.games.shootcraft.pool.ShootCraftPool;
import lombok.Getter;

@Getter
public class ShootCraftAddon extends GameAddon<ShootCraftPool> {

    private ShootCraftDataManager dataManager;

    public ShootCraftAddon() {
        super(new ShootCraftPool());
    }

    @Override
    public void enable() {
        this.dataManager = new ShootCraftDataManager(heriaApi);

        pool.loadDefaultGames();

        registerListener(
                new CancelListener(pool.getGamesManager()),
                new PlayerConnectionListener(dataManager),
                new PlayerInteractListener(this),
                new PlayerMoveListener(pool.getGamesManager()),
                new PlayerChatListener(pool.getGamesManager())
        );
    }

    public void openTeamSelectorGui(ShootCraftGame game, ShootCraftPlayer gamePlayer) {
        openGui(new GameTeamSelectorGui<>(game, gamePlayer, "Équipes"));
    }

    @Override
    public void disable() {

    }

}