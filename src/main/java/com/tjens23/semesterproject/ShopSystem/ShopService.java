package com.tjens23.semesterproject.ShopSystem;

public interface ShopService {
    boolean buyItem(Player player, Item item);
    boolean sellItem(Player player, Item item);
    void displayItems();
}
