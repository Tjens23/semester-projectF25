package dk.sdu.pathfinding;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.World;
import dk.sdu.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PathfindingComponentTest {

    private PathfindingComponent pathfinder = new PathfindingComponent();
    private World mockWorld;
    private Player mockPlayer;
    private Entity mockEntity;

    @BeforeEach
    void setUp() throws Exception {
        // Set up mocks
        mockWorld = mock(World.class);
        mockPlayer = mock(Player.class);
        mockEntity = mock(Entity.class);

        // Create component
        pathfinder = new PathfindingComponent();

        // Use reflection to set the world field
        Field worldField = PathfindingComponent.class.getDeclaredField("world");
        worldField.setAccessible(true);
        worldField.set(pathfinder, mockWorld);

        // Setup default mock behavior
        when(mockPlayer.getX()).thenReturn(300.0);
        when(mockPlayer.getY()).thenReturn(400.0);

        when(mockEntity.getX()).thenReturn(100.0);
        when(mockEntity.getY()).thenReturn(200.0);

        List<Entity> entities = new ArrayList<>();
        entities.add(mockPlayer);
        when(mockWorld.getEntities()).thenReturn(entities);
    }

    @Test
    void testFindPlayer() throws Exception {
        Method findPlayerMethod = PathfindingComponent.class.getDeclaredMethod("findPlayer");
        findPlayerMethod.setAccessible(true);

        Entity foundPlayer = (Entity) findPlayerMethod.invoke(pathfinder);
        assertSame(mockPlayer, foundPlayer);
    }

    @Test
    void testFindPlayerWhenNoPlayer() throws Exception {
        // Set empty entity list
        when(mockWorld.getEntities()).thenReturn(new ArrayList<>());

        Method findPlayerMethod = PathfindingComponent.class.getDeclaredMethod("findPlayer");
        findPlayerMethod.setAccessible(true);

        Entity foundPlayer = (Entity) findPlayerMethod.invoke(pathfinder);
        assertNull(foundPlayer);
    }

    @Test
    void testUpdate() {
        // Test the update method
        pathfinder.update(mockEntity);

        // Verify entity position/rotation was updated
        verify(mockEntity).setRotation(anyDouble());
    }

    @Test
    void testUpdateWithNoPlayer() {
        // Setup world with no player
        when(mockWorld.getEntities()).thenReturn(new ArrayList<>());

        pathfinder.update(mockEntity);

        // Entity should not be updated
        verify(mockEntity, never()).setRotation(anyDouble());
    }

    @Test
    void testFindPathToPlayer() throws Exception {
        Method findPathMethod = PathfindingComponent.class.getDeclaredMethod(
            "findPathToPlayer", Entity.class, Entity.class);
        findPathMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<PathfindingComponent.Node> path =
            (List<PathfindingComponent.Node>) findPathMethod.invoke(pathfinder, mockEntity, mockPlayer);

        // Should find a path
        assertNotNull(path);
        assertFalse(path.isEmpty());
    }

    @Test
    void testMoveTowardsTarget() throws Exception {
        Method moveMethod = PathfindingComponent.class.getDeclaredMethod(
            "moveTowardsTarget", Entity.class, int.class, int.class);
        moveMethod.setAccessible(true);

        // Call moveTowardsTarget
        moveMethod.invoke(pathfinder, mockEntity, 10, 10);

        // Verify rotation updated
        verify(mockEntity).setRotation(anyDouble());
    }

    @Test
    void testHeuristic() throws Exception {
        // Need to get the Node class and heuristic method
        Class<?> nodeClass = Class.forName("dk.sdu.pathfinding.PathfindingComponent$Node");
        Constructor<?> nodeCtor = nodeClass.getDeclaredConstructor(int.class, int.class);
        nodeCtor.setAccessible(true);

        Object node1 = nodeCtor.newInstance(0, 0);
        Object node2 = nodeCtor.newInstance(3, 4);

        Method heuristicMethod = PathfindingComponent.class.getDeclaredMethod(
            "heuristic", nodeClass, nodeClass);
        heuristicMethod.setAccessible(true);

        double distance = (Double) heuristicMethod.invoke(pathfinder, node1, node2);

        // Should be 5.0 (Euclidean distance between (0,0) and (3,4))
        assertEquals(5.0, distance, 0.0001);
    }

    @Test
    void testIsObstacle() throws Exception {
        Class<?> nodeClass = Class.forName("dk.sdu.pathfinding.PathfindingComponent$Node");
        Constructor<?> nodeCtor = nodeClass.getDeclaredConstructor(int.class, int.class);
        nodeCtor.setAccessible(true);

        Object node = nodeCtor.newInstance(5, 5);

        Method isObstacleMethod = PathfindingComponent.class.getDeclaredMethod(
            "isObstacle", nodeClass);
        isObstacleMethod.setAccessible(true);

        boolean isObstacle = (Boolean) isObstacleMethod.invoke(pathfinder, node);

        // Current implementation should return false
        assertFalse(isObstacle);
    }

    @Test
    void testGetNeighbors() throws Exception {
        Class<?> nodeClass = Class.forName("dk.sdu.pathfinding.PathfindingComponent$Node");
        Constructor<?> nodeCtor = nodeClass.getDeclaredConstructor(int.class, int.class);
        nodeCtor.setAccessible(true);

        Object node = nodeCtor.newInstance(5, 5);

        Method getNeighborsMethod = PathfindingComponent.class.getDeclaredMethod(
            "getNeighbors", nodeClass);
        getNeighborsMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<Object> neighbors = (List<Object>) getNeighborsMethod.invoke(pathfinder, node);

        // Should return 8 neighbors (8-directional movement)
        assertEquals(8, neighbors.size());
    }

    @Test
    void testNodeComparison() throws Exception {
        Class<?> nodeClass = Class.forName("dk.sdu.pathfinding.PathfindingComponent$Node");
        Constructor<?> nodeCtor = nodeClass.getDeclaredConstructor(int.class, int.class);
        nodeCtor.setAccessible(true);

        Object node1 = nodeCtor.newInstance(5, 5);
        Object node2 = nodeCtor.newInstance(5, 5);
        Object node3 = nodeCtor.newInstance(6, 6);

        // Test equals
        assertEquals(node1, node2);
        assertNotEquals(node1, node3);

        // Test hashCode
        assertEquals(node1.hashCode(), node2.hashCode());
    }

    @Test
    void testNodeComparable() throws Exception {
        Class<?> nodeClass = Class.forName("dk.sdu.pathfinding.PathfindingComponent$Node");
        Constructor<?> nodeCtor = nodeClass.getDeclaredConstructor(int.class, int.class);
        nodeCtor.setAccessible(true);

        Object node1 = nodeCtor.newInstance(1, 1);
        Object node2 = nodeCtor.newInstance(2, 2);

        // Set fScores
        Field fScoreField = nodeClass.getDeclaredField("fScore");
        fScoreField.setAccessible(true);
        fScoreField.set(node1, 10.0);
        fScoreField.set(node2, 5.0);

        // Create a PriorityQueue to test the ordering
        PriorityQueue<Object> queue = new PriorityQueue<>();
        queue.add(node1);
        queue.add(node2);

        // Node with lower fScore should come out first
        assertSame(node2, queue.poll());
        assertSame(node1, queue.poll());
    }
}