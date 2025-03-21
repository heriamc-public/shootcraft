package fr.heriamc.games.shootcraft.pool;

import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.games.api.DirectConnectStrategy;
import fr.heriamc.games.api.pool.GamePool;
import fr.heriamc.games.shootcraft.ShootCraftGame;

import java.util.function.Supplier;

public class ShootCraftPool extends GamePool<ShootCraftGame> {

    public ShootCraftPool() {
        super(ShootCraftGame.class, "ShootCraft Pool", HeriaServerType.SHOOTCRAFT, 0, 6, DirectConnectStrategy.FILL_GAME);
    }

    @Override
    public Supplier<ShootCraftGame> newGame() {
        return ShootCraftGame::new;
    }

}