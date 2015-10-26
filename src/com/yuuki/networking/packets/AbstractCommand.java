package com.yuuki.networking.packets;

import java.util.ArrayList;

/**
 * Command Class
 *
 * @author Yuuki
 * @date 25/10/2015
 * @package com.yuuki.networking.packets
 * @project Revolution
 */
public abstract class AbstractCommand {
    private String            header;
    private ArrayList<String> packetArray;

    public AbstractCommand(String header) {
        this.header      = header;
        this.packetArray = new ArrayList<>();

        //Assembles the basic command structure => 0|HEADER|Params..
        this.addParameter("0");
        this.addParameter(header);
    }

    /**
     * Abstract method to add parameters to the packet.
     * Will be overwritten in each command adding the needed params
     */
    public abstract void assemblePacket();

    /**
     * Adds a parameter to the command
     * @param parameter Param object. Can be any type of variable
     */
    public void addParameter(Object parameter) {
        if(parameter != null) {
            packetArray.add(String.valueOf(parameter) + "|");
        }
    }

    /**
     * Returns the full packet as String
     * @return Assembled packet string
     */
    public String getPacket() {
        String packet = "";
        for(String p : packetArray) {
            packet += p;
        }

        return packet;
    }
}
