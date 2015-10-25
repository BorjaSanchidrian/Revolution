package com.yuuki.mysql;

import com.yuuki.game.GameManager;
import com.yuuki.game.exceptions.ObjectAlreadyInMap;
import com.yuuki.game.objects.*;
import com.yuuki.networking.ServerManager;
import com.yuuki.utils.Console;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Yuuki
 * @date 31/08/2015 | 17:55
 * @package com.yuuki.mysql
 */
public class QueryManager {
    private static MySQLManager mySQLManager = ServerManager.getInstance().getMySQLManager();

    private static String query = null;

    /**
     * Load all the ships which lootID is not null into GameManager.gameShips
     * @throws SQLException
     */
    public static void loadShips() throws SQLException, JSONException {
        query = "SELECT shipID,lootID,health,speed,reward FROM ships WHERE lootID != ''";

        mySQLManager.executeQuery(query, null);
        ArrayList<Object[]> data = mySQLManager.getData();

        for(Object[] ship : data) {
            //For each ship in the returned data...

            int    shipID           = (int   ) ship[0];
            String shipLootID       = (String) ship[1];
            int    shipHealth       = (int   ) ship[2];
            int    shipSpeed        = (int   ) ship[3];

            //Rewards
            JSONObject reward = null;
            //Throws custom exception if something went wrong
            try {
                reward = new JSONObject((String) ship[4]);
            } catch (JSONException e) {
                throw new JSONException("Couldn't convert row reward to JSONObject (" + ship[4] + ") in QueryManager.loadShips");
            }

            int    rewardExperience = reward.getInt("experience");
            int    rewardHonor      = reward.getInt("honor");
            int    rewardCredits    = reward.getInt("credits");
            int    rewardUridium    = reward.getInt("uridium");

            /**
             * Check if the ship is a player ship, registered on server_items as well and return its levels.
             * To check later the slotsets
             */
            JSONObject levels = null;

            query = "SELECT levels FROM server_items WHERE lootID = ? LIMIT 1";
            Object[] secondParams = {shipLootID};

            mySQLManager.executeQuery(query, secondParams);
            if(mySQLManager.getData().size() > 0){
                //Throws custom exception if something went wrong
                try {
                    levels = new JSONObject((String) mySQLManager.getData().get(0)[0]);
                } catch (JSONException e) {
                    throw new JSONException("Couldn't convert row levels to JSONObject (" + mySQLManager.getData().get(0)[0] + ") in QueryManager.loadShips");
                }
            }

            //Ship slots. If is npc will stay as 0
            int laserSlots     = 0;
            int generatorSlots = 0;
            int heavyGunsSlots = 0;
            int extraSlots     = 0;

            if(levels != null) {
                laserSlots     = levels.getJSONObject("slotsets").getJSONObject("lasers").getInt    ("Q");
                generatorSlots = levels.getJSONObject("slotsets").getJSONObject("generators").getInt("Q");
                heavyGunsSlots = levels.getJSONObject("slotsets").getJSONObject("heavy_guns").getInt("Q");
                extraSlots     = levels.getJSONObject("slotsets").getJSONObject("extras").getInt    ("Q");
            }

            //Assemble one ship object that will be saved into GameManager.gameShips hashmap
            try {
                GameManager.addShip(new Ship(shipID,
                        shipLootID,
                        shipHealth,
                        shipSpeed,
                        laserSlots,
                        generatorSlots,
                        heavyGunsSlots,
                        extraSlots,
                        rewardExperience,
                        rewardHonor,
                        rewardUridium,
                        rewardCredits));
            } catch (ObjectAlreadyInMap objectAlreadyInMap) {
                //Prints the exception message if something went wrong.
                Console.error(objectAlreadyInMap.getMessage());
            }
        }
    }

    /**
     * Load all the npc templates inside GameManager.npcTemplates
     * @throws SQLException
     */
    public static void loadNpcTemplates() throws SQLException {
        query = "SELECT shipID,name,lootID,damage,shdAbs FROM ships WHERE ia = ?";
        Object[] params = {1};

        mySQLManager.executeQuery(query, params);
        ArrayList<Object[]> data = mySQLManager.getData();

        for(Object[] npc : data) {
            int    npcID         = (int   ) npc[0];
            String name          = (String) npc[1];
            String lootID        = (String) npc[2];
            Ship   npcShip       = GameManager.getShip(lootID);
            int    averageDamage = (int   ) npc[3];
            int    shieldAbsorb  = (int   ) npc[4];

            //Adds the npc to the npcs map if can find its respective ship in GameManager.gameShips
            try {
                if(npcShip != null) {
                    GameManager.addNpc(new Npc(npcID,
                            name,
                            npcShip,
                            averageDamage,
                            shieldAbsorb));
                }
            } catch (ObjectAlreadyInMap objectAlreadyInMap) {
                //Prints the exeption
                Console.error(objectAlreadyInMap.getMessage());
            }
        }
    }

    /**
     * Load all the spacemaps of the game inside GameManager.spacemaps
     * @throws SQLException
     * @throws JSONException
     */
    public static void loadSpacemaps() throws SQLException, JSONException {
        query = "SELECT * FROM maps";

        mySQLManager.executeQuery(query, null);
        ArrayList<Object[]> data = mySQLManager.getData();

        for(Object[] spacemap : data) {
            //Basic map vars
            int    mapID   = (int   ) spacemap[0];
            String mapName = (String) spacemap[1];

            //Map limits
            String limits    = (String) spacemap[2];
            Point  mapLimits = new Point(Integer.parseInt(limits.split("x")[0]), Integer.parseInt(limits.split("x")[1]));

            //JSON time
            JSONArray npcsJSON = null;
            try {
                npcsJSON = new JSONArray((String) spacemap[4]);
            } catch (JSONException e) {
                throw new JSONException("Couldn't parse to JSONArray npcs row (" + spacemap[4] + ") in QueryManager.loadSpacemaps");
            }

            //TODO
            //JSONObject portals = new JSONObject((String) spacemap[3]);

            //Adds a spacemap object to GameManager.spacemaps
            try {
                GameManager.addSpacemap(new Spacemap(mapID,
                        mapName,
                        mapLimits,
                        npcsJSON));
            } catch (ObjectAlreadyInMap objectAlreadyInMap) {
                //Throws the exception
                Console.error(objectAlreadyInMap.getMessage());
            }

        }
    }

    /**
     * Loads the selected player
     * @param playerID of the player
     * @throws SQLException
     * @throws JSONException
     * @return loadedPlayer or null if can't find it
     */
    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public static Player loadPlayer(int playerID) throws SQLException, JSONException {
        query = "SELECT player.*, hangar.shipID,health,nanohull FROM players player INNER JOIN player_hangars hangar ON player.playerID=hangar.playerID AND player.hangarID=hangar.hangarID WHERE player.playerID = ? LIMIT 1";
        Object[] params = {playerID};

        mySQLManager.executeQuery(query, params);
        ArrayList<Object[]> data = mySQLManager.getData();

        if(data.size() > 0) {
            for(Object[] player : data) {
                //Basic variables
                int    playerEntityID  = (int   ) player[0];
                String playerName      = (String) player[1];
                Ship   playerShip      = GameManager.getShip((String) player[18]);
                int    playerHangarID  = (int   ) player[3];
                int    playerFactionID = (int   ) player[4];
                int    playerRankID    = (int   ) player[14];
                String sessionID       = (String) player[2];

                //Position
                int   playerMapID    = (int) player[5];
                Point playerPosition = new Point((int) player[6], (int) player[7]);

                //Stats
                JSONObject settings       = new JSONObject((String) player[16]);
                int        actualHealth   = (int) player[19];
                int        actualNanohull = (int) player[20];

                //Player information
                int     playerLevel      = (int    ) player[8];
                double  playerExperience = (double ) player[9];
                double  playerHonor      = (double ) player[10];
                double  playerUridium    = (double ) player[11];
                double  playerCredits    = (double ) player[12];
                float   playerJackpot    = (float  ) player[13];
                int     playerGgRings    = (int    ) player[15];
                boolean playerIsPremium  = (boolean) player[17];

                /**
                 * Search the player configurations in GameManager.playerEquipment
                 * if the map is empty load them in QueryManager.loadEquipment
                 */
                PlayerEquipment[] configurations = new PlayerEquipment[2];

                for (int configID = 0; configID < configurations.length; configID++) {
                    configurations[configID] = GameManager.getPlayerEquipment(playerEntityID, configID);

                    //The player doesn't have a configuration in the map
                    if (configurations[configID] == null) {
                        configurations[configID] = QueryManager.loadEquipment(playerEntityID, playerHangarID, configID);
                        //And adds the config to GameManager to get it from there the next time
                        GameManager.addPlayerEquipment(configurations[configID]);
                    }
                }

                Player loadedPlayer = new Player(
                        playerEntityID,
                        playerName,
                        playerShip,
                        configurations,
                        playerPosition,
                        playerMapID,
                        actualHealth,
                        actualNanohull,
                        playerHangarID,
                        playerFactionID,
                        playerRankID,
                        sessionID,
                        playerLevel,
                        playerExperience,
                        playerHonor,
                        playerCredits,
                        playerUridium,
                        playerJackpot,
                        playerGgRings,
                        playerIsPremium,
                        settings,
                        QueryManager.getPlayerAmmo(playerEntityID),
                        QueryManager.getPlayerDrones(playerEntityID, playerHangarID)
                );

                //Adds the account to GameManager, so if the player reconnects will be taken from there instead of doing another query
                try {
                    GameManager.addAccount(loadedPlayer);
                } catch (ObjectAlreadyInMap objectAlreadyInMap) {
                    Console.error(objectAlreadyInMap.getMessage());
                }

                return loadedPlayer;

            } //@end for
        } else {
            //if couldn't find the user in the database
            Console.error("(QueryManager.loadPlayer) Player with ID " + playerID + " doesn't exist on the database.");
        }
        return null;
    }

