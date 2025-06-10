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
                    // Check for zombie-zombie collision
                    else if (isZombieZombieCollision(entity1, entity2)) {
                        handleZombieZombieCollision(entity1, entity2);
                    }
                    // Check for entity-wall collision
                    else if (isEntityWallCollision(entity1, entity2)) {
                        handleEntityWallCollision(entity1, entity2);
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
    
    private boolean isZombieZombieCollision(Entity entity1, Entity entity2) {
        String tag1 = entity1.getTag();
        String tag2 = entity2.getTag();
        
        if (tag1 == null || tag2 == null) {
            return false;
        }
        
        return "ZOMBIE".equalsIgnoreCase(tag1) && "ZOMBIE".equalsIgnoreCase(tag2);
    }
    
    private void handleZombieZombieCollision(Entity zombie1, Entity zombie2) {
        // Calculate the direction vector between the two zombies
        double dx = zombie1.getX() - zombie2.getX();
        double dy = zombie1.getY() - zombie2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // Avoid division by zero
        if (distance == 0) {
            // If zombies are at exact same position, push them in opposite directions
            zombie1.setX(zombie1.getX() + 1);
            zombie2.setX(zombie2.getX() - 1);
            return;
        }
        
        // Normalize the direction vector
        double normalizedDx = dx / distance;
        double normalizedDy = dy / distance;
        
        // Calculate how much they overlap
        double overlap = (zombie1.getRadius() + zombie2.getRadius()) - distance;
        
        // Push zombies apart by half the overlap distance each
        double pushDistance = overlap * 0.5;
        
        // Move zombie1 away from zombie2
        zombie1.setX(zombie1.getX() + normalizedDx * pushDistance);
        zombie1.setY(zombie1.getY() + normalizedDy * pushDistance);
        
        // Move zombie2 away from zombie1
        zombie2.setX(zombie2.getX() - normalizedDx * pushDistance);
        zombie2.setY(zombie2.getY() - normalizedDy * pushDistance);
        
        System.out.println("[COLLISION] Zombie-zombie collision resolved: pushed apart by " + pushDistance + " units");
    }
    
    private boolean isEntityWallCollision(Entity entity1, Entity entity2) {
        String tag1 = entity1.getTag();
        String tag2 = entity2.getTag();
        
        if (tag1 == null || tag2 == null) {
            return false;
        }
        
        // Check if one entity is a wall and the other is a movable entity (player or zombie)
        boolean entity1IsWall = "WALL".equalsIgnoreCase(tag1);
        boolean entity2IsWall = "WALL".equalsIgnoreCase(tag2);
        
        boolean entity1IsMovable = "PLAYER".equalsIgnoreCase(tag1) || "ZOMBIE".equalsIgnoreCase(tag1);
        boolean entity2IsMovable = "PLAYER".equalsIgnoreCase(tag2) || "ZOMBIE".equalsIgnoreCase(tag2);
        
        return (entity1IsWall && entity2IsMovable) || (entity2IsWall && entity1IsMovable);
    }
    
    private void handleEntityWallCollision(Entity entity1, Entity entity2) {
        // Find which entity is the wall and which is the movable entity
        Entity wall;
        Entity movableEntity;
        
        if ("WALL".equalsIgnoreCase(entity1.getTag())) {
            wall = entity1;
            movableEntity = entity2;
        } else {
            wall = entity2;
            movableEntity = entity1;
        }
        
        // Calculate collision resolution direction
        double dx = movableEntity.getX() - wall.getX();
        double dy = movableEntity.getY() - wall.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // Avoid division by zero
        if (distance == 0) {
            // Push movable entity away from wall in a default direction
            movableEntity.setX(movableEntity.getX() + movableEntity.getRadius());
            return;
        }
        
        // Normalize the direction vector
        double normalizedDx = dx / distance;
        double normalizedDy = dy / distance;
        
        // Calculate how much they overlap
        double overlap = (movableEntity.getRadius() + wall.getRadius()) - distance;
        
        // Push movable entity out of the wall
        if (overlap > 0) {
            movableEntity.setX(movableEntity.getX() + normalizedDx * overlap);
            movableEntity.setY(movableEntity.getY() + normalizedDy * overlap);
            
            System.out.println("[COLLISION] " + movableEntity.getTag() + "-wall collision resolved: pushed entity out by " + overlap + " units");
        }
    }

}
