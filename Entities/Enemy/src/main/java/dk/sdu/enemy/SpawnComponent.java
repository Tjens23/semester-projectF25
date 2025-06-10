package dk.sdu.enemy;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class SpawnComponent implements ZombieComponent {
    private final String spawnType;
    private final Random random = new Random();
    
    // Map layout for spawn validation - same as in PathfindingComponent
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
    
    private static final int TILE_SIZE = 48; // Match PathfindingComponent grid size

    // Different spawn types (e.g., "random", "cluster", "wave")
    public static final String RANDOM_SPAWN = "random";
    public static final String CLUSTER_SPAWN = "cluster";
    public static final String WAVE_SPAWN = "wave";

    public SpawnComponent(String spawnType) {
        this.spawnType = spawnType;
    }

    @Override
    public void update(Zombie zombie) {
        // Check if the zombie needs to be spawned
        if (!zombie.isActive()) {
            spawnZombie(zombie);
        }
    }

    private void spawnZombie(Zombie zombie) {
        switch (spawnType) {
            case CLUSTER_SPAWN:
                spawnInCluster(zombie);
                break;
            case WAVE_SPAWN:
                spawnInWave(zombie);
                break;
            case RANDOM_SPAWN:
            default:
                spawnRandomly(zombie);
                break;
        }

        // After spawning, mark the zombie as active
        zombie.setActive(true);
    }

    private void spawnRandomly(Zombie zombie) {
        // Get all walkable positions and spawn at a random one
        List<Position> walkablePositions = getWalkablePositions();
        
        if (!walkablePositions.isEmpty()) {
            Position spawnPos = walkablePositions.get(random.nextInt(walkablePositions.size()));
            zombie.setX(spawnPos.x);
            zombie.setY(spawnPos.y);
            System.out.println("[SPAWN] Spawned zombie at walkable position (" + spawnPos.x + ", " + spawnPos.y + ")");
        } else {
            // Fallback to a known safe position
            zombie.setX(16 * TILE_SIZE + TILE_SIZE / 2); // Grid position (16, 1)
            zombie.setY(1 * TILE_SIZE + TILE_SIZE / 2);
            System.out.println("[SPAWN] No walkable positions found, using fallback");
        }
    }

    private void spawnInCluster(Zombie zombie) {
        // Find a cluster of walkable positions in the center-right area of the map
        List<Position> clusterPositions = new ArrayList<>();
        
        // Look for walkable positions in the main open area (around grid 15-20, 2-7)
        for (int x = 15; x <= 20 && x < mapLayout[0].length; x++) {
            for (int y = 2; y <= 7 && y < mapLayout.length; y++) {
                if (mapLayout[y][x] == 0) { // Walkable
                    double worldX = x * TILE_SIZE + TILE_SIZE / 2;
                    double worldY = y * TILE_SIZE + TILE_SIZE / 2;
                    clusterPositions.add(new Position(worldX, worldY));
                }
            }
        }
        
        if (!clusterPositions.isEmpty()) {
            Position spawnPos = clusterPositions.get(random.nextInt(clusterPositions.size()));
            zombie.setX(spawnPos.x);
            zombie.setY(spawnPos.y);
            System.out.println("[SPAWN] Spawned zombie in cluster at (" + spawnPos.x + ", " + spawnPos.y + ")");
        } else {
            spawnRandomly(zombie); // Fallback
        }
    }

    private void spawnInWave(Zombie zombie) {
        // Spawn along the edges of walkable areas
        List<Position> edgePositions = new ArrayList<>();
        
        // Find walkable positions along the edges of the map
        for (int y = 0; y < mapLayout.length; y++) {
            for (int x = 0; x < mapLayout[y].length; x++) {
                if (mapLayout[y][x] == 0) { // Walkable
                    // Check if this position is on the edge of walkable area
                    boolean isEdge = isEdgePosition(x, y);
                    if (isEdge) {
                        double worldX = x * TILE_SIZE + TILE_SIZE / 2;
                        double worldY = y * TILE_SIZE + TILE_SIZE / 2;
                        edgePositions.add(new Position(worldX, worldY));
                    }
                }
            }
        }
        
        if (!edgePositions.isEmpty()) {
            Position spawnPos = edgePositions.get(random.nextInt(edgePositions.size()));
            zombie.setX(spawnPos.x);
            zombie.setY(spawnPos.y);
            System.out.println("[SPAWN] Spawned zombie in wave at edge (" + spawnPos.x + ", " + spawnPos.y + ")");
        } else {
            spawnRandomly(zombie); // Fallback
        }
    }
    
    private boolean isEdgePosition(int x, int y) {
        // Check if at least one adjacent position is an obstacle or out of bounds
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                
                int nx = x + dx;
                int ny = y + dy;
                
                // Out of bounds or obstacle
                if (ny < 0 || ny >= mapLayout.length || 
                    nx < 0 || nx >= mapLayout[ny].length || 
                    mapLayout[ny][nx] != 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private List<Position> getWalkablePositions() {
        List<Position> positions = new ArrayList<>();
        
        for (int y = 0; y < mapLayout.length; y++) {
            for (int x = 0; x < mapLayout[y].length; x++) {
                if (mapLayout[y][x] == 0) { // Walkable
                    double worldX = x * TILE_SIZE + TILE_SIZE / 2;
                    double worldY = y * TILE_SIZE + TILE_SIZE / 2;
                    positions.add(new Position(worldX, worldY));
                }
            }
        }
        
        return positions;
    }
    
    private static class Position {
        final double x, y;
        
        Position(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}