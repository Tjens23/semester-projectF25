package dk.sdu.collisionsystem;

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
                    // Check for player-zombie collision using tags
                    if (isPlayerZombieCollision(entity1, entity2)) {
                        handlePlayerZombieCollision(gameData, world, entity1, entity2);
                        return; // Stop processing after game over
                    } else {
                        // Handle other collisions (bullets, items, etc.)
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
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

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

}
