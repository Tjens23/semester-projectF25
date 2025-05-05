package dk.sdu.itemsystem;

import dk.sdu.cbse.weaponsystem.Weapon;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IPostEntityProcessingService;
import dk.sdu.healthsystem.HealthComponent;
import dk.sdu.inventorysystem.Inventory;
//Skal ændre module-info så den require den rigtig path til Inventory!!!
public class ItemSystem implements IPostEntityProcessingService{
    @Override
    public void process(GameData gameData, World world) {
        for (Entity itemEntity : world.getEntities()) {
            Item item = itemEntity.getComponent(Item.class);
            if (item == null || item.isCollected()) continue;

            for (Entity other : world.getEntities()) {
                if (other == itemEntity || !other.hasTag("PLAYER")) continue;

                if (checkCollision(itemEntity, other)) {
                    applyItemEffect(itemEntity, item, other);
                    item.collect();
                    itemEntity.markForRemoval();
                    break;
                }
            }
        }
    }

    private void applyItemEffect(Entity itemEntity, Item item, Entity player) {
        switch (item.getType()) {
            case HEALTH_PACK -> {
                System.out.println("Player picked up health pack");
                player.getComponent(HealthComponent.class).heal(25);
            }
            case AMMO -> {
                System.out.println("Player picked up ammo");
                player.getComponent(Weapon.class).reload(10);
            }
            case EQUIPMENT -> {
                Equipment equip = itemEntity.getComponent(Equipment.class);
                if (equip != null) {
                    equip.equip();
                    System.out.println("Equipped: " + equip.getType());
                    player.getComponent(Inventory.class).add(equip);
                }
            }
            case LOOT_BOX -> {
                LootBox box = itemEntity.getComponent(LootBox.class);
                if (box != null && !box.isOpened()) {
                    Equipment.EquipmentType reward = box.open();
                    if (reward != null) {
                        System.out.println("Player looted: " + reward);
                        // Optionally spawn a new equipment item and add to player or world
                    }
                }
            }
        }
    }

    private boolean checkCollision(Entity a, Entity b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (a.getRadius() + b.getRadius());
    }
//    private void handleItemPickup(Entity itemEntity, Entity other) {
//        Item item = itemEntity.getComponent(Item.class);
//        if (item == null || item.isCollected()) return;
//
//        if (!other.hasTag("PLAYER")) return;
//
//        item.collect();
//
//        switch (item.getType()) {
//            case HEALTH_PACK -> applyHealth(other);
//            case AMMO -> addAmmo(other);
//            case EQUIPMENT -> {
//                Equipment equip = itemEntity.getComponent(Equipment.class);
//                if (equip != null) {
//                    equip.equip(); // mark it equipped
//                    applyEquipmentEffect(other, equip.getType());
//                }
//            }
//            case LOOT_BOX -> {
//                LootBox box = itemEntity.getComponent(LootBox.class);
//                if (box != null) {
//                    EquipmentType reward = box.open();
//                    if (reward != null) {
//                        giveEquipmentToPlayer(other, reward);
//                    }
//                }
//            }
//        }
//
//        // Optionally remove entity from the world
//        itemEntity.markForRemoval();
//    }
//
//    // These would interface with player components (like Health, Inventory, etc.)
//    private void applyHealth(Entity player) {
//        System.out.println("Health pack applied to player");
//        // player.getComponent(Health.class).heal(25);
//
//    }
//
//    private void addAmmo(Entity player) {
//        System.out.println("Ammo added to player");
//        // player.getComponent(Weapon.class).addAmmo(10);
//    }
//
//    private void applyEquipmentEffect(Entity player, EquipmentType type) {
//        System.out.println("Equipped: " + type);
//        // Apply effects based on equipment
//    }
//
//    private void giveEquipmentToPlayer(Entity player, EquipmentType reward) {
//        System.out.println("Player received loot: " + reward);
//        // Inventory inv = player.getComponent(Inventory.class);
//        // inv.add(new Equipment(reward));
//    }



}
