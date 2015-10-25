package com.yuuki.networking.game_server;

import com.yuuki.networking.packets.AbstractHandler;
import com.yuuki.networking.packets.AbstractPacket;
import com.yuuki.networking.packets.ClientPackets.LoginRequest;
import com.yuuki.networking.packets.ClientPackets.PolicyRequest;
import com.yuuki.networking.packets.Handlers.LoginRequestHandler;
import com.yuuki.networking.packets.Handlers.PolicyRequestHandler;
import com.yuuki.utils.Console;

import java.util.TreeMap;

/**
 * HandlersLookup Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.game_server
 * @project Revolution
 */
public class HandlersLookup {
    private static TreeMap<String, Class> handlers;

    public static void initLookup() {
        handlers = new TreeMap<>();
        handlers.put(PolicyRequest.HEADER, PolicyRequestHandler.class);
        handlers.put(LoginRequest.HEADER, LoginRequestHandler.class);
    }

    @SuppressWarnings("unchecked") //wrong optimization
    public static AbstractHandler getHandler(AbstractPacket packet, GameClientConnection gameClientConnection) {
        Class handler = handlers.get(packet.getHeader());

        if(handler != null) {
            try {
                return (AbstractHandler) handler.getConstructor(AbstractPacket.class, GameClientConnection.class).newInstance(packet, gameClientConnection);
            } catch (Exception e) {
                Console.error("Couldn't instance handler for packet (" + packet.getPacket() + ")");
            }
        }

        return null;
    }
}
