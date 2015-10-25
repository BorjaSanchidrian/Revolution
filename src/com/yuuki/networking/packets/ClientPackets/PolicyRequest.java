package com.yuuki.networking.packets.ClientPackets;

import com.yuuki.networking.packets.AbstractPacket;

/**
 * PolicyRequest Class
 *
 * "<policy-file-request/>" as header and the whole packet...
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.packets.ClientPackets
 * @project Revolution
 */
public class PolicyRequest extends AbstractPacket {
    public static final String HEADER = "<policy-file-request/>";

    public PolicyRequest(String packet) {
        super(packet);
    }

    @Override
    public void readPacket() {
        //Nothing to read :D
    }
}
