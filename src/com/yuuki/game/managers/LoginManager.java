package com.yuuki.game.managers;

import com.yuuki.game.GameManager;
import com.yuuki.game.objects.Player;
import com.yuuki.game.objects.Spacemap;
import com.yuuki.mysql.QueryManager;
import com.yuuki.networking.ConnectionHandler;
import com.yuuki.networking.GameSession;
import com.yuuki.utils.Console;
import org.json.JSONException;

import java.sql.SQLException;

/**
 * @author Yuuki
 * @date 11/09/2015 | 19:14
 * @package com.yuuki.game.managers
 */
public class LoginManager {
    /***********************
     * FOR SINGLETON USAGE *
     ***********************/
    private static LoginManager INSTANCE = null;

    private LoginManager() {
        //FOR SINGLETON
    }

    /**
     * Return the LoginManager instance
     */
    public static LoginManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new LoginManager();
        }
        return INSTANCE;
    }

    /************************************************
     * ============================================ *
     ************************************************/

    /**
     * Gets the player account object using the playerID and sessionID (not yet)
     * @param playerID of the player
     * @param sessionID ^^
     * @return GameSession object or null
     * @throws SQLException
     * @throws JSONException
     */
    public GameSession checkLogin(int playerID, String sessionID) throws SQLException, JSONException {
        GameSession gameSession = null;

        //The player is reconnecting while his account is still connected
        if(GameManager.existsGameSession(playerID)) {
            gameSession = GameManager.getGameSession(playerID);
            if (gameSession != null) {
                gameSession.closeSession();
            }
        }

        //lets create a new one
        if(gameSession == null) {
            //1º we need the player object
            Player player = null;

            //Check if the emulator has the account loaded
            player = GameManager.getPlayer(playerID);

            if(player == null) {
                //the account wasn't loaded before
                player = QueryManager.loadPlayer(playerID);

                //Couldn't load the account...
                if(player == null) {
                    Console.error("Account not found");
                    return null;
                }
            }

            //Assembles the new gameSession
            gameSession = new GameSession(player);
        }

        //if the sessionID's are different returns null and stops the login
        if(!gameSession.getPlayer().getSessionID().equals(sessionID)) {
            Console.error("Different sessionID");
            return null;
        }

        return gameSession;
    }

    /**
     * Send all the commands needed for the login
     * @param gameSession To execute the login, take care with the sockets
     */
    public void executeLogin(GameSession gameSession) throws JSONException {
        //adds the gameSession to gameManager
        GameManager.addGameSession(gameSession);

        /**
         * Adds the player entity to the correspondent spacemap
         */
        Spacemap playerSpacemap = null;
        if(GameManager.containsSpacemap(gameSession.getPlayer().getMapID())) {
            //noinspection ConstantConditions
            playerSpacemap = GameManager.getSpacemap(gameSession.getPlayer().getMapID());
            if (playerSpacemap != null) {
                playerSpacemap.addEntity(gameSession.getPlayer());
            }
        }

        /**
         * gets the client9 handler to send commands easily
         */
        ConnectionHandler connectionHandler = gameSession.getGameClientConnection();

//        /**
//         * Send ship initialization command
//         */
//        connectionHandler.sendCommand(gameSession.getPlayer().getShipInitializationCommand());
//
//        /**
//         * send player settings
//         */
//        connectionHandler.sendCommand(ClientConfigurationManager.getInstance().getUserSettingsCommand(gameSession));
//
//        /**
//         * Send the keyBindings command
//         */
//        connectionHandler.sendCommand(ClientConfigurationManager.getInstance().getUserKeyBindings(gameSession));
//
//        /**
//         * Send menu bars
//         */
//        connectionHandler.sendCommand(ClientConfigurationManager.getInstance().getMenuBarsCommand(gameSession));
//
//        /**
//         * Send slot menu bars
//         */
//        connectionHandler.sendCommand(ClientConfigurationManager.getInstance().getSlotBar(gameSession));
//
//
//        /**
//         * Send my ship to the players in range
//         */
//        ConnectionHandler.sendCommandToRange(gameSession.getPlayer(), gameSession.getPlayer().getShipCreateCommand());
//
//        /**
//         * Send the drones command to all the players in range and to the own user
//         */
//        connectionHandler.sendCommand(gameSession.getPlayer().getDronesCommand());
//        ConnectionHandler.sendCommandToRange(gameSession.getPlayer(), gameSession.getPlayer().getDronesCommand());
//
//        /**
//         * Load in range ships and drones
//         */
//        if(playerSpacemap != null) {
//            for(Map.Entry<Integer, GameCharacter> characterEntry : playerSpacemap.getMapCharacterEntities()) {
//                //if the character is in 'my' range
//                if(gameSession.getPlayer().inRange(characterEntry.getValue())) {
//                    //draws it (3O.o)3
//                    connectionHandler.sendCommand(characterEntry.getValue().getShipCreateCommand());
//
//                    //Load character drones if it is a player
//                    if(characterEntry.getValue() instanceof Player) {
//                        connectionHandler.sendCommand(((Player) characterEntry.getValue()).getDronesCommand());
//                    }
//                }
//            }
//        }
//
//        /**
//         * Sets the speed and default config
//         */
//        connectionHandler.sendCommand(new SetSpeedCommand(gameSession.getPlayer().getSpeed(), gameSession.getPlayer().getSpeed()));
//        connectionHandler.sendCommand(new LegacyModule("0|A|CC|1"));
//
//        /**
//         * Spacemap window update
//         */
//        connectionHandler.sendCommand(new SpacemapWindowUpdate(true, true));
//
//        /**
//         * Startup messages
//         */
//        connectionHandler.sendCommand(new LegacyModule("0|A|STD|Welcome to ProjectX\nThis server is still in development."));
//        connectionHandler.sendCommand(new LegacyModule("0|A|STD|Please don't report things that aren't working\nWe perfectly know which systems aren't working yet."));

//        connectionHandler.sendCommand(new ShipCreateCommand(
//                -10,
//                "ship_police",
//                3, //expansion
//                "", //clan tag
//                "-=[TestNpc]=-",
//                (int)1000,
//                (int)1000,
//                0,
//                0, //rings
//                0, //rank
//                false, //warnIcon
//                new ClanRelationModule(ClanRelationModule.NONE),
//                1, //param13
//                false, //param14,
//                true, //isNpc
//                false, //cloaked
//                7, //param17
//                100, //positionIndex (?)
//                new Vector<VisualModifierCommand>(),
//                new class_365(class_365.DEFAULT)
//        ));
    }
}
