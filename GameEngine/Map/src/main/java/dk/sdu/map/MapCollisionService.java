package dk.sdu.map;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling collision detection between entities and map tiles.
 * Manages wall entities and provides pathfinding support.
 */
public class MapCollisionService {
    private static final int TILE_SIZE = 16 * 3; // From MapRenderer: TILE_SIZE * SCALE
    
    // Map layout from MapRenderer - tiles with ID 1 and above are solid
    private final int[][] mapLayout = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 3, 3, 3, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 3, 4, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 3, 3, 3, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 3, 3, 3, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 3, 4, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 3, 3, 3, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    
    private List<Wall> walls;
    
    public MapCollisionService() {
        generateWalls();
    }
    
    /**
     * Generate wall entities from the map layout
     */
    private void generateWalls() {
        walls = new ArrayList<>();
        
        for (int y = 0; y < mapLayout.length; y++) {
            for (int x = 0; x < mapLayout[y].length; x++) {
                int tileId = mapLayout[y][x];
                
                // All tile IDs except 0 are solid walls
                if (tileId != 0) {
                    Wall wall = new Wall(x, y, TILE_SIZE);
                    walls.add(wall);
                }
            }
        }
        
        System.out.println("[MAP] Generated " + walls.size() + " wall entities");
    }
    
    /**
     * Add all wall entities to the world
     */
    public void addWallsToWorld(World world) {
        for (Wall wall : walls) {
            world.addEntity(wall);
        }
        System.out.println("[MAP] Added " + walls.size() + " walls to world");
    }
    
    /**
     * Check if a world position is blocked by a wall
     */
    public boolean isPositionBlocked(double worldX, double worldY) {
        int tileX = (int) (worldX / TILE_SIZE);
        int tileY = (int) (worldY / TILE_SIZE);
        
        return isTileBlocked(tileX, tileY);
    }
    
    /**
     * Check if a tile coordinate is blocked
     */
    public boolean isTileBlocked(int tileX, int tileY) {
        // Check bounds
        if (tileY < 0 || tileY >= mapLayout.length || 
            tileX < 0 || tileX >= mapLayout[tileY].length) {
            return true; // Out of bounds is considered blocked
        }
        
        // Tile ID 0 is walkable, everything else is blocked
        return mapLayout[tileY][tileX] != 0;
    }
    
    /**
     * Check if an entity would collide with a wall at the given position
     */
    public boolean wouldCollideWithWall(Entity entity, double newX, double newY) {
        // Check the four corners of the entity's bounding box
        float radius = entity.getRadius();
        
        // Check multiple points around the entity's perimeter
        double[] checkX = {newX - radius, newX + radius, newX - radius, newX + radius};
        double[] checkY = {newY - radius, newY - radius, newY + radius, newY + radius};
        
        for (int i = 0; i < checkX.length; i++) {
            if (isPositionBlocked(checkX[i], checkY[i])) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Get safe position for entity (push it out of walls if necessary)
     */
    public void adjustPositionToAvoidWalls(Entity entity) {
        double currentX = entity.getX();
        double currentY = entity.getY();
        
        if (!wouldCollideWithWall(entity, currentX, currentY)) {
            return; // Already in safe position
        }
        
        // Try to push entity to nearest safe position
        float radius = entity.getRadius();
        
        // Try moving in different directions to find safe spot
        double[] offsets = {-radius * 2, 0, radius * 2};
        
        for (double xOffset : offsets) {
            for (double yOffset : offsets) {
                double testX = currentX + xOffset;
                double testY = currentY + yOffset;
                
                if (!wouldCollideWithWall(entity, testX, testY)) {
                    entity.setX(testX);
                    entity.setY(testY);
                    System.out.println("[MAP] Adjusted entity position to avoid wall collision");
                    return;
                }
            }
        }
    }
    
    public List<Wall> getWalls() {
        return walls;
    }
    
    public int getTileSize() {
        return TILE_SIZE;
    }
    
    public int getMapWidth() {
        return mapLayout[0].length;
    }
    
    public int getMapHeight() {
        return mapLayout.length;
    }
}

