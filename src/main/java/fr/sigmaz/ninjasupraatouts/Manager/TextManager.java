package fr.sigmaz.ninjasupraatouts.Manager;

import fr.sigmaz.ninjasupraatouts.Main;

public class TextManager {
    public static String formattedText(String msg){
        return msg
                .replace("%prefix%", Main.getInstance().getConfig().getString("options.prefix"))
                .replace("&", "§");
    }
}
