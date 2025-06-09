package dk.sdu.enemy;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Handles continuous spawning of zombies during gameplay
 */
public class ZombieSpawner implements IEntityProcessingService {
    private static final double SCALE = 0.8;
    private static final int MIN_DISTANCE_FROM_PLAYER = 500;
    private static final int MAX_ZOMBIES = 8;
    private static final long SPAWN_INTERVAL = 15000; // 15 seconds
    
    private long lastSpawnTime = 0;
    private int totalZombiesSpawned = 0;
    
    @Override
    public void process(GameData gameData, World world) {
        long currentTime = System.currentTimeMillis();
        
        // Initialize spawn timer on first run
        if (lastSpawnTime == 0) {
            lastSpawnTime = currentTime;
            return;
        }
        
        // Count current living zombies
        long livingZombies = world.getEntities().stream()
            .filter(entity -> entity instanceof Zombie)
            .map(entity -> (Zombie) entity)
            .filter(Zombie::isActive)
            .count();
        
        // Spawn new zombie if enough time has passed and we haven't reached the max
        if (currentTime - lastSpawnTime > SPAWN_INTERVAL && livingZombies < MAX_ZOMBIES) {
            Entity newZombie = createZombie(gameData, world);
            if (newZombie != null) {
                world.addEntity(newZombie);
                totalZombiesSpawned++;
                lastSpawnTime = currentTime;
                System.out.println("Spawned zombie #" + totalZombiesSpawned + ". Total living zombies: " + (livingZombies + 1));
            }
        }
    }
    
    private Entity createZombie(GameData gameData, World world) {
        Zombie zombie = new Zombie(10, 1, "normal");

        // Find player position
        double playerX = gameData.getDisplayWidth() / 2.0;
        double playerY = gameData.getDisplayHeight() / 2.0;

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player) {
                playerX = entity.getX();
                playerY = entity.getY();
                break;
            }
        }

        // Generate random position far from player
        Random random = new Random();
        double spawnX, spawnY;
        double distance;
        int attempts = 0;
        
        // Safeguard against non-positive display dimensions
        int displayWidth = Math.max(1, gameData.getDisplayWidth());
        int displayHeight = Math.max(1, gameData.getDisplayHeight());
        do {
            // Generate point on edge of screen
            int side = random.nextInt(4); // 0: top, 1: right, 2: bottom, 3: left
            switch (side) {
                case 0: // top
                    spawnX = random.nextInt(displayWidth);
                    spawnY = 0;
                    break;
                case 1: // right
                    spawnX = displayWidth;
                    spawnY = random.nextInt(displayHeight);
                    break;
                case 2: // bottom
                    spawnX = random.nextInt(displayWidth);
                    spawnY = displayHeight;
                    break;
                default: // left
                    spawnX = 0;
                    spawnY = random.nextInt(displayHeight);
                    break;
            }

            // Calculate distance from player
            double dx = spawnX - playerX;
            double dy = spawnY - playerY;
            distance = Math.sqrt(dx * dx + dy * dy);
            attempts++;
        } while (distance < MIN_DISTANCE_FROM_PLAYER && attempts < 10);
        
        // If we couldn't find a good spawn position, don't spawn
        if (attempts >= 10) {
            return null;
        }

        // Setup visuals
        try {
            Image zombieSprite = new Image(getClass().getResource("/Zombie.png").toExternalForm());
            ImageView zombieImageView = new ImageView(zombieSprite);
            zombieImageView.setViewport(new Rectangle2D(0, 0, 32, 32));
            zombieImageView.setFitWidth(48 * SCALE);
            zombieImageView.setFitHeight(64 * SCALE);

            // Set position
            zombie.setX(spawnX);
            zombie.setY(spawnY);
            zombie.setRadius(10);
            zombie.setView(zombieImageView);

            // Add pathfinding component
            zombie.addComponent(new ZombiePathfindingComponent(world));
            
            // Add health bar component
            zombie.addComponent(new HealthBarComponent(gameData.getGameWindow()));

            return zombie;
        } catch (Exception e) {
            System.err.println("Failed to create zombie: " + e.getMessage());
            return null;
        }
    }
}

