package fr.sigmaz.ninjasupraatouts.listener;

import fr.sigmaz.ninjasupraatouts.Main;
import fr.sigmaz.ninjasupraatouts.Manager.TextManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class CraftListeners implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack it = event.getCurrentItem();


        if (it.hasItemMeta() &&
            it.getItemMeta().hasDisplayName() &&
            it.getItemMeta().getDisplayName().equals(TextManager.formattedText(Main.getInstance().getConfig()
                    .getString("items.scepter.display-name")))){

            ItemMeta itemMeta = it.getItemMeta();

            List<String> lore = new ArrayList<>();
            for (String line : Main.getInstance().getConfig().getStringList("items.scepter.lore")) {
                lore.add(TextManager.formattedText(line
                        .replace("%owner%", player.getName())
                        .replace("%uses%", Main.getInstance().getConfig().getString("items.scepter.uses")))
                );
            }

            itemMeta.setLore(lore);
            it.setItemMeta(itemMeta);
        }
    }
}
