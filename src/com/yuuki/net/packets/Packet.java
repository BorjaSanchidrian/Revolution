package com.yuuki.net.packets;

import java.util.Arrays;

/**
 * Packet Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.net.packets
 * @project Revolution
 */
public abstract class Packet {
    private String[] packet;
    private int packetCounter;

    public Packet(String packet) {
        //DO packets are for example "LOGIN|playerID|sessionID"
        this.packet        = packet.split("\\|");
        this.packetCounter = 0;
    }

    /**
     * Will be used to fill the packet variables in each case
     */
    public abstract void readPacket();

    /**
     * Returns the first param of the Packet.
     * @return Header of the packet (LOGIN, S, ...)
     */
    public String getHeader() {
        return packet[0];
    }

    /**
     * Increments packetCounter and returns the next param as String
     * @return Next param as String
     */
    public String nextString() {
        return packet[++packetCounter];
    }

    /**
     * Increments packetCounter and returns the next param as int
     * @return Next param as int
     */
    public int nextInt() {
        int nextInt;
        try {
            nextInt = Integer.parseInt(packet[++packetCounter]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Packet String ('" + packet[packetCounter] + "') can't be parsed to int");
        }

        return nextInt;
    }

    /**
     * Returns the packet array as String
     * @return Packet String
     */
    public String getPacket() {
        return Arrays.toString(packet);
    }
}
