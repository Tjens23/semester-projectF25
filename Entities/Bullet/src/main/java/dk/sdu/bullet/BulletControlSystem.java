package dk.sdu.bullet;

import dk.sdu.bullet.Bullet;
import dk.sdu.bullet.BulletSPI;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IEntityProcessingService;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private static final double MOVEMENT_SPEED = 6.0;
    private static final double BULLET_SPAWN_OFFSET = 16.0;
    private static final double PLAYER_SPRITE_X_OFFSET = 48.0;
    private static final double PLAYER_SPRITE_Y_OFFSET = 60.0;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX * MOVEMENT_SPEED);
            bullet.setY(bullet.getY() + changeY * MOVEMENT_SPEED);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        Image bulletSprite = new Image(getClass().getResource("/bullet.png").toExternalForm());
        ImageView bulletImageView = new ImageView(bulletSprite);
        bulletImageView.setViewport(new Rectangle2D(16 * 17, 16 * 9, 16, 16));
        bullet.setView(bulletImageView);
        
        double angle = shooter.getRotation(); // Get rotation from shooter (rotation is set to direction of where mouse is pointing)
        double changeX = Math.cos(Math.toRadians(angle));
        double changeY = Math.sin(Math.toRadians(angle));
        bullet.setX(shooter.getX() + PLAYER_SPRITE_X_OFFSET + changeX * BULLET_SPAWN_OFFSET); // Offset to spawn bullet in front of player
        bullet.setY(shooter.getY() + PLAYER_SPRITE_Y_OFFSET + changeY * BULLET_SPAWN_OFFSET); // Offset to spawn bullet in front of player
        bullet.setRotation(angle);
        bullet.setRadius(1); // Set radius for collision detection

        return bullet;
    }
}
