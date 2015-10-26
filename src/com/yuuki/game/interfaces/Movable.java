package com.yuuki.game.interfaces;

import java.awt.*;

/**
 * This interface will be used to move the objects in the spacemap.
 *
 * All the move-able objects should implement it
 * @author Yuuki
 * @date 17/09/2015 | 19:36
 * @package com.yuuki.game.interfaces
 */
public interface Movable {
    //Basic
    int getEntityID();

    //Movement needed times
    void setMovementTime     (int movementTime     );
    int getMovementTime();

    void setMovementStartTime(long movementStartTime);
    long getMovementStartTime();

    void isMoving(boolean isMoving);
    boolean isMoving();

    //Vector positions
    void setOldPosition(Point oldPosition);
    Point getOldPosition();

    void setDestination(Point destination);
    Point getDestination();

    void setDirection  (Point direction  );
    Point getDirection();

    //Final position
    void setPosition(Point position);
    Point getPosition();

    int getSpeed();

}
