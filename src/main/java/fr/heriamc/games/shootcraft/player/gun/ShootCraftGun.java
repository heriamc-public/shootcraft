package fr.heriamc.games.shootcraft.player.gun;

import fr.heriamc.api.game.GameState;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.api.GameApi;
import fr.heriamc.games.engine.ability.GameAbilityItemCooldown;
import fr.heriamc.games.shootcraft.ShootCraftGame;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayer;
import fr.heriamc.games.shootcraft.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.TimeUnit;

public class ShootCraftGun extends GameAbilityItemCooldown<ShootCraftGame, ShootCraftPlayer, PlayerInteractEvent> {

    public ShootCraftGun() {
        super("ShootCraftGun",
                ShootCraftGame.class, PlayerInteractEvent.class,
                2, TimeUnit.SECONDS, true,
                new ItemBuilder(Material.STICK).setName("Batongue").build());

        this.actionBar = (cd, uuid, gamePlayer) -> "§eChargement : §6" + cd.getTimeLeftInSeconds(uuid) + "s";
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var item = event.getItem();
        var game = GameApi.getInstance().getGamePoolManager().getNullableGame(player);

        if (item == null || game == null) return;

        if (game.getState() == GameState.IN_GAME && item.equals(itemStack))
            handleAbility((ShootCraftGame) game, player.getUniqueId(), event);
    }

    @Override
    public void onUse(ShootCraftGame game, ShootCraftPlayer gamePlayer, PlayerInteractEvent event) {
        // SECURITY CHECK
        if (game.getState() != GameState.IN_GAME) return;

        var player = event.getPlayer();

        // SECURITY CHECK
        var vector = player.getEyeLocation().getDirection();
        var start = player.getEyeLocation().add(vector.clone().multiply(1));

        double maxDistance = 20.0;
        boolean playerHit = false;
        double hitDistance = 0;

        for (double i = 0; i < maxDistance; i += 0.5) {
            var point = start.clone().add(vector.clone().multiply(i));
            var block = point.getBlock();

            if (block.getType() != Material.AIR) break;

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                ParticleEffect.FIREWORKS_SPARK.display(vector, 0f, point, onlinePlayer);
            }

            for (Entity entity : player.getWorld().getNearbyEntities(point, 0.5, 0.5, 0.5)) {
                if (entity.equals(player)) continue;

                if (entity instanceof Player target) {
                    var targetPlayer = game.getNullablePlayer(target);

                    if (targetPlayer == null) return;

                    System.out.println("SAME TEAM ? " + targetPlayer.sameTeam(gamePlayer));

                    if (gamePlayer.sameTeam(targetPlayer)) return;

                    playerHit = true;

                    game.broadcast(player.getName() + " a tué " + target.getName());

                    gamePlayer.onKill();
                    targetPlayer.onDeath();

                    // PLAY SOUND EFFECT HERE OR IN onKill(); METHOD

                    game.getSettings().getGameMapManager().getSpawnPoints().randomTeleport(targetPlayer);

                    break;
                }
            }

            if (playerHit) break;
        }
    }

}