package org.bremsstrahlung.goml;

import android.content.res.AssetManager;

/**
 * Created by gupe7789 on 2014-02-18.
 */
public interface Engine {
    public int getWidth();
    public int getHeight();

    public AssetManager getAssetManager();

    public Renderer getRenderer();

    public Screen getStartScreen();
    public Screen getCurrentScreen();
    public void setScreen(Screen screen);
}
