package fr.heriamc.games.shootcraft.task;

import fr.heriamc.api.game.GameState;
import fr.heriamc.games.engine.utils.task.countdown.CountdownTask;
import fr.heriamc.games.engine.utils.task.countdown.GameCountdownTask;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayer;
import fr.heriamc.games.shootcraft.player.gun.ShootCraftGun;

public class ShootCraftGameCycleTask extends GameCountdownTask<ShootCraftGame> {

    private final static ShootCraftGun gun = new ShootCraftGun();

    public ShootCraftGameCycleTask(ShootCraftGame game) {
        super(300, game);
    }

    @Override
    public void onStart() {
        game.setState(GameState.IN_GAME);

        game.getAlivePlayers().forEach(gamePlayer -> {
            gamePlayer.cleanUp();
            gun.giveItem(gamePlayer);
        });
    }

    @Override
    public void onNext(CountdownTask task) {
        switch (task.getSecondsLeft().get()) {
            case 240, 180, 120, 60, 10 -> game.broadcast("IL RESTE " + getTimeLeft());
        }
    }

    @Override
    public void onComplete() {
        game.getAlivePlayers().forEach(ShootCraftPlayer::cleanUp);
        // SECURITY CHECK
        if (!game.getEndGameTask().isStarted())
            game.getEndGameTask().run();
    }

    @Override
    public void onCancel() {
        // CANCEL
    }

    public String getTimeLeft() {
        var time = getSecondsLeft().get();
        var minutes = (time % 3600) / 60;
        var timeLeft = time % 60;

        return String.format("%02d:%02d", minutes, timeLeft);
    }

}