package me.brunobelloni;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private PluginManager pluginManager = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        this.pluginManager.registerEvents(new Event(), this);
    }

    @Override
    public void onDisable() {
    }
}
