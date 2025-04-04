package dk.sdu.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Map implements IMapService {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    @Override
    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    @Override
    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    @Override
    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    @Override
    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    @Override
    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> result = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    result.add(e);
                }
            }
        }
        return result;
    }

    @Override
    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }
}
