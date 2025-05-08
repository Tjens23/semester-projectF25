package dk.sdu.coreengine;

import dk.sdu.common.data.GameData;
import dk.sdu.common.data.GameKeys;
import dk.sdu.common.services.IGamePlugin;

import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.toList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements IGamePlugin {

    public static void main(String[] args) {
        System.out.println("CoreEngine is running!");
        launch(Main.class);
    }

        // Lookup all Game Plugins using ServiceLoader
        //for (IGamePluginService iGamePlugin : getPluginServices()) {
           // iGamePlugin.start(gameData/*, world*/);
        //}
      
        render();
        window.setScene(scene);
        window.setTitle("ZOMBIE DESTROYER (Unofficial)");
        window.show();
    }

    private void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //update();
                //draw();
                gameData.getKeys().update();
            }

        }.start();
    }
}

