package org.bremsstrahlung.goml;

import org.bremsstrahlung.goml.component.Mesh;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
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

    public Mesh createMesh(int width, int height, int texture) {
        Mesh mesh = new Mesh();
        float halfWidth = width / 2.0f;
        float halfHeight = height / 2.0f;

        FloatBuffer vertices = ByteBuffer.allocateDirect(12 * (Float.SIZE / 8)).order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertices.put(new float[] {
                halfWidth,  halfHeight,
                -halfWidth,  halfHeight,
                -halfWidth, -halfHeight,
                halfWidth,  halfHeight,
                -halfWidth, -halfHeight,
                halfWidth, -halfHeight
        });

        vertices.flip();

        FloatBuffer texCoords = ByteBuffer.allocateDirect(12 * (Float.SIZE / 8)).order(ByteOrder.nativeOrder()).asFloatBuffer();
        texCoords.put(new float[] {
                1, 0,
                0, 0,
                0, 1,
                1, 0,
                0, 1,
                1, 1
        });
        texCoords.flip();

        mesh.setVertices(vertices);
        mesh.setTexCoords(texCoords);
        mesh.setTexture(texture);

        return mesh;
    }
}
