package com.yuuki.game.objects;

/**
 * @author Yuuki
 * @date 25/09/2015
 * @package com.yuuki.game.objects
 * @project ProjectX - Emulator
 */
public class Drone {
    //Basic variables
    private int    droneID;
    private String lootID;

    //Stats
    private int droneLevel;
    private int droneHealth;
    //private int droneEffect; TODO (?)

    //Equipment
    private PlayerEquipment[] droneEquipment;

    /**
     * Constructor of the Drone class
     * @param droneID aka itemID
     * @param lootID iris, apis, ...
     * @param droneLevel 0-5
     * @param droneHealth destruction level
     */
    public Drone(int droneID, String lootID, int droneLevel, int droneHealth, PlayerEquipment[] droneEquipment) {
        this.droneID        = droneID;
        this.lootID         = lootID;
        this.droneLevel     = droneLevel;
        this.droneHealth    = droneHealth;
        this.droneEquipment = droneEquipment;
    }

    /***********
     * GETTERS *
     ***********/
    //<editor-fold desc="Getters">
    public int getDroneID() {
        return droneID;
    }

    public String getLootID() {
        return lootID;
    }

    public int getDroneLevel() {
        return droneLevel;
    }

    public int getDroneHealth() {
        return droneHealth;
    }

    public int getDroneType() {
        switch (lootID) {
            case "drone_flax":
                return 1;
            case "drone_iris":
                return 2;
            case "drone_apis":
                return 3;
            case "drone_zeus":
                return 4;
            default:
                return 1;
        }
    }

    public PlayerEquipment[] getDroneEquipment() {
        return droneEquipment;
    }

    public void setDroneEquipment(PlayerEquipment[] droneEquipment) {
        this.droneEquipment = droneEquipment;
    }

    //</editor-fold>
}
