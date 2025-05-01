package dk.sdu.shop;


import dk.sdu.item.Item;
import dk.sdu.player.Player;

public interface ShopService {
    boolean buyItem(dk.sdu.player.Player player, Item item);
    boolean sellItem(Player player, Item item);
    void displayItems();
}
