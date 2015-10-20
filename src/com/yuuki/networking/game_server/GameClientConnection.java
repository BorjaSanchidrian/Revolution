package com.yuuki.networking.game_server;

import com.yuuki.networking.ConnectionHandler;
import com.yuuki.networking.packets.Handler;
import com.yuuki.networking.packets.Packet;

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
        Packet command = PacketsLookup.getCommand(packet);
        if (command != null) {
            Handler handler = HandlersLookup.getHandler(command, this);

            if (handler != null) {
                handler.execute();
            }
        }
    }
}
