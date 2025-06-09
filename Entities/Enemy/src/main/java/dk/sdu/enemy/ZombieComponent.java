package dk.sdu.enemy;

import dk.sdu.common.components.Component;

// This interface extends the common Component interface
// and specializes it for Zombie entities
public interface ZombieComponent extends Component {
    void update(Zombie zombie);
    
    // Default implementation that delegates to the specialized method
    @Override
    default void update(dk.sdu.common.data.Entity entity) {
        if (entity instanceof Zombie) {
            update((Zombie) entity);
        }
    }
}

