package com.yuuki.game.managers;

import com.yuuki.game.GameManager;
import com.yuuki.game.objects.Player;
import com.yuuki.game.objects.Spacemap;
import com.yuuki.mysql.QueryManager;
import com.yuuki.networking.GameSession;
import com.yuuki.networking.game_server.GameClientConnection;
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
            Player player;

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
            Console.error("Different sessionID for playerID = " + playerID);
            return null;
        }

        return gameSession;
    }

    /**
     * Send all the commands needed for the login
     * @param gameSession To execute the login, take care with the sockets
     */
    public void executeLogin(GameSession gameSession) throws JSONException {
        /**
         * Adds the gameSession to the correspondent online GameSessions map
         */
        GameManager.addGameSession(gameSession);

        /**
         * Adds the player entity to the correspondent spacemap
         */
        Spacemap playerSpacemap;
        if(GameManager.containsSpacemap(gameSession.getPlayer().getMapID())) {
            //noinspection ConstantConditions
            playerSpacemap = GameManager.getSpacemap(gameSession.getPlayer().getMapID());
            if (playerSpacemap != null) {
                playerSpacemap.addEntity(gameSession.getPlayer());
            }
        }

        /**
         * gets the client handler to send commands easily
         */
        GameClientConnection connectionHandler = gameSession.getGameClientConnection();


        /**
         * Send client settings command/s
         */
        connectionHandler.sendPacket("0|A|SET|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1");

        /**
         * Send login command
         */
        connectionHandler.sendPacket(gameSession.getPlayer().getLoginCommand());
    }
}
