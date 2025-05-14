package dk.sdu.player;

import dk.sdu.bullet.Bullet;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IGamePluginService;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class PlayerPlugin implements IGamePluginService {
    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Player player1 = new Player();

        Image playerSprite = new Image(getClass().getResource("/idle.png").toExternalForm());
        ImageView playerImageView = new ImageView(playerSprite);
        playerImageView.setViewport(new Rectangle2D(0, 0, 48, 64));
        
        playerImageView.setFitWidth(48);
        playerImageView.setFitHeight(64);
        playerImageView.setX(gameData.getDisplayWidth() / 2);
        playerImageView.setY(gameData.getDisplayHeight() / 2);

        player1.setX(gameData.getDisplayWidth() / 2);
        player1.setY(gameData.getDisplayHeight() / 2);
        player1.setRadius(15);

        player1.setView(playerImageView);

        System.out.println("Player sprite loaded: " + (playerSprite.getWidth() > 0));
        return player1;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }

}
