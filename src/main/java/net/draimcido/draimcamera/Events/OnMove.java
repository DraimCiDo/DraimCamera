package net.draimcido.draimcamera.Events;

import net.draimcido.draimcamera.Main;
import net.draimcido.draimcamera.Utils.Camera.CameraMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMove implements Listener {

    private Main plugin;

    public OnMove(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (plugin.player_camera_mode.get(event.getPlayer().getUniqueId()) != null) {
            if (plugin.player_camera_mode.get(event.getPlayer().getUniqueId()) == CameraMode.PREVIEW) {
                event.setCancelled(true);
            }
        }
    }
}
