package com.yuuki.networking.packets.ServerPackets;

import com.yuuki.networking.packets.AbstractCommand;

import java.awt.*;

/**
 * LoginCommand Class
 *
 * @author Yuuki
 * @date 25/10/2015
 * @package com.yuuki.networking.packets.ServerPackets
 * @project Revolution
 */
public class LoginCommand extends AbstractCommand {
    private static final String HEADER = "I";

    //Player basics
    private int    playerID;
    private String username;
    private short  shipID;
    private short  rankID;
    private int    clanID;
    private String clanTag;
    private short  ggRings;

    //Ship stats
    private short maxSpeed;
    private int   currentShield;
    private int   maxShield;
    private int   currentHealth;
    private int   maxHealth;
    private int   currentCargo;
    private int   maxCargo;

    //Player position
    private Point position;
    private short mapID;
    private short factionID;

    //Ship storage
    private int shipAmmoAmount;
    private int shipRocketAmount;
    private int expansionID;

    //Others
    private int   premium;   //Should be boolean but the packet need the integer boolean value instead of 'true'
    private long  experience;
    private long  honor;
    private short level;
    private long  credits;
    private long  uridium;
    private float jackpot;
    private int   cloaked;

    public LoginCommand(int playerID, String username, short shipID, short rankID, int clanID, String clanTag, short maxSpeed, int currentShield,
                        int maxShield, int currentHealth, int maxHealth, int currentCargo, int maxCargo, Point position, short mapID, short factionID,
                        int shipAmmoAmount, int shipRocketAmount, int expansionID, boolean premium, long experience, long honor, short level, long credits,
                        long uridium, float jackpot, short ggRings, int cloaked) {
        super(HEADER);
        this.playerID         = playerID;
        this.username         = username;
        this.shipID           = shipID;
        this.rankID           = rankID;
        this.clanID           = clanID;
        this.clanTag          = clanTag;
        this.maxSpeed         = maxSpeed;
        this.currentShield    = currentShield;
        this.maxShield        = maxShield;
        this.currentHealth    = currentHealth;
        this.maxHealth        = maxHealth;
        this.currentCargo     = currentCargo;
        this.maxCargo         = maxCargo;
        this.position         = position;
        this.mapID            = mapID;
        this.factionID        = factionID;
        this.shipAmmoAmount   = shipAmmoAmount;
        this.shipRocketAmount = shipRocketAmount;
        this.expansionID      = expansionID;
        this.premium          = (premium) ? 1 : 0;
        this.experience       = experience;
        this.honor            = honor;
        this.level            = level;
        this.credits          = credits;
        this.uridium          = uridium;
        this.jackpot          = jackpot;
        this.ggRings          = ggRings;
        this.cloaked          = cloaked;
    }

    @Override
    public void assemblePacket() {
        //RDY|I|playerID|username|shipID|maxSpeed|shield|maxShield|health|maxHealth|cargo|maxCargo|user.x|usery|mapId|factionId|clanId|shipAmmo|shipRockets|equipment(?)|premium|exp|honor|level|credits|uridium|jackpot|rank|clanTag|ggates|0|cloaked
        addParameter(playerID);
        addParameter(username);
        addParameter(shipID);
        addParameter(maxSpeed);
        addParameter(currentShield);
        addParameter(maxShield);
        addParameter(currentHealth);
        addParameter(maxHealth);
        addParameter(currentCargo);
        addParameter(maxCargo);
        addParameter(position.getX());
        addParameter(position.getY());
        addParameter(mapID);
        addParameter(factionID);
        addParameter(clanID);
        addParameter(shipAmmoAmount);
        addParameter(shipRocketAmount);
        addParameter(expansionID);
        addParameter(premium);
        addParameter(experience);
        addParameter(honor);
        addParameter(level);
        addParameter(credits);
        addParameter(uridium);
        addParameter(jackpot);
        addParameter(rankID);
        addParameter(clanTag);
        addParameter(ggRings);
        addParameter(cloaked);
    }

    /***********
     * GETTERS *
     ***********/
    //<editor-fold desc="Getters">

    public int getPlayerID() {
        return playerID;
    }

    public String getUsername() {
        return username;
    }

    public short getShipID() {
        return shipID;
    }

    public short getRankID() {
        return rankID;
    }

    public int getClanID() {
        return clanID;
    }

    public String getClanTag() {
        return clanTag;
    }

    public short getMaxSpeed() {
        return maxSpeed;
    }

    public int getCurrentShield() {
        return currentShield;
    }

    public int getMaxShield() {
        return maxShield;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentCargo() {
        return currentCargo;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

    public Point getPosition() {
        return position;
    }

    public short getMapID() {
        return mapID;
    }

    public short getFactionID() {
        return factionID;
    }

    public int getShipAmmoAmount() {
        return shipAmmoAmount;
    }

    public int getShipRocketAmount() {
        return shipRocketAmount;
    }

    public int getExpansionID() {
        return expansionID;
    }

    public int getPremium() {
        return premium;
    }

    public long getExperience() {
        return experience;
    }

    public long getHonor() {
        return honor;
    }

    public short getLevel() {
        return level;
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

    public int getCloaked() {
        return cloaked;
    }

    //</editor-fold>
}
