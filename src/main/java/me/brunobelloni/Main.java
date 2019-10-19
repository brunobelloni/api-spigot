package me.brunobelloni;

import me.brunobelloni.api.EventCaller;
import me.brunobelloni.api.commands.command.CommandManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private final PluginManager pluginManager = this.getServer().getPluginManager();

    private final CommandManager commandManager = new CommandManager(this); //where plugin is a plugin instance

    @Override
    public void onEnable() {
        // Automatically finds all classes that implements the CommandListener.class and registers their commands
        commandManager.registerCommands();
        //registers a generated help topic to bukkit
        commandManager.registerHelp();
        //so the /help PluginName displays our plugin's registered commands

        registraEventos();
    }

    @Override
    public void onDisable() {
    }

    /**
     * Registra os Eventos que precisam ser escutados pelo plugin
     */
    private void registraEventos() {
        pluginManager.registerEvents(new EventCaller(this), this);
        pluginManager.registerEvents(new ApiEventHandler(this), this);
    }

}
