package dk.sdu.pathfinding;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.World;
import dk.sdu.enemy.Component;
import dk.sdu.enemy.Zombie;
import dk.sdu.player.Player;

import java.util.*;

public class PathfindingComponent implements Component {

    // Grid representation of the game world for pathfinding
    private static final int GRID_SIZE = 50; // Grid cell size
    private static final int MAX_SEARCH_NODES = 1000; // Limit search to prevent performance issues
    private final World world;


    public PathfindingComponent() {
        this.world = new World();
    }

    public PathfindingComponent(World world) {
        this.world = world;
    }

    @Override
    public void update(Zombie zombie) {
        Entity player = findPlayer();
        if (player == null) return;

        List<Node> path = findPathToPlayer(zombie, player);

        if (path != null && !path.isEmpty()) {
            // Get the next node in the path
            Node nextNode = path.get(0);

            // Move zombie towards the next node
            moveTowardsTarget(zombie, nextNode.x, nextNode.y);
        }
    }

    public Entity findPlayer() {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player) {
                return entity;
            }
        }
        return null;
    }

    private List<Node> findPathToPlayer(Zombie zombie, Entity player) {
        // Starting and target positions
        int startX = (int) (zombie.getX() / GRID_SIZE);
        int startY = (int) (zombie.getY() / GRID_SIZE);
        int targetX = (int) (player.getX() / GRID_SIZE);
        int targetY = (int) (player.getY() / GRID_SIZE);

        // Create start and target nodes
        Node startNode = new Node(startX, startY);
        Node targetNode = new Node(targetX, targetY);

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

        // If we get here, no path was found
        System.out.println("No path found to player");
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
        // In a real implementation, this would check for walls, obstacles, etc.
        // For now, return false (no obstacles)
        return false;
    }



    private void moveTowardsTarget(Zombie zombie, int gridX, int gridY) {
        // Convert grid coordinates back to game world coordinates
        double targetX = gridX * GRID_SIZE + GRID_SIZE / 2;
        double targetY = gridY * GRID_SIZE + GRID_SIZE / 2;

        // Calculate direction vector
        double dx = targetX - zombie.getX();
        double dy = targetY - zombie.getY();

        // Normalize the direction vector
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length > 0) {
            dx /= length;
            dy /= length;
        }

        // Apply movement
        double speed = zombie.getSpeed(); // Adjust based on zombie speed

        // Update position using setter methods or direct position update
        zombie.setX(zombie.getX() + dx * speed);
        zombie.setY(zombie.getY() + dy * speed);

        // Update rotation to face the direction of movement
        zombie.setRotation(Math.toDegrees(Math.atan2(dy, dx)));

        System.out.println("Zombie at " + zombie.getX() + " "+  zombie.getY() + " moving towards player");
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
