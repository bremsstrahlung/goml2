package org.bremsstrahlung.goml;

import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by gupe7789 on 2014-02-19.
 */
public class ShaderFactory {
    private static final String TAG = ShaderFactory.class.getSimpleName();

    private AssetManager assetManager;

    public ShaderFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Shader createShader(String baseName) {
        Log.d(TAG, "createShader");

        int program = GLES20.glCreateProgram();

        int vs = loadShader(GLES20.GL_VERTEX_SHADER ,String.format("%s.vs", baseName));
        int fs = loadShader(GLES20.GL_FRAGMENT_SHADER, String.format("%s.fs", baseName));

        int[] status = new int[1];

        GLES20.glAttachShader(program, vs);
        GLES20.glAttachShader(program, fs);
        GLES20.glLinkProgram(program);
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, status, 0);
        if(status[0] != GLES20.GL_TRUE) {
            Log.e(TAG, "createShader: failed to link shader: " + GLES20.glGetProgramInfoLog(program));
        }

        return new Shader(program);
    }

    private int loadShader(int type, String asset) {
        Log.d(TAG, "loadShader");

        int shader = -1;

        try {
            BufferedReader rdr = new BufferedReader(new InputStreamReader(assetManager.open(asset)));

            StringBuilder source = new StringBuilder();
            String line = null;
            while((line = rdr.readLine()) != null) {
                source.append(line);
            }

            rdr.close();

            int[] status = new int[1];

            shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, source.toString());
            GLES20.glCompileShader(shader);
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0);
            if(status[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "loadShader: failed to compile shader: " + GLES20.glGetShaderInfoLog(shader));
            }
        }
        catch(IOException ex) {
            Log.e(TAG, "loadShader: couldn't load asset: " + ex.getMessage());
        }

        return shader;
    }
}
