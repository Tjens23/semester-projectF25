package dk.sdu.coreengine;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.toList;

import dk.sdu.common.SPI.PlayerSPI;
import dk.sdu.common.data.*;
import dk.sdu.common.services.*;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game {
    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Pane gameWindow = new Pane();
    private final Map<Entity, ImageView> entities = new ConcurrentHashMap<>();

    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServices;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    private final Text fpsCounter = new Text(1200, 20, "FPS: 0");
    private final Text zombiesKilledCounter = new Text(10, 20, "Zombies killed: 0");
    // For FPS Counter
    private int frames = 0;
    private long lastFpsTime = 0;


    Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServices, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServices = entityProcessingServices;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }
    
    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(zombiesKilledCounter);
        gameWindow.getChildren().add(fpsCounter);

        fpsCounter.setStyle("-fx-font-size: 20px; -fx-fill: green;");
        zombiesKilledCounter.setStyle("-fx-font-size: 20px; -fx-fill: red;");

        gameData.setGameWindow(gameWindow);
        Scene scene = new Scene(gameWindow);
        //Track mouse position for firing bullets
        scene.setOnMouseMoved(event -> {
            gameData.setMouseX(event.getX());
            gameData.setMouseY(event.getY());
        });

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
            if (event.getCode().equals(KeyCode.P)) {
                gameData.getKeys().setKey(GameKeys.SHOP, true);
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
            if (event.getCode().equals(KeyCode.P)) {
                gameData.getKeys().setKey(GameKeys.SHOP, false);
            }
        });

        // Lookup all Game Plugins using service loader
        System.out.println("GamePluginServices: " + gamePluginServices);
        for (IGamePluginService plugin : gamePluginServices) {
            plugin.start(gameData, world);
        }
        // Lookup all Entities in world object
        for (Entity entity : world.getEntities()) {
            gameWindow.getChildren().add(entity.getView());
        }
        fpsCounter.toFront();
        zombiesKilledCounter.toFront();

        // Set up the stage
        window.setTitle("Zombie Destroyer Demo");
        window.setScene(scene);
        window.setResizable(false);
        gameData.setDisplayWidth(/*(int) window.getWidth()^*/ gameData.getDisplayWidth());
        gameData.setDisplayHeight(/*(int) window.getHeight()*/ gameData.getDisplayHeight());
        
        window.show();
    }


    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();

                // FPS Counter
                frames++;
                if (lastFpsTime == 0) {
                    lastFpsTime = now;
                }
                if (now - lastFpsTime >= 1_000_000_000) { // 1 second}
                    fpsCounter.setText("FPS: " + frames);
                    frames = 0;
                    lastFpsTime = now;
                }
            }
        }.start();
    }


    private void update() {
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        
        for (Entity entity : entities.keySet()) {
            if (!world.getEntities().contains(entity)) {
                ImageView removedEntity = entities.get(entity);
                entities.remove(entity);
                gameWindow.getChildren().remove(removedEntity);
            }
        }

        for (Entity entity : world.getEntities()) {
            ImageView view = entities.get(entity);

            if (view == null) {
                view = entity.getView();
                if (view != null) {
                    entities.put(entity, view);
                    if (!gameWindow.getChildren().contains(view)) { // Prevent duplicate addition
                        gameWindow.getChildren().add(view);
                    }
                }
            }

            if (view != null /*&& !(entity instanceof Player)*/) {
                view.setX(entity.getX());
                view.setY(entity.getY());
                view.setRotate(entity.getRotation());
            }
        }

        // Bring player to front
        Entity player = null;
        for (PlayerSPI spi : getPlayerSPI()) {
            System.out.println("Checking PlayerSPI: " + spi.getClass().getName());
            player = spi.getPlayer(gameData, world);
            if (player != null) break;
        }
        if (player != null) {
            System.out.println("Player found: " + player);
            ImageView playerView = entities.get(player);
            if (playerView != null) {
                playerView.toFront();
            }
        }
    }

    public List<IGamePluginService> getGamePluginServices() {
        System.out.println("GamePluginServices: " + gamePluginServices);
        return gamePluginServices;
    }

    public List<IEntityProcessingService> getEntityProcessingServices() {
        return entityProcessingServices;
    }

    public List<IPostEntityProcessingService> getPostEntityProcessingServices() {
        return postEntityProcessingServices;
    }

    private Collection<? extends PlayerSPI> getPlayerSPI() {
        return ServiceLoader.load(PlayerSPI.class).stream()
            .map(ServiceLoader.Provider::get)
            .collect(toList());
    }

}
