package com.yuuki.game.objects;

import java.awt.*;

/**
 * Npc Class
 * @author Yuuki
 * @date 01/09/2015 | 15:30
 * @package com.yuuki.game.objects
 */
public class Npc extends GameCharacter {
    //Basic vars
    private int npcID;

    //Stats
    private int averageDamage;
    private int shieldAbsorb;

    /**
     * This constructor will be used to create a basic template of the characteristics of the NPC
     * more or less as a template
     * @param npcID For example 84 (Streuner)
     * @param name Npc name in game
     * @param ship Ship object with the needed information
     * @param averageDamage Medium damage
     * @param shieldAbsorb shield absorb
     */
    public Npc(int npcID, String name, Ship ship,  int averageDamage, int shieldAbsorb) {
        super(name, ship);
        this.npcID         = npcID;
        this.averageDamage = averageDamage;
        this.shieldAbsorb  = shieldAbsorb;
    }

    /**
     * Npc constructor
     * @param entityID       Object ID
     * @param name           name of the character (visible in game)
     * @param ship           npc ship from GameManager.gameShips
     * @param position       Point vector
     * @param mapID          location mapID
     * @param actualHealth   current health
     * @param actualNanohull current nanohull
     * @param npcID          represents the npc type
     * @param averageDamage  medium damage
     * @param shieldAbsorb   shield absorb
     */
    public Npc(int entityID, int npcID, String name, Ship ship, Point position, short mapID, int actualHealth, int actualNanohull,
               int averageDamage, int shieldAbsorb) {

        super(entityID, name, ship, position, mapID, actualHealth, actualNanohull);
        this.npcID         = npcID;
        this.averageDamage = averageDamage;
        this.shieldAbsorb  = shieldAbsorb;
    }

    /*******************
     * COMMAND GETTERS *
     *******************/
//    @Override
//    public ShipCreateCommand getShipCreateCommand() {
//        return null;
//    }
//
//    @Override
//    public ShipSelectionCommand getShipSelectionCommand() {
//        return null;
//    }

    /**
     * Player tick method
     */
    @Override
    public void tick() {
        super.tick();
    }

    /***********
     * GETTERS *
     ***********/

    public int getNpcID() {
        return npcID;
    }

    public int getAverageDamage() {
        return averageDamage;
    }

    public int getShieldAbsorb() {
        return shieldAbsorb;
    }

    @Override
    public String toString() {
        return "#" + getEntityID() + ": npcID: " + npcID + " | name: " + getName() + " | shipLootID: " + getShip().getShipLootID();
    }


    /**
     * STATS GETTERS
     */
    @Override
    public int getMaxHealth() {
        return 0;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public int getCurrentShield() {
        return 0;
    }

    @Override
    public void setCurrentShield(int shield) {

    }


    @Override
    public int getMaxShield() {
        return 0;
    }
}
