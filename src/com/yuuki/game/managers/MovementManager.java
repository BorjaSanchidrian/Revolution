package com.yuuki.game.managers;

import com.yuuki.game.interfaces.Movable;
import com.yuuki.game.objects.GameCharacter;
import com.yuuki.networking.ConnectionHandler;
import com.yuuki.networking.packets.ServerPackets.MoveCommand;

import java.awt.*;
import java.util.Calendar;

/**
 * @author Borja
 * @date 28/06/2015
 * @package simulator.systems
 * @project S7KServer
 */
public class MovementManager {
    /**
     * For singleton usage
     */
    private static MovementManager INSTANCE;

    private MovementManager() {
        //SINGLETON
    }

    public static MovementManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovementManager();
        }
        return INSTANCE;
    }

    /**
     * Moves a movable (interface) object
     * @param object Movable object
     * @param destination new x,y point
     */
    public void move(Movable object, Point destination) {
        //Gets the movement time
        object.setMovementTime(getTime(object, destination));

        //Gets the system time when the movement starts
        object.setMovementStartTime(Calendar.getInstance().getTimeInMillis());
        object.isMoving(true);

        //sends the movement to the rest of the players in range if both are on the same map
        ConnectionHandler.sendPacketToRange((GameCharacter) object, new MoveCommand(object.getEntityID(), destination, object.getMovementTime()).getPacket());
    }

    /**
     * Gets the movementTime based on the new position and the object speed
     * @param object Movable object
     * @param destination new x,y point
     * @return time (int)
     */
    public int getTime(Movable object, Point destination) {
        //Gets the position before the movement
        Point oldPosition = actualPosition(object);
        object.setOldPosition(oldPosition);

        //And the destination position
        object.setDestination(destination);

        //Same with the direction, will be used to calculate the position
        object.setDirection(new Point((int)(destination.getX() - oldPosition.getX()),(int)(destination.getY() - oldPosition.getY())));

        double distance = destination.distance(oldPosition);

        return (int) ((distance / (double) object.getSpeed()) * 1000);
    }

    /**
     * Check the object actual position and sets it as position of the object
     * @param object
     * @return Vector with the actual position of the object
     */
    public Point actualPosition(Movable object) {
        Point actualPosition;

        if (object.isMoving()) {
            long timeElapsed = Calendar.getInstance().getTimeInMillis() - object.getMovementStartTime();

            //if the object continues moving
            if (timeElapsed < object.getMovementTime()) {
                //maths time, it returns the actual position while flying
                actualPosition = new Point((int) (object.getOldPosition().getX() + (object.getDirection().getX() * ((double) timeElapsed / (double) object.getMovementTime()))),
                        (int) (object.getOldPosition().getY() + (object.getDirection().getY() * ((double) timeElapsed / (double) object.getMovementTime()))));
            } else {
                //the object should be on the destination position
                object.isMoving(false);
                actualPosition = object.getDestination();
            }
        } else {
            //the object is not moving
            actualPosition = object.getPosition();
        }

        //updates the actual position into the object
        object.setPosition(actualPosition);
        return actualPosition;
    }
}