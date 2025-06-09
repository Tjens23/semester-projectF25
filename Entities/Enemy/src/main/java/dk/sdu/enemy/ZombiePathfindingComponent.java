package dk.sdu.enemy;

import dk.sdu.pathfinding.PathfindingComponent;
import dk.sdu.common.data.World;

/**
 * A wrapper component that adapts PathfindingComponent to work with ZombieComponent interface
 */
public class ZombiePathfindingComponent implements ZombieComponent {
    private final PathfindingComponent pathfinding;
    
    public ZombiePathfindingComponent() {
        this.pathfinding = new PathfindingComponent();
    }
    
    public ZombiePathfindingComponent(World world) {
        this.pathfinding = new PathfindingComponent(world);
    }
    
    @Override
    public void update(Zombie zombie) {
        // Delegate to the pathfinding component
        pathfinding.update(zombie);
    }
}

