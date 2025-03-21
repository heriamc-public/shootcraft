package fr.heriamc.games.shootcraft.player;

import fr.heriamc.games.engine.team.GameTeam;
import fr.heriamc.games.engine.team.GameTeamColor;

public class ShootCraftTeam extends GameTeam<ShootCraftPlayer> {

    public ShootCraftTeam(int maxSize, GameTeamColor color) {
        super(maxSize, color);
    }

}