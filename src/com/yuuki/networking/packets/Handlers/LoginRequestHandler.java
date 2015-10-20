package com.yuuki.networking.packets.Handlers;

import com.yuuki.networking.game_server.GameClientConnection;
import com.yuuki.networking.packets.Handler;
import com.yuuki.networking.packets.Packet;
import com.yuuki.utils.Console;

import java.util.Random;

/**
 * PolicyRequestHandler Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.packets.Handlers
 * @project Revolution
 */
public class LoginRequestHandler extends Handler {
    /**
     * Handler constructor
     *
     * @param packet               Packet that will handle
     * @param gameClientConnection Needed to send packets back
     */
    public LoginRequestHandler(Packet packet, GameClientConnection gameClientConnection) {
        super(packet, gameClientConnection);
    }

    @Override
    public void execute() {
        /**
         * RDY|I|playerID|username|shipID|maxSpeed|shield|maxShield|health|maxHealth|cargo|maxCargo|user.x|usery|mapId|factionId|clanId|shipAmmo|shipRockets|equipment(?)|premium|exp|honor|level|credits|uridium|jackpot|rank|clanTag|ggates|0|cloaked
         */
        Random r = new Random();
        String loginPacket = "RDY|I|" + r.nextInt(10) + "|GameTester|10|100|1|1|1|1|1|1|1000|1000|1|1||0|0|3|1|1|1|1|1|1|1|21|TEST|0|0|0";
        getGameClientConnection().sendPacket(loginPacket);

        Console.out(this.getClass().getSimpleName() + " executed");
    }
}
