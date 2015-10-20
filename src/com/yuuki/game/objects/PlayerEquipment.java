package com.yuuki.game.objects;

import com.yuuki.game.objects.equipment_objects.Extra;
import com.yuuki.game.objects.equipment_objects.Generator;
import com.yuuki.game.objects.equipment_objects.Laser;
import com.yuuki.game.objects.equipment_objects.RocketLauncher;
import com.yuuki.mysql.QueryManager;
import com.yuuki.utils.Console;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Yuuki
 * @date 04/09/2015 | 17:00
 * @package com.yuuki.game.objects
 */
public class PlayerEquipment {
    //Basic variables
    private int playerID;
    private int configID;

    //Equipment items
    private ArrayList<Laser>          lasers;
    private ArrayList<RocketLauncher> heavyGuns;
    private ArrayList<Generator>      generators;
    private ArrayList<Extra>          extras;

    //Current stats
    private int currentHealth = 0;
    private int currentShield = 0;

    /**
     * Equipment constructor.
     * @param playerID 'Owner' of the equipment
     * @param configID config num
     * @param lasers Laser objects
     * @param heavyGuns RocketLauncher objects
     * @param generators Generators objects
     * @param extras Extra objects
     */
    public PlayerEquipment(int playerID, int configID, ArrayList<Laser> lasers, ArrayList<RocketLauncher> heavyGuns, ArrayList<Generator> generators,
                           ArrayList<Extra> extras, int currentShield) {
        this.playerID      = playerID;
        this.configID      = configID;
        this.lasers        = lasers;
        this.heavyGuns     = heavyGuns;
        this.generators    = generators;
        this.extras        = extras;
        this.currentShield = currentShield;
    }

    /**
     * For a given configuration JSON string returns the correspondent PlayerEquipment object
     * @param playerID PlayerID
     * @param configID Config num
     * @param configString JSON Config String
     * @return PlayerEquipment object
     * @throws JSONException
     * @throws SQLException
     */
    public static PlayerEquipment parseEquipment(int playerID, int configID, String configString, int currentShield) throws JSONException, SQLException {
        JSONObject equipmentJSON = new JSONObject(configString);

        /**
         * Lasers
         */
        ArrayList<Laser> lasers     = new ArrayList<>();
        JSONArray        lasersJSON = equipmentJSON.getJSONArray("lasers");

        for(int i = 0; i < lasersJSON.length(); i++) {
            int itemID = lasersJSON.getInt(i);
            //Adds one laser object to the array
            lasers.add(new Laser(itemID, QueryManager.getItemLootID(itemID)));
        }

        /**
         * Heavy Guns
         */
        ArrayList<RocketLauncher> heavyGuns     = new ArrayList<>();
        JSONArray                 heavyGunsJSON = equipmentJSON.getJSONArray("heavy_guns");

        for(int i = 0; i < heavyGunsJSON.length(); i++) {
            int itemID = heavyGunsJSON.getInt(i);
            //Adds one laser object to the array
            heavyGuns.add(new RocketLauncher(itemID, QueryManager.getItemLootID(itemID)));
        }

        /**
         * Generators
         */
        ArrayList<Generator> generators     = new ArrayList<>();
        JSONArray            generatorsJSON = equipmentJSON.getJSONArray("generators");

        for(int i = 0; i < generatorsJSON.length(); i++) {
            int itemID = generatorsJSON.getInt(i);
            //Adds one laser object to the array
            generators.add(new Generator(itemID, QueryManager.getItemLootID(itemID)));
        }

        /**
         * Extras
         */
        ArrayList<Extra> extras     = new ArrayList<>();
        JSONArray        extrasJSON = equipmentJSON.getJSONArray("extras");

        for(int i = 0; i < extrasJSON.length(); i++) {
            int itemID = extrasJSON.getInt(i);
            //Adds one laser object to the array
            extras.add(new Extra(itemID, QueryManager.getItemLootID(itemID)));
        }

        return new PlayerEquipment(playerID, configID, lasers, heavyGuns, generators, extras, currentShield);
    }

    /**
     * For a given JSON drone configuration returns the correspondent PlayerEquipment array
     * @param playerID PlayerID
     * @param configString JSON string
     * @return PlayerEquipment array
     * @throws JSONException
     * @throws SQLException
     */
    public static PlayerEquipment[] parseDroneEquipment(int playerID, String configString) throws JSONException, SQLException {
        PlayerEquipment[] playerEquipment = new PlayerEquipment[2];
        JSONObject        equipmentJSON   = new JSONObject(configString);

        //{"hangarID":1,"EQ":[{"default":[],"configID":1,"design":[]},{"default":[],"configID":2,"design":[]}]}
        JSONArray configurations = equipmentJSON.getJSONArray("EQ");

        for(int configID = 0; configID < configurations.length(); configID++) {
            //get the items foreach config
            JSONArray items = configurations.getJSONObject(configID).getJSONArray("default");

            //Items arrays
            ArrayList<Laser>     lasers     = new ArrayList<>();
            ArrayList<Generator> generators = new ArrayList<>();
            ArrayList<Extra>     extras     = new ArrayList<>();

            for(int j = 0; j < items.length(); j++) {
                int    itemID = items.getInt(j);
                String lootID = QueryManager.getItemLootID(itemID);

                String[] splittedLootID = lootID.split("_");
                switch(splittedLootID[1]) {
                    case "generator":
                        generators.add(new Generator(itemID, lootID));
                        break;
                    case "weapon":
                        lasers.add(new Laser(itemID, lootID));
                        break;
                    case "extra":
                        extras.add(new Extra(itemID, lootID));
                        break;
                    default:
                        Console.error("Unknown drone item #" + itemID + "|" + lootID);
                }
            }

            playerEquipment[configID] = new PlayerEquipment(playerID, configID, lasers, null, generators, extras, 0);
        }

        return playerEquipment;
    }

    /**
     * Gets the total shield of the configuration
     * TODO add support for more shields
     * @return shield amount
     */
    public int getMaxShield() {
        int shield = 0;

        for(Generator g : getGenerators()) {
            switch(g.getLootID()) {
                case "equipment_generator_shield_sg3n-b02":
                    shield += 10000;
                    break;
            }
        }

        return shield;
    }

    /**
     * Gets the max speed of this config
     * @return Speed (int)
     */
    public int getSpeed() {
        int speed = 0;

        for(Generator g : getGenerators()) {
            switch(g.getLootID()) {
                case "equipment_generator_speed_g3n-7900":
                    speed += 10;
                    break;
            }
        }

        return speed;
    }

    /***********
     * GETTERS *
     ***********/
    public String getIdentifier() {
        return playerID + "|" + configID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getConfigID() {
        return configID;
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public ArrayList<RocketLauncher> getHeavyGuns() {
        return heavyGuns;
    }

    public ArrayList<Generator> getGenerators() {
        return generators;
    }

    public ArrayList<Extra> getExtras() {
        return extras;
    }

    public int getCurrentShield() {
        return currentShield;
    }

    public void setCurrentShield(int currentShield) {
        this.currentShield = currentShield;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
