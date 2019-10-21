package me.brunobelloni.api.event.internal;

import me.brunobelloni.api.event.Helper;
import me.brunobelloni.api.event.plugin.HelperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Provides the instance which loaded the helper classes into the server
 */
public final class LoaderUtils {
    private static HelperPlugin plugin = null;
    private static Thread mainThread = null;

    private LoaderUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static synchronized HelperPlugin getPlugin() {
        if (plugin == null) {
            JavaPlugin pl = JavaPlugin.getProvidingPlugin(LoaderUtils.class);
            if (!(pl instanceof HelperPlugin)) {
                throw new IllegalStateException("helper providing plugin does not implement HelperPlugin: " + pl.getClass().getName());
            }
            plugin = (HelperPlugin) pl;

            String pkg = LoaderUtils.class.getPackage().getName();
            pkg = pkg.substring(0, pkg.length() - ".internal".length());

            Bukkit.getLogger().info("[helper] helper (" + pkg + ") bound to plugin " + plugin.getName() + " - " + plugin.getClass().getName());

            setup();
        }

        return plugin;
    }

    public static Set<Plugin> getHelperImplementationPlugins() {
        return Stream.concat(
               Stream.<Plugin>of(getPlugin()),
               Arrays.stream(Helper.plugins().getPlugins())
                      .filter(pl -> pl.getClass().isAnnotationPresent(HelperImplementationPlugin.class))
        ).collect(Collectors.toSet());
    }

    public static Set<HelperPlugin> getHelperPlugins() {
        return Stream.concat(
               Stream.of(getPlugin()),
               Arrays.stream(Helper.plugins().getPlugins())
                      .filter(pl -> pl instanceof HelperPlugin)
                      .map(pl -> (HelperPlugin) pl)
        ).collect(Collectors.toSet());
    }

    public static synchronized Thread getMainThread() {
        if (mainThread == null) {
            if (Bukkit.getServer().isPrimaryThread()) {
                mainThread = Thread.currentThread();
            }
        }
        return mainThread;
    }

    // performs an intial setup for global handlers
    private static void setup() {

        // cache main thread in this class
        getMainThread();
    }

}
