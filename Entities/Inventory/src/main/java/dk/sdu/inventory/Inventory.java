package dk.sdu.inventory;

import dk.sdu.item.Item;

import java.util.ArrayList;

public class Inventory {
    public ArrayList<Item> inventory = new ArrayList<>();

    public void AddItemToInventory(Item item) {
        int inventorySlots = 8;
        if (inventory.size() >= inventorySlots) {
            return;
        }
        inventory.add(item);

    }

    public void RemoveItemFromInventory(int itemIndex) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).index == itemIndex)
                inventory.remove(itemIndex);
        }
    }

    public void RemoveItemFromInventory(Item item) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(item.name))
                inventory.remove(item);
        }
    }


    public Item CheckForItem(int itemIndex) {
        for (Item item :
                inventory) {
            if (item.index == itemIndex)
                return item;
        }
        return null;
    }

    public Item CheckForItem(Item itemName) {
        for (Item item : inventory) {
            if (item.name.equals(itemName))
                return item;
        }
        return null;
    }


    public Boolean playerHasItem(Item item) {
        for (Item item1 : inventory) {
            if (item1.name.equals(item.name))
                return true;
        }
        return false;
    }

    public Boolean BoolCheckForItemType(Item.itemTypes itemTypes) {
        for (Item item :
                inventory) {
            if (item.itemType.equals(itemTypes))
                return true;
        }
        return false;
    }

    public void add(Item item) {
        if (inventory.size() < 8) {
            inventory.add(item);
        } else {
            System.out.println("Inventory is full!");
        }
    }
}
