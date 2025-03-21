package fr.heriamc.games.shootcraft.waiting;

import fr.heriamc.games.engine.waitingroom.GameWaitingRoom;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayer;
import fr.heriamc.games.shootcraft.task.ShootCraftStartingTask;

public class ShootCraftWaitingRoom extends GameWaitingRoom<ShootCraftGame, ShootCraftPlayer, ShootCraftWaitingRoomItems> {

    public ShootCraftWaitingRoom(ShootCraftGame game) {
        super(game, ShootCraftWaitingRoomItems.class);
        this.countdownTask = new ShootCraftStartingTask(game);
    }

    @Override
    public void onJoin(ShootCraftPlayer shootCraftPlayer) {

    }

    @Override
    public void onLeave(ShootCraftPlayer shootCraftPlayer) {

    }


}
