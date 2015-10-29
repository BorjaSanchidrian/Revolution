package com.yuuki.networking.packets.ServerPackets;

import com.yuuki.networking.packets.AbstractCommand;

import java.awt.*;

/**
 * ShipCreateCommand Class
 *
 * @author Yuuki
 * @date 25/10/2015
 * @package com.yuuki.networking.packets.ServerPackets
 * @project Revolution
 */
public class MoveCommand extends AbstractCommand {
    private static final String HEADER = "C";

    private int   entityID;
    private Point playerDestination;
    private int   movementTime;

    public MoveCommand(int entityID, Point playerDestination, int movementTime) {
        super(HEADER);
        this.entityID          = entityID;
        this.playerDestination = playerDestination;
        this.movementTime      = movementTime;

        //Assembles the packet
        this.assemblePacket();
    }

    @Override
    public void assemblePacket() {
        //0|1|playerID|newX|newY|time(ms)
        addParameter(entityID);
        addParameter(playerDestination.getX());
        addParameter(playerDestination.getY());
        addParameter(movementTime);
    }

    /***********
     * GETTERS *
     ***********/
    //<editor-folder desc="Getters">

    public int getEntityID() {
        return entityID;
    }

    public Point getPlayerDestination() {
        return playerDestination;
    }

    public int getMovementTime() {
        return movementTime;
    }

    //</editor-folder>
}
