package com.yuuki.networking.packets.Handlers;

import com.yuuki.game.managers.MovementManager;
import com.yuuki.game.objects.Player;
import com.yuuki.networking.game_server.GameClientConnection;
import com.yuuki.networking.packets.AbstractHandler;
import com.yuuki.networking.packets.AbstractPacket;
import com.yuuki.networking.packets.ClientPackets.MoveRequest;
import com.yuuki.utils.Console;

/**
 * MoveRequestHandler Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.packets.Handlers
 * @project Revolution
 */
public class MoveRequestHandler extends AbstractHandler {
    /**
     * Handler constructor
     *
     * @param packet               Packet that will handle
     * @param gameClientConnection Needed to send packets back
     */
    public MoveRequestHandler(AbstractPacket packet, GameClientConnection gameClientConnection) {
        super(packet, gameClientConnection);
    }

    @Override
    public void execute() {
        MoveRequest moveRequest = (MoveRequest) getPacket();
        Player      player      = getGameClientConnection().getPlayer();

        /**
         * Checks if the position given by the packet differs with the real player position.
         * TD:LR Anti feel like speedhackers :D (1441 ~= '1 X' in my language)
         */
        if(moveRequest.getPlayerPosition().distance(player.getPosition()) < 1500) {
            //Moves the player
            MovementManager.getInstance().move(player, moveRequest.getPlayerDestination());
        } else {
            Console.error("Player #" + player.getEntityID() + " is probably trying to speedhack", "Packet position: " + moveRequest.getPlayerPosition().toString() + " | Player real position: " + player.getPosition().toString());
            //TODO ban him?
        }
    }
}
