package com.yuuki.networking.packets.ClientPackets;

import com.yuuki.networking.packets.AbstractPacket;

import java.awt.*;

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

    private Point playerDestination;
    private Point playerPosition;

    public MoveRequest(String packet) {
        super(packet);
    }

    @Override
    public void readPacket() {
        //1|newX|newY|playerX|playerY
        this.playerDestination = new Point(nextInt(), nextInt());
        this.playerPosition    = new Point(nextInt(), nextInt());
    }

    /***********
     * GETTERS *
     ***********/
    public Point getPlayerPosition() {
        return playerPosition;
    }

    public Point getPlayerDestination() {
        return playerDestination;
    }
}
