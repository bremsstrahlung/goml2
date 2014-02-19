package org.bremsstrahlung.goml;

/**
 * Created by gupe7789 on 2014-02-18.
 */
public abstract class Screen {
    Engine engine;

    Screen(Engine engine) {
        this.engine = engine;
    }

    public abstract void resume();
    public abstract void pause();
    public abstract void dispose();
    public abstract void update(float deltaTime);
    public abstract void display(float deltaTime);
}
