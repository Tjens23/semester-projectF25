package dk.sdu.player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;
//import dk.sdu.common.bullet.Bullet;
import dk.sdu.bullet.BulletSPI;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.GameKeys;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService{
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

            // Handle shooting mechanics
            if (gameData.getKeys().isDown(GameKeys.SPACE)) {
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

