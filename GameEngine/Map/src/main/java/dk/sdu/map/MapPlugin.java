package dk.sdu.map;

import dk.sdu.common.data.World;
import dk.sdu.common.data.GameData;
import dk.sdu.common.services.IGamePluginService;
import javafx.scene.image.Image;

public class MapPlugin implements IGamePluginService {
    private MapRenderer mapRenderer;

    @Override
    public void start(GameData gameData, World world) {
        Image tileset = new Image(getClass().getResource("/tileset.png").toExternalForm());
        mapRenderer = new MapRenderer(tileset);

        mapRenderer.render(world);
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
}
