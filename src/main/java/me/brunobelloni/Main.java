package me.brunobelloni;

import me.brunobelloni.api.commands.command.CommandManager;
import me.brunobelloni.snowball.eventos.InGame;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private final CommandManager commandManager = new CommandManager(this);
    private final ApiEventHandler apiEventHandler = new ApiEventHandler();

    @Override
    public void onEnable() {
        commandManager.registerCommands();
        commandManager.registerHelp();

        apiEventHandler.implement();

        new InGame(this).execute();
    }

    @Override
    public void onDisable() {
    }
}
