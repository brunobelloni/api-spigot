package me.brunobelloni;

import me.brunobelloni.api.actionbar.Title;
import me.brunobelloni.api.commands.command.CommandListener;
import me.brunobelloni.api.commands.command.CommandManager;
import me.brunobelloni.api.event.Events;
import me.brunobelloni.snowball.eventos.InGame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
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
        InGame inGame = new InGame(this);
        inGame.execute();


        Events.subscribe(PlayerJoinEvent.class)
                .handler(e -> {
                    e.setJoinMessage("");
                    Player player = e.getPlayer();

                    Title title = new Title("Bem-vindo", "teste", 1, 5, 1);
                    title.send(player);
                });
    }

    @Override
    public void onDisable() {
    }
}
