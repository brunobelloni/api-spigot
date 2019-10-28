package me.brunobelloni;

import me.brunobelloni.api.commands.command.CommandListener;
import me.brunobelloni.api.commands.command.CommandManager;
import me.brunobelloni.snowball.eventos.GameEvent;
import me.brunobelloni.snowball.eventos.InGame;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    /**
     * Controlador de comandos
     */
    private final CommandManager commandManager = new CommandManager(this);

    @Override
    public void onEnable() {
        /**
         * Inicializa todos os comandos registrados
         *
         * A api busca pelos comandos por meio das classes que implementam
         * a interface {@link CommandListener}
         */
        commandManager.registerCommands();
        commandManager.registerHelp();
    
        /**
         * Registra os eventos que devem ser escutados
         */
        GameEvent inGame = new InGame(this);
    
        inGame.execute();
    }

    @Override
    public void onDisable() {
    }
}
