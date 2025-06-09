package dk.sdu.collisionsystem;

import dk.sdu.bullet.Bullet;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.services.IPostEntityProcessingService;
import dk.sdu.common.data.World;

public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        // Skip collision detection if game is already over
        if (gameData.isGameOver()) {
            return;
        }

        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // check if the entity is collidable
                if (!entity1.isCollidable() || !entity2.isCollidable()) {
                    continue;
                }

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
                    // Check for player-zombie collision
                    if (isPlayerZombieCollision(entity1, entity2)) {
                        handlePlayerZombieCollision(gameData, world, entity1, entity2);
                        return; // Stop processing after game over
                    }
                    // Check for bullet-zombie collision
                    else if (isBulletZombieCollision(entity1, entity2)) {
                        handleBulletZombieCollision(world, entity1, entity2);
                    }
                    // Handle other collisions if needed
                    else {
                        // This might need to be updated based on other collision types
                    }
                }
            }
        }
    }
    
    private boolean isPlayerZombieCollision(Entity entity1, Entity entity2) {
        // Use entity tags to identify player and zombie entities
        String tag1 = entity1.getTag();
        String tag2 = entity2.getTag();
        
        if (tag1 == null || tag2 == null) {
            return false;
        }
        
        return ("PLAYER".equalsIgnoreCase(tag1) && "ZOMBIE".equalsIgnoreCase(tag2)) ||
               ("ZOMBIE".equalsIgnoreCase(tag1) && "PLAYER".equalsIgnoreCase(tag2));
    }
    
    private void handlePlayerZombieCollision(GameData gameData, World world, Entity entity1, Entity entity2) {
        // Find the player entity using tags
        Entity player = null;
        if ("PLAYER".equalsIgnoreCase(entity1.getTag())) {
            player = entity1;
        } else if ("PLAYER".equalsIgnoreCase(entity2.getTag())) {
            player = entity2;
        }
        
        if (player != null) {
            // Mark player for removal (represents death)
            player.markForRemoval();
            
            // Trigger game over
            gameData.setGameOver(true, "You were caught by a zombie!");
        }
    }

    private boolean isBulletZombieCollision(Entity entity1, Entity entity2) {
        String tag1 = entity1.getTag();
        String tag2 = entity2.getTag();

        if (tag1 == null || tag2 == null) {
            return false;
        }

        return (entity1 instanceof Bullet && "ZOMBIE".equalsIgnoreCase(tag2)) ||
               (entity2 instanceof Bullet && "ZOMBIE".equalsIgnoreCase(tag1));
    }

    private void handleBulletZombieCollision(World world, Entity entity1, Entity entity2) {
        // Find which entity is the zombie and which is the bullet
        Entity zombie;
        Entity bullet;

        if ("ZOMBIE".equalsIgnoreCase(entity1.getTag())) {
            zombie = entity1;
            bullet = entity2;
        } else {
            zombie = entity2;
            bullet = entity1;
        }

        // Decrease zombie health
        int currentHealth = zombie.getHealth();
        System.out.println("[COLLISION] Bullet hit zombie! Current health: " + currentHealth);
        
        zombie.setHealth(currentHealth - 1);
        int newHealth = zombie.getHealth();
        System.out.println("[COLLISION] Zombie health decreased to: " + newHealth);

        // Remove the bullet
        world.removeEntity(bullet);
        System.out.println("[COLLISION] Bullet removed from world");

        // Check if zombie is dead
        if (zombie.getHealth() <= 0) {
            world.removeEntity(zombie);
            System.out.println("[COLLISION] Zombie eliminated! Health reached 0, removing from world");
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

}
