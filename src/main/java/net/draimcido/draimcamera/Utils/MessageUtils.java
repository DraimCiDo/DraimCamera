package net.draimcido.draimcamera.Utils;

import net.draimcido.draimcamera.Config.MessageConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.draimcido.draimcamera.Utils.ColorUtils.color;


/**
 * The type Message utils.
 */
public class MessageUtils {

    /**
     * Send message.
     *
     * @param message the message
     * @param p       the p
     */
    public static void sendMessage(String message, CommandSender p) {
        if (!(p instanceof Player player)) {
            p.sendMessage(ColorUtils.colorMessage(message
                    .replace("chat!", "")
                    .replace("title!", "")
                    .replace("actionbar!", "")
            ));
        } else {
            if (message.startsWith("chat!")) {
                sendChatMessage(message.replace("chat!", ""), player);
            } else if (message.startsWith("title!")) {
                sendTitleMessage(message.replace("title!", ""), player);
            } else if (message.startsWith("actionbar!")) {
                sendActionBarMessage(message.replace("actionbar!", ""), player);
            } else {
                sendChatMessage(message.replace("chat!", ""), player);
            }
        }
    }


    /**
     * Config operator string.
     *
     * @param message the message
     * @param player  the player
     * @param amount  the amount
     * @return the string
     */
    public static String ConfigOperator(String message, Player player, int amount) {
        String message1 = placeholder(message, player, amount);
        String message2 = color(message1);
        return message2;
    }

    /**
     * Placeholder string.
     *
     * @param message the message
     * @param player  the player
     * @param amount  the amount
     * @return the string
     */
    public static String placeholder(String message, Player player, int amount) {
        String message1 = message.replace("%player%", player.getName());
        String message2 = message1.replace("%amount%", String.valueOf(amount));
        return message2;
    }

    /**
     * Send chat message.
     *
     * @param message the message
     * @param player  the player
     */
    public static void sendChatMessage(String message, Player player) {
        player.sendMessage(ColorUtils.colorMessage(message));
    }

    /**
     * Send title message.
     *
     * @param message the message
     * @param player  the player
     */
    public static void sendTitleMessage(String message, Player player) {
        player.sendTitle(ColorUtils.colorMessage(message), "", 20, 80, 20);
    }

    /**
     * Send action bar message.
     *
     * @param message the message
     * @param player  the player
     */
    public static void sendActionBarMessage(String message, Player player) {
        BaseComponent component = ComponentSerializer.parse(ColorUtils.colorBungee(message))[0];
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
    }

    /**
     * To check message string.
     *
     * @param message the message
     * @return the string
     */
    public static String toCheckMessage(String message) {
        return ChatColor.stripColor(message).toLowerCase();
    }


}
