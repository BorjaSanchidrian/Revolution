package com.yuuki.game.objects;

import com.yuuki.game.interfaces.Tick;
import com.yuuki.networking.packets.ServerPackets.LoginCommand;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Game Player class
 * @author Yuuki
 * @date 31/08/2015 | 17:15
 * @package com.yuuki.game.objects
 */
public class Player extends GameCharacter implements Tick {
    //Player information
    private int    hangarID;
    private short  factionID;
    private short  rankID;
    private String sessionID;

    //Player stats
    private short level;
    private long  experience;
    private long  honor;
    private long  credits;
    private long  uridium;
    private float jackpot;

    //Player objects
    private PlayerEquipment[] playerEquipment;
    private PlayerAmmunition  playerAmmunition;
    private ArrayList<Drone>  playerDrones;

    //Extra
    private short      ggRings;
    private short      currentConfig;
    private boolean    isPremium;
    private JSONObject settings;
    private JSONArray  keyBindings;
    private long       lastConfigChange;

    //Default constants TODO change this
    public final int DEFAULT_RENDER_RANGE = Integer.MAX_VALUE;

    /**
     * Player constructor. Self explained
     * @param entityID       Object ID
     * @param name           name of the character (visible in game)
     * @param ship           player ship from GameManager
     * @param position       Point vector
     * @param mapID          location mapID
     * @param actualHealth   current health
     * @param actualNanohull current nanohull
     * @param ggRings        Binary number (0-64) (000 000 - 111 111)
     * @param playerDrones   ArrayList of Drone objects
     */
    public Player(int entityID, String name, Ship ship, PlayerEquipment[] playerEquipment, Point position, short mapID, int actualHealth, int actualNanohull,
                  int hangarID, short factionID, short rankID, String sessionID, short level, long experience, long honor, long credits, long uridium,
                  float jackpot, short ggRings, boolean isPremium, JSONObject settings, PlayerAmmunition playerAmmunition, ArrayList<Drone> playerDrones) {

        super(entityID, name, ship, position, mapID, actualHealth, actualNanohull);
        this.playerEquipment  = playerEquipment;
        this.hangarID         = hangarID;
        this.factionID        = factionID;
        this.rankID           = rankID;
        this.sessionID        = sessionID;
        this.level            = level;
        this.experience       = experience;
        this.honor            = honor;
        this.credits          = credits;
        this.uridium          = uridium;
        this.jackpot          = jackpot;
        this.playerAmmunition = playerAmmunition;
        this.ggRings          = ggRings;
        this.isPremium        = isPremium;
        this.settings         = settings;
        this.playerDrones     = playerDrones;

        /*
         * Other
         */

        //config1 selected by default
        this.currentConfig    = 0;
        //set to 0 the initial config timer
        this.lastConfigChange = 0;
        try {
            //Get the keyBindings array
            this.keyBindings = settings.getJSONArray("boundKeys");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //set the default field of view for the players
        setRenderRange(DEFAULT_RENDER_RANGE);
    }

    /*******************
     * SERVER COMMANDS *
     *******************/
    // <editor-fold desc="ServerCommands Getters">
    /**
     * Returns the LoginCommand for this player
     */
    public LoginCommand getLoginCommand() {
        /**
         * LoginCommand(int playerID, String username, short shipID, short rankID, int clanID, String clanTag, short maxSpeed, int currentShield,
         int maxShield, int currentHealth, int maxHealth, int currentCargo, int maxCargo, Point position, short mapID, short factionID,
         int shipAmmoAmount, int shipRocketAmount, int expansionID, int premium, long experience, long honor, short level, long credits,
         long uridium, float jackpot, short ggRings, int cloaked)
         */
        return new LoginCommand(
                getEntityID(),
                getName(),
                (short) 10, //shipID
                getRankID(),
                0, //clanID,
                "", //clanTag
                (short)getSpeed(), //TODO change speed method later
                getCurrentShield(),
                getMaxShield(),
                getActualHealth(),
                getMaxHealth(),
                0, //currentCargo
                1, //maxCargo
                getPosition(),
                getMapID(),
                getFactionID(),
                0, //shipAmmoStorage
                0, //rocketsStorage
                3, //expansion
                isPremium(),
                getExperience(),
                getHonor(),
                getLevel(),
                getCredits(),
                getUridium(),
                getJackpot(),
                getGgRings(),
                0 //cloaked
        );
    }
//
//    /**
//     * Returns the ShipCreateCommand
//     */
//    @Override
//    public ShipCreateCommand getShipCreateCommand() {
//        return new ShipCreateCommand(
//                getEntityID(),
//                getShip().getShipLootID(),
//                3, //expansion
//                "", //clan tag
//                getName(),
//                (int)getPosition().getX(),
//                (int)getPosition().getY(),
//                getFactionID(),
//                getGgRings(),
//                getRankID(),
//                false, //warnIcon
//                new ClanRelationModule(ClanRelationModule.NONE),
//                1, //param13
//                false, //param14,
//                false, //isNpc
//                false, //cloaked
//                7, //param17
//                100, //positionIndex (?)
//                new java.util.Vector<VisualModifierCommand>(),
//                new class_365(class_365.DEFAULT)
//        );
//    }
//
//    /**
//     * Get the drones packet
//     */
//    public LegacyModule getDronesCommand() {
//        if(playerDrones != null && playerDrones.size() > 0) {
//            //0|n|d|playerID|formationID|droneType|droneLevel|droneDesign|...
//            String packet = "0|n|d|" + getEntityID() + "|0";
//
//            for(Drone drone : playerDrones) {
//                packet += "|" + drone.getDroneType() + "|" + (drone.getDroneLevel() + 1) + "|0";
//            }
//
//            return new LegacyModule(packet);
//        }
//
//        return null;
//    }
//
//    /**
//     * Get the selected GameCharacter ShipSelectionCommand
//     */
//    @Override
//    public ShipSelectionCommand getShipSelectionCommand() {
//        return new ShipSelectionCommand(
//                getMaxShield(),
//                getMaxHealth(), //max nanohull
//                getActualHealth(),
//                getMaxHealth(),
//                getEntityID(),
//                true, //shield skill
//                3, //var2852
//                getCurrentShield(),
//                getActualNanohull()
//        );
//    }

    //</editor-fold>

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
    //<editor-fold desc="Getters">
    public PlayerEquipment[] getPlayerEquipment() {
        return playerEquipment;
    }

    public int getHangarID() {
        return hangarID;
    }

    public short getFactionID() {
        return factionID;
    }

    public short getRankID() {
        return rankID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public short getLevel() {
        return level;
    }

    public long getExperience() {
        return experience;
    }

    public long getHonor() {
        return honor;
    }

    public long getCredits() {
        return credits;
    }

    public long getUridium() {
        return uridium;
    }

    public float getJackpot() {
        return jackpot;
    }

    public short getGgRings() {
        return ggRings;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public JSONObject getSettings() {
        return settings;
    }

    public PlayerAmmunition getPlayerAmmunition() {
        return playerAmmunition;
    }

    public void setPlayerEquipment(int configID, PlayerEquipment playerEquipment) {
        this.playerEquipment[configID - 1] = playerEquipment;
    }

    public JSONArray getKeyBindings() {
        return keyBindings;
    }

    public void setKeyBindings(JSONArray keyBindings) {
        this.keyBindings = keyBindings;
    }

    public short getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentConfig(short currentConfig) {
        this.currentConfig = currentConfig;
    }

    public long getLastConfigChange() {
        return lastConfigChange;
    }

    public void setLastConfigChange(long lastConfigChange) {
        this.lastConfigChange = lastConfigChange;
    }

    //</editor-fold>

    /**
     * EQUIPMENT GETTERS
     */
    //<editor-fold desc="Equipment getters">
    @Override
    public int getMaxHealth() {
        return getShip().getShipHealth();
    }

    @Override
    public int getSpeed() {
        return getShip().getShipSpeed() + playerEquipment[currentConfig].getSpeed();
    }

    @Override
    public int getCurrentShield() {
        return playerEquipment[currentConfig].getCurrentShield();
    }

    @Override
    public void setCurrentShield(int shield) {
        playerEquipment[currentConfig].setCurrentShield(shield);
    }

    @Override
    public int getMaxShield() {
        int maxShield = 0;

        //Get drones shield
        for(Drone drone : playerDrones) {
            maxShield += drone.getDroneEquipment()[currentConfig].getMaxShield();
        }

        //adds player ship shield
        maxShield += playerEquipment[currentConfig].getMaxShield();

        return maxShield;
    }

    /**
     * Updates the selected drone equipment
     */
    public void updateDroneEquipment(int droneID, PlayerEquipment[] newEquipment) {
        for(Drone drone : playerDrones) {
            if(drone.getDroneID() == droneID) {
                drone.setDroneEquipment(newEquipment);
            }
        }
    }
    //</editor-fold>
}