package dk.sdu.coreengine;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dk.sdu.common.data.*;
import dk.sdu.common.services.*;
//import dk.sdu.map.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Game {
    private final GameData gameData = new GameData();
    private final Pane gameWindow = new Pane();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    
    
    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(new Text(10, 20, "Zombies killed: 0"));

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.W)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.A)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.D)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.S)) {
                gameData.getKeys().setKey(GameKeys.DOWN, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
            if (event.getCode().equals(KeyCode.R)) {
                gameData.getKeys().setKey(GameKeys.RELOAD, true);
            }
            if (event.getCode().equals(MouseButton.PRIMARY)) {
                gameData.getKeys().setKey(GameKeys.LEFT_CLICK, true);
            }
            if (event.getCode().equals(MouseButton.SECONDARY)) {
                gameData.getKeys().setKey(GameKeys.RIGHT_CLICK, true);
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                gameData.getKeys().setKey(GameKeys.ESC, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.W)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.A)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.D)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.S)) {
                gameData.getKeys().setKey(GameKeys.DOWN, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
            if (event.getCode().equals(KeyCode.R)) {
                gameData.getKeys().setKey(GameKeys.RELOAD, false);
            }
            if (event.getCode().equals(MouseButton.PRIMARY)) {
                gameData.getKeys().setKey(GameKeys.LEFT_CLICK, false);
            }
            if (event.getCode().equals(MouseButton.SECONDARY)) {
                gameData.getKeys().setKey(GameKeys.RIGHT_CLICK, false);
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                gameData.getKeys().setKey(GameKeys.ESC, false);
            }
        });
    }


    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //update();
                //draw();
                gameData.getKeys().update();
            }
        }.start();
    }


    /*  private void update() {
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }*/

   /* private void draw() {
        for (Entity polygonEntity : polygons.keySet()) {
            if (!world.getEntities().contains(polygonEntity)) {
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }

    }*/
}
