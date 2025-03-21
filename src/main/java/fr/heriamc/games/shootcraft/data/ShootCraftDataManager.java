package fr.heriamc.games.shootcraft.data;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.games.shootcraft.player.ShootCraftPlayerData;

import java.util.UUID;

public class ShootCraftDataManager extends PersistentDataManager<UUID, ShootCraftPlayerData> {

    public ShootCraftDataManager(HeriaAPI heriaAPI) {
        super(heriaAPI.getRedisConnection(), heriaAPI.getMongoConnection(), "shootcraft", "shootcraft");
    }

    @Override
    public ShootCraftPlayerData getDefault() {
        return new ShootCraftPlayerData(null, 0, 0, 0);
    }

}