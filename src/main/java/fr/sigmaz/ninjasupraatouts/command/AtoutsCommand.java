package fr.sigmaz.ninjasupraatouts.command;

import fr.sigmaz.ninjasupraatouts.Main;
import fr.sigmaz.ninjasupraatouts.Manager.ItemManager;
import fr.sigmaz.ninjasupraatouts.Manager.TextManager;
import fr.sigmaz.ninjasupraatouts.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtoutsCommand implements CommandExecutor {
    Boolean find = false;
    Boolean hasEnchant = false;
    Boolean playerOnLine = false;
    Player argPlayer = null;
    String cmd = "ninjaatouts";
    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if (args.length == 0){
            s.sendMessage("§aMenu d'aide :");
            s.sendMessage("§a/"+ cmd + " give [player] [item]");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "give":
                if (args.length == 1) {
                    s.sendMessage("§4§l✘ §7§l◆ §cUsage: /"+ cmd + " give [player] [item] ");
                    break;
                }
                if (args.length == 2) {
                    s.sendMessage("§4§l✘ §7§l◆ §cUsage: /"+ cmd + " give [player] [item] ");
                    s.sendMessage("§4§l✘ §7§l◆ §6Liste des items disponibles :");

                    List<String> items = ItemManager.getItems();
                    if (items != null) {
                        for (String i : items) {
                            s.sendMessage("§f-> §e" + i);
                            s.sendMessage("§7-> §e" + TextManager.formattedText(Main.getInstance().getConfig().getString("items." + i + ".display-name")));
                        }
                    } else {
                        s.sendMessage("§4§l✘ §7§l◆ §cAucun objet trouvé dans la configuration.");
                    }
                    break;
                }
                if (args.length == 3) {
                    String playerName = args[1];
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getName().equals(playerName)){
                            playerOnLine = true;
                            argPlayer = p;
                        }
                    }
                    if (!playerOnLine){
                        s.sendMessage("§4§l✘ §7§l◆ Le joueur n'est pas en ligne.");
                        break;
                    }

                    String the_item = args[2];
                    List<String> items = ItemManager.getItems();
                    if (items != null) {
                        for (String i : items) {
                            if (i.equals(the_item)) {
                                find = true;
                                break;
                            }
                        }
                    } else {
                        s.sendMessage("§4§l✘ §7§l◆ §cAucun objet trouvé dans la configuration.");
                    }

                    if (!find) {
                        s.sendMessage("§4§l✘ §7§l◆ §cL'objet n'existe pas.");
                        break;
                    }

                    //display name
                    String display_name = TextManager.formattedText(Main.getInstance().getConfig().getString("items." + the_item + ".display-name"));
                    //lore
                    List<String> lore = new ArrayList<>();
                    for (String line : Main.getInstance().getConfig().getStringList("items." + the_item + ".lore")) {
                        lore.add(line
                                .replace("%owner%", argPlayer.getName())
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



                    argPlayer.sendMessage(TextManager.formattedText("&2&l✔ &7&l◆ &aVous avez reçu " + display_name));
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
                    //give item
                    argPlayer.getInventory().addItem(CustomItem);
                    /*if (ItemManager.hasUsesInLore(CustomItem)){
                        ItemManager.setUsesInLore(CustomItem, Main.getInstance().getConfig().getInt("items." + the_item + ".uses"));
                    }
                     */
                    ItemManager.setUsesInLore(CustomItem, Main.getInstance().getConfig().getInt("items." + the_item + ".uses"));
                    break;
                }
                s.sendMessage("§4§l✘ §7§l◆ §cIl ne peut pas avoir plus de 2 argument après §4/"+ cmd + " give§c.");
                break;
            case "reload":
                s.sendMessage(TextManager.formattedText("&2&l✔ &7&l◆ &aconfig.yml rechargé."));
                s.sendMessage(TextManager.formattedText("&2&l✔ &7&l◆ &adata.yml rechargé."));
                Main.getInstance().reloadConfig();
                break;
        }

        return true;
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
}
