package fr.sigmaz.ninjasupraatouts.powers;

import fr.sigmaz.ninjasupraatouts.Main;
import fr.sigmaz.ninjasupraatouts.Manager.TextManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpectreTp {
    public static Map<String, Long> cooldowns = new HashMap<>();
    public static final long COOLDOWN_SECONDS = Main.getInstance().getConfig().getInt("items.scepter.delay");
    public static void teleportRandomly(Player player) {
        World world = player.getWorld();
        Location playerLocation = player.getLocation();

        int radius = Main.getInstance().getConfig().getInt("items.scepter.radius");
        int x = playerLocation.getBlockX() + getRandomOffset(radius);
        int z = playerLocation.getBlockZ() + getRandomOffset(radius);

        int y = world.getHighestBlockYAt(x, z);

        Location teleportLocation = new Location(world, x + 0.5, y+2, z + 0.5);
        player.teleport(teleportLocation);

        player.sendMessage(TextManager.formattedText(Main.getInstance().getConfig().getString("messages.scepter-use")));
    }

    public static int getRandomOffset(int radius) {
        Random random = new Random();
        return random.nextInt(radius * 2 + 1) - radius;
    }

    public static boolean checkCooldown(Player player) {
        if (cooldowns.containsKey(player.getName())) {
            long secondsLeft = ((cooldowns.get(player.getName()) / 1000) + COOLDOWN_SECONDS) - (System.currentTimeMillis() / 1000);
            return secondsLeft > 0;
        }
        return false;
    }

    public static void setCooldown(Player player) {
        cooldowns.put(player.getName(), System.currentTimeMillis());
    }

    public static long getCooldownTime(Player player) {
        if (cooldowns.containsKey(player.getName())) {
            long secondsLeft = ((cooldowns.get(player.getName()) / 1000) + COOLDOWN_SECONDS) - (System.currentTimeMillis() / 1000);
            return Math.max(0, secondsLeft);
        }
        return 0;
    }
}
