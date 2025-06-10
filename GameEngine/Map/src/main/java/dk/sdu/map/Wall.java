package dk.sdu.map;

import dk.sdu.common.data.Entity;

/**
 * Wall entity representing solid obstacles in the game world.
 * These are used for collision detection and pathfinding.
 */
public class Wall extends Entity {
    private final int tileX;
    private final int tileY;
    private final int tileSize;
    
    public Wall(int tileX, int tileY, int tileSize) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.tileSize = tileSize;
        
        // Set entity properties
        this.setTag("WALL");
        this.setCollidable(true);
        
        // Set position in world coordinates
        this.setX(tileX * tileSize + tileSize / 2.0); // Center of tile
        this.setY(tileY * tileSize + tileSize / 2.0); // Center of tile
        
        // Set radius for circular collision detection
        this.setRadius(tileSize / 2.0f);
    }
    
    public int getTileX() {
        return tileX;
    }
    
    public int getTileY() {
        return tileY;
    }
    
    public int getTileSize() {
        return tileSize;
    }
    
    /**
     * Check if a world coordinate is inside this wall tile
     */
    public boolean containsPoint(double worldX, double worldY) {
        double left = tileX * tileSize;
        double right = (tileX + 1) * tileSize;
        double top = tileY * tileSize;
        double bottom = (tileY + 1) * tileSize;
        
        return worldX >= left && worldX < right && worldY >= top && worldY < bottom;
    }
}

