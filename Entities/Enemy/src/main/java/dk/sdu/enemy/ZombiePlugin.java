package dk.sdu.enemy;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IGamePluginService;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class ZombiePlugin implements IGamePluginService  {
    private static final double SCALE = 0.8; // Reduced from 2 to 0.8 to make zombie smaller
    private static final int MIN_DISTANCE_FROM_PLAYER = 500;
    private static final int INITIAL_ZOMBIE_COUNT = 3; // Number of zombies to spawn at start
    private static final int MAX_ZOMBIES = 8; // Maximum number of zombies
    private static final long SPAWN_INTERVAL = 10000; // Spawn new zombie every 15 seconds (in milliseconds)
    
    private long lastSpawnTime = 0;
    private int currentZombieCount = 0;

    @Override
    public void start(GameData gameData, World world) {
        // Spawn initial zombies
        for (int i = 0; i < INITIAL_ZOMBIE_COUNT; i++) {
            Entity zombie = createZombie(gameData, world, i);
            zombie.setRadius(40);
            world.addEntity(zombie);
            currentZombieCount++;
        }
        lastSpawnTime = System.currentTimeMillis();
    }

    private Entity createZombie(GameData gameData, World world, int zombieIndex) {
        Zombie zombie = new Zombie(10, 1, "normal"); // Reduced speed from 2 to 1

        // Find player position
        double playerX = gameData.getDisplayWidth() / 2;
        double playerY = gameData.getDisplayHeight() / 2;

        for (Entity entity : world.getEntities()) {
            if (entity.getTag().equalsIgnoreCase("PLAYER")) {
                playerX = entity.getX();
                playerY = entity.getY();
                break;
            }
        }

        // Generate random position far from player
        Random random = new Random();
        double spawnX, spawnY;
        double distance;

        // Ensure display dimensions are valid to avoid IllegalArgumentException
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
        } while (distance < MIN_DISTANCE_FROM_PLAYER);

        // Setup visuals
        Image zombieSprite = new Image(getClass().getResource("/Zombie.png").toExternalForm());
        ImageView zombieImageView = new ImageView(zombieSprite);
        zombieImageView.setViewport(new Rectangle2D(0, 0, 32, 32));
        zombieImageView.setFitWidth(48 * SCALE);
        zombieImageView.setFitHeight(64 * SCALE);

        // Set position
        zombie.setX(spawnX);
        zombie.setY(spawnY);
        zombie.setRadius(10); // Reduced from 15 to 10 to match smaller size
        zombie.setCollidable(true); // Make zombie collidable
        zombie.setTag("ZOMBIE"); // Set zombie tag for collision detection
        zombie.setView(zombieImageView);

        // Add pathfinding component
        zombie.addComponent(new ZombiePathfindingComponent(world));
        
        // Add health bar component
        zombie.addComponent(new HealthBarComponent(gameData.getGameWindow()));

        return zombie;
    }
    

    @Override
    public void stop(GameData gameData, World world) {
        // Remove all zombies
        world.getEntities().stream()
            .filter(entity -> entity instanceof Zombie)
            .forEach(world::removeEntity);
    }
}