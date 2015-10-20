package com.yuuki.game.managers;

import com.yuuki.game.GameManager;
import com.yuuki.game.objects.GameCharacter;
import com.yuuki.game.objects.Player;
import com.yuuki.game.objects.PlayerEquipment;
import com.yuuki.game.objects.Spacemap;
import com.yuuki.networking.GameSession;

import java.util.Calendar;

/**
 * @author Yuuki
 * @date 29/09/2015
 * @package com.yuuki.game.managers
 * @project ProjectX_Emulator
 */
public class PlayerManager {
    /***********************
     * FOR SINGLETON USAGE *
     ***********************/
    private static PlayerManager INSTANCE = null;

    private PlayerManager() {
        //SINGLETON
    }

    public static PlayerManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    /*************
     * CONSTANTS *
     *************/
    private static final int DEFAULT_CHANGE_CONFIG_TIME       =  5000;
    private static final int DEFAULT_REPAIR_TIME              = 15000;
    private static final int DEFAULT_TIME_BETWEEN_REPAIR_TICK =  2000;

    /**
     * This method will return true/false depending
     * of the time since the last configChange
     */

    public boolean canChangeConfig(Player player) {
        return (Calendar.getInstance().getTimeInMillis() - player.getLastConfigChange()) >= DEFAULT_CHANGE_CONFIG_TIME;
    }

    /**
     * Changes the active configuration
     * @param newConfigID configID (1-2)
     * @param gameSession Used to get the needed sockets and player object
     */
    public void changeConfig(GameSession gameSession, int newConfigID) {
        Player player = gameSession.getPlayer();

        //Check if the configID is incorrect
        if(newConfigID > 2 || newConfigID < 0) {
            newConfigID = 1;
        }
        //Changes the config variable (0-1). and newConfigID (1-2)
        player.setCurrentConfig(newConfigID - 1);
        player.setLastConfigChange(Calendar.getInstance().getTimeInMillis());

//        gameSession.getGameClientConnection().sendCommand(new LegacyModule("0|A|CC|" + newConfigID));
        updatePlayer(gameSession);
    }

    /**
     * Updates in real time the in-game player information
     * @param gameSession Used to get the needed sockets and player object
     */
    public void updatePlayer(GameSession gameSession) {
        Player player = gameSession.getPlayer();

        /**
         * Check if the current shield of the configuration is > of the maxShield of that configuration and corrects it
         */
        PlayerEquipment playerEquipment = player.getPlayerEquipment()[player.getCurrentConfig()];
        if(playerEquipment.getCurrentShield() >= playerEquipment.getMaxShield()) {
            playerEquipment.setCurrentShield(playerEquipment.getMaxShield());
        }

        //Sends update shield command
//        gameSession.getClient9Connection().sendCommand(new AttributeShieldUpdateCommand(player.getCurrentShield(), player.getMaxShield()));
//
//        //Sends update speed command
//        gameSession.getClient9Connection().sendCommand(new SetSpeedCommand(player.getSpeed(), player.getSpeed()));
//
//        //Sends update ship command
//        ConnectionHandler.sendCommandToRange(player, new VisualModifierCommand(player.getEntityID(), VisualModifierCommand.CHANGE_SHIP, 1, player.getShip().getShipLootID(), 1, true)); //to the in-range players
//        gameSession.getClient9Connection().sendCommand(new VisualModifierCommand(player.getEntityID(), VisualModifierCommand.CHANGE_SHIP, 1, player.getShip().getShipLootID(), 1, true));              //to the player
    }

    /**
     * Checks if you can select the clicked entity
     * @param gameSession Used to get the needed sockets and player object
     * @param clickedID From ShipSelectionRequest
     */
    public void selectCharacter(GameSession gameSession, int clickedID) {
        Player   player         = gameSession.getPlayer();
        Spacemap playerSpacemap = GameManager.getSpacemap(player.getMapID());

        if(playerSpacemap != null) {
            GameCharacter selectedEntity = playerSpacemap.getEntity(clickedID);

            //Check if the entity can be targeted (EMP, etc..)
            if(selectedEntity.canBeTargeted()) {
                player.setSelectedEntity(selectedEntity);
//                gameSession.getClient9Connection().sendCommand(selectedEntity.getShipSelectionCommand());
            }
        }
    }

