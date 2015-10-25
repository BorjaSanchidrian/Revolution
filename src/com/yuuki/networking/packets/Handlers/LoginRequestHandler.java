package com.yuuki.networking.packets.Handlers;

import com.yuuki.game.managers.LoginManager;
import com.yuuki.networking.GameSession;
import com.yuuki.networking.game_server.GameClientConnection;
import com.yuuki.networking.packets.AbstractHandler;
import com.yuuki.networking.packets.AbstractPacket;
import com.yuuki.networking.packets.ClientPackets.LoginRequest;
import com.yuuki.utils.Console;
import org.json.JSONException;

import java.sql.SQLException;

/**
 * PolicyRequestHandler Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.networking.packets.Handlers
 * @project Revolution
 */
public class LoginRequestHandler extends AbstractHandler {
    /**
     * Handler constructor
     *
     * @param packet               Packet that will handle
     * @param gameClientConnection Needed to send packets back
     */
    public LoginRequestHandler(AbstractPacket packet, GameClientConnection gameClientConnection) {
        super(packet, gameClientConnection);
    }

    @Override
    public void execute() {
        LoginRequest loginRequest = (LoginRequest) getPacket();

        try {
            GameSession gameSession = LoginManager.getInstance().checkLogin(loginRequest.getPlayerID(), loginRequest.getSessionID());

            if(gameSession != null) {
                //Logged in (3O.o)3
                //adds the game handler to the gameSession
                gameSession.setClientConnection(getGameClientConnection());
                getGameClientConnection().setPlayer(gameSession.getPlayer());

                LoginManager.getInstance().executeLogin(gameSession);
            } else {
                Console.error("Couldn't connect player " + loginRequest.getPlayerID());
            }
        } catch (SQLException | JSONException e) {
            Console.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
