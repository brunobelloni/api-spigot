package me.brunobelloni.api.event;

import me.brunobelloni.api.event.internal.LoaderUtils;
import me.brunobelloni.api.event.plugin.HelperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Optional;

/**
 * Base class for helper, which mainly just proxies calls to {@link Bukkit#getServer()} for convenience.
 */
public final class Helper {

    private Helper() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Gets the plugin which is "hosting" helper.
     *
     * @return the host plugin
     */
    public static HelperPlugin hostPlugin() {
        return LoaderUtils.getPlugin();
    }

    public static Server server() {
        return Bukkit.getServer();
    }

    public static ConsoleCommandSender console() {
        return server().getConsoleSender();
    }

    public static PluginManager plugins() {
        return server().getPluginManager();
    }

    public static ServicesManager services() {
        return server().getServicesManager();
    }

//    public static <T> T serviceNullable(Class<T> clazz) {
//        return Services.get(clazz).orElse(null);
//    }

//    public static <T> Optional<T> service(Class<T> clazz) {
//        return Services.get(clazz);
//    }

    public static BukkitScheduler bukkitScheduler() {
        return server().getScheduler();
    }

    public static void executeCommand(String command) {
        server().dispatchCommand(console(), command);
    }

    public static World worldNullable(String name) {
        return server().getWorld(name);
    }

    public static Optional<World> world(String name) {
        return Optional.ofNullable(worldNullable(name));
    }

}
