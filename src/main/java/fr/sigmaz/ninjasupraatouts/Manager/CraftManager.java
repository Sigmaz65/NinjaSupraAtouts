package fr.sigmaz.ninjasupraatouts.Manager;

import fr.sigmaz.ninjasupraatouts.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.security.Key;

import static org.bukkit.Bukkit.getServer;

public class CraftManager {

    public static void craftScepter() {

        ShapedRecipe scepter = new ShapedRecipe(ItemManager.getItemJustWithDisplayName("scepter"));

        scepter.shape("123","456","789");
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it0").equals("AIR")) {
            scepter.setIngredient('1', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it0")));
        }
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it1").equals("AIR")) {
            scepter.setIngredient('2', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it1")));
        }
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it2").equals("AIR")) {
            scepter.setIngredient('3', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it2")));
        }
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it3").equals("AIR")) {
            scepter.setIngredient('4', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it3")));
        }
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it4").equals("AIR")) {
            scepter.setIngredient('5', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it4")));
        }
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it5").equals("AIR")) {
            scepter.setIngredient('6', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it5")));
        }
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it6").equals("AIR")) {
            scepter.setIngredient('7', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it6")));
        }
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it7").equals("AIR")) {
            scepter.setIngredient('8', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it7")));
        }
        if (!Main.getInstance().getConfig().getString("items.scepter.craft.it8").equals("AIR")) {
            scepter.setIngredient('9', Material.getMaterial(Main.getInstance().getConfig().getString("items.scepter.craft.it8")));
        }


        getServer().addRecipe(scepter);
    }
}
