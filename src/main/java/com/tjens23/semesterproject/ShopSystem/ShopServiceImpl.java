package com.tjens23.semesterproject.ShopSystem;

import com.tjens23.semesterproject.ShopSystem.entities.Player;
import com.tjens23.semesterproject.ShopSystem.Item;

import java.util.HashMap;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final Map<String, Item> items = new HashMap<>();

    public ShopServiceImpl() {
        // Initialiser items i butikken
    }

    @Override
    public boolean buyItem(Player player, Item item) {
        if (player.getCurrency() >= item.getPrice()) {
            player.decreaseCurrency(item.getPrice());
            player.getInventory().addItem(item);
            return true;
        }
        return false;
    }

    @Override
    public boolean sellItem(Player player, Item item) {
        player.increaseCurrency(item.getPrice() / 2);
        player.getInventory().removeItem(item);
        return true;
    }

    @Override
    public void displayItems() {
        items.forEach((name, item) -> System.out.println(name + " - " + item.getPrice()));
    }
}
