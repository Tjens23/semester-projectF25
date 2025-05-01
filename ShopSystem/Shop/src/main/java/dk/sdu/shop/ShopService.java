package dk.sdu.shop;

import com.tjens23.semesterproject.entities.Player;
import com.tjens23.semesterproject.entities.Item;

public interface ShopService {
    boolean buyItem(Player player, Item item);
    boolean sellItem(Player player, Item item);
    void displayItems();
}
