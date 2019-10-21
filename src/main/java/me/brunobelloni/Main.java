package me.brunobelloni;

import me.brunobelloni.api.commands.command.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private final CommandManager commandManager = new CommandManager(this);
    private final ApiEventHandler apiEventHandler = new ApiEventHandler();

    @Override
    public void onEnable() {
        commandManager.registerCommands();
        commandManager.registerHelp();

        apiEventHandler.implement();
    }

    @Override
    public void onDisable() {
    }
}
