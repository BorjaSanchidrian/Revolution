package com.yuuki.game.objects;

/**
 * @author Yuuki
 * @date 31/08/2015 | 0:35
 * @package com.yuuki.game.objects
 */
public class Ship {
    //Basic variables
    private int    shipID;
    private String shipLootID;

    //Ship stats
    private int shipHealth;
    private int shipSpeed;

    //Ship slots
    private int laserSlots;
    private int generatorSlots;
    private int heavyGunsSlots;
    private int extraSlots;

    //Reward variables
    private int rewardExperience;
    private int rewardHonor;
    private int rewardUridium;
    private int rewardCredits;

    /**
     * Ship constructor
     */
    public Ship(int shipID, String shipLootID, int shipHealth, int shipSpeed, int laserSlots,
                int generatorSlots, int heavyGunsSlots, int extraSlots, int rewardExperience,
                int rewardHonor, int rewardUridium, int rewardCredits) {

        this.shipID           = shipID;
        this.shipLootID       = shipLootID;
        this.shipHealth       = shipHealth;
        this.shipSpeed        = shipSpeed;
        this.laserSlots       = laserSlots;
        this.generatorSlots   = generatorSlots;
        this.heavyGunsSlots   = heavyGunsSlots;
        this.extraSlots       = extraSlots;
        this.rewardExperience = rewardExperience;
        this.rewardHonor      = rewardHonor;
        this.rewardUridium    = rewardUridium;
        this.rewardCredits    = rewardCredits;
    }

    /***********
     * GETTERS *
     ***********/

    public int getShipID() {
        return shipID;
    }

    public String getShipLootID() {
        return shipLootID;
    }

    public int getShipHealth() {
        return shipHealth;
    }

    public int getShipSpeed() {
        return shipSpeed;
    }

    public int getLaserSlots() {
        return laserSlots;
    }

    public int getGeneratorSlots() {
        return generatorSlots;
    }

    public int getHeavyGunsSlots() {
        return heavyGunsSlots;
    }

    public int getExtraSlots() {
        return extraSlots;
    }

    public int getRewardExperience() {
        return rewardExperience;
    }

    public int getRewardHonor() {
        return rewardHonor;
    }

    public int getRewardUridium() {
        return rewardUridium;
    }

    public int getRewardCredits() {
        return rewardCredits;
    }
}