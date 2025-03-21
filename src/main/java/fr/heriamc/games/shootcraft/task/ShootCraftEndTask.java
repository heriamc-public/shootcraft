package fr.heriamc.games.shootcraft.task;

import fr.heriamc.api.game.GameState;
import fr.heriamc.games.api.GameApi;
import fr.heriamc.games.engine.utils.task.countdown.CountdownTask;
import fr.heriamc.games.engine.utils.task.countdown.GameCountdownTask;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import fr.heriamc.games.shootcraft.setting.message.ShootCraftMessages;

public class ShootCraftEndTask extends GameCountdownTask<ShootCraftGame> {

    public ShootCraftEndTask(ShootCraftGame game) {
        super(10, game);
    }

    @Override
    public void onStart() {
        game.getGameCycleTask().end();
        game.setState(GameState.END);

        // SEND VICTORY AND DEFEAT MESSAGE

        var top1 = " §6§lMéDAILLE D'OR §f! Vous êtes l'un des meilleurs athlètes que l'on n'ait jamais connus";
        var top2 = " §7§lMéDAILLE D'ARGENT §f! Félicitations vous êtes un concurrent de taille";
        var top3 = " §c§lMéDAILLE DE BRONZE §f! Félicitations pour cette partie";

        var defeat = " §6§lMéDAIL D'OR !  §Vous êtes l'un des meilleurs athlètes que l'on n'ait jamais connus";

        game.broadcast(ShootCraftMessages.END_VICTORY_MESSAGE.getMessages(
                "",
                game.getTopKiller(0),
                game.getTopKiller(1),
                game.getTopKiller(2)));

        System.out.println("FIN DU JEU");
    }

    @Override
    public void onNext(CountdownTask task) {
        switch (task.getSecondsLeft().get()) {
            case 5, 10, 15, 20 -> game.broadcast(ShootCraftMessages.END_BACK_TO_HUB.getMessage(task.getSecondsLeft().get()));
        }
    }

    @Override
    public void onComplete() {
        game.getPlayers().values().forEach(GameApi.getInstance()::redirectToHub);
        game.endGame();
    }

    @Override
    public void onCancel() {
        log.info("[JumpScadeEndTask] SOMETHING WRONG HAPPEN SHOULD NOT BE CALLED");
    }

}