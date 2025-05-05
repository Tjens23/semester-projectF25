package dk.sdu.shop;


import dk.sdu.item.Item;
import dk.sdu.player.Player;

import java.util.HashMap;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final Map<String, Item> items = new HashMap<>();
    public ShopServiceImpl() {
        // Tilføjer nogle varer til butikken (du kan tilføje flere)
        items.put("Sword", new Item(0,"Sword", Item.itemTypes.antihailnet,500));
        items.put("Shield", new Item(1,"Shield", Item.itemTypes.fertilizer,300));
        items.put("Potion", new Item(2, "Potion", Item.itemTypes.plantPesticide,100));
    }

    @Override
    public boolean buyItem(Player player, Item item) {
        if (player.getCurrencySystem().subtractCurrency(item.getPrice())) {
            player.getInventory().AddItemToInventory(item);
            System.out.println("Købte: " + item.getName());
            return true;
        }
        System.out.println("Ikke nok valuta til at købe: " + item.getName());
        return false;
    }

    @Override
    public boolean sellItem(Player player, Item item) {
        if (player.getInventory().playerHasItem(item)) {
            player.getCurrencySystem().addCurrency(item.getPrice() / 2); // Sælger til halv pris
            player.getInventory().RemoveItemFromInventory(item);
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
