package dk.sdu.player;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IGamePluginService;
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

        Image image = new Image("Some-image.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageView.setX(gameData.getDisplayWidth() / 2);
        imageView.setY(gameData.getDisplayHeight() / 2);

        player1.setX(gameData.getDisplayWidth() / 2);
        player1.setY(gameData.getDisplayHeight() / 2);
        player1.setRadius(15);

        player1.setView(imageView);

        return player1;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }

}
