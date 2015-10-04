package com.yuuki.net.game_server;

import com.yuuki.net.packets.ClientPackets.LoginRequest;
import com.yuuki.net.packets.ClientPackets.PolicyRequest;
import com.yuuki.net.packets.Packet;
import com.yuuki.utils.Console;

import java.util.TreeMap;

/**
 * CommandLookup Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.net
 * @project Revolution
 */
public class PacketsLookup {
    private static TreeMap<String, Class> commandsLookup;

    /**
     * Here you will find all the packets sent by the client and
     * the way to read them.
     *
     * Navigate into a concrete packet class to
     * see how it works.
     */
    public static void initLookup() {
        commandsLookup = new TreeMap<>();
        commandsLookup.put(PolicyRequest.HEADER, PolicyRequest.class);
        commandsLookup.put(LoginRequest.HEADER, LoginRequest.class);
    }


    /**
     * This method will search for each Packet header in commandsLookup.
     *
     * If it finds the associated class will reflects it and return it.
     * @param packet ClientPacket
     * @return Packet class
     */
    @SuppressWarnings("unchecked") //Because the compiler gives a wrong optimization
    public static Packet getCommand(String packet) {
        try {
            String packetHeader = packet.split("\\|")[0];
            Class  packetClass  = commandsLookup.get(packetHeader);

            if(packetClass != null) {
                Packet gamePacket = (Packet) packetClass.getConstructor(String.class).newInstance(packet);
                gamePacket.readPacket();
                return gamePacket;
            } else {
                Console.error("Packet with HEADER=" + packetHeader + " not found on CommandLookup");
            }
        } catch(Exception e) {
            Console.error("Couldn't read packet (" + packet + ") in CommandLookup", e.getMessage());
        }
        return null;
    }
}
