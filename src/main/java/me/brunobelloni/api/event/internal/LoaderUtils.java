package me.brunobelloni.api.event.internal;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


/**
 * Provides the instance which loaded the helper classes into the server
 */
public final class LoaderUtils {

    private LoaderUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static synchronized Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("ArenaDuel");
    }
}
