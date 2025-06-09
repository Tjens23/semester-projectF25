package dk.sdu.enemy;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IGamePluginService;
import dk.sdu.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class ZombiePlugin implements IGamePluginService  {
    private Entity zombie;
    private static final double SCALE = 0.8; // Reduced from 2 to 0.8 to make zombie smaller
    private static final int MIN_DISTANCE_FROM_PLAYER = 500;

    @Override
    public void start(GameData gameData, World world) {
        zombie = createzombie(gameData, world);
        world.addEntity(zombie);
    }

    private Entity createzombie(GameData gameData, World world) {
        Zombie zombie1 = new Zombie(10, 1, "normal"); // Reduced speed from 2 to 1

        // Find player position
        double playerX = gameData.getDisplayWidth() / 2;
        double playerY = gameData.getDisplayHeight() / 2;

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

        do {
            // Generate point on edge of screen
            int side = random.nextInt(4); // 0: top, 1: right, 2: bottom, 3: left
            switch (side) {
                case 0: // top
                    spawnX = random.nextInt(gameData.getDisplayWidth());
                    spawnY = 0;
                    break;
                case 1: // right
                    spawnX = gameData.getDisplayWidth();
                    spawnY = random.nextInt(gameData.getDisplayHeight());
                    break;
                case 2: // bottom
                    spawnX = random.nextInt(gameData.getDisplayWidth());
                    spawnY = gameData.getDisplayHeight();
                    break;
                default: // left
                    spawnX = 0;
                    spawnY = random.nextInt(gameData.getDisplayHeight());
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
        zombie1.setX(spawnX);
        zombie1.setY(spawnY);
        zombie1.setRadius(10); // Reduced from 15 to 10 to match smaller size
        zombie1.setView(zombieImageView);

        // Add pathfinding component
        zombie1.addComponent(new ZombiePathfindingComponent(world));

        return zombie1;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(zombie);
    }
}