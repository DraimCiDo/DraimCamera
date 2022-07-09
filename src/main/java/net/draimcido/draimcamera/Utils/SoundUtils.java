package net.draimcido.draimcamera.Utils;


import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * The type Sound utils.
 */
public class SoundUtils {

    /**
     * Play sound.
     *
     * @param player the player
     * @param sound  the sound
     */
    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

}
