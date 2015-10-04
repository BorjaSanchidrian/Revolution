package com.yuuki.net;

import com.yuuki.net.game_server.GameServer;
import com.yuuki.net.game_server.HandlersLookup;
import com.yuuki.net.game_server.PacketsLookup;

import java.io.IOException;

/**
 * ServerManager Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.net
 * @project Revolution
 */
public class ServerManager {
    //private ConfigManager configManager;

    //Servers
    private GameServer gameServer;

    public ServerManager() {
        //TODO add configManager as param
    }

    public void init() throws IOException {
        PacketsLookup.initLookup();
        HandlersLookup.initLookup();
        startServers();
    }

    private void startServers() throws IOException {
        this.gameServer = new GameServer(8080);
        this.gameServer.startServer();
    }

}
