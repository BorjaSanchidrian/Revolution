package com.yuuki.networking.packets.ClientPackets;

import com.yuuki.networking.packets.AbstractPacket;

/**
 * MoveRequest Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.packets
 * @project Revolution
 */
public class MoveRequest extends AbstractPacket {
    public static final String HEADER = "1";

    private int    playerID;
    private String sessionID;

    public MoveRequest(String packet) {
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
