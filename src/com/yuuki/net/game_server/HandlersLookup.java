package com.yuuki.net.game_server;

import com.yuuki.net.packets.ClientPackets.PolicyRequest;
import com.yuuki.net.packets.Handler;
import com.yuuki.net.packets.Handlers.PolicyRequestHandler;
import com.yuuki.net.packets.Packet;
import com.yuuki.utils.Console;

import java.util.TreeMap;

/**
 * HandlersLookup Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.net.game_server
 * @project Revolution
 */
public class HandlersLookup {
    private static TreeMap<String, Class> handlers;

    public static void initLookup() {
        handlers = new TreeMap<>();
        handlers.put(PolicyRequest.HEADER, PolicyRequestHandler.class);
    }

    @SuppressWarnings("unchecked") //wrong optimization
    public static Handler getHandler(Packet packet, GameClientConnection gameClientConnection) {
        Class handler = handlers.get(packet.getHeader());

        if(handler != null) {
            try {
                return (Handler) handler.getConstructor(Packet.class, GameClientConnection.class).newInstance(packet, gameClientConnection);
            } catch (Exception e) {
                Console.error("Couldn't instance handler for packet (" + packet.getPacket() + ")");
            }
        }

        return null;
    }
}
