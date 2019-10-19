package me.brunobelloni;

import me.brunobelloni.api.commands.command.CommandHandler;
import me.brunobelloni.api.commands.command.CommandListener;
import me.brunobelloni.api.commands.command.Flag;
import me.brunobelloni.api.commands.command.objects.CommandInfo;

/**
 * @link https://github.com/kh498/CommandAPI
 * command: (String) This is the name of the command. eg command /test will have command = "test"
 * aliases: (String[], default: {}) A list of aliases for this command
 * permission: (String, default: "") The permission a players need to execute the command
 * noPermission: (String, default: "You don't have permission to do that.") The string displayed when the player doesn't have the permission for this command
 * usage: (String, default: "") The arbitrary arguments of the command
 * description: (String, default: "") A description of what the command does
 * min: (int, default: 0) The minimum number of arguments this command can have
 * max: (int, default: -1) The maximum number og arguments this command can have
 * playerOnly: (boolean, default: false) If only players can excecute this command
 * flags: (String, default: "") The flags of this command (see flags example below)
 * strictArgs: (boolean, default: false) If only known subcommand are allowed as arguments (see first example below)
 * flagDesc: (String[], default: {}) The description of what each flag does (see flags example below)
 */
public class ApiCommandHandler implements CommandListener {
    /**
     * command is the only required field for the annotation
     * <p>
     * The base command is required (bug in 2.0). If you want the base command to display the
     * help screen when called without any arguments add the attribute values "strictArgs = true" and "max = 0"
     * <p>
     * Do NOT register the command in plugin.yml as it is all handled by this API!
     */
    @CommandHandler(command = "test")
    public static void testingCommand(final CommandInfo info) {
        info.getSender().sendMessage("Test worked");
    }

    /**
     * A dot in the command string marks this as a sub command. It can go infinitely deep.
     * Do not have the command and/or subcommand in the usage, that is built in.
     */
    @CommandHandler(
            command = "test.test2",
            permission = "test.test2",
            noPermission = "No access!",
            aliases = {"2", "testing"},
            usage = "<player>",
            flags = {@Flag(flag = 'f', usage = "-k activate some feature")},
            description = "Testing out (almost) all of the CommandHandler's attribute values")
    public static void testingCommand2(final CommandInfo info) {
        info.getSender().sendMessage("Test2 worked");
    }

    /**
     * A flag is a single character such as {@code -f} that will alter the behaviour of the command. flags can only
     * be any english character (a-z and A-Z) including * as a catch all.
     * <p>
     * Defines if there can be arbitrary variables. If set to true the command cannot have any unknown variables.
     * The arguments will either be a subcommand or a flag, if not an error is thrown. This means that flags are
     * ignored and can be used.
     * <p>
     * It is suggested that this is set to true if you only want flags as arguments.
     */
    @CommandHandler(
            command = "test.reset",
            flags = {@Flag(flag = 'k', usage = "-k resets kingdoms"), @Flag(flag = 'r', usage = "-r resets reficules")},
            strictArgs = true,
            description = "resets stuff!")
    public static void testingCommand3(final CommandInfo info) {
        //user gave the argument -f or -*
        if (info.hasFlag('k')) {
            // Do some resetting
            info.getSender().sendMessage("flag k");
        }
        //returns true if one of the chars in the input string matches one of the flags the user gave
        if (info.hasOneOfFlags("kr")) {
            // reload or something
            info.getSender().sendMessage("flag kr");
        }
    }
}