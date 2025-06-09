package dk.sdu.map;

import dk.sdu.common.data.Entity;

public class Tile extends Entity {
    private final int width;
    private final int height;
    private final int tileId;

    public Tile(int tileId, int width, int height) {
        this.width = width;
        this.height = height;
        this.tileId = tileId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileId() {
        return tileId;
    }
}
