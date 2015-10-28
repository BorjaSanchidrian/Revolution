package com.yuuki.main;

import com.yuuki.utils.Conversions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class will load all the configurations needed. It will try to read them from a config file located on 'filePath'
 * If the variable is empty will be overwritten with its default value.
 *
 * @author Yuuki
 * @date 29/04/2015
 * @package main
 * @project S7KBetaServer
 */
public class ConfigManager {
    /*************************
     * Config variable names *
     *************************/
    private static final String SERVER_ID_VAR           = "serverID";
    private static final String DATABASE_HOST_VAR       = "databaseHost";
    private static final String DATABASE_USERNAME_VAR   = "databaseUsername";
    private static final String DATABASES_PASSWORD_VAR  = "databasePassword";
    private static final String DATABASE_TABLE_VAR      = "databaseTable";
    private static final String GAME_SERVER_PORT_VAR    = "gameServerPort";
    private static final String POLICY_SERVER_PORT_VAR  = "policyServerPort";
    private static final String CHAT_SERVER_PORT_VAR    = "chatServerPort";
    private static final String WEB_SERVER_PORT_VAR     = "webServerPort";
    private static final String AUTO_SAVE_TIME_VAR      = "autoSaveTime";
    private static final String DEBUG_MODE_VAR          = "debugMode";

    /*************************
     * Config default values *
     *************************/
    private static final String SERVER_ID_DEFAULT           = "1";
    private static final String DATABASE_HOST_DEFAULT       = "localhost";
    private static final String DATABASE_USERNAME_DEFAULT   = "root";
    private static final String DATABASES_PASSWORD_DEFAULT  = "";
    private static final String DATABASE_TABLE_DEFAULT      = "projectx";
    private static final String GAME_SERVER_PORT_DEFAULT    = "8080";
    private static final String POLICY_SERVER_PORT_DEFAULT  = "843";
    private static final String CHAT_SERVER_PORT_DEFAULT    = "9338";
    private static final String WEB_SERVER_PORT_DEFAULT     = "25000";
    private static final String AUTO_SAVE_TIME_DEFAULT      = "60";
    private static final String DEBUG_MODE_DEFAULT          = "false";

    /**********************************************
     * Config file values. Reachable with getters *
     **********************************************/
    private int     serverID;
    private String  databaseHost;
    private String  databaseUsername;
    private String  databasePassword;
    private String  databaseTable;
    private int     gameServerPort;
    private int     policyServerPort;
    private int     chatServerPort;
    private int     webServerPort;
    private int     autoSaveTime;
    private boolean debugMode;

    private final Properties properties;

    /**
     * Constructor of the class.
     *
     * @param filePath Config file path
     *
     * @throws FileNotFoundException If the file cannot be found on that filepath.
     * @throws IOException If there is any I/O error.
     * @throws IllegalArgumentException If the values in the file are wrong.
     */
    public ConfigManager(String filePath) throws IOException, IllegalArgumentException {
        properties = new Properties();
        properties.load(new BufferedReader(new FileReader(filePath)));

        //Database values
        this.databaseHost     = properties.getProperty(DATABASE_HOST_VAR, DATABASE_HOST_DEFAULT          );
        this.databaseUsername = properties.getProperty(DATABASE_USERNAME_VAR, DATABASE_USERNAME_DEFAULT  );
        this.databasePassword = properties.getProperty(DATABASES_PASSWORD_VAR, DATABASES_PASSWORD_DEFAULT);
        this.databaseTable    = properties.getProperty(DATABASE_TABLE_VAR, DATABASE_TABLE_DEFAULT        );

        //Server values
        this.serverID         = Conversions.stringToInt(properties.getProperty(SERVER_ID_VAR, SERVER_ID_DEFAULT                  ));
        this.gameServerPort   = Conversions.stringToInt(properties.getProperty(GAME_SERVER_PORT_VAR, GAME_SERVER_PORT_DEFAULT    ));
        this.policyServerPort = Conversions.stringToInt(properties.getProperty(POLICY_SERVER_PORT_VAR, POLICY_SERVER_PORT_DEFAULT));
        this.chatServerPort   = Conversions.stringToInt(properties.getProperty(CHAT_SERVER_PORT_VAR, CHAT_SERVER_PORT_DEFAULT    ));
        this.webServerPort    = Conversions.stringToInt(properties.getProperty(WEB_SERVER_PORT_VAR, WEB_SERVER_PORT_DEFAULT      ));

        //Other values
        this.autoSaveTime = Conversions.stringToInt    (properties.getProperty(AUTO_SAVE_TIME_VAR, AUTO_SAVE_TIME_DEFAULT));
        this.debugMode    = Conversions.stringToBoolean(properties.getProperty(DEBUG_MODE_VAR, DEBUG_MODE_DEFAULT        ));

    }

    /***********
     * Getters *
     ***********/
    public int getServerID() {
        return serverID;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseTable() {
        return databaseTable;
    }

    public int getGameServerPort() {
        return gameServerPort;
    }

    public int getPolicyServerPort() {
        return policyServerPort;
    }

    public boolean getDebugMode() {
        return debugMode;
    }

    public int getAutoSaveTime() {
        return autoSaveTime;
    }

    public int getChatServerPort() {
        return chatServerPort;
    }

    public Properties getProperties() {
        return properties;
    }

    public int getWebServerPort() {
        return webServerPort;
    }
}