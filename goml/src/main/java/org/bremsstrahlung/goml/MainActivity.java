package org.bremsstrahlung.goml;

import android.app.Activity;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.bremsstrahlung.goml.system.InputSystem;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private World world;
    private GLSurfaceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        world = new World(getAssets());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(world);
        setContentView(view);

        view.setOnTouchListener(world.getInputSystem());

        new Thread(world).start();
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
}
