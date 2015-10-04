package com.yuuki.net.packets.ClientPackets;

import com.yuuki.net.packets.Packet;

/**
 * PolicyRequest Class
 *
 * "<policy-file-request/>" as header and the whole packet...
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.net.packets.ClientPackets
 * @project Revolution
 */
public class PolicyRequest extends Packet {
    public static final String HEADER = "<policy-file-request/>";

    public PolicyRequest(String packet) {
        super(packet);
    }

    @Override
    public void readPacket() {
        //Nothing to read :D
    }
}
