package dk.sdu.common.data;

import dk.sdu.common.data.GameKeys;
import javafx.scene.layout.Pane;

public class GameData {

    private int displayWidth  = 1280 ;
    private int displayHeight = 720;
    private final GameKeys keys = new GameKeys();

    private double mouseX;
    private double mouseY;

    private Pane gameWindow;

    public Pane getGameWindow() {
        return gameWindow;
    }

    public void setGameWindow(Pane gameWindow) {
        this.gameWindow = gameWindow;
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }


}

