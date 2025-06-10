package dk.sdu.player;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IGamePluginService;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class PlayerPlugin implements IGamePluginService {
    private Entity player;
    private static final double SCALE = 2;
    private static final int PLAYER_SPRITE_WIDTH = 48;
    private static final int PLAYER_SPRITE_HEIGHT = 64;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Player player1 = new Player();

        Image playerSprite = new Image(getClass().getResource("/idle.png").toExternalForm());
        ImageView playerImageView = new ImageView(playerSprite);
        playerImageView.setViewport(new Rectangle2D(0, 0, PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT));
        
        playerImageView.setFitWidth(PLAYER_SPRITE_WIDTH * SCALE);
        playerImageView.setFitHeight(PLAYER_SPRITE_HEIGHT * SCALE);
        playerImageView.setX(gameData.getDisplayWidth() / 2);
        playerImageView.setY(gameData.getDisplayHeight() / 2);

        player1.setX(gameData.getDisplayWidth() / 2);
        player1.setY(gameData.getDisplayHeight() / 2);
        player1.setRadius(15);
        player1.setCollidable(true); // Make player collidable
        player1.setTag("PLAYER"); // Set player tag for collision detection

        player1.setView(playerImageView);

        System.out.println("Player sprite loaded: " + (playerSprite.getWidth() > 0));
        return player1;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }

}
