/*
 * Copyright (c) 2026 NicDevTV
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
package tv.nicdev.projectportal.infra.config;

import java.io.InputStream;
import java.util.Properties;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigService {
    private static ConfigService instance;
    private final JavaPlugin plugin;
    private final Properties buildFlags = new Properties();

    public ConfigService(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public void load() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        loadBuildFlags();
    }

    public boolean isExperimentalBuild() {
        return Boolean.parseBoolean(buildFlags.getProperty("experimentalBuild", "false"));
    }

    public String buildId() {
        return buildFlags.getProperty("buildId", "");
    }

    public static ConfigService get() {
        if (instance == null) {
            throw new IllegalStateException("ConfigService is not initialized yet.");
        }
        return instance;
    }

    private void loadBuildFlags() {
        buildFlags.clear();
        try (InputStream inputStream = plugin.getResource("build-flags.properties")) {
            if (inputStream != null) {
                buildFlags.load(inputStream);
            }
        } catch (Exception ignored) {
        }
    }
}
