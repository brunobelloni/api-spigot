package me.brunobelloni.api.commands.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Richmond Steele, William Reed, kh498
 * @since 12/16/13 All rights Reserved Please read included LICENSE file
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandler {

    /**
     * Label of the command Sub-commands have '.' to split the child from the parent /test => test /test set =>
     * test.set
     *
     * @return command label
     */
    String command();

    /**
     * Aliases of the command /test2 => /test /test set2 => /test set
     *
     * @return command aliases
     */
    String[] aliases() default {};

    /**
     * Permission to use this command
     *
     * @return permission
     */
    String permission() default "";

    /**
     * Message to send to CommandSender if they do not have permission to use this command
     *
     * @return noPermission message
     */
    String noPermission() default "You don't have permission to do that.";

    /**
     * Given the command {@code test.set}
     * <p>
     * The default usage will give the result:
     * <p>
     * {@code Usage: /test set}
     * <p>
     * With the usage {@code [player]} it will display:
     * <p>
     * {@code Usage: /test set [player]}
     *
     * @return The arbitrary arguments of the command
     */
    String usage() default "";

    /**
     * Description of command /test => Testing the dynamic CommandAPI
     *
     * @return command description
     */
    String description() default "";

    /**
     * Minimum arguments the command must have must be > 0
     *
     * @return min number of args
     */
    int min() default 0;

    /**
     * Max arguments the command can have -1 is unlimited
     *
     * @return max number of args
     */
    int max() default -1;

    /**
     * Determines if you want the plugin to be only executed by a player and not on command line
     *
     * @return true if you want this only to be used by an in game player
     */
    boolean playerOnly() default false;

    /**
     * Defines the flags available for this command. A flag is a single character such as {@code -f} that will alter the
     * behaviour of the command. Each character in this string will be counted as a valid flag: extra flags will be
     * discarded. If one of the flags is '*' (asterisks) then this will override the default asterisks flag. With this
     * you can set the permission of the asterisks flag. If you do not want to have the asterisk flag then set {@code
     * asteriskFlag = false}
     * <p>
     * Flags can only be any english character (a-z and A-Z) including '*' (asterisks) as a catch all.
     */
    Flag[] flags() default {};

    /**
     * Defines if asterisks (*) can be used as a catch all. If this is set to false but there is declared a asterisk
     * flag with the {@code flags()} attribute that flag will function as an asterisk flag. Note that it is not
     * necessary to enable this to override the permission of the asterisk flag.
     */
    boolean asteriskFlag() default true;

    /**
     * Defines if there can be arbitrary variables. If set to true the command cannot have any unknown variables. The
     * arguments will either be a subcommand or a flag, if not an error is thrown. This means that flags are ignored and
     * can be used.
     * <p>
     * It is suggested that this is set to true if you only want flags as args.
     */
    boolean strictArgs() default false;
}
