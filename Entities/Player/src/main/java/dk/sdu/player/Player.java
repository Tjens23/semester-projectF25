package dk.sdu.player;
import dk.sdu.common.SPI.PlayerSPI;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
//import dk.sdu.currency.CurrencySystem;
//import dk.sdu.inventory.Inventory;

public class Player extends Entity implements PlayerSPI {
    private String name;
    private int health;
    //private Inventory inventory = new Inventory();
    //private CurrencySystem currencySystem = new CurrencySystem(5000);
    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.setTag("Player");
    }
    public Player() {
        this.setTag("Player");
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
    
    public boolean isAlive() {
        return this.health > 0;
    }

    /*public Inventory getInventory() {
        return inventory;
    }*/


    /*public CurrencySystem getCurrencySystem() {
        return this.currencySystem;
    }*/

    @Override
    public Entity getPlayer(GameData gameData, World world) {
        return world.getEntities(Player.class).stream()
                .findFirst()
                .orElse(null);
        
    }
}
