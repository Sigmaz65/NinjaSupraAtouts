package fr.sigmaz.ninjasupraatouts.listener;

import fr.sigmaz.ninjasupraatouts.Main;
import fr.sigmaz.ninjasupraatouts.Manager.ItemManager;
import fr.sigmaz.ninjasupraatouts.Manager.TextManager;
import fr.sigmaz.ninjasupraatouts.powers.SpectreTp;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.xml.soap.Text;

public class ItemListeners implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        ItemStack it = e.getItem();

        if (it != null) {

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

                if (it.getType() == Material.BLAZE_ROD){

                    if (it.hasItemMeta() && it.getItemMeta().hasDisplayName()){

                        if (it.getItemMeta().getDisplayName().equals(TextManager.formattedText(Main.getInstance()
                                .getConfig().getString("items.scepter.display-name")))) {
                            if (ItemManager.getUsesFromLore(it) > 0) {
                                if (!SpectreTp.checkCooldown(player)) {
                                    SpectreTp.teleportRandomly(player);
                                    ItemManager.setUsesInLore(it, (ItemManager.getUsesFromLore(it) - 1));
                                    SpectreTp.setCooldown(player);
                                }else{
                                    long secondsLeft = SpectreTp.getCooldownTime(player);
                                    String second = "";
                                    if (secondsLeft > 1) {
                                        second = Main.getInstance().getConfig().getString("times.seconds");
                                    }else{
                                        second = Main.getInstance().getConfig().getString("times.second");
                                    }
                                    player.sendMessage(TextManager.formattedText(Main.getInstance().getConfig()
                                            .getString("messages.scepter-cooldown").replace("%cooldown%", "" + secondsLeft).replace("%second%", second)));
                                }
                            }else{
                                player.sendMessage(TextManager.formattedText(Main.getInstance().getConfig().getString("messages.scepter-broken")));
                            }
                        }
                    }

                }
            }
        }
    }
}
