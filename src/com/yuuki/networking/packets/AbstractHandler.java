package com.yuuki.networking.packets;

import com.yuuki.networking.game_server.GameClientConnection;

/**
 * Handler Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.packets.ClientPackets
 * @project Revolution
 */
public abstract class AbstractHandler {
    private AbstractPacket packet;
    private GameClientConnection gameClientConnection;

    /**
     * Handler constructor
     * @param packet Packet that will handle
     * @param gameClientConnection Needed to send packets back
     */
    public AbstractHandler(AbstractPacket packet, GameClientConnection gameClientConnection) {
        this.packet               = packet;
        this.gameClientConnection = gameClientConnection;
    }

    /**
     * Executes the handler
     */
    public abstract void execute();

    /***********
     * GETTERS *
     ***********/
    public AbstractPacket getPacket() {
        return packet;
    }

    public GameClientConnection getGameClientConnection() {
        return gameClientConnection;
    }
}
