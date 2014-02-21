package org.bremsstrahlung.goml;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;

import org.bremsstrahlung.goml.system.InputSystem;
import org.bremsstrahlung.goml.system.RenderSystem;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wilco on 2/20/14.
 */
public class World implements Runnable, GLSurfaceView.Renderer {
    private AssetManager assetManager;
    private EntityManager entityManager;
    private InputSystem input;
    private RenderSystem render;
    private ShaderFactory shaderFactory;

    private GameState state;
    private long lastUpdate = System.nanoTime();
    private int screenWidth = -1;
    private int screenHeight = -1;

    private MenuScene menuScene;
    private GameScene gameScene;
    private Scene currentScene;

    public enum GameState {
        Initializing,
        Loading,
        Menu,
        Running,
        Paused,
        Idle
    }

    public World(AssetManager assetManager) {
        this.assetManager = assetManager;
        entityManager = new EntityManager();
        input = new InputSystem(this);
        render = new RenderSystem(this);
        shaderFactory = new ShaderFactory(assetManager);

        menuScene = new MenuScene(this);
        gameScene = new GameScene(this);

        state = GameState.Initializing;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        switch(state) {
            case Menu:
                menuScene.prepare();
                currentScene = menuScene;
                break;
            case Running:
                gameScene.prepare();
                currentScene = gameScene;
                break;
        }

        this.state = state;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ShaderFactory getShaderFactory() {
        return shaderFactory;
    }

    public InputSystem getInputSystem() {
        return input;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public InputStream openAsset(String asset) throws IOException {
        return assetManager.open(asset);
    }

    public void run() {
        while(state != GameState.Idle) {
            float deltaTime = (System.nanoTime() - lastUpdate) / 1000000000.0f;
            lastUpdate = System.nanoTime();

            input.update(deltaTime);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 notUsed, EGLConfig eglConfig) {
    }

    @Override
    public void onSurfaceChanged(GL10 notUsed, int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;

        setState(GameState.Menu);
    }

    @Override
    public void onDrawFrame(GL10 notUsed) {
        render.update(0);
    }
}
