package dk.sdu.map;


import dk.sdu.common.data.Entity;

import java.util.Collection;
import java.util.List;
public interface IMapService {
    String addEntity(Entity entity);
    void removeEntity(String entityID);
    void removeEntity(Entity entity);
    Collection<Entity> getEntities();
    <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes);
    Entity getEntity(String ID);
}
