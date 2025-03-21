package fr.heriamc.games.shootcraft.listener;

import fr.heriamc.bukkit.chat.event.HeriaChatEvent;
import fr.heriamc.games.api.pool.GameManager;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public record PlayerChatListener(GameManager<ShootCraftGame> gameManager) implements Listener {

    @EventHandler
    public void onChat(HeriaChatEvent event) {
        var player = event.getPlayer();
        var game = gameManager.getNullableGame(player);

        event.setCancelled(true);

        if (game == null) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        var team = gamePlayer.getTeam();
        var space = new TextComponent(" ");
        var finalText = new TextComponent((team == null ? "§7" : team.getColoredName() + " ")
                + event.getName()
                + " §8» §f" + event.getFormattedMessage());

        game.getPlayers().values().forEach(otherPlayer -> otherPlayer.getPlayer().spigot().sendMessage(event.getReportComponent(), space, finalText));
    }

}
