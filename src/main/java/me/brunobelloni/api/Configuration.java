package me.brunobelloni.api;

import org.bukkit.Sound;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static org.bukkit.Sound.*;

public class Configuration {

    public final static Long   inviteExpireTime           = 5L;
    public final static Long   startAfterInviteAcceptTime = 3L;
    public final static Sound  winSound                   = FIREWORK_LAUNCH;
    public final static Sound  startSound                 = NOTE_BASS_GUITAR;
    public final static Sound  loseSound                  = ENDERDRAGON_DEATH;
    public final static String winMensage                 = translate("Você venceu!");
    public final static String loseMensage                = translate("Você perdeu!");
    public final static String acceptInviteMensage        = translate("Seu convite foi aceito!");
    public final static String denyInviteMensage          = translate("Seu convite foi negado!");
    public final static String inviteExpireMensage        = translate("Seu convite expirou!");
    public final static String inviteWaitExpireMensage    = translate("Você ja realizou um convite, espere!");

    private static String translate(String string) {
        return translateAlternateColorCodes('&', string);
    }
}
