package com.yuuki.net;

import com.yuuki.utils.Console;

import java.io.*;
import java.net.Socket;

/**
 * @author Borja
 * @date 27/08/2015
 * @package com.yuuki.blackeye.networking
 * @project ProjectX - Emulator
 */
public abstract class ConnectionHandler {
    //This will be the same socket created on the server 'handler'
    private Socket socket;

    //used to print this name when closing the connection. Not needed.
    protected String handlerName;

    /**
     * This class will be used as template to handle all the incoming
     * connections for all the servers needed in this emulator
     * @param socket Socket to handle the connection
     */
    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Will be used to handle connections using 'BigEndian'
     */
    private DataInputStream dataInputStream;

    /**
     * Creates a new thread which reads all the input data
     * from the connection socket and pass the complete packets
     * to a function called 'processPacket' which will be overwritten
     * in the correspondent handlers
     */
    protected void startDataInputStreamReader() {
        Thread dataInputStreamReader = new Thread() {
            @Override
            public void run() {
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());

                    while(socket.isConnected()) {
                        int length = dataInputStream.readShort();
                        if(length > 0) {
                            byte[] byteArray = new byte[length];
                            dataInputStream.read(byteArray, 0, length);

                            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray, 0, byteArray.length);
                            DataInputStream packet = new DataInputStream(bais);

                            //Process the packet
                            processPacket(packet);
                        }
                    }
                } catch(EOFException e) {
                    //Client closed/restarted the connection => socket is not longer valid
                    Console.error("EOF reached while reading from DataInputStream.");
                } catch(IOException e) {
                    Console.error("Something went wrong when trying to read from DataInputStream.", e.getMessage());
                } finally {
                    closeConnection();
                }
            }
        };
        dataInputStreamReader.setName("[DataInputStream Reader | " + socket.getInetAddress() + "]");
        dataInputStreamReader.start();
    }

    /**
     * Will be used to handle connections of 'plain-text'
     */
    private BufferedReader bufferedReader;

    /**
     * Similar to 'startDataInputStream' but using bufferedReader to
     * get plain text from the socket.
     */
    protected void startBufferedReader() {
        Thread bufferedReaderThread = new Thread() {
            @Override
            public void run() {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String packet = "";
                    char[] packetChar = new char[1];

                    /*
                     * While the packetChar isn't -1
                     */
                    while(bufferedReader.read(packetChar, 0, 1) != -1) {
                        //if the packet isn't null, new line or return char
                        if(packetChar[0] != '\u0000' && packetChar[0] != '\n' && packetChar[0] != '\r') {
                            //packet will increase it's value with the char
                            packet += packetChar[0];
                        } else if(!packet.isEmpty()) {
                            //we've the full packet.. the packerChars are over and packet != ""
                            packet = new String(packet.getBytes(), "UTF-8");

                            processPacket(packet);

                            //set the packet again to ""
                            packet = "";
                        }
                    }

                } catch(EOFException e) {
                    Console.error("EOF reached while reading from BufferedReader.", e.getMessage());
                }  catch (IOException e) {
                    Console.error("Something went wrong when trying to read from BufferedReader.", e.getMessage());
                } finally {
                    closeConnection();
                }
            }
        };
        bufferedReaderThread.setName("[Buffered Reader | " + socket.getInetAddress() + "]");
        bufferedReaderThread.start();
    }

    /**
     * Closes all the streams and sockets.
     */
    protected void closeConnection() {
        try {
            Console.out("Closing " + handlerName + " connection for " + socket.getInetAddress());

            if(dataInputStream != null) {
                dataInputStream.close();
            }

            if(bufferedReader != null) {
                bufferedReader.close();
            }

            if(socket.isConnected()) {
                socket.close();
            }
        } catch (IOException e) {
            Console.error("Something went wrong closing DataInputStream, BufferedReader and connection Socket in " + this.getClass().getSimpleName(), e.getMessage());
        }
    }

    /**
     * Sends a plain text packet to the socket
     *
     * I'm not sure if needs to be synchronized
     * @param packet String packet to be send
     */
    public synchronized void sendPacket(String packet) {
        try {
            //First get the PrintWriter object from the socket
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //And then print the packet to the PrintWriter object
            out.print((packet) + (char) 0x00);
            //Flush it and you sent your packet!
            out.flush();
        } catch (IOException e) {
            //We couldn't sent this packet! that's bad...
            Console.error("Couldn't send packet " + packet + " to " + socket.getInetAddress().getHostAddress(), e.getMessage());
        }
    }

    /**
     * Used to handle all the incoming packets from DataInputStream
     * reader.
     *
     * This method will be overwritten.
     * @param packet DataInputStream object
     */
    public abstract void processPacket(DataInputStream packet);

    /**
     * Used to handle all the incoming packets from BufferedReader
     *
     * This method will be overwritten.
     * @param packet String packet
     */
    public abstract void processPacket(String packet);
}
