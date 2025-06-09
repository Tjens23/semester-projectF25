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
                System.out.println("ZombieControlSystem: Updating zombie at (" + zombie.getX() + ", " + zombie.getY() + ") with " + zombie.getComponents().size() + " components");
                // Call the zombie's update method which will update all its components
                zombie.update();
            }
        }
    }
}

