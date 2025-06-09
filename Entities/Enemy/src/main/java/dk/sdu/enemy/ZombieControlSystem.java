package dk.sdu.enemy;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IEntityProcessingService;

/**
 * Entity processing service that handles zombie component updates
 */
public class ZombieControlSystem implements IEntityProcessingService {
    
    @Override
    public void process(GameData gameData, World world) {
        // Process all zombie entities in the world
        for (Entity entity : world.getEntities(Zombie.class)) {
            if (entity instanceof Zombie) {
                Zombie zombie = (Zombie) entity;
                
                // Check if zombie should be marked for removal (dead)
                if (zombie.getHealth() <= 0 && !zombie.isMarkedForRemoval()) {
                    zombie.markForRemoval(); // Mark for removal, cleanup will be handled by EntityCleanupService
                    System.out.println("[ZOMBIE] Marking dead zombie for removal: " + zombie.getID());
                }
                
                // Update zombie components (including health bar) if not marked for removal
                if (!zombie.isMarkedForRemoval()) {
                    zombie.update();
                }
            }
        }
    }
}

