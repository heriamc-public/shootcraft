package fr.heriamc.games.shootcraft.player;

import com.google.gson.annotations.SerializedName;
import fr.heriamc.api.data.SerializableData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ShootCraftPlayerData implements SerializableData<UUID> {

    @SerializedName("id")
    private UUID identifier;

    private int kills, deaths, playedGames;

    public void updateStats(ShootCraftPlayer gamePlayer) {
        this.kills += gamePlayer.getKills();
        this.deaths += gamePlayer.getDeaths();
        this.playedGames += 1;
    }

}