package com.yuuki.networking;

import com.yuuki.game.GameManager;
import com.yuuki.game.objects.Player;
import com.yuuki.game.objects.Spacemap;
import com.yuuki.networking.game_server.GameClientConnection;
import com.yuuki.utils.Console;

/**
 * This class will store all the sockets and handlers of each player
 * @author Yuuki
 * @date 12/09/2015 | 17:33
 * @package com.yuuki.projectx.networking
 */
public class GameSession {
    //Handlers
    private GameClientConnection gameClientConnection;

    //GameSession "owner"
    private Player player;

    public GameSession(Player player) {
        this.player = player;
        Console.out("Created new GameSession #" + player.getEntityID() + " -> " + player.getName());
    }

    /**
     * Finish the online GameSession for the player
     */
    public void closeSession() {
        if(gameClientConnection != null) {
            gameClientConnection.closeConnection();
            Console.out("GameSession finished or refreshed for " + player.getName() + " #" + player.getEntityID());
        }

        //Removes the online session
        GameManager.removeGameSession(player.getEntityID());

        //Removes the player from the spacemap
        Spacemap spacemap = GameManager.getSpacemap(player.getMapID());
        if (spacemap != null) {
            spacemap.removeEntity(player.getEntityID());
        }
    }

    /***********************
     * GETTERS AND SETTERS *
     ***********************/
    public GameClientConnection getGameClientConnection() {
        return this.gameClientConnection;
    }

    public void setClient9Connection(GameClientConnection gameClientConnection) {
        this.gameClientConnection = gameClientConnection;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
