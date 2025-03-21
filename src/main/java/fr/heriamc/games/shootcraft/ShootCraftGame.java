package fr.heriamc.games.shootcraft;

import fr.heriamc.api.game.size.GameSizeTemplate;
import fr.heriamc.games.engine.Game;
import fr.heriamc.games.engine.player.SimpleGamePlayer;
import fr.heriamc.games.engine.team.GameTeamColor;
import fr.heriamc.games.engine.utils.task.countdown.CountdownTask;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayer;
import fr.heriamc.games.shootcraft.player.ShootCraftTeam;
import fr.heriamc.games.shootcraft.setting.ShootCraftMapManager;
import fr.heriamc.games.shootcraft.setting.ShootCraftSettings;
import fr.heriamc.games.shootcraft.task.ShootCraftEndTask;
import fr.heriamc.games.shootcraft.task.ShootCraftGameCycleTask;
import fr.heriamc.games.shootcraft.waiting.ShootCraftWaitingRoom;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Getter
public class ShootCraftGame extends Game<ShootCraftPlayer, ShootCraftTeam, ShootCraftSettings> {

    private final ShootCraftWaitingRoom waitingRoom;

    private final ShootCraftGameCycleTask gameCycleTask;
    private final CountdownTask endGameTask;

    public ShootCraftGame() {
        super("shootcraft", new ShootCraftSettings(GameSizeTemplate.SIZE_SOLO.toGameSize()));
        this.settings.setGameMapManager(new ShootCraftMapManager(this));
        this.waitingRoom = new ShootCraftWaitingRoom(this);
        this.gameCycleTask = new ShootCraftGameCycleTask(this);
        this.endGameTask = new ShootCraftEndTask(this);
    }

    @Override
    public void load() {
        settings.getGameMapManager().setup();
    }

    public List<ShootCraftPlayer> getTopKillers() {
        return getAlivePlayers().stream()
                .sorted(Comparator.comparingInt(SimpleGamePlayer::getKills).reversed())
                .toList();
    }

    public String getTopKiller(int position) {
        var killers = getTopKillers();

        if (position < 0 || position >= killers.size()) {
            return "personne";
        }

        var killer = killers.get(position);
        return killer.getName() + " §8(§b" + killer.getKills() + "§8)";
    }

    public String getTopKiller(ShootCraftPlayer gamePlayer) {
        var killers = getTopKillers();
        var index = killers.indexOf(gamePlayer);

        return "#" + index;
    }

    @Override
    public ShootCraftTeam defaultGameTeam(int size, GameTeamColor gameTeamColor) {
        return new ShootCraftTeam(size, gameTeamColor);
    }

    @Override
    public ShootCraftPlayer defaultGamePlayer(UUID uuid, boolean spectator) {
        return new ShootCraftPlayer(uuid, 0, 0, spectator);
    }

}