package com.yuuki.net.packets.Handlers;

import com.yuuki.net.game_server.GameClientConnection;
import com.yuuki.net.packets.Handler;
import com.yuuki.net.packets.Packet;
import com.yuuki.utils.Console;

/**
 * PolicyRequestHandler Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.net.packets.Handlers
 * @project Revolution
 */
public class PolicyRequestHandler extends Handler {
    /**
     * Handler constructor
     *
     * @param packet               Packet that will handle
     * @param gameClientConnection Needed to send packets back
     */
    public PolicyRequestHandler(Packet packet, GameClientConnection gameClientConnection) {
        super(packet, gameClientConnection);
    }

    @Override
    public void execute() {
        String policyPacket = "<?xml version=\"1.0\"?>\r\n" +
                "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n" +
                "<cross-domain-policy>\r\n" +
                "<allow-access-from domain=\"*\" to-ports=\"*\" />\r\n" +
                "</cross-domain-policy>";
        getGameClientConnection().sendPacket(policyPacket);

        Console.out(this.getClass().getSimpleName() + " executed");
    }
}
