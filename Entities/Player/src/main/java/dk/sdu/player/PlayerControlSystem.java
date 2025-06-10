package dk.sdu.player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;
//import dk.sdu.common.bullet.Bullet;
import dk.sdu.common.SPI.BulletSPI;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.GameKeys;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.player.PlayerPlugin;

public class PlayerControlSystem implements IEntityProcessingService{
    private long lastFiringTime = 0;
    private static final long FIRE_COOLDOWN = 200;
    private static final double SCALE = 2;
    private static final int PLAYER_SPRITE_WIDTH = 48;
    private static final int PLAYER_SPRITE_HEIGHT = 64;
    
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            double speed = 1.2; // Reduced from 2 to 1.2 for better balance with slower zombie
            double dx = 0; //Change in position in x
            double dy = 0; //Change in position in y
            
            // Movement keys
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                dx = -speed;
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                dx = speed;
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                dy = -speed;
            }
            if (gameData.getKeys().isDown(GameKeys.DOWN)) {
                dy = speed;
            }

            // Take into account for diagonal movement
            if (dx != 0 && dy != 0) {
                double diagonalSpeed = speed / Math.sqrt(2);
                dx = dx > 0 ? diagonalSpeed : -diagonalSpeed;
                dy = dy > 0 ? diagonalSpeed : -diagonalSpeed;
            }

            //Update player position
            player.setX(player.getX() + dx);
            player.setY(player.getY() + dy);

            
            // Circumvent edge of screen
            if (player.getX() < 0) {
                player.setX(1);
            }

            if (player.getY() < 0) {
                player.setY(1);
            }
            
            long currentTime = System.currentTimeMillis(); 
            if (gameData.getKeys().isDown(GameKeys.SPACE) && currentTime - lastFiringTime > FIRE_COOLDOWN) {
                lastFiringTime = currentTime;
                
                // Calculate direction of bullet based on mouse position and player position
                double bx = gameData.getMouseX() - (player.getX() + PLAYER_SPRITE_WIDTH * SCALE / 2);
                double by = gameData.getMouseY() - (player.getY() + PLAYER_SPRITE_HEIGHT * SCALE / 2);
                double angle = Math.toDegrees(Math.atan2(by, bx));
                player.setRotation(angle); // Set player rotation to face the mouse position to get ready for firing bullet.
                
                // Create bullet entity to fire.
                getBulletSPIs().stream().findFirst().ifPresent(
                    spi -> world.addEntity(spi.createBullet(player, gameData))
                );
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

