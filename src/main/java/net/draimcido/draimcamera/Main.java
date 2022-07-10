package net.draimcido.draimcamera;

import net.draimcido.draimcamera.Commands.MainCommands;
import net.draimcido.draimcamera.Commands.TabComplete;
import net.draimcido.draimcamera.Config.CameraConfig;
import net.draimcido.draimcamera.Config.MessageConfig;
import net.draimcido.draimcamera.Handlers.CameraHandler;
import net.draimcido.draimcamera.Utils.Camera.CameraMode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Класс Main.
 * Отвечает за запуск плагина.
 * И запускает все остальные классы.
 * @author DraimCiDo
 */
public final class Main extends JavaPlugin {

    public static FileConfiguration config;    // Статический метод для получения конфигурации плагина.
    private static Main instance;    // Статический метод инициализации плагина.
    private PluginDescriptionFile pdf;    // Метод для получение информации из plugin.yml.

    private CameraConfig config_cameras;    // Инициализация конфигурации камеры.
    private MessageConfig config_plugin;    // Инициализация конфигурации плагина.

    public HashMap<UUID, String> player_selected_camera = new HashMap<UUID, String>();    // Игрок который в данный момент видит камеру.
    public HashMap<UUID, CameraMode> player_camera_mode = new HashMap<UUID, CameraMode>();    // Метод выбора камеры для игрока.
    public HashMap<UUID, CameraHandler> player_camera_handler = new HashMap<UUID, CameraHandler>();    // Обработчик камеры под игрока.

    /**
     * Получение инстанса плагина.
     *
     * @return instance - инстанс плагина.
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Получение внутриной конфигурации плагина.
     *
     * @param path путь к параметру
     * @return - возвращение параметра.
     */
    public static int getConfigInt(String path) {
        return config.getInt(path);
    }


    /**
     * Получение информации из plugin.yml.
     *
     * @return - возвращение к plugin.yml
     */
    public PluginDescriptionFile getPluginDescriptionFile() {
        return this.pdf;
    }


    /**
     *  Метод для запуска плагина.
     */
    public void onEnable() {
        pdf = this.getDescription();         // Получение информации из plugin.yml.

        Bukkit.getServer().getPluginCommand("draimcamera").setExecutor((CommandExecutor) new MainCommands(this));        // Подгрузка команд плагина.
        Bukkit.getServer().getPluginCommand("draimcamera").setTabCompleter(new TabComplete(this));        // Подгрузка табуляции команд.

        setupConfig();        // Загрузка конфигурации плагина.

        getLogger().info("DraimCamera v" + pdf.getVersion() + " enabled!");        // Оповещение о запуске плагина.
    }

    /**
     * Метод для остановки плагина.
     */
    public void onDisable() {
        getLogger().info("DraimCamera v" + pdf.getVersion() + " disabled!");        // Оповещение о отключении плагина.
    }

    /**
     * Метод для загрузки конфигурации плагина.
     */
    private void setupConfig() {
        config_plugin = new MessageConfig(this);         // Подхват файла MessageConfig как конфигурации плагина.
        config_cameras = new CameraConfig(this);        // Подхват файла CameraConfig как конфигурации камеры.

        config_plugin.getConfig().set("version", null);
        config_cameras.getConfig().set("version", null);

        if (!config_plugin.getConfig().isSet("Camera-Effects.spectator-mode")) config_plugin.getConfig().set("Camera-Effects.spectator-mode", true);
        if (!config_plugin.getConfig().isSet("Camera-Effects.invisible")) config_plugin.getConfig().set("Camera-Effects.invisible", false);

        for (String camera_name : config_cameras.getCameras()) {
            List<String> points = config_cameras.getPoints(camera_name);
            List<String> new_point = new ArrayList<String>();
            for (String point : points) {
                if (!point.startsWith("location:") && !point.startsWith("command:")) {
                    point = "location:" + point;
                }

                if (point.startsWith("location:") && !(point.startsWith("location:linear:") || point.startsWith("location:teleport:"))) {
                    point = point.replaceFirst("location:", "location:linear:");
                }

                new_point.add(point);
            }
            config_cameras.getConfig().set("cameras." + camera_name + ".points", new_point);
        }

        config_plugin.getConfig().set("version", getPluginDescriptionFile().getVersion());
        config_plugin.saveConfig();        // Сохранение конфигурации плагина.

        config_cameras.getConfig().set("version", getPluginDescriptionFile().getVersion());
        config_cameras.saveConfig();         // Сохранение конфигурации камеры.
    }

    /**
     * Получение сообщения из конфигурации плагина.
     *
     * @return config_plugin - возвращение к конфигу.
     */
    public MessageConfig getMessageConfig() {
        return config_plugin;
    }

    /**
     * Получение сообщения из конфигурации камеры.
     *
     * @return config_cameras - возвращение к конфигу.
     */
    public CameraConfig getConfigCameras() {
        return config_cameras;
    }

}
