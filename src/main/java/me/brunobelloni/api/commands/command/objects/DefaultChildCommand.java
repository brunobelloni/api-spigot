package me.brunobelloni.api.commands.command.objects;

import me.brunobelloni.api.commands.command.handler.DefaultHandler;

/**
 * @author Richmond Steele
 * @since 12/22/13 All rights Reserved Please read included LICENSE file
 */
public class DefaultChildCommand extends ChildCommand {

    public DefaultChildCommand(final String command) {
        super(null, false);
        this.command = command;
        this.setHandler(new DefaultHandler(null));
    }

    public void setPermission(final String permission) {
        this.permission = permission;
    }
}
