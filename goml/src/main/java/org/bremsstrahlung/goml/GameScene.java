package org.bremsstrahlung.goml;

import android.util.Log;

/**
 * Created by wilco on 2/21/14.
 */
public class GameScene extends Scene {
    private static final String TAG = GameScene.class.getSimpleName();

    public GameScene(World world) {
        super(world);
    }

    @Override
    public void prepare() {
        Log.d(TAG, "prepare");
    }
}
