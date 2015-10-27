package com.yuuki.networking.packets.ServerPackets;

import com.yuuki.networking.packets.AbstractCommand;

import java.awt.*;

/**
 * ShipRemoveCommand Class
 *
 * @author Yuuki
 * @date 25/10/2015
 * @package com.yuuki.networking.packets.ServerPackets
 * @project Revolution
 */
public class ShipRemoveCommand extends AbstractCommand {
    private static final String HEADER = "R";

    //Player basics
    private int playerID;

    public ShipRemoveCommand(int playerID) {
        super(HEADER);
        this.playerID = playerID;

        //Assembles the packet
        this.assemblePacket();
    }

    @Override
    public void assemblePacket() {
        //0|R|playerID
        addParameter(playerID);
    }

    /***********
     * GETTERS *
     ***********/
    public int getPlayerID() {
        return playerID;
    }
}
