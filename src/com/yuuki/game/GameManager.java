package com.yuuki.game;

import com.yuuki.game.exceptions.ObjectAlreadyInMap;
import com.yuuki.game.objects.*;
import com.yuuki.networking.GameSession;
import com.yuuki.utils.Console;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * GameManager class.
 * Used to store all the game information
 *
 * @author Yuuki
 * @date 31/08/2015 | 18:19
 * @package com.yuuki.game
 */
public class GameManager {

    /******************
     * GAME SHIPS MAP *
     ******************/
    private static ConcurrentHashMap<String, Ship> gameShips = new ConcurrentHashMap<>();

    /**
     * Adds a ship object to gameShips map.
     * @param ship Object to be added
     * @throws ObjectAlreadyInMap If the object is in the map
     */
    public static void addShip(Ship ship) throws ObjectAlreadyInMap {
        if(gameShips.containsKey(ship.getShipLootID())) {
            throw new ObjectAlreadyInMap("Object " + ship.getShipLootID() + " already in GameManager.gameShips");
        }

        gameShips.put(ship.getShipLootID(), ship);
    }

    /**
     * Get the selected ship from gameShips map
     * @param shipLootID Of the ship to be returned
     * @return Ship object if exists. Otherwise will return null.
     */
    public static Ship getShip(String shipLootID) {
        if(gameShips.containsKey(shipLootID)) {
           return gameShips.get(shipLootID);
        } else {
            return null;
        }
    }

    /**
     * Get the amount of ships inside gameShips
     * @return gameShips size
     */
    public static int getGameShipsSize() {
        return gameShips.size();
    }



    /**********************
     * NPCS TEMPLATES MAP *
     **********************/
    private static ConcurrentHashMap<Integer, Npc> npcTemplates = new ConcurrentHashMap<>();

    //Map getter
    public static ConcurrentHashMap<Integer, Npc> getNpcTemplates() {
        return npcTemplates;
    }

    /**
     * Adds one npc object to npcTemplates
     * @param npc Npc object to be added
     * @throws ObjectAlreadyInMap If the object is in the map
     */
    public static void addNpc(Npc npc) throws ObjectAlreadyInMap {
        if(npcTemplates.containsKey(npc.getNpcID())) {
            throw new ObjectAlreadyInMap("Object " + npc.getNpcID() + " is already in GameManager.npcTemplates");
        }

        npcTemplates.put(npc.getNpcID(), npc);
    }

    /**
     * Get the selected npc template
     * @param npcID npcID
     * @return npc object or null if doesn't exist
     */
    public static Npc getNpc(int npcID) {
        if(npcTemplates.containsKey(npcID)) {
            return npcTemplates.get(npcID);
        } else {
            return null;
        }
    }



    /*****************
     * SPACEMAPS MAP *
     *****************/
    private static ConcurrentHashMap<Short, Spacemap> spacemaps = new ConcurrentHashMap<>();

    /**
     * Get the amount of spacemaps inside spacemaps
     * @return spacemaps size
     */
    public static int getSpacemapsSize() {
        return spacemaps.size();
    }

    public static boolean containsSpacemap(short mapID) {
        return spacemaps.containsKey(mapID);
    }

    /**
     * Adds a spacemap object to spacemaps
     * @param spacemap Object to be added
     * @throws ObjectAlreadyInMap
     */
    public static void addSpacemap(Spacemap spacemap) throws ObjectAlreadyInMap {
        if(spacemaps.containsKey(spacemap.getMapID())) {
            throw new ObjectAlreadyInMap("The spacemap #" + spacemap.getMapID() + " is already in GameManager.spacemaps");
        }

        spacemaps.put(spacemap.getMapID(), spacemap);
    }

    /**
     * Get the selected spacemap
     * @param mapID mapID
     * @return Spacemap object or null if doesn't exist
     */
    public static Spacemap getSpacemap(short mapID) {
        if(spacemaps.containsKey(mapID)) {
            return spacemaps.get(mapID);
        } else {
            return null;
        }
    }

    /**
     * Returns the spacemaps entry set object
     */
    public static Set<Map.Entry<Short, Spacemap>> getSpacemapsEntrySet() {
        return spacemaps.entrySet();
    }


    /************************
     * PLAYER EQUIPMENT MAP *
     ************************/
    private static ConcurrentHashMap<String, PlayerEquipment> playerEquipmentMap = new ConcurrentHashMap<>();

    /**
     * Uses the identifier as key to difference between configurations. Using a integer like playerID
     * will result in a ObjectAlreadyInMap Exception because it only admits one entry per player
     * @param playerEquipment To be added
     */
    public static void addPlayerEquipment(PlayerEquipment playerEquipment) {
        if(playerEquipmentMap.containsKey(playerEquipment.getIdentifier())) {
            playerEquipmentMap.remove(playerEquipment.getIdentifier());
            Console.out("PlayerEquipment updated for player " + playerEquipment.getPlayerID() + " -> configID: " + playerEquipment.getConfigID());
        }
        playerEquipmentMap.put(playerEquipment.getIdentifier(), playerEquipment);
    }

    /**
     * Gets the selected configuration
     * @param playerID playerID
     * @param configID configID
     * @return playerEquipment object
     */
    public static PlayerEquipment getPlayerEquipment(int playerID, int configID) {
        //Used to difference both configurations. (+1 because configs are saved as array.. 0-1)
        String identifier = playerID + "|" + (configID + 1);
        if(playerEquipmentMap.containsKey(identifier)) {
            return playerEquipmentMap.get(identifier);
        } else {
            return null;
        }
    }


    /****************
     * ACCOUNTS MAP *
     ****************/
    private static ConcurrentHashMap<Integer, Player> loadedAccounts = new ConcurrentHashMap<>();

    /**
     * Adds an player account to the map
     * @param player to be added
     * @throws ObjectAlreadyInMap
     */
    public static void addAccount(Player player) throws ObjectAlreadyInMap {
        if(loadedAccounts.containsKey(player.getEntityID())) {
            throw new ObjectAlreadyInMap("Player " + player.getEntityID() + " is already on the map. GameManager.loadedAccounts");
        }
        loadedAccounts.put(player.getEntityID(), player);
    }

    /**
     * Returns the selected player account
     * @param playerID PlayerID of the account (EntityID)
     * @return player object or null
     */
    public static Player getPlayer(int playerID) {
        if(loadedAccounts.containsKey(playerID)) {
            return loadedAccounts.get(playerID);
        } else {
            return null;
        }
    }

    /*****************
     * GAME SESSIONS *
     *****************/
    private static ConcurrentHashMap<Integer, GameSession> gameSessions = new ConcurrentHashMap<>();

    /**
     * Adds a gameSession to the map
     */
    public static void addGameSession(GameSession gameSession) {
        if(gameSessions.containsKey(gameSession.getPlayer().getEntityID())) {
            gameSessions.remove(gameSession.getPlayer().getEntityID());
        }
        gameSessions.put(gameSession.getPlayer().getEntityID(), gameSession);
    }

    /**
     * Returns the selected gameSession
     * @param playerID of the selected playerID
     * @return
     */
    public static GameSession getGameSession(int playerID) {
        if(gameSessions.containsKey(playerID)) {
            return gameSessions.get(playerID);
        } else {
            return null;
        }
    }

    /**
     * Removes the online gameSession
     * @param playerID for the playerID
     */
    public static void removeGameSession(int playerID) {
        if(existsGameSession(playerID)) {
            gameSessions.remove(playerID);
        }
    }

    public static boolean existsGameSession(int playerID) {
        return gameSessions.containsKey(playerID);
    }

}