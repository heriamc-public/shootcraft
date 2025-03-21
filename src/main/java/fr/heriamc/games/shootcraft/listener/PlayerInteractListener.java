package fr.heriamc.games.shootcraft.listener;

import fr.heriamc.games.shootcraft.ShootCraftAddon;
import fr.heriamc.games.shootcraft.waiting.ShootCraftWaitingRoomItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public record PlayerInteractListener(ShootCraftAddon addon) implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        var itemStack = event.getItem();

        if (itemStack == null || !itemStack.hasItemMeta()) return;

        var player = event.getPlayer();
        var game = addon.getPool().getGamesManager().getNullableGame(player);

        if (game == null) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        switch (game.getState()) {
            case WAIT, STARTING -> ShootCraftWaitingRoomItems.getWaitingItem(itemStack).ifPresent(item -> item.getConsumer().accept(addon, game, gamePlayer));
            case END -> {}
        }

        event.setCancelled(true);
    }

}