package com.yuuki.networking.packets.Handlers;

import com.yuuki.networking.game_server.GameClientConnection;
import com.yuuki.networking.packets.AbstractHandler;
import com.yuuki.networking.packets.AbstractPacket;
import com.yuuki.utils.Console;

/**
 * PolicyRequestHandler Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.packets.Handlers
 * @project Revolution
 */
public class PolicyRequestHandler extends AbstractHandler {
    /**
     * Handler constructor
     *
     * @param packet               Packet that will handle
     * @param gameClientConnection Needed to send packets back
     */
    public PolicyRequestHandler(AbstractPacket packet, GameClientConnection gameClientConnection) {
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
