package dk.sdu.pathfinding;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.World;
import dk.sdu.common.components.Component;
import dk.sdu.player.Player;

import java.util.*;

public class PathfindingComponent implements Component {

    // Grid representation of the game world for pathfinding
    private static final int GRID_SIZE = 48; // Match map tile size (16 * 3)
    private static final int MAX_SEARCH_NODES = 1000; // Limit search to prevent performance issues
    private final World world;
    
    // Map layout for obstacle detection - same as in MapRenderer
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


    public PathfindingComponent() {
        this.world = new World();
    }

    public PathfindingComponent(World world) {
        this.world = world;
    }

    @Override
    public void update(Entity entity) {
        Entity player = findPlayer();
        if (player == null) {
            return;
        }

        List<Node> path = findPathToPlayer(entity, player);
        
        if (path != null && !path.isEmpty()) {
            // Get the next node in the path
            Node nextNode = path.get(0);

            // Move entity towards the next node
            moveTowardsTarget(entity, nextNode.x, nextNode.y);
        }
    }

    public Entity findPlayer() {
        for (Entity entity : world.getEntities()) {
            /*if (entity.getTag() != null){
                if (entity.getTag().equalsIgnoreCase("PLAYER")) {
                    return entity;
                }
            }*/
            if (entity instanceof Player) {
                return entity;
            }
        }
        return null;
    }

    private List<Node> findPathToPlayer(Entity entity, Entity player) {
        // Starting and target positions
        int startX = (int) (entity.getX() / GRID_SIZE);
        int startY = (int) (entity.getY() / GRID_SIZE);
        int targetX = (int) (player.getX() / GRID_SIZE);
        int targetY = (int) (player.getY() / GRID_SIZE);

        // Quick validation check
        boolean startIsObstacle = isObstacle(new Node(startX, startY));
        boolean targetIsObstacle = isObstacle(new Node(targetX, targetY));
        
        // Log only when there are actual issues
        if (startIsObstacle || targetIsObstacle) {
            System.out.println("[PATHFINDING] Position issue - Start obstacle: " + startIsObstacle + ", Target obstacle: " + targetIsObstacle);
        }

        // Create start and target nodes
        Node startNode = new Node(startX, startY);
        Node targetNode = new Node(targetX, targetY);
        
        // If start or target is an obstacle, try to find nearby walkable positions
        if (startIsObstacle) {
            startNode = findNearestWalkablePosition(startX, startY);
            if (startNode == null) {
                System.out.println("[PATHFINDING] No walkable position found near start");
                return null;
            }
        }
        
        if (targetIsObstacle) {
            targetNode = findNearestWalkablePosition(targetX, targetY);
            if (targetNode == null) {
                System.out.println("[PATHFINDING] No walkable position found near target");
                return null;
            }
        }

        // Setup for A*
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();

        // Initialize start node
        gScore.put(startNode, 0.0);
        startNode.fScore = heuristic(startNode, targetNode);
        openSet.add(startNode);

        int nodesExplored = 0;

        while (!openSet.isEmpty() && nodesExplored < MAX_SEARCH_NODES) {
            Node current = openSet.poll();
            nodesExplored++;

            // Check if we've reached the target
            if (current.equals(targetNode)) {
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            // Check all neighbors
            for (Node neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor) || isObstacle(neighbor)) {
                    continue;
                }

                double tentativeGScore = gScore.getOrDefault(current, Double.MAX_VALUE) + 1;

                if (!openSet.contains(neighbor) || tentativeGScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    // This path is better than any previous one
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    neighbor.fScore = tentativeGScore + heuristic(neighbor, targetNode);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        // If we get here, no path was found - this is normal if entities are far apart
        return null;
    }

    private List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }

        // Remove the start position (zombie's current position)
        if (path.size() > 1) {
            path.remove(0);
        }

        return path;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();

        // 8-directional movement (including diagonals)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                neighbors.add(new Node(node.x + dx, node.y + dy));
            }
        }

        return neighbors;
    }

    private double heuristic(Node a, Node b) {
        // Euclidean distance
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    private boolean isObstacle(Node node) {
        // Check if the node position is within map bounds
        if (node.y < 0 || node.y >= mapLayout.length || 
            node.x < 0 || node.x >= mapLayout[node.y].length) {
            return true; // Out of bounds is considered an obstacle
        }
        
        // Tile ID 0 is walkable, everything else is an obstacle
        return mapLayout[node.y][node.x] != 0;
    }
    
    private Node findNearestWalkablePosition(int centerX, int centerY) {
        // Search in expanding radius around the center position
        for (int radius = 1; radius <= 5; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    // Only check positions on the border of the current radius
                    if (Math.abs(dx) != radius && Math.abs(dy) != radius) {
                        continue;
                    }
                    
                    Node candidate = new Node(centerX + dx, centerY + dy);
                    if (!isObstacle(candidate)) {
                        return candidate;
                    }
                }
            }
        }
        return null; // No walkable position found within reasonable distance
    }



    private void moveTowardsTarget(Entity entity, int gridX, int gridY) {
        // Convert grid coordinates back to game world coordinates
        double targetX = gridX * GRID_SIZE + GRID_SIZE / 2;
        double targetY = gridY * GRID_SIZE + GRID_SIZE / 2;

        // Calculate direction vector
        double dx = targetX - entity.getX();
        double dy = targetY - entity.getY();

        // Normalize the direction vector
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length > 0) {
            dx /= length;
            dy /= length;
        }

        // Apply movement - use a default speed since Entity doesn't have getSpeed()
        double speed = 0.8; // Reduced speed to make zombies slower

        // Update position using setter methods
        entity.setX(entity.getX() + dx * speed);
        entity.setY(entity.getY() + dy * speed);

        // Update rotation to face the direction of movement
        entity.setRotation(Math.toDegrees(Math.atan2(dy, dx)));
    }

    // Node class for A* algorithm
    static class Node implements Comparable<Node> {
        int x, y;
        double fScore = 0;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.fScore, other.fScore);
        }
    }
}
