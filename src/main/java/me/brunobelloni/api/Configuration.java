package me.brunobelloni.api;

import org.bukkit.Location;
import org.bukkit.Sound;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static org.bukkit.Sound.*;

public class Configuration {


    public static Location arenaSpawn;
    public static Location commomSpawn;
    public static Location arenaLoc1;
    public static Location arenaLoc2;

    public final static Long   inviteExpireTime           = 5L;
    public final static Long   startAfterInviteAcceptTime = 3L;
    public final static Sound  winSound                   = FIREWORK_LAUNCH;
    public final static Sound  startSound                 = NOTE_BASS_GUITAR;
    public final static Sound  loseSound                  = ENDERDRAGON_DEATH;
    public final static String winMensage                 = translate("&aVocê venceu!");
    public final static String loseMensage                = translate("&cVocê perdeu!");
    public final static String acceptInviteMensage        = translate("&aSeu convite foi aceito!");
    public final static String denyInviteMensage          = translate("&cSeu convite foi negado!");
    public final static String inviteExpireMensage        = translate("&cSeu convite expirou!");
    public final static String inviteWaitExpireMensage    = translate("&cVocê ja realizou um convite, espere!");

    private static String translate(String string) {
        return translateAlternateColorCodes('&', string);
    }

    public static Location getArenaSpawn() {
        return arenaSpawn;
    }
    public static void setArenaSpawn(Location arenaSpawn) {
        Configuration.arenaSpawn = arenaSpawn;
    }

    public static Location getCommomSpawn() {
        return commomSpawn;
    }
    public static void setCommomSpawn(Location commomSpawn) {
        Configuration.commomSpawn = commomSpawn;
    }

    public static Location getArenaLoc1() {
        return arenaLoc1;
    }
    public static void setArenaLoc1(Location arenaLoc1) {
        Configuration.arenaLoc1 = arenaLoc1;
    }

    public static Location getArenaLoc2() {
        return arenaLoc2;
    }
    public static void setArenaLoc2(Location arenaLoc2) {
        Configuration.arenaLoc2 = arenaLoc2;
    }
}
