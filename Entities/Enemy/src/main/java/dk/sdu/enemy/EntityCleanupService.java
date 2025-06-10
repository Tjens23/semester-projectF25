package dk.sdu.enemy;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for cleaning up entities marked for removal
 * Ensures proper cleanup of health bars when zombies are removed
 */
public class EntityCleanupService implements IPostEntityProcessingService {
    
    @Override
    public void process(GameData gameData, World world) {
        // Collect entities marked for removal
        List<Entity> entitiesToRemove = new ArrayList<>();
        
        for (Entity entity : world.getEntities()) {
            if (entity.isMarkedForRemoval()) {
                entitiesToRemove.add(entity);
            }
        }
        
        // Clean up and remove entities
        for (Entity entity : entitiesToRemove) {
            // Special cleanup for zombies
            if (entity instanceof Zombie) {
                Zombie zombie = (Zombie) entity;
                zombie.cleanup(); // This will clean up the health bar
                System.out.println("[CLEANUP] Cleaned up zombie health bar for zombie: " + zombie.getID());
            }
            
            // Remove the entity from the world
            world.removeEntity(entity);
            System.out.println("[CLEANUP] Removed entity from world: " + entity.getID());
        }
    }
}

