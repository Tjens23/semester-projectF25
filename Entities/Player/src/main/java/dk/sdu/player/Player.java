package dk.sdu.player;
import dk.sdu.common.data.Entity;
import dk.sdu.currency.CurrencySystem;
import dk.sdu.inventory.Inventory;

public class Player extends Entity {
    private String name;
    private int health;
    private Inventory inventory = new Inventory();
    private CurrencySystem currencySystem = new CurrencySystem(5000);
    public Player(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Inventory getInventory() {
        return inventory;
    }


    public CurrencySystem getCurrencySystem() {
        return this.currencySystem;
    }

}
