package com.yuuki.game.objects;

import com.yuuki.game.interfaces.Movable;
import com.yuuki.game.interfaces.Tick;
import com.yuuki.game.managers.MovementManager;
import com.yuuki.game.managers.PlayerManager;
import com.yuuki.networking.packets.ServerPackets.ShipCreateCommand;

import java.awt.*;

/**
 * Will be abstract because I don't want this to get instantiate
 *
 * Basic template for players and npcs in the future
 *
 * @author Yuuki
 * @date 30/08/2015 | 23:57
 * @package com.yuuki.game.objects
 */
public abstract class GameCharacter implements Movable, Tick {
    //Basic variables
    private int    entityID;
    private String name;

    //Location variables
    private Point position;
    private short mapID;

    //Stats variables
    private int actualHealth;
    private int actualNanohull;

    //Aka the field of view
    private int renderRange;

    //Objects
    private Ship          ship;
    private GameCharacter selectedEntity;

    //Timers
    private long lastAttackTime;
    private long lastRepairTick;


    /**
     * This constructor will be used to create templates of Npcs from QueryManager.
     * In this way an NPC object will be saved into GameManager with all the NPC information
     * and later will be used to instantiate the correct amount of npcs when needed.
     * @param name Name of the npc
     */
    public GameCharacter(String name, Ship ship) {
        this.name = name;
        this.ship = ship;

        //Set default values | I'll comment this because can be useful to throw null pointer if something went wrong to find the error
//        this.entityID       = 0;
//        this.name           = "";
//        this.position       = new Point();
//        this.mapID          = 0;
//        this.actualHealth   = 0;
//        this.actualNanohull = 0;
//        this.selectedEntity = null;
    }

    /**
     * GameCharacter constructor
     * @param entityID Object ID
     * @param name name of the character (visible in game)
     * @param position Point vector
     * @param mapID location mapID
     * @param actualHealth current health
     * @param actualNanohull current nanohull
     */
    public GameCharacter(int entityID, String name, Ship ship, Point position, short mapID, int actualHealth, int actualNanohull) {
        this.entityID       = entityID;
        this.name           = name;
        this.ship           = ship;
        this.position       = position;
        this.mapID          = mapID;
        this.actualHealth   = actualHealth;
        this.actualNanohull = actualNanohull;

        //defaults
        this.lastAttackTime = 0;
        this.lastRepairTick = 0;
    }

    /**********************************************
     * COMMAND GETTERS                            *
     *                                            *
     * Here you will find all the global commands *
     **********************************************/
    //<editor-fold desc="Command getters">
    /**
     * Gets the ShipCreateCommand for this instance
     */
    public abstract ShipCreateCommand getShipCreateCommand();
//
//    public abstract ShipSelectionCommand getShipSelectionCommand();
//
//    public ShipRemoveCommand getShipRemoveCommand() {
//        return new ShipRemoveCommand(getEntityID());
//    }
    //</editor-fold>

    /******************
     * MOVEMENT STUFF *
     ******************/
    // <editor-fold desc="Movement Stuff">

    private int movementTime = 0;

    @Override
    public void setMovementTime(int movementTime) {
        this.movementTime = movementTime;
    }

    @Override
    public int getMovementTime() {
        return movementTime;
    }

    private long movementStartTime = 0;

    @Override
    public void setMovementStartTime(long movementStartTime) {
        this.movementStartTime = movementStartTime;
    }

    @Override
    public long getMovementStartTime() {
        return movementStartTime;
    }

    private boolean isMoving = false;

    @Override
    public void isMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    @Override
    public boolean isMoving() {
        return isMoving;
    }

    private Point oldPosition;

    @Override
    public void setOldPosition(Point oldPosition) {
        this.oldPosition = oldPosition;
    }

    @Override
    public Point getOldPosition() {
        return oldPosition;
    }

    private Point destination;

    @Override
    public void setDestination(Point destination) {
        this.destination = destination;
    }

    @Override
    public Point getDestination() {
        return destination;
    }

    private Point direction;

    @Override
    public void setDirection(Point direction) {
        this.direction = direction;
    }

    @Override
    public Point getDirection() {
        return direction;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    public GameCharacter getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(GameCharacter selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    // </editor-fold>


    /**
     * Main tick method for GameCharacter. Will contain methods
     * that must be executed foll all the extending classes.
     *
     * Like update the position.
     *
     * Important! This method must be called in each extending class
     * in the correspondent tick();
     */
    public void tick() {
        //Updates the position (to check distances for example)
        MovementManager.getInstance().actualPosition(this);

        //Repair manager
        PlayerManager.getInstance().repairManager(this);
    }


    /***********
     * GETTERS *
     ***********/
    // <editor-fold desc="Getters">
    public int getEntityID() {
        return entityID;
    }

    public String getName() {
        return name;
    }

    public Point getPosition() {
        return position;
    }

    public short getMapID() {
        return mapID;
    }

    public int getActualHealth() {
        return actualHealth;
    }

    public void setActualHealth(int actualHealth) {
        this.actualHealth = actualHealth;
    }

    public int getActualNanohull() {
        return actualNanohull;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getRenderRange() {
        return renderRange;
    }

    public void setRenderRange(int renderRange) {
        this.renderRange = renderRange;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public long getLastRepairTick() {
        return lastRepairTick;
    }

    public void setLastRepairTick(long lastRepairTick) {
        this.lastRepairTick = lastRepairTick;
    }

    /**
     * Returns true/false depending if the object is inside the renderRange
     */
    public boolean hasCharacterIsInRange(GameCharacter object) {
        return (mapID == object.getMapID() && position.distance(object.getPosition()) <= getRenderRange() &&
                entityID != object.entityID);
    }
    // </editor-fold>

    /*****************
     * STATS GETTERS *
     *****************/
    public abstract int getMaxHealth();

    public abstract int getSpeed();

    public abstract int getCurrentShield();

    public abstract void setCurrentShield(int shield);

    public abstract int getMaxShield();

    /**
     * TODO Overwrite this when needed
     */
    public boolean canBeTargeted() {
        return true;
    }
}
