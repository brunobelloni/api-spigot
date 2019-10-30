package me.brunobelloni.snowball.cmds;

import me.brunobelloni.api.commands.command.CommandHandler;
import me.brunobelloni.api.commands.command.CommandListener;
import me.brunobelloni.api.commands.command.objects.CommandInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static me.brunobelloni.snowball.Utils.Snowball.*;
import static me.brunobelloni.snowball.Utils.SnowballLocation.setLeaveLocation;
import static me.brunobelloni.snowball.Utils.SnowballLocation.setSnowballLocation;

public class PreGame implements CommandListener {

    /**
     * Comando para entrar/sair na arena
     */
    @CommandHandler(command = "snowball", playerOnly = true)
    public static void snowball(final CommandInfo info) {
        Player player = info.getPlayer();

        if (playerIsInSnowball(player)) {
            removePlayerToSnowball(player);
        } else {
            addPlayerToSnowball(player);
        }
    }

    @CommandHandler(command = "snowball.setspawn", description = "ops", playerOnly = true)
    public static void setSpawn(final CommandInfo info) {
        Player player = info.getPlayer();

        if (player.isOp()) {
            setSnowballLocation(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "Você setou o spawn!");
        } else {
            player.sendMessage(ChatColor.RED + "Você não tem permissão!");
        }
    }

    @CommandHandler(command = "snowball.setleave", description = "ops", playerOnly = true)
    public static void setLeave(final CommandInfo info) {
        Player player = info.getPlayer();

        if (player.isOp()) {
            setLeaveLocation(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "Você setou o leave!");
        } else {
            player.sendMessage(ChatColor.RED + "Você não tem permissão!");
        }
    }
}