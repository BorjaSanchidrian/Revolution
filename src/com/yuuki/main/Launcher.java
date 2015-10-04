package com.yuuki.main;

import com.yuuki.net.ServerManager;
import com.yuuki.utils.Console;

import java.io.IOException;

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
        ServerManager serverManager = new ServerManager();
        try {
            serverManager.init();
        } catch (IOException e) {
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
