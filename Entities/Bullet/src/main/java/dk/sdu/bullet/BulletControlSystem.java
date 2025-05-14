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

    private static final double MOVEMENT_SPEED = 3.0;
    private static final double BULLET_SPAWN_OFFSET = 10.0;

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
        double changeX = Math.cos(Math.toRadians(shooter.getRotation()));
        double changeY = Math.sin(Math.toRadians(shooter.getRotation()));
        bullet.setX(shooter.getX() + changeX * 10);
        bullet.setY(shooter.getY() + changeY * 10);
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(1);

        return bullet;
    }
}
