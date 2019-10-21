package me.brunobelloni.api.event.plugin;

import me.brunobelloni.api.event.terminable.TerminableConsumer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

import java.io.File;

public interface HelperPlugin extends Plugin, TerminableConsumer {

    /**
     * Register a listener with the server.
     *
     * <p>{@link me.brunobelloni.api.event.Events} should be used instead of this method in most cases.</p>
     *
     * @param listener the listener to register
     * @param <T>      the listener class type
     * @return the listener
     */

    <T extends Listener> T registerListener(T listener);

    /**
     * Registers a CommandExecutor with the server
     *
     * @param command the command instance
     * @param aliases the command aliases
     * @param <T>     the command executor class type
     * @return the command executor
     */

    default <T extends CommandExecutor> T registerCommand(T command, String... aliases) {
        return registerCommand(command, null, null, null, aliases);
    }

    /**
     * Registers a CommandExecutor with the server
     *
     * @param command           the command instance
     * @param permission        the command permission
     * @param permissionMessage the message sent when the sender doesn't the required permission
     * @param description       the command description
     * @param aliases           the command aliases
     * @param <T>               the command executor class type
     * @return the command executor
     */

    <T extends CommandExecutor> T registerCommand(T command, String permission, String permissionMessage, String description, String... aliases);

    /**
     * Gets a service provided by the ServiceManager
     *
     * @param service the service class
     * @param <T>     the class type
     * @return the service
     */

    <T> T getService(Class<T> service);

    /**
     * Provides a service to the ServiceManager, bound to this plugin
     *
     * @param clazz    the service class
     * @param instance the instance
     * @param priority the priority to register the service at
     * @param <T>      the service class type
     * @return the instance
     */

    <T> T provideService(Class<T> clazz, T instance, ServicePriority priority);

    /**
     * Provides a service to the ServiceManager, bound to this plugin at {@link ServicePriority#Normal}.
     *
     * @param clazz    the service class
     * @param instance the instance
     * @param <T>      the service class type
     * @return the instance
     */

    <T> T provideService(Class<T> clazz, T instance);

    /**
     * Gets if a given plugin is enabled.
     *
     * @param name the name of the plugin
     * @return if the plugin is enabled
     */
    boolean isPluginPresent(String name);

    /**
     * Gets a plugin instance for the given plugin name
     *
     * @param name        the name of the plugin
     * @param pluginClass the main plugin class
     * @param <T>         the main class type
     * @return the plugin
     */
    <T> T getPlugin(String name, Class<T> pluginClass);

    /**
     * Gets a bundled file from the plugins resource folder.
     *
     * <p>If the file is not present, a version of it it copied from the jar.</p>
     *
     * @param name the name of the file
     * @return the file
     */

    File getBundledFile(String name);

    /**
     * Loads a config file from a file name.
     *
     * <p>Behaves in the same was as {@link #getBundledFile(String)} when the file is not present.</p>
     *
     * @param file the name of the file
     * @return the config instance
     */

    YamlConfiguration loadConfig(String file);

    /**
     * Populates a config object.
     *
     * @param file         the name of the file
     * @param configObject the config object
     * @param <T>          the config object type
     */

    <T> T setupConfig(String file, T configObject);

    /**
     * Gets the plugin's class loader
     *
     * @return the class loader
     */

    ClassLoader getClassloader();

}
