package me.brunobelloni.api;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

import static me.brunobelloni.api.Arena.addPlayerToArena;
import static me.brunobelloni.api.Arena.removePlayerFromArena;
import static me.brunobelloni.api.Configuration.*;

public class ArenaCommand extends BukkitCommand {

    public ArenaCommand(String name) {
        super(name);
    }

    public ArenaCommand(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Não utilize esse comando do console!");
            return false;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            addPlayerToArena(player);
        } else if (args.length == 1) {
            if ("join".equals(args[0])) {
                addPlayerToArena(player);
            } else if ("leave".equals(args[0])) {
                removePlayerFromArena(player);
            } else if (player.isOp()) {
                if ("setspawn".equals(args[0])) {
                    setArenaSpawn(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "Você setou o Spawn da Arena");
                } else if ("setloc1".equals(args[0])) {
                    setArenaLoc1(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "Você setou o Loc1 da Arena");
                } else if ("setloc2".equals(args[0])) {
                    setArenaLoc2(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "Você setou o Loc2 da Arena");
                } else if ("setcommomspawn".equals(args[0])) {
                    player.sendMessage(ChatColor.GREEN + "Você setou o CommomSpawn da Arena");
                    setCommomSpawn(player.getLocation());
                } else if ("setall".equals(args[0])) {
                    player.sendMessage(ChatColor.GREEN + "Você setou todas as posições");
                    setArenaSpawn(player.getLocation());
                    setArenaLoc1(player.getLocation());
                    setArenaLoc2(player.getLocation());
                    setCommomSpawn(player.getLocation());
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /arena (setspawn/setloc1/setloc2/setcommomspawn)");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /arena (join/leave)");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Usage: /arena (setspawn/setloc1/setloc2/setcommomspawn)");
        }

        return true;
    }
}
