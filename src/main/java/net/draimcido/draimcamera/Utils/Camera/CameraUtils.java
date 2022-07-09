package net.draimcido.draimcamera.Utils.Camera;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Camera utils.
 */
public class CameraUtils {

    /**
     * Serialize location string.
     *
     * @param loc the loc
     * @return the string
     */
    public static String serializeLocation(Location loc) {
        return loc.getWorld().getUID() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
    }

    /**
     * Deserialize location location.
     *
     * @param loc the loc
     * @return the location
     */
    public static Location deserializeLocation(String loc) {
        String[] input_split = loc.split(";");

        UUID world_uid = UUID.fromString(input_split[0]);

        double x = Double.parseDouble(input_split[1]);
        double y = Double.parseDouble(input_split[2]);
        double z = Double.parseDouble(input_split[3]);

        float yaw = Float.parseFloat(input_split[4]);
        float pitch = Float.parseFloat(input_split[5]);

        World world = Bukkit.getServer().getWorld(world_uid);
        if (world == null) {
            world = Bukkit.getServer().getWorlds().get(0);
        }

        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Time string to seconds convertor int.
     *
     * @param time_input the time input
     * @return the int
     */
    public static int timeStringToSecondsConvertor(String time_input) {
        Matcher regex_int = Pattern.compile("^\\d[^a-zA-Z]{0,1}$").matcher(time_input);

        Matcher regex_seconds = Pattern.compile("\\d+[sS]").matcher(time_input);
        Matcher regex_minutes = Pattern.compile("\\d+[mM]").matcher(time_input);
        Matcher regex_hours = Pattern.compile("\\d+[hH]").matcher(time_input);

        int seconds = 0;

        if (regex_int.find()) {
            seconds = Integer.parseInt(time_input);
        } else {
            if (regex_seconds.find()) {
                seconds += Integer.parseInt(time_input.substring(regex_seconds.start(), time_input.length() - 1));
            }
            if (regex_minutes.find()) {
                seconds += Integer.parseInt(time_input.substring(regex_minutes.start(), time_input.length() - 1)) * 60;
            }
            if (regex_hours.find()) {
                seconds += Integer.parseInt(time_input.substring(regex_hours.start(), time_input.length() - 1)) * 3600;
            }
        }

        return seconds;
    }


    /**
     * Is player invisible boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public static boolean isPlayerInvisible(Player player) {
        try {
            return player.isInvisible();
        } catch (NoSuchMethodError e) {
            return player.hasPotionEffect(PotionEffectType.INVISIBILITY);
        }
    }
}
