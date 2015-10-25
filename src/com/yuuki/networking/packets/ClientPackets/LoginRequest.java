package com.yuuki.networking.packets.ClientPackets;

import com.yuuki.networking.packets.AbstractPacket;

/**
 * LoginPacket Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.packets
 * @project Revolution
 */
public class LoginRequest extends AbstractPacket {
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
