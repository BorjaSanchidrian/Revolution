package com.yuuki.net;


import com.yuuki.utils.Console;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BasicServer class
 *
 * Will be used as basic template for all the servers needed in this emulator.
 * Implements the basic functions like startServer, stopServer, a simple DDoS checker
 * and creates a new thread to keep the server running in the background.
 *
 * This class is abstract to prevent the use of BasicServer without extending it
 *
 * @author Borja
 * @date 26/08/2015
 * @package com.yuuki.blackeye.networking
 * @project ProjectX - Emulator
 */
public abstract class BasicServer implements Runnable {
    //serverSocket which listens on the selected port
    private ServerSocket    serverSocket;
    //used to keep the server running
    private Thread          thread;

    /**
     * Starts a socket server in the 'port' passed in the parameters
     * and sets a new thread with the selected 'serverName'
     *
     * @param port Port used on the serverSocket
     * @param serverName Name used for the thread
     * @throws IOException If the selected port is already used
     */
    public BasicServer(int port, String serverName) throws IOException {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IOException("Port " + port + " already in use");
        }
        this.thread = new Thread(this, serverName);
    }

    /**
     * Starts the selected server
     */
    public void startServer() {
        this.thread.start();
    }

    /**
     * Stop the serverSocket, socket and the correspondent thread
     * @throws IOException If something went wrong closing the serverSocket
     */
    public void stopServer() throws IOException {
        //if the serverSocket is bound closes it
        if(serverSocket.isBound()) {
            serverSocket.close();
        }

        //if the thread is still working closes it
        if(thread.isAlive()) {
            thread.interrupt();
        }
    }

    /**
     * Used to store all the address and correspondent time of connection.
     * Is not static because will depend of each server, otherwise players
     * can be banned wrong if the chat and game connection are below the
     * MAX_TIME_ALLOWED_BETWEEN_CONNECTIONS.
     */
    private ConcurrentHashMap<String, Long> addressConnectionTime = new ConcurrentHashMap<>();

    /**
     * On the other hand blockedAddresses will be static. So if one player 'tries'
     * to DDoS one server (game, chat, policy, ...) will be banned for the
     * rest of the servers as well.
     */
    private static ArrayList<String> blockedAddresses = new ArrayList<>();

    //Const to determinate the allowed time between connections
    private static final long MAX_TIME_ALLOWED_BETWEEN_CONNECTIONS = 500;

    /**
     * Checks if a specific address is 'trying' to DDoS and blocks/allow the access
     * to the server.
     * @param address Address to be checked
     * @return true/false if the access is granted
     */
    protected boolean checkAddress(String address) {
        //if the IP comes from the local server always allows the connection
        if(address.equalsIgnoreCase("127.0.0.1")) {
            return true;
        }

        if(blockedAddresses.contains(address)) {
            return false;
        }

        if (addressConnectionTime.containsKey(address)) {
            long addressLastConnection = addressConnectionTime.get(address);
            long currentTime = Calendar.getInstance().getTimeInMillis();

            if((currentTime - addressLastConnection) < MAX_TIME_ALLOWED_BETWEEN_CONNECTIONS) {
                blockedAddresses.add(address);
                return false;
            } else {
                return true;
            }
        } else {
            addressConnectionTime.put(address, Calendar.getInstance().getTimeInMillis());
            return true;
        }
    }

    /**
     * As thread needs 'Runnable' to work we need 'run' as well.
     * It will only print a simple message on the console.
     *
     * This method will be overwritten in the following servers which extends this class
     * *Can't be abstract
     */
    @Override
    public void run() {
        System.out.println(Console.getDateTime() + thread.getName() + " started on port " + serverSocket.getLocalPort() + " successfully");
        // => From now on this will be overwritten <=
    }

    /**
     * ServerSocket getter
     * @return SeverSocket object
     */
    protected ServerSocket getServerSocket() {
        return serverSocket;
    }
}
