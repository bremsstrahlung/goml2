package org.bremsstrahlung.goml;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import org.apache.http.impl.conn.IdleConnectionHandler;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends Activity implements Engine, Renderer {
    private static final String TAG = MainActivity.class.getSimpleName();

    public enum GameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }

    private GLSurfaceView view;
    private GameState state;
    private Object stateLock;
    private long startTime;

    private int width;
    private int height;

    private AssetManager assetManager;
    private Screen screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        state = GameState.Initialized;
        stateLock = new Object();
        startTime = System.nanoTime();

        assetManager = getAssets();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(this);
        setContentView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        view.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public Renderer getRenderer() {
        return null;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Screen getStartScreen() {
        Log.d(TAG, "getStartScreen");

        return new LoadingScreen(this);
    }

    @Override
    public Screen getCurrentScreen() {
        Log.d(TAG, "getCurrentScreen");

        return null;
    }

    @Override
    public void setScreen(Screen screen) {
        Log.d(TAG, "setScreen");

        this.screen.pause();
        this.screen.dispose();

        screen.resume();
        screen.update(0);

        this.screen = screen;
    }

    @Override
    public void onSurfaceCreated(GL10 notUsed, EGLConfig eglConfig) {
        Log.d(TAG, "onSurfaceCreated");

        synchronized(stateLock) {
            if(state == GameState.Initialized) {
                screen = getStartScreen();
                state = GameState.Running;
            }
        }
    }

    @Override
    public void onSurfaceChanged(GL10 notUsed, int width, int height) {
        Log.d(TAG, "onSurfaceChanged");

        this.width = width;
        this.height = height;

        screen.resume();
        startTime = System.nanoTime();
    }

    @Override
    public void onDrawFrame(GL10 notUsed) {
        GameState state = null;

        synchronized(stateLock) {
            state = this.state;
        }

        if(state == GameState.Running) {
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            screen.update(deltaTime);
            screen.display(deltaTime);
        }
    }
}
