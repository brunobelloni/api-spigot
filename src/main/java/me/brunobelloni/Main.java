package me.brunobelloni;

import me.brunobelloni.api.ArenaCommand;
import me.brunobelloni.api.EventCaller;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Main extends JavaPlugin {

    private Field bukkitCommandMap;
    private CommandMap commandMap;
    private PluginManager pluginManager = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        registraEventos();

        try {
            registraComandos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
    }

    private void registraEventos() {
        pluginManager.registerEvents(new EventCaller(this), this);
        pluginManager.registerEvents(new ApiEventHandler(this), this);
    }

    private void registraComandos() throws NoSuchFieldException, IllegalAccessException {
        this.bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        this.bukkitCommandMap.setAccessible(true);
        this.commandMap = (CommandMap) this.bukkitCommandMap.get(Bukkit.getServer());

        this.commandMap.register("arena", new ArenaCommand("arena"));
    }
}
