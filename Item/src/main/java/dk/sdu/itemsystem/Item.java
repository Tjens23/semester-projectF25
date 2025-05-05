package dk.sdu.itemsystem;

import dk.sdu.common.data.Entity;
public class Item extends Entity {
    public enum ItemType {
        AMMO,
        HEALTH_PACK,
        SPEED_BOOST,
        WEAPON,
        LOOT_BOX,
        EQUIPMENT
    }

    private final ItemType type;
    private boolean collected = false;

    public Item(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }

}
