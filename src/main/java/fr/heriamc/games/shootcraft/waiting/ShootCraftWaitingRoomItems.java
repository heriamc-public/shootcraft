package fr.heriamc.games.shootcraft.waiting;

import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.engine.utils.func.TriConsumer;
import fr.heriamc.games.engine.waitingroom.WaitingRoomItems;
import fr.heriamc.games.shootcraft.ShootCraftAddon;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ShootCraftWaitingRoomItems implements WaitingRoomItems {

    TEAM_SELECTOR (4,
            new ItemBuilder(Material.CHEST).setName("§eÉquipes§8・§7Clic droit").build(),
            ShootCraftAddon::openTeamSelectorGui),

    LEAVE (8,
            new ItemBuilder(Material.BED).setName("§cQuitter§8・§7Clic droit").build(),
            (addon, game, gamePlayer) -> addon.redirectToHub(gamePlayer));

    private final int slot;
    private final ItemStack itemStack;
    // MAYBE USELESS
    private final TriConsumer<ShootCraftAddon, ShootCraftGame, ShootCraftPlayer> consumer;

    private static final List<ShootCraftWaitingRoomItems> items = Arrays.asList(values());

    public static Optional<ShootCraftWaitingRoomItems> getWaitingItem(ItemStack itemStack) {
        return items.stream().filter(lobbyItems -> lobbyItems.getItemStack().equals(itemStack)).findFirst();
    }

}