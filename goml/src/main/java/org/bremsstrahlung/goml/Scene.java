package org.bremsstrahlung.goml;

/**
 * Created by wilco on 2/20/14.
 */
public abstract class Scene {
    protected World world;
    protected float[] projection = new float[16];
    protected Shader shader;

    public Scene(World world) {
        this.world = world;
    }

    public float[] getProjection() {
        return projection;
    }

    public Shader getShader() {
        return shader;
    }

    public abstract void prepare();
}
