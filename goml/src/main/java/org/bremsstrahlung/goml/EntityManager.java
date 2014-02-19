package org.bremsstrahlung.goml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gupe7789 on 2014-02-19.
 */
public class EntityManager {
    private int entitySequence = 0;
    private Object entitySequenceLock = new Object();
    private List<Entity> entities = new LinkedList<Entity>();

    public Entity createEntity() {
        int seq = -1;

        synchronized(entitySequenceLock) {
            seq = entitySequence++;
        }

        Entity entity = new Entity(seq);
        entities.add(entity);

        return entity;
    }

    public List<Entity> getEntities(Class<?> type) {
        List<Entity> result = new ArrayList<Entity>();

        for(Entity e: entities) {
            if(e.hasComponent(type)) {
                result.add(e);
            }
        }

        return result;
    }
}
