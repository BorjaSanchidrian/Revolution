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
    public static void main(String[] args) {
        sendHeader();
        ServerManager serverManager = ServerManager.getInstance();
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
}
