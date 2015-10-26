package com.yuuki.networking.game_server;

import com.yuuki.game.objects.Player;
import com.yuuki.networking.ConnectionHandler;
import com.yuuki.networking.packets.AbstractCommand;
import com.yuuki.networking.packets.AbstractHandler;
import com.yuuki.networking.packets.AbstractPacket;
import com.yuuki.utils.Console;

import java.io.DataInputStream;
import java.net.Socket;

/**
 * GameClientConnection Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.game_server
 * @project Revolution
 */
public class GameClientConnection extends ConnectionHandler {

    //Player 'owner' of this connection
    private Player player;

    /**
     * This class will be used to handle all the incoming
     * connections of GameServer
     *
     * @param socket Socket to handle the connection
     */
    public GameClientConnection(Socket socket) {
        super(socket);
        startBufferedReader();
    }

    @Override
    public void processPacket(DataInputStream packet) {
        // -> Depreciated <-
    }

    /**
     * This method will search each Packet object into PacketsLookup
     * if it finds the right packet will try to find the correct Handler
     * and execute it.
     * @param packet String packet
     */
    @Override
    public void processPacket(String packet) {
        //search the packet into PacketsLookup
        System.out.println("Packet: " + packet);
        AbstractPacket command = PacketsLookup.getCommand(packet);
        if (command != null) {
            AbstractHandler handler = HandlersLookup.getHandler(command, this);

            if (handler != null) {
                handler.execute();
            }
        }
    }

    /**
     * Allows to send a AbstractCommand directly to the client.
     * @param command Command to send
     */
    public void sendPacket(AbstractCommand command) {
        if(player != null)
            Console.out("Sent " + command.getClass().getSimpleName() + " to player " + player.getEntityID());
        super.sendPacket(command.getPacket());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
