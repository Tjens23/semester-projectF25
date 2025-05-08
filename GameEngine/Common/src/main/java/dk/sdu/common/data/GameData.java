package dk.sdu.common.data;

import dk.sdu.common.data.GameKeys;

public class GameData {

    private int displayWidth  = 1280 ;
    private int displayHeight = 720;
    private final GameKeys keys = new GameKeys();

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