    /**
     * Gets the correspondent PlayerAmmunition object for the selected playerID
     * @throws SQLException
     * @throws JSONException
     */
    public static PlayerAmmunition getPlayerAmmo(int playerID) throws SQLException, JSONException {
        query = "SELECT * FROM player_ammunition WHERE playerID = ? LIMIT 1";
        Object[] params = {playerID};

        mySQLManager.executeQuery(query, params);
        ArrayList<Object[]> data = mySQLManager.getData();

        if(data.size() > 0) {
            for(Object[] playerAmmo : data) {
                JSONArray laserAmmo = new JSONArray((String) playerAmmo[1]);

                return new PlayerAmmunition(playerID, laserAmmo);
            }
        }

        return null;
    }

    /**
     * Get the player drones
     * @param playerID playerID
     * @return Drone ArrayList or null
     * @throws SQLException
     */
    public static ArrayList<Drone> getPlayerDrones(int playerID, int hangarID) throws SQLException, JSONException {
        query = "SELECT player_drone.*, player_item.* FROM player_drones player_drone INNER JOIN player_items player_item ON player_drone.droneID=player_item.itemID WHERE player_item.playerID = ?";
        Object[] params = {playerID};

        mySQLManager.executeQuery(query, params);
        ArrayList<Object[]> data         = mySQLManager.getData();
        ArrayList<Drone>    playerDrones = new ArrayList<>();

        if(data.size() > 0) {
            for(Object[] drone : data) {
                int    droneID     = (int   ) drone[0];
                String droneLootID = (String) drone[1];
                int    droneLevel  = (int   ) drone[2];
                int    droneHealth = (int   ) drone[3];

                //effect [4]
                JSONArray hangarsJSON = new JSONArray((String) drone[5]);

                /**
                 * Drone configurations
                 */
                PlayerEquipment[] droneEquipment = null;

                for (int i = 0; i < hangarsJSON.length(); i++) {
                    //select the configurations for the correct hangarID
                    if (hangarsJSON.getJSONObject(i).getInt("hangarID") == hangarID) {
                        //Gets the configurations json
                        String configurations = hangarsJSON.get(i).toString();
                        droneEquipment = PlayerEquipment.parseDroneEquipment(playerID, configurations);
                    }
                }

                playerDrones.add(new Drone(droneID, droneLootID, droneLevel, droneHealth, droneEquipment));
            }
        }

        return playerDrones;
    }


    /**
     * Gets the itemLootId for the selected item
     * @param itemID ItemID
     * @return itemLootID (String)
     * @throws SQLException
     */
    public static String getItemLootID(int itemID) throws SQLException {
        query = "SELECT lootID FROM player_items WHERE itemID = ?";
        Object[] params = {itemID};

        mySQLManager.executeQuery(query, params);
        ArrayList<Object[]> data = mySQLManager.getData();

        //to avoid ArrayOutOfBoundExceptions
        return (data.size() > 0) ? (String) data.get(0)[0] : null;
    }

    /**
     * Parses a JSON String taken from the player hangar config to one PlayerEquipment object
     * @param playerID playerID
     * @param hangarID hangarID
     * @param configID config num
     * @return PlayerEquipment object
     * @throws SQLException
     * @throws JSONException
     */
    public static PlayerEquipment loadEquipment(int playerID, int hangarID, int configID) throws SQLException, JSONException {
        query = "SELECT config" + (configID + 1) + " FROM player_hangars WHERE playerID = ? AND hangarID = ?";
        Object[] params = {playerID, hangarID};

        mySQLManager.executeQuery(query, params);
        ArrayList<Object[]> data = mySQLManager.getData();

        //Parse the selected data string
        return PlayerEquipment.parseEquipment(playerID, configID, (String) data.get(0)[0], 0);
    }
}