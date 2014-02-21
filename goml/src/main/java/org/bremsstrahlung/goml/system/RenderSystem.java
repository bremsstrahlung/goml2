package org.bremsstrahlung.goml.system;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import org.bremsstrahlung.goml.Entity;
import org.bremsstrahlung.goml.Scene;
import org.bremsstrahlung.goml.World;
import org.bremsstrahlung.goml.component.Mesh;
import org.bremsstrahlung.goml.component.Position;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wilco on 2/20/14.
 */
public class RenderSystem extends System {
    private static final String TAG = RenderSystem.class.getSimpleName();

    private float[] modelView = new float[16];

    public RenderSystem(World world) {
        super(world);
    }

    public void update(float deltaTime) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Scene scene = world.getCurrentScene();

        List<Entity> drawables = world.getEntityManager().getEntities(Mesh.class);

        for(Entity e: drawables) {
            Position p = e.getComponent(Position.class);
            Mesh m = e.getComponent(Mesh.class);

            Matrix.setIdentityM(modelView, 0);
            Matrix.translateM(modelView, 0, p.getX(), p.getY(), 0);

            scene.getShader().setModelView(modelView);
            scene.getShader().setTexture(m.getTexture());
            scene.getShader().setPositionData(m.getVertices());
            scene.getShader().setTexCoordData(m.getTexCoords());

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        }
    }
}
