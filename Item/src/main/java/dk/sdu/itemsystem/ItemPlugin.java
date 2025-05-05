package dk.sdu.itemsystem;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IGamePluginService;

public class ItemPlugin implements IGamePluginService {
    Entity item;

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Item.class) {
                world.removeEntity(e);
            }
        }
    }
    private Entity createHealthPack(double x, double y) {
        Entity item = new Entity();
        item.setX(x);
        item.setY(y);
        item.setRadius(10);
        item.setTag("ITEM");
        item.addComponent(new Item(Item.ItemType.HEALTH_PACK));
        return item;
    }
    private Entity createAmmoPack(double x, double y){
        Entity item = new Entity();
        item.setX(x);
        item.setY(y);
        item.setRadius(15);
        item.setTag("ITEM");
        item.addComponent(new Item(Item.ItemType.AMMO));
        return item;
    }

    private Entity createWeapon(double x, double y){
        Entity item = new Entity();
        item.setX(x);
        item.setY(y);
        item.setRadius(10);
        item.setTag("ITEM");
        item.addComponent(new Item(Item.ItemType.WEAPON));
        return item;
    }
    private Entity createLootBox(double x, double y){
        Entity item = new Entity();
        item.setX(x);
        item.setY(y);
        item.setRadius(10);
        item.setTag("ITEM");
        item.addComponent(new Item(Item.ItemType.LOOT_BOX));
        return item;
    }
    private Entity createEquipment(double x, double y){
        Entity item = new Entity();
        item.setX(x);
        item.setY(y);
        item.setRadius(10);
        item.setTag("ITEM");
        item.addComponent(new Item(Item.ItemType.EQUIPMENT));
        return item;
    }



}
