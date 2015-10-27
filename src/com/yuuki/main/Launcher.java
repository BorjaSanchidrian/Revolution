package com.yuuki.main;

import com.yuuki.networking.ServerManager;
import com.yuuki.utils.Console;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Launcher Class
 *
 * @author Yuuki
 * @date 04/10/2015
 * @package com.yuuki.main
 * @project Revolution
 */
public class Launcher {
    private static final String CONFIG_FILE_PATH = "config/serverConfig.ini";
    private static ConfigManager configManager;
    private static ServerManager serverManager;

    public static void main(String[] args) {
        sendHeader();
        readConfigFile(CONFIG_FILE_PATH);

        serverManager = new ServerManager(configManager);
        try {
            serverManager.init();
        } catch (IOException | SQLException | JSONException e) {
            Console.error(e.getMessage());
        }
    }

    /**
     * Prints the Console header
     */
    private static void sendHeader() {
        System.out.println("Revolution | DarkOrbit Emulator");
        System.out.println("Version v0.1");
        System.out.println("Open source project hosted in https://github.com/BorjaSanchidrian/Revolution");
        System.out.println(Console.LINE_EQ);
    }

    /**
     * Makes one attempt to read the config file. If it can't find the config file will load the default values
     * @see ConfigManager
     * @param filePath Path of the config file
     */
    private static void readConfigFile(String filePath) {
        try {
            configManager = new ConfigManager(filePath);
        } catch (IOException e) {
            Console.error("Couldn't find the config file (" + CONFIG_FILE_PATH + "). Loading default configuration");
        }
    }

    /**
     * ServerManager getter. (Used on QueryManager)
     */
    public static ServerManager getServerManager() {
        return serverManager;
    }
}