    /**
     * Check if the character can be repaired. Checking the time of the lastAttack and the lastRepair tick
     * @param character Main object
     * @return true/false
     */
    public boolean canRepair(GameCharacter character) {
        return ((Calendar.getInstance().getTimeInMillis() - character.getLastAttackTime()) >= DEFAULT_REPAIR_TIME) && ((Calendar.getInstance().getTimeInMillis() - character.getLastRepairTick()) >= DEFAULT_TIME_BETWEEN_REPAIR_TICK);
    }


    /**
     * Repairs the selected character
     * @param character To be repaired
     */
    public void repairManager(GameCharacter character) {
        //Checks if the character can be repaired
        if(canRepair(character)) {

            /***********************
             * Shield Regeneration *
             ***********************/
            //Check if the user has less shield than the maximum
            if(character.getCurrentShield() < character.getMaxShield()) {
                //will regenerate X amount per tick
                int shieldRegeneration = (int) (character.getMaxShield() * 0.1);

                //check if the current shield + the regen amount will be more than the maxShield and sets it to the right value
                if ((character.getCurrentShield() + shieldRegeneration) > character.getMaxShield()) {
                    shieldRegeneration = character.getMaxShield() - character.getCurrentShield();
                }

                //updates the shield
                character.setCurrentShield(character.getCurrentShield() + shieldRegeneration);

                if(character instanceof Player) {
                    GameSession gameSession = GameManager.getGameSession(character.getEntityID());

                    if(gameSession != null) {
                        //Shield update
//                        gameSession.getClient9Connection().sendCommand(new AttributeShieldUpdateCommand(character.getCurrentShield(), character.getMaxShield()));

                        /*
                         * Send the update shipSelectionCommand with the new stats
                         */
//                        sendPacketToLockedUsers(gameSession.getPlayer().getShipSelectionCommand(), gameSession);
                    }
                }
            }

            /***********************
             * Health Regeneration *
             ***********************/
            //TODO check repbot and auto rep lelz
            if(character.getActualHealth() < character.getMaxHealth()) {
                int healthRegeneration = (int) (character.getMaxHealth() * 0.1);

                //check if the current health + the regen amount will be more than the maxHealth and sets it to the right value
                if ((character.getActualHealth() + healthRegeneration) > character.getMaxHealth()) {
                    healthRegeneration = character.getMaxHealth() - character.getActualHealth();
                }

                //updates character health
                character.setActualHealth(character.getActualHealth() + healthRegeneration);

                if(character instanceof Player) {
                    GameSession gameSession = GameManager.getGameSession(character.getEntityID());

                    if(gameSession != null) {
                        //health update
//                        gameSession.getClient9Connection().sendCommand(new AttributeHitpointUpdateCommand(
//                                character.getActualHealth(),
//                                character.getMaxHealth(),
//                                character.getActualNanohull(),
//                                character.getMaxHealth())
//                        );

                        /*
                         * Send the update shipSelectionCommand with the new stats
                         */
//                        sendPacketToLockedUsers(gameSession.getPlayer().getShipSelectionCommand(), gameSession);
                    }
                }
            }

            //Sets the last tick time to the current time
            character.setLastRepairTick(Calendar.getInstance().getTimeInMillis());
        }
    }

    /**
     * Send a command to all the users who have gameSession.getPlayer() locked.
     * Useful to update stats
     * @param serverCommand Command to be send
     * @param gameSession To check which players have this player locked
     */
//    public void sendPacketToLockedUsers(IServerCommand serverCommand, GameSession gameSession) {
//        Spacemap spacemap = GameManager.getSpacemap(gameSession.getPlayer().getMapID());
//        if(spacemap != null) {
//            for(Map.Entry<Integer, GameCharacter> characterEntry : spacemap.getMapCharacterEntities()) {
//                if(characterEntry.getValue() instanceof Player) {
//                    //If the character has selected our player
//                    GameCharacter selectedEntity = characterEntry.getValue().getSelectedEntity();
//                    if(selectedEntity != null && selectedEntity.getEntityID() == gameSession.getPlayer().getEntityID()) {
//                                        /*
//                                         * If the character is a Player (you can't send commands to npcs) will update the ship selection command
//                                         */
//                        GameSession characterGameSession = GameManager.getGameSession(characterEntry.getValue().getEntityID());
//
//                        if (characterGameSession != null) {
//                            characterGameSession.getClient9Connection().sendCommand(serverCommand);
//                        }
//                    }
//                }
//            }
//        }
//    }
}