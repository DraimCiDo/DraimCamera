package net.draimcido.draimcamera.Handlers;

import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CameraMode;
import net.draimcido.draimcamera.Utils.Camera.CameraUtils;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Camera handler.
 */
public class CameraHandler extends BukkitRunnable {

    private int single_frame_duration_ms = 50;
    private int ticks = 0;

    private Main plugin;
    private Player player;
    private String camera_name;
    private ArrayList<Location> camera_points = new ArrayList<Location>();
    private HashMap<Integer, ArrayList<String>> camera_commands = new HashMap<Integer, ArrayList<String>>();

    private GameMode privious_gamemode;
    private Location privious_player_location;
    private boolean previous_invisible;

    private ArrayList<String> HiddenPlayers = new ArrayList<String>();

    /**
     * Instantiates a new Camera handler.
     *
     * @param plugin      the plugin
     * @param player      the player
     * @param camera_name the camera name
     */
    public CameraHandler(Main plugin, Player player, String camera_name) {
        this.plugin = plugin;
        this.player = player;
        this.camera_name = camera_name;
    }

    /**
     * Generate path camera handler.
     *
     * @return the camera handler
     */
    public CameraHandler generatePath() {
        int max_points = (this.plugin.getConfigCameras().getDuration(this.camera_name) * 1000) / this.single_frame_duration_ms;

        List<String> raw_camera_points = this.plugin.getConfigCameras().getPoints(this.camera_name);
        List<String> raw_camera_move_points = getMovementPoints(raw_camera_points);

        if (raw_camera_move_points.size() - 1 == 0) {
            for (int j = 0; j < max_points; j++) {
                this.camera_points.add(CameraUtils.deserializeLocation(raw_camera_points.get(0).split(":", 2)[1]));
            }
        } else {
            for (int i = 0; i < raw_camera_move_points.size() -1; i++) {
                String raw_point = raw_camera_move_points.get(i).split(":", 2)[1];
                String raw_point_next = raw_camera_move_points.get(i + 1).split(":", 2)[1];
                String easing = raw_camera_move_points.get(i).split(":", 2)[0];

                Location point = CameraUtils.deserializeLocation(raw_point);
                Location point_next = CameraUtils.deserializeLocation(raw_point_next);

                this.camera_points.add(point);
                for (int j = 0; j < max_points / (raw_camera_move_points.size() - 1); j++) {
                    if (easing.equalsIgnoreCase("linear")) {
                        this.camera_points.add(translateLinear(point, point_next, j, max_points / (raw_camera_move_points.size() - 1) - 1));
                    }
                    if (easing.equalsIgnoreCase("teleport")) {
                        this.camera_points.add(point_next);
                    }
                }
            }
        }

        int command_index = 0;
        for (String raw_point : raw_camera_points) {
            String type = raw_point.split(":", 3)[0];
            String data = raw_point.split(":", (type == "location" ? 3 : 2))[type == "location" ? 2 : 1];

            if (type.equalsIgnoreCase("location")) {
                command_index += 1;
            }

            if (type.equalsIgnoreCase("command")) {
                int index = ((command_index) * max_points / (raw_camera_points.size()) -1);
                index = command_index == 0 ? 0 : index - 1;
                index = index < 0 ? 0 : index;
                if (!this.camera_commands.containsKey(index)) this.camera_commands.put(index, new ArrayList<String>());
                this.camera_commands.get(index).add(data);
            }
        }

        return this;
    }

    private List<String> getMovementPoints(List<String> raw_camera_points) {
        List<String> output = new ArrayList<String>();
        for (String raw_point : raw_camera_points) {
            String[] point_data = raw_point.split(":", 2);
            if (point_data[0].equalsIgnoreCase("location")) {
                output.add(point_data[1]);
            }
        }
        return output;
    }

    private Location translateLinear(Location point, Location point_next, int progress, int progress_max) {
        if (!point.getWorld().getUID().toString().equals(point_next.getWorld().getUID().toString())) {
            return point_next;
        }

        Location new_point = new Location(point_next.getWorld(), point.getX(), point.getY(), point.getZ());

        new_point.setX(calculateProgress(point.getX(), point_next.getX(), progress, progress_max));
        new_point.setY(calculateProgress(point.getY(), point_next.getY(), progress, progress_max));
        new_point.setZ(calculateProgress(point.getZ(), point_next.getZ(), progress, progress_max));
        new_point.setYaw((float) calculateProgress(point.getYaw(), point_next.getYaw(), progress, progress_max));
        new_point.setPitch((float) calculateProgress(point.getPitch(), point_next.getPitch(), progress, progress_max));


        return new_point;
    }

    private double calculateProgress(double start, double end, int progress, int progress_max) {
        return start + ((double) progress / (double) progress_max) * (end - start);
    }

    private Vector calculateVelocity(Location start, Location end) {
        return new Vector(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ());
    }


    /**
     * Убирает других игроков, когда игрок находиться в режиме камеры.
     */
    private void HideOtherPlayers(String yes_or_no) {
        for (Player other : Bukkit.getServer().getOnlinePlayers()) {

            if (yes_or_no.equalsIgnoreCase("no")) {
                player.showPlayer(other);
            }
            if (yes_or_no.equalsIgnoreCase("yes")) {
                player.hidePlayer(other);
            }
        }
    }

    /**
     * Убирает показ игрока, когда он находиться в режиме камеры.
     */
    private void HidePlayer(String yes_or_no) {
        for (Player pls : Bukkit.getOnlinePlayers()) {

            if (yes_or_no.equalsIgnoreCase("no")) {
                HiddenPlayers.add(player.getName());
                pls.showPlayer(player);
            }
            if (yes_or_no.equalsIgnoreCase("yes")) {
                HiddenPlayers.remove(player.getName());
                pls.hidePlayer(player);
            }
        }
    }


    /**
     * Start camera handler.
     *
     * @return the camera handler
     */
    public CameraHandler start() {
        this.privious_gamemode = this.player.getGameMode();
        this.privious_player_location = this.player.getLocation();
        this.previous_invisible = CameraUtils.isPlayerInvisible(this.player);

        if (this.plugin.getConfig().getBoolean("Camera-Effects.spectator-mode")) this.player.setGameMode(GameMode.SPECTATOR);
        if (this.plugin.getConfig().getBoolean("Camera-Effects.invisible")) player.setInvisible(true);
        if (this.plugin.getConfig().getBoolean("Camera-Effects.cinematic-mode")) player.getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN, 1));
        if (this.plugin.getConfig().getBoolean("Camera-Effects.hide-other-players")) HideOtherPlayers("yes");
        if (this.plugin.getConfig().getBoolean("Camera-Effects.hide-player")) HidePlayer("yes");

        this.plugin.player_camera_mode.put(this.player.getUniqueId(), CameraMode.VIEW);
        runTaskTimer(this.plugin, 1L, 1L);
        if (camera_points.size() > 0) {
            player.teleport(camera_points.get(0));
        }

        if (!this.player.hasPermission("draimcamera.bypass")) this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Messages.Camera.viewing-start")));

        return this;
    }

    /**
     * Stop camera handler.
     *
     * @return the camera handler
     */
    public CameraHandler stop() {
        plugin.player_camera_mode.put(player.getUniqueId(), CameraMode.NONE);
        try {
            this.cancel();
        } catch (Exception e) {
        }

        player.teleport(privious_player_location);
        if (this.plugin.getConfig().getBoolean("Camera-Effects.spectator-mode")) player.setGameMode(privious_gamemode);
        if (this.plugin.getConfig().getBoolean("Camera-Effects.invisible")) player.setInvisible(previous_invisible);
        if (this.plugin.getConfig().getBoolean("Camera-Effects.cinematic-mode")) player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        if (this.plugin.getConfig().getBoolean("Camera-Effects.hide-other-players")) HideOtherPlayers("no");
        if (this.plugin.getConfig().getBoolean("Camera-Effects.hide-player")) HidePlayer("no");

        if (!this.player.hasPermission("draimcamera.bypass")) this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Messages.Camera.viewing-stop")));

        return this;
    }

    public void run() {
        if (plugin.player_camera_mode.get(player.getUniqueId()) == CameraMode.VIEW) {
            if (this.ticks > camera_points.size() - 2) {
                this.stop();
                return;
            }

            Location current_pos = camera_points.get(this.ticks);
            Location next_pos = camera_points.get(this.ticks + 1);

            player.teleport(camera_points.get(this.ticks));

            if (camera_commands.containsKey(this.ticks)) {
                for (String cmd : camera_commands.get(this.ticks)) {
                    String command = cmd.replaceAll("%player%", player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            }

            player.setVelocity(calculateVelocity(current_pos, next_pos));
            this.ticks += 1;
        } else {
            if (plugin.player_camera_mode.get(player.getUniqueId()) == CameraMode.NONE) return;
            player.teleport(privious_player_location);
            if (this.plugin.getConfig().getBoolean("Camera-Effects.spectator-mode")) player.setGameMode(privious_gamemode);
            if (this.plugin.getConfig().getBoolean("Camera-Effects.invisible")) player.setInvisible(previous_invisible);
            if (this.plugin.getConfig().getBoolean("Camera-Effects.cinematic-mode")) player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
            if (this.plugin.getConfig().getBoolean("Camera-Effects.hide-other-players")) HideOtherPlayers("no");
            if (this.plugin.getConfig().getBoolean("Camera-Effects.hide-player")) HidePlayer("no");

            plugin.player_camera_mode.put(player.getUniqueId(), CameraMode.NONE);
            player.sendMessage(ChatColor.WHITE + "Preview stopped.");
        }
    }
}
