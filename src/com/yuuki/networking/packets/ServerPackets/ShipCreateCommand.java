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
public class ShipCreateCommand extends AbstractCommand {
    private static final String HEADER = "C";

    private int    entityID;
    private short  shipID;
    private short  expansionID;
    private String clanTag;
    private String username;
    private Point  position;
    private short  factionID;
    private int    clanID;
    private short  rankID;
    private int    warnIcon;     //boolean
    private short  clanDiplomacy;
    private short  ggRings;
    private int    isNpc;        //boolean
    private int    cloaked;      //boolean


    public ShipCreateCommand(int entityID, short shipID, short expansionID, String username, Point position, short factionID,
                             int clanID, short rankID, boolean warnIcon, short clanDiplomacy, short ggRings, boolean isNpc, boolean cloaked, String clanTag) {
        super(HEADER);
        this.entityID      = entityID;
        this.shipID        = shipID;
        this.expansionID   = expansionID;
        this.clanTag       = clanTag;
        this.username      = username;
        this.position      = position;
        this.factionID     = factionID;
        this.clanID        = clanID;
        this.rankID        = rankID;
        this.warnIcon      = warnIcon ? 1 : 0;
        this.clanDiplomacy = clanDiplomacy;
        this.ggRings       = ggRings;
        this.isNpc         = isNpc    ? 1 : 0;
        this.cloaked       = cloaked  ? 1 : 0;

        //Assembles the packet
        this.assemblePacket();
    }

    @Override
    public void assemblePacket() {
        //0|C|USERID|SHIPID|EXPANSION|CLANTAG|USERNAME|X|Y|FactionId|CLANID|RANK|WARNICON|CLANDIPLOMACY|GALAXYGATES|NPC|CLOACK
        //0|C|1|10|3||Yuuki|1000.0|1000.0|1|0|1|0|0|0|0|0|
        addParameter(entityID);
        addParameter(shipID);
        addParameter(expansionID);
        addParameter(clanTag);
        addParameter(username);
        addParameter(position.getX());
        addParameter(position.getY());
        addParameter(factionID);
        addParameter(clanID);
        addParameter(rankID);
        addParameter(warnIcon);
        addParameter(clanDiplomacy);
        addParameter(ggRings);
        addParameter(isNpc);
        addParameter(cloaked);
    }

    /***********
     * GETTERS *
     ***********/
    //<editor-folder desc="Getters">
    public int getCloaked() {
        return cloaked;
    }

    public int getIsNpc() {
        return isNpc;
    }

    public short getGgRings() {
        return ggRings;
    }

    public short getClanDiplomacy() {
        return clanDiplomacy;
    }

    public int getWarnIcon() {
        return warnIcon;
    }

    public short getRankID() {
        return rankID;
    }

    public int getClanID() {
        return clanID;
    }

    public short getFactionID() {
        return factionID;
    }

    public Point getPosition() {
        return position;
    }

    public String getUsername() {
        return username;
    }

    public String getClanTag() {
        return clanTag;
    }

    public short getExpansionID() {
        return expansionID;
    }

    public short getShipID() {
        return shipID;
    }

    public int getEntityID() {
        return entityID;
    }
    //</editor-folder>
}
