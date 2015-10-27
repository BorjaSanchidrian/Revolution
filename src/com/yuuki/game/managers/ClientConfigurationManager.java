package com.yuuki.game.managers;

/**
 * ClientConfigurationManager
 * Used to control all the configurations and settings used by the client.
 *
 * @author Borja
 * @date 29/06/2015
 * @package simulator.managers
 * @project com.yuuki.game.managers
 */
public class ClientConfigurationManager {
    /**
     * For singleton usage
     */
    private static ClientConfigurationManager INSTANCE;

    private ClientConfigurationManager() {
        //SINGLETON
    }

    public static ClientConfigurationManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientConfigurationManager();
        }
        return INSTANCE;
    }

    //TODO
}
