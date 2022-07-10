package net.draimcido.draimcamera.Events;

import net.draimcido.draimcamera.Handlers.CameraHandler;
import net.draimcido.draimcamera.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Random;

public class OnJoin implements Listener {

    private Main plugin;

    public OnJoin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            if (!event.getPlayer().hasPermission("draimcamera.bypass.joincamera")) {
                if (this.plugin.getConfigCameras().addPlayer(event.getPlayer().getUniqueId()) || !this.plugin.getMessageConfig().getConfig().getBoolean("On-Join.show-once")) {
                    List<String> joinCameras = this.plugin.getMessageConfig().getConfig().getStringList("On-Join.random-player-camera-path");
                    Random rand = new Random();
                    String camera_name = joinCameras.get(rand.nextInt(joinCameras.size()));
                    if (camera_name.length() > 0) {
                        if (this.plugin.getConfigCameras().camera_exists(camera_name)) {
                            this.plugin.player_camera_handler.put(event.getPlayer().getUniqueId(), new CameraHandler(plugin, event.getPlayer(), camera_name)).generatePath().start();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ignore
        }

        try {
            if (event.getPlayer().isInvisible()) {
                event.getPlayer().setInvisible(false);
            }
        } catch (Exception e) {
            // Ignore
        }
    }
}
