package com.yuuki.networking;

import com.yuuki.game.GameManager;
import com.yuuki.game.objects.Spacemap;
import com.yuuki.mysql.MySQLManager;
import com.yuuki.mysql.QueryManager;
import com.yuuki.networking.game_server.GameServer;
import com.yuuki.networking.game_server.HandlersLookup;
import com.yuuki.networking.game_server.PacketsLookup;
import com.yuuki.utils.Console;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Yuuki
 * @date 07/10/2015 | 18:00
 * @package com.yuuki.blackeye.networking
 */
public class ServerManager {
    /***********************
     * FOR SINGLETON USAGE *
     ***********************/
    private static ServerManager INSTANCE = null;

    private ServerManager() {
        //SINGLETON
    }

    /**
     * Return the ServerManager instance
     */
    public static ServerManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ServerManager();
        }
        return INSTANCE;
    }

    /************************************************
     * ============================================ *
     ************************************************/

    private MySQLManager mySQLManager;

    //Emulator servers
    private int gameServerPorts[] = {8080,5555,22222,5001,8084,6667,8000,3389,5631};
    private GameServer gameServers[];
//    private WebServer    webServer;

    /**
     * Where all the magic starts :D
     * @throws IOException
     * @throws SQLException
     * @throws JSONException
     */
    public void init() throws IOException, SQLException, JSONException {
        createServers();
        startServers();
        connectMySQL();
        executeQueries();
        PacketsLookup.initLookup();
        HandlersLookup.initLookup();
        startTicks();
    }

    /**
     * Used to instantiate all the needed servers for the emulation
     * @throws IOException If one port is in use
     */
    private void createServers() throws IOException {
//        policyServer      = new PolicyServer(843);
        gameServers = new GameServer[gameServerPorts.length];

        for(int port = 0; port < gameServerPorts.length; port++) {
            gameServers[port] = new GameServer(gameServerPorts[port]);
        }
//        webServer         = new WebServer(25000);
    }

    /**
     * Start the servers
     */
    private void startServers() {
        Console.out(Console.LINE_EQ, "Starting servers...");
//        policyServer.startServer();
        for(GameServer gameServer : gameServers) {
            gameServer.startServer();
        }
        Console.out("[GameServer] started successfully in all the needed ports");
//        webServer.startServer();
        Console.out(Console.LINE_EQ);
    }

    /**
     * Connects to the MySQL database
     */
    private void connectMySQL() {
        mySQLManager = new MySQLManager("127.0.0.1", "doserver", "Gallego99", "projectx");
        Console.out(Console.LINE_EQ, "Setting up connection to MySQL");
    }

    /**
     * Execute the initial queries to load the needed information into the emulator
     * @throws SQLException
     * @throws JSONException
     */
    private void executeQueries() throws SQLException, JSONException {
        QueryManager.loadShips();
        Console.out(GameManager.getGameShipsSize() + " ships loaded");
        QueryManager.loadNpcTemplates();
        QueryManager.loadSpacemaps();
        Console.out(GameManager.getSpacemapsSize() + " spacemaps loaded", Console.LINE_EQ);
    }

    /**
     * This method will execute the main thread of the game.
     *
     * Executing the method "tick" of each spacemap, that will contains checks like
     * distance between players, its health, etc.. the game in one word
     */
    private void startTicks() {
        Console.out("Starting ticks...");
        Thread ticksThread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        for (Map.Entry<Integer, Spacemap> spacemapEntry : GameManager.getSpacemapsEntrySet()) {
                            spacemapEntry.getValue().tick();
                        }
                        sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ticksThread.setName("TicksMainThread");
        ticksThread.start();
    }

    /***********
     * GETTERS *
     ***********/
    public MySQLManager getMySQLManager() {
        return mySQLManager;
    }
}
