package dk.sdu.enemy;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IGamePluginService;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ZombiePlugin implements IGamePluginService  {
    private Entity zombie;
    private static final double SCALE = 2;
    @Override
    public void start(GameData gameData, World world) {
        zombie = createzombie(gameData);
        world.addEntity(zombie);
    }

    private Entity createzombie(GameData gameData) {
        Zombie zombie1 = new Zombie(10,5, "normal");

        Image zombieSprite = new Image(getClass().getResource("/idle.png").toExternalForm());
        ImageView zombieImageView = new ImageView(zombieSprite);
        zombieImageView.setViewport(new Rectangle2D(0, 0, 48, 64));

        zombieImageView.setFitWidth(48 * SCALE);
        zombieImageView.setFitHeight(64 * SCALE);
        zombieImageView.setX((double) gameData.getDisplayWidth() / 2);
        zombieImageView.setY((double) gameData.getDisplayHeight() / 2);

        zombie1.setX((double) gameData.getDisplayWidth() / 2);
        zombie1.setY((double) gameData.getDisplayHeight() / 2);
        zombie1.setRadius(15);

        zombie1.setView(zombieImageView);

        System.out.println("zombie sprite loaded: " + (zombieSprite.getWidth() > 0));
        return zombie;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(zombie);
    }
}
