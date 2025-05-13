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
            System.out.println("x: " + player.getX() + " y: " + player.getY());
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 5);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 5);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            if(gameData.getKeys().isDown(GameKeys.SPACE)) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {world.addEntity(spi.createBullet(player, gameData));}
                );
            }

            if (player.getX() < 0) {
                player.setX(1);
            }
            
            //TODO: Should center view to player center instead, so can never be out of bounds of display
            /*if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(gameData.getDisplayWidth()-1);
            }*/

            if (player.getY() < 0) {
                player.setY(1);
            }

            //TODO: Should center view to player center instead, so can never be out of bounds of display
            /*if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(gameData.getDisplayHeight()-1);
            }*/


        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

