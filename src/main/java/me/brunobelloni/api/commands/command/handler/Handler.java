package me.brunobelloni.api.commands.command.handler;

import me.brunobelloni.api.commands.command.objects.CommandInfo;

/**
 * @author Richmond Steele
 * @since 12/17/13 All rights Reserved Please read included LICENSE file
 */
public interface Handler {

    void handleCommand(CommandInfo info) throws CommandException;
}
