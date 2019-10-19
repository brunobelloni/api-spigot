package me.brunobelloni.api.commands.command.handler;

import me.brunobelloni.api.commands.command.CommandHandler;
import me.brunobelloni.api.commands.command.Flag;
import me.brunobelloni.api.commands.command.objects.*;
import org.bukkit.ChatColor;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

/**
 * @author Richmond Steele, William Reed, kh498
 * @since 12/18/13 All rights Reserved Please read included LICENSE file
 */
public class DefaultHandler implements Handler {

    private final QueuedCommand queue;

    public DefaultHandler(final QueuedCommand queue) {
        this.queue = queue;
    }

    private static void sendHelpScreen(final CommandInfo info, final String errorMsg) {
        info.getSender().sendMessage(ChatColor.RED + errorMsg);
        RegisteredCommand.displayDefaultUsage(info);
    }

    @Override
    public void handleCommand(final CommandInfo info) throws CommandException {
        final List<String> args = info.getArgs();
        final ParentCommand parentCommand = info.getParentCommand();
        if (args.size() == 0 || parentCommand.getAllChildCommands().size() == 0) {
            //noinspection VariableNotUsedInsideIf
            if (this.queue != null) {
                sendCommand(info);
            } else {
                RegisteredCommand.displayDefaultUsage(info);
            }
        } else if (args.size() > 0) {
            if (("help".equalsIgnoreCase(args.get(0)) && !parentCommand.getAllChildCommands().containsKey("help")) ||
                    ("?".equalsIgnoreCase(args.get(0)) && !parentCommand.getAllChildCommands().containsKey("?"))) {
//                final CommandHandler ch = this.queue.getMethod().getAnnotation(CommandHandler.class);
                RegisteredCommand.displayDefaultUsage(info);
                return;
            }
            final ChildCommand child = parentCommand.getAllChildCommands().get(args.get(0));
            if (child == null) {
                //needed to send parent command instead of throwing errors so that parent command can process args
                try {
                    sendCommand(info);
                } catch (final CommandException e) {
                    e.printStackTrace();
                }
                return;
            }
            if (!child.checkPermission(info.getSender())) {
                info.getSender().sendMessage(ChatColor.RED + child.getCommandHandler().noPermission());
                return;
            }

            final CommandInfo cmdInfo = new CommandInfo(
                    info.getRegisteredCommand(), child, child.getCommandHandler(), info.getSender(),
                    args.get(0), args.size() == 1 ? Collections.<String>emptyList() : args.subList(1, args.size()),
                    info.getUsage(), info.getPermission()
            );

            try {
                child.getHandler().handleCommand(cmdInfo);
            } catch (final CommandException e) {
                info.getSender().sendMessage(ChatColor.RED + "Failed to handle command properly.");
            }
        }
    }

    private void sendCommand(final CommandInfo info) throws CommandException {
        final CommandHandler ch = this.queue.getMethod().getAnnotation(CommandHandler.class);

        if (ch.strictArgs() && info.getArgsLength() == 0 &&
                (info.getCommandHandler().flags().length != 0 || ch.max() == 0)) {
            RegisteredCommand.displayDefaultUsage(info);
            return;
        }

        if (info.getArgsLength() < info.getCommandHandler().min()) {
            sendHelpScreen(info, "Too few arguments.");
            return;
        }
        if (info.getCommandHandler().max() != -1 && info.getArgsLength() > info.getCommandHandler().max()) {
            sendHelpScreen(info, "Too many arguments.");
            return;
        }
        if (!"".equals(info.getCommandHandler().permission()) &&
                !info.getSender().hasPermission(info.getCommandHandler().permission())) {
            info.getSender().sendMessage(ChatColor.RED + info.getCommandHandler().noPermission());
            return;
        }
        if (info.playersOnly() && !info.isPlayer()) {
            //maybe make this configurable some how
            info.getSender().sendMessage(ChatColor.RED + "This command can only be executed in game.");
            return;
        }

        for (final char flagChar : info.getFlags()) {
            final ChildCommand cmd = info.getParentCommand().getParentAsChild();
            final Flag flag;

            //Let the flag * always be present, but if it is overridden then use that
            if (flagChar == '*' && !cmd.hasFlag('*')) {
                flag = info.getCommandHandler().asteriskFlag() ? defaultAsteriskFlag() : null;
            } else {
                flag = cmd.getFlagAnnotation(flagChar);
            }

            if (flag == null) {
                sendHelpScreen(info, "Unknown flag: " + flagChar);
                return;
            }

            //player does not have permission to execute the command with this flag
            if (info.isPlayer() && !"".equals(flag.permission()) &&
                    !info.getPlayer().hasPermission(flag.permission())) {
                info.getSender().sendMessage(ChatColor.RED + flag.noPermission());
                return;
            }
        }


        if (ch.strictArgs()) {
            for (final String arg : info.getArgs()) {
                if (!CommandInfo.matchesFlagPattern(arg)) {
                    sendHelpScreen(info, "Unknown subcommand: " + arg);
                    return;
                }
            }
        }

        try {
            this.queue.getMethod().invoke(this.queue.getObject(), info);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Flag(flag = '*')
    private Flag defaultAsteriskFlag() {
        try {
            return getClass().getDeclaredMethod("defaultAsteriskFlag").getAnnotation(Flag.class);
        } catch (final NullPointerException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
