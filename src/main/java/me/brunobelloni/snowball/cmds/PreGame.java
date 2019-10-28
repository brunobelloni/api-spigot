package me.brunobelloni.snowball.cmds;

import me.brunobelloni.api.commands.command.CommandHandler;
import me.brunobelloni.api.commands.command.CommandListener;
import me.brunobelloni.api.commands.command.Flag;
import me.brunobelloni.api.commands.command.objects.CommandInfo;

public class PreGame implements CommandListener {
    
    /**
     *
     */
    @CommandHandler(
           command = "snowball.set",
           flags = {@Flag(flag = 'k', usage = "-k resets kingdoms"), @Flag(flag = 'r', usage = "-r resets reficules")},
           strictArgs = true,
           description = "resets stuff!")
    public static void testingCommand3(final CommandInfo info) {
        // TODO
        
    }
    
}