package fr.sigmaz.ninjasupraatouts.Manager;

import fr.sigmaz.ninjasupraatouts.Main;
import fr.sigmaz.ninjasupraatouts.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemManager {
    public static List<String> getItems() {

        ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("items");
        if (section != null) {
            return new ArrayList<>(section.getKeys(false));
        }
        return null;
    }

    public static ItemStack getItem(String the_item, Player playerToGive){
        Boolean hasEnchant = false;
        //display name
        String display_name = TextManager.formattedText(Main.getInstance().getConfig().getString("items." + the_item + ".display-name"));
        //lore
        List<String> lore = new ArrayList<>();
        for (String line : Main.getInstance().getConfig().getStringList("items." + the_item + ".lore")) {
            lore.add(line
                    .replace("%owner%", playerToGive.getName())
                    .replace("%kill%", "0")
                    .replace("%last_kill%", "-")
                    .replace("%uses%", Main.getInstance().getConfig().getString("items." + the_item + ".uses"))
                    .replace("%skid_players%", "-")
                    .replace("%block_broken%", "0")
                    .replace("%points%", "0")
                    .replace("%killed-mobs%", "0")
            );
        }
        //material
        Material material = Material.getMaterial(Main.getInstance().getConfig().getString("items." + the_item + ".type")) ;
        //enchantement
        if (getEnchantements(the_item) != null) {
            hasEnchant = true;
        }



        playerToGive.sendMessage(TextManager.formattedText("&2&l✔ &7&l◆ &aVous avez reçu " + display_name));
        ItemStack CustomItem = new ItemBuilder(material, 1).setDisplayName(display_name).setLoreWithList(lore).build(false);
        //add enchantements
        if (hasEnchant) {

            HashMap<String, Integer> enchantements = getEnchantements(the_item);

            if (enchantements != null) {
                ItemMeta CustomItemMeta = CustomItem.getItemMeta();

                for (Map.Entry<String, Integer> entry : enchantements.entrySet()) {
                    String enchName = entry.getKey();
                    int enchLvl = entry.getValue();
                    Enchantment ench = Enchantment.getByName(enchName);

                    CustomItemMeta.addEnchant(ench, enchLvl, true);
                }
                //def item meta
                CustomItem.setItemMeta(CustomItemMeta);
            } else {
                System.out.println("§4§l✘ §7§l◆ §cAucun enchantement trouvé pour cet objet.");
            }
        }

        ItemManager.setUsesInLore(CustomItem, Main.getInstance().getConfig().getInt("items." + the_item + ".uses"));
        return CustomItem;
    }

    public static ItemStack getItemJustWithDisplayName(String the_item){
        String display_name = TextManager.formattedText(Main.getInstance().getConfig().getString("items." + the_item + ".display-name"));
        Material material = Material.getMaterial(Main.getInstance().getConfig().getString("items." + the_item + ".type")) ;

        return new ItemBuilder(material, 1).setDisplayName(display_name).build(false);
    }

    public static HashMap<String, Integer> getEnchantements(String item) {

        ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("items." + item + ".enchants");
        if (section != null) {
            HashMap<String, Integer> Enchantments = new HashMap<>();

            List<String> EnchantNames = new ArrayList<>(section.getKeys(false));
            for (String eN : EnchantNames) {
                Enchantments.put(eN, Main.getInstance().getConfig().getInt("items." + item + ".enchants." + eN));
            }

            return Enchantments;
        }
        return null;
    }

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
