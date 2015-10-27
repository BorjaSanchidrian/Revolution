package com.yuuki.game.objects;

import com.yuuki.game.GameManager;
import com.yuuki.game.interfaces.Tick;
import com.yuuki.networking.ConnectionHandler;
import com.yuuki.networking.packets.ServerPackets.ShipRemoveCommand;
import com.yuuki.utils.Console;
import org.json.JSONArray;
import org.json.JSONException;

import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yuuki
 * @date 02/09/2015 | 13:52
 * @package com.yuuki.game.objects
 */
public class Spacemap implements Tick {
    //Basic variables
    private short  mapID;
    private String mapName;
    private Point  mapLimits;

    //Map entities
    //private ArrayList<Portal> mapPortals = new ArrayList<>(); TODO
    private ConcurrentHashMap<Integer, GameCharacter> mapCharacterEntities = new ConcurrentHashMap<>();

    /**
     * EntrySet getter
     */
    public Set<Map.Entry<Integer, GameCharacter>> getMapCharacterEntities() {
        return mapCharacterEntities.entrySet();
    }

    /*
     * Used to store the npcs information when the spacemap is created in QueryManager.
     * instead of creating X npcs directly in QueryManager, maybe the spacemap is always empty
     * and wastes memory...
     */
    private JSONArray npcsJSON;

    /**
     * Spacemap constructor
     * @param mapID mapID => look maps.php
     * @param mapName map name
     * @param mapLimits limits of radiation
     */
    public Spacemap(short mapID, String mapName, Point mapLimits, JSONArray npcsJSON) {
        this.mapID     = mapID;
        this.mapName   = mapName;
        this.mapLimits = mapLimits;
        this.npcsJSON  = npcsJSON;
    }

    /**
     * Parse npcsJSON array and create the amount of npcs needed into npcsEntities
     */
    public void createNpcs() {
        try {
            /*
             * For each entry in Npcs JSON Array
             */
            for (int i = 0; i < npcsJSON.length(); i++) {
                int npcID  = npcsJSON.getJSONObject(i).getInt("npcID");
                int amount = npcsJSON.getJSONObject(i).getInt("count");

                //This has int npcID, String name, Ship ship,  int averageDamage, int shieldAbsorb
                Npc npcTemplate = GameManager.getNpc(npcID);

                //If something went wrong because the npc doesn't exists in the templates
                if(npcTemplate != null) {
                    for (int j = 0; j < amount; j++) {

                        //Because all the npcs will have negative ID's
                        int entityID = -(mapCharacterEntities.size() + 1);

                        Npc createdNpc = new Npc(entityID,
                                npcID,
                                npcTemplate.getName(),
                                npcTemplate.getShip(),
                                new Point(0, 0), //TODO add random spawn
                                mapID,
                                npcTemplate.getShip().getShipHealth(),
                                npcTemplate.getActualNanohull(),
                                npcTemplate.getAverageDamage(),
                                npcTemplate.getShieldAbsorb()
                        );
                        //adds the npc to the map
                        addEntity(createdNpc);
                    }
                }
            }
        } catch (JSONException e) {
            Console.error("Something went wrong reading the Npcs JSONArray in Spacemap.createNpcs()", e.getMessage());
        }
    }

    /**
     * Adds or updates a entity of the map
     * @param character Can be a player or npc
     */
    public void addEntity(GameCharacter character) {
        if(mapCharacterEntities.containsKey(character.getEntityID())) {
            mapCharacterEntities.remove(character.getEntityID());
        }
        mapCharacterEntities.put(character.getEntityID(), character);
    }

    /**
     * Removes the selected entity from the mapCharacterEntities map
     * @param entityID ID to remove
     */
    public void removeEntity(int entityID) {
        if(mapCharacterEntities.containsKey(entityID)) {
            //Removes the ship from the entitiesMap
            mapCharacterEntities.remove(entityID);

            //Removes the ship from the spacemap (visual)
            ConnectionHandler.sendPacketToSpacemap(mapID, new ShipRemoveCommand(entityID).getPacket());
        }
    }

    /**
     * Get the selected GameCharacter object
     * @param entityID Of the object
     * @return GameCharacter object or null if it doesn't exists
     */
    public GameCharacter getEntity(int entityID) {
        if (mapCharacterEntities.containsKey(entityID)) {
            return mapCharacterEntities.get(entityID);
        } else {
            return null;
        }
    }

    /**
     * Main tick method
     */
    @Override
    public void tick() {
        for(Map.Entry<Integer, GameCharacter> characterEntry : getMapCharacterEntities()) {
            //Executes each GameCharacter tick (Player, Npc atm)
            characterEntry.getValue().tick();
        }
    }

    /***********
     * GETTERS *
     ***********/

    public short getMapID() {
        return mapID;
    }

    public String getMapName() {
        return mapName;
    }

    public Point getMapLimits() {
        return mapLimits;
    }
}
