package com.yuuki.net.packets.ClientPackets;

import com.yuuki.net.packets.Packet;

/**
 * LoginPacket Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.net.packets
 * @project Revolution
 */
public class LoginRequest extends Packet {
    public static final String HEADER = "LOGIN";

    private int    playerID;
    private String sessionID;

    public LoginRequest(String packet) {
        super(packet);
    }

    @Override
    public void readPacket() {
        this.playerID  = nextInt();
        this.sessionID = nextString();
    }

    /***********
     * GETTERS *
     ***********/
    public int getPlayerID() {
        return playerID;
    }

    public String getSessionID() {
        return sessionID;
    }
}
