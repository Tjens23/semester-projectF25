package dk.sdu.shop;

import com.tjens23.semesterproject.entities.Player;
import com.tjens23.semesterproject.entities.Item;

import java.util.HashMap;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final Map<String, Item> items = new HashMap<>();

    public ShopServiceImpl() {
        // Tilføjer nogle varer til butikken (du kan tilføje flere)
        items.put("Sword", new Item("Sword", 500));
        items.put("Shield", new Item("Shield", 300));
        items.put("Potion", new Item("Potion", 100));
    }

    @Override
    public boolean buyItem(Player player, Item item) {
        if (player.getCurrencySystem().subtractCurrency(item.getPrice())) {
            player.getInventory().add(item);
            System.out.println("Købte: " + item.getName());
            return true;
        }
        System.out.println("Ikke nok valuta til at købe: " + item.getName());
        return false;
    }

    @Override
    public boolean sellItem(Player player, Item item) {
        if (player.getInventory().contains(item)) {
            player.getCurrencySystem().addCurrency(item.getPrice() / 2); // Sælger til halv pris
            player.getInventory().remove(item);
            System.out.println("Solgte: " + item.getName());
            return true;
        }
        System.out.println("Du har ikke " + item.getName() + " i din inventory.");
        return false;
    }

    @Override
    public void displayItems() {
        System.out.println("Varer tilgængelige i butikken:");
        items.forEach((name, item) -> System.out.println("- " + name + ": " + item.getPrice() + " valuta"));
    }
}
