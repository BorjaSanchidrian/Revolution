package com.yuuki.game.objects.equipment_objects;

/**
 * Class used as template for all the equipment objects
 * @author Yuuki
 * @date 04/09/2015 | 17:05
 * @package com.yuuki.game.objects.equipment_objects
 */
public abstract class EquipmentItem {
    private int    itemID;
    private String lootID;

    public EquipmentItem(int itemID, String lootID) {
        this.itemID = itemID;
        this.lootID = lootID;
    }

    /***********
     * GETTERS *
     ***********/
    public int getItemID() {
        return itemID;
    }

    public String getLootID() {
        return lootID;
    }
}
