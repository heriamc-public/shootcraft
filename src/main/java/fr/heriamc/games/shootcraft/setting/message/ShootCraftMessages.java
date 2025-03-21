package fr.heriamc.games.shootcraft.setting.message;

import java.util.ArrayList;
import java.util.List;

public enum ShootCraftMessages {

    PREFIX ("§6§lSHOOTCRAFT §8┃ "),

    STARTING_CANCELLED ("§cLancement de la partie annuler..."),
    STARTING_MESSAGE ("§fLancement dans §e%d §fsecondes !"),

    TIME_LEFT_MESSAGE ("§fIl ne reste que %s pour effectuer le plus de kills !"),

    DEATH_MESSAGE ("§fVous avez été tué par §c%s §f%s"),
    KILL_MESSAGE ("§fTu as tué §c%s §f!"),
    //VOID_DEATH_MESSAGE("§c%s §fest tombé..."),

    END_VICTORY_MESSAGE (
            "§m-------------------------------------------",
            "",
            "§f§l➨ CLASSEMENT",
            "",
            "%s",
            "",
            "§a① §f%s",
            "§e② §f%s",
            "§c③ §f%s",
            "",
            "§fTu vas être redirigé vers le hub dans quelques secondes...",
            "",
            "§m-------------------------------------------"),

    END_BACK_TO_HUB ("§fRetour au hub dans §e%d §fsecondes !");

    private String message;
    private String[] messages;

    ShootCraftMessages(String message) {
        this.message = message;
    }

    ShootCraftMessages(String... messages) {
        this.messages = messages;
    }

    public String getMessage() {
        return PREFIX.message + message;
    }

    public String getMessage(Object... objects) {
        return PREFIX.message + message.formatted(objects);
    }

    public String[] getMessages(Object... objects) {
        List<String> formattedMessages = new ArrayList<>(messages.length);
        var index = 0;

        for (String message : messages) {
            if (message.contains("%")) {
                formattedMessages.add(message.formatted(objects[index]));
                index++;
            } else
                formattedMessages.add(message);
        }

        return formattedMessages.toArray(String[]::new);
    }

    public String getMessageWithoutPrefix() {
        return message;
    }

}