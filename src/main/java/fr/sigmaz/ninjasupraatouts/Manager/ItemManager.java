package fr.sigmaz.ninjasupraatouts.Manager;

import fr.sigmaz.ninjasupraatouts.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemManager {
    public static List<String> getItems() {

        ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("items");
        if (section != null) {
            return new ArrayList<>(section.getKeys(false));
        }
        return null;
    }

    /*
    public static void removeUse(ItemStack item, Player player){
        String display_name = item.getItemMeta().getDisplayName();
        String the_item = getItemWithDisplayName(display_name);

        ItemMeta itemMeta = item.getItemMeta();
        //lore
        List<String> lore = new ArrayList<>();
        for (String line : Main.getInstance().getConfig().getStringList("items." + the_item + ".lore")) {
            lore.add(line
                    .replace("%owner%", player.getName())
                    .replace("%uses%", String.valueOf(Main.getInstance().getConfig().getInt("items." + the_item + ".uses") - 1))
            );
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

     */

    public static int getUsesFromLore(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (String line : lore) {
                if (line.contains("Utilisation:")) {
                    String[] parts = ChatColor.stripColor(line).split("/");
                    if (parts.length == 2) {
                        try {
                            return Integer.parseInt(parts[0].replace("◆ Utilisation: ", ""));
                        } catch (NumberFormatException e) {
                            // En cas d'erreur lors de la conversion
                            return 0;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static void setUsesInLore(ItemStack item, int uses) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();

            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains("Utilisation:")) {
                    lore.set(i, ChatColor.GRAY + "◆ " + ChatColor.WHITE + "Utilisation: " + ChatColor.GREEN + uses + "/5");
                    break;
                }
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }

    public static boolean hasUsesInLore(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (String line : lore) {
                if (line.contains("Utilisation:")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getItemWithDisplayName(String displayName) {
        List<String> items = getItems();

        assert items != null;
        for (String it : items) {
            if (displayName.equals(getDisplayNameWithItem(it))){
                return it;
            }
        }
        return null;
    }

    public static String getDisplayNameWithItem(String item){
        return Objects.requireNonNull(Main.getInstance().getConfig().getString("items." + item + ".display-name"));
    }
}
