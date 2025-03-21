package fr.heriamc.games.shootcraft.setting;

import fr.heriamc.api.game.size.GameSize;
import fr.heriamc.games.engine.GameSettings;
import fr.heriamc.games.engine.MiniGame;
import fr.heriamc.games.engine.board.GameBoard;
import fr.heriamc.games.engine.board.GameBoardManager;
import fr.heriamc.games.engine.player.BaseGamePlayer;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import fr.heriamc.games.shootcraft.board.ShootCraftBoard;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayer;

public class ShootCraftSettings extends GameSettings<ShootCraftMapManager> {

    public ShootCraftSettings(GameSize gameSize) {
        super(gameSize, new GameBoardManager());
    }

    @Override
    public GameBoard<?, ?> defaultBoard(MiniGame game, BaseGamePlayer gamePlayer) {
        return new ShootCraftBoard((ShootCraftGame) game, (ShootCraftPlayer) gamePlayer);
    }

}