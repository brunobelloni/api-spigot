package me.brunobelloni.api.actionbar;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

/**
 * @author TheLuca98
 * @author brunobelloni
 */
public class ActionBar {

    /**
     * Used to toggle debug messages. Disabled by default.
     *
     * @deprecated No longer in use.
     */
    @Deprecated
    public static boolean DEBUG;

    private JSONObject json;

    /**
     * Constructs an {@link ActionBar} object based on plain text.
     *
     * @param text Text to display.
     */
    public ActionBar(String text) {
        Preconditions.checkNotNull(text);
        this.json = Title.convert(text);
    }

    /**
     * Constructs an {@link ActionBar} object based on JSON-formatted text.
     *
     * @param json Text to display Must be in /tellraw JSON format.
     */
    public ActionBar(JSONObject json) {
        Preconditions.checkNotNull(json);
        Preconditions.checkArgument(!json.isEmpty());
        this.json = json;
    }

    /**
     * This method has been kept just to ensure backwards compatibility with older versions of TextAPI.
     * It is not supported and will be removed in a future release.
     *
     * @param player  The player to send the message to.
     * @param message The message to send.
     * @deprecated Please create a new {@link ActionBar} instance instead.
     */
    @Deprecated
    public static void send(Player player, String message) {
        new ActionBar(message).send(player);
    }

    /**
     * This method has been kept just to ensure backwards compatibility with older versions of TextAPI.
     * It is not supported and will be removed in a future release.
     *
     * @param message The message to send.
     * @deprecated Please create a new {@link ActionBar} instance instead.
     */
    @Deprecated
    public static void sendToAll(String message) {
        new ActionBar(message).sendToAll();
    }

    /**
     * Sends an action bar message to a specific player.
     *
     * @param player The player to send the message to.
     */
    public void send(Player player) {
        Preconditions.checkNotNull(player);
        try {
            Class<?> clsIChatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent");
            Class<?> clsChatMessageType = ServerPackage.MINECRAFT.getClass("ChatMessageType");
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Object chatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, json.toString());
            Object chatMessageType = clsChatMessageType.getMethod("valueOf", String.class).invoke(null, "GAME_INFO");
            Object packetPlayOutChat = ServerPackage.MINECRAFT.getClass("PacketPlayOutChat").getConstructor(clsIChatBaseComponent, clsChatMessageType).newInstance(chatBaseComponent, chatMessageType);
            playerConnection.getClass().getMethod("sendPacket", ServerPackage.MINECRAFT.getClass("Packet")).invoke(playerConnection, packetPlayOutChat);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends an action bar message to all online players.
     */
    public void sendToAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            send(player);
        }
    }

    /**
     * Changes the text to display.
     *
     * @param text Text to display.
     */
    public void setText(String text) {
        Preconditions.checkNotNull(text);
        this.json = Title.convert(text);
    }

    /**
     * Changes the text to display.
     *
     * @param json Text to display. Must be in /tellraw JSON format.
     */
    public void setJsonText(JSONObject json) {
        Preconditions.checkNotNull(json);
        Preconditions.checkArgument(!json.isEmpty());
        this.json = json;
    }
}