package fr.heriamc.games.shootcraft.setting;

import fr.heriamc.api.game.GameState;
import fr.heriamc.games.engine.map.GameMapManager;
import fr.heriamc.games.engine.map.slime.SlimeGameMap;
import fr.heriamc.games.engine.map.slime.SlimeMap;
import fr.heriamc.games.engine.map.slime.SlimeWorldLoader;
import fr.heriamc.games.engine.point.MultiplePoint;
import fr.heriamc.games.engine.utils.location.LocationCoordinates;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import lombok.Getter;

@Getter
public class ShootCraftMapManager extends GameMapManager<ShootCraftGame, SlimeMap, SlimeWorldLoader> {

    private final SlimeMap waiting, arena;

    private final MultiplePoint spawnPoints;

    public ShootCraftMapManager(ShootCraftGame game) {
        super(game, new SlimeWorldLoader());
        this.waiting = new SlimeGameMap(getFormattedTypeMapName("waiting"), getFormattedMapTemplateName("waitingroom"));
        this.arena = new SlimeGameMap(getFormattedTypeMapName("arena"), getFormattedMapTemplateName("classic"));
        this.spawnPoints = new MultiplePoint("random");
    }

    @Override
    public void setup() {
        addMap(waiting);
        addMap(arena);

        mapLoader.load(waiting).whenComplete((slimeMap, throwable) -> {
            slimeMap.setSpawn(-24.5, 108, -34.5, -180, 0);
            slimeMap.getWorld().setGameRuleValue("doFireTick", "false");

            game.getWaitingRoom().setMap(slimeMap);
        });

        mapLoader.load(arena).whenComplete((slimeMap, throwable) -> {
            slimeMap.getWorld().setGameRuleValue("doFireTick", "false");

            spawnPoints.addPoints(
                    slimeMap,
                    new LocationCoordinates(-12.5, 87, 6.5, 0, 0),
                    new LocationCoordinates(-46.5, 87, 2.5, -90, 0),
                    new LocationCoordinates(-16.5, 87, 61.5, 90, 0),
                    new LocationCoordinates(-50.5, 87, 57.5, 180, 0),
                    new LocationCoordinates(-34.5, 87, 36.5, 0, 0),
                    new LocationCoordinates(-28.5, 87, 27.5, 180, 0),
                    new LocationCoordinates(-7.5, 102, 31.5, -90, 0),
                    new LocationCoordinates(-55.5, 102, 31.5, 90, 0)
            );

            game.setState(GameState.WAIT);
        });
    }

    @Override
    public void end() {
        delete(waiting);
        delete(arena);
    }

}