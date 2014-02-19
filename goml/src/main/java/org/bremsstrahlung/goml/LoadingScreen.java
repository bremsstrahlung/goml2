package org.bremsstrahlung.goml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import org.bremsstrahlung.goml.org.bremsstrahlung.goml.component.Mesh;
import org.bremsstrahlung.goml.org.bremsstrahlung.goml.component.Position;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Created by gupe7789 on 2014-02-18.
 */
public class LoadingScreen extends Screen {
    private static final String TAG = LoadingScreen.class.getSimpleName();

    private float[] projection = new float[16];
    private float[] modelView = new float[16];
    private Shader shader;
    private int[] texture = new int[2];

    private EntityManager entityManager;

    public LoadingScreen(Engine engine) {
        super(engine);

        Log.d(TAG, "LoadingScreen");
    }

    @Override
    public void resume() {
        Log.d(TAG, "resume");

        entityManager = new EntityManager();

        GLES20.glGenTextures(2, texture, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(engine.getAssetManager().open("chainsaw.png"));

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            bitmap.recycle();
        }
        catch(IOException ex) {
            Log.e(TAG, "load asset: " + ex.getMessage());
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[1]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(engine.getAssetManager().open("getoffmylawn.png"));

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            bitmap.recycle();
        }
        catch(IOException ex) {
            Log.e(TAG, "load asset: " + ex.getMessage());
        }

        ShaderFactory sf = new ShaderFactory(engine.getAssetManager());

        shader = sf.createShader("default");
        shader.bind();

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        /* setup view matrix */
        Log.d(TAG, "viewPort: " + engine.getWidth() + "x" + engine.getHeight());

        GLES20.glViewport(0, 0, engine.getWidth(), engine.getHeight());

        Matrix.setIdentityM(projection, 0);
        Matrix.orthoM(projection, 0, 0, engine.getWidth(), 0, engine.getHeight(), -1, 1);
        shader.setProjection(projection);

        Entity logo = entityManager.createEntity();
        logo.addComponent(new Position(engine.getWidth() / 2, 128));
        logo.addComponent(createMesh(1024, 256, texture[1]));

        Entity pict = entityManager.createEntity();
        pict.addComponent(new Position(engine.getWidth() / 2, 468));
        pict.addComponent(createMesh(1200, 600, texture[0]));
    }

    private Mesh createMesh(int width, int height, int texture) {
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

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void display(float deltaTime) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        List<Entity> drawables = entityManager.getEntities(Mesh.class);

        for(Entity e: drawables) {
            Position p = e.getComponent(Position.class);
            Mesh m = e.getComponent(Mesh.class);

            Matrix.setIdentityM(modelView, 0);
            Matrix.translateM(modelView, 0, p.getX(), p.getY(), 0);

            shader.setModelView(modelView);
            shader.setTexture(m.getTexture());
            shader.setPositionData(m.getVertices());
            shader.setTexCoordData(m.getTexCoords());

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        }
    }
}
