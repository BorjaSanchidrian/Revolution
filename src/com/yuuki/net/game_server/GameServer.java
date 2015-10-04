package com.yuuki.net.game_server;

import com.yuuki.net.BasicServer;
import com.yuuki.utils.Console;

import java.io.IOException;
import java.net.Socket;

/**
 * GameServer Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.net.game_server
 * @project Revolution
 */
public class GameServer extends BasicServer {
    private static final String SERVER_NAME = "[GameServer]";

    //used to handle connections
    private Socket socket;

    /**
     * Starts a socket server in the 'port' passed in the parameters
     * and sets a new thread with the selected 'serverName'
     *
     * @param port       Port used on the serverSocket
     * @throws IOException If the selected port is already used
     * @see BasicServer
     */
    public GameServer(int port) throws IOException {
        super(port, SERVER_NAME);
    }

    /**
     * Checks if the address which tries to connect has or not access to the server (banned)
     * and establish the connection
     */
    @Override
    public void run() {
        super.run();

        while(super.getServerSocket().isBound()) {
            try {
                //accepts the incoming connection
                socket = super.getServerSocket().accept();

                //checks if the address which tries to connect has or not access to the server
//                if(super.checkAddress(socket.getInetAddress().getHostAddress())) {
                    //sends the socket to the ConnectionHandler
                    new GameClientConnection(socket);
//                } else {
//                    Console.error(socket.getInetAddress().getHostAddress() + " was blocked for suspected DDoS " + SERVER_NAME);
//                    socket.close();
//                }
            } catch (IOException e) {
                Console.error("Something went wrong handling the connection", e.getMessage());
            }
        }
    }
}
