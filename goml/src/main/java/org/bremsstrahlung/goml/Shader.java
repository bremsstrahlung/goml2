package org.bremsstrahlung.goml;

import android.content.res.AssetManager;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

/**
 * Created by gupe7789 on 2014-02-19.
 */
public class Shader {
    private int program;
    private int positionHandle;
    private int texCoordHandle;
    private int projectionHandle;
    private int modelViewHandle;
    private int textureHandle;

    public Shader(int program) {
        this.program = program;

        projectionHandle = GLES20.glGetUniformLocation(program, "projection");
        modelViewHandle = GLES20.glGetUniformLocation(program, "modelView");
        positionHandle = GLES20.glGetAttribLocation(program, "position");
        texCoordHandle = GLES20.glGetAttribLocation(program, "texCoord");
        textureHandle = GLES20.glGetUniformLocation(program, "texture");
    }

    public void setProjection(float[] matrix) {
        GLES20.glUniformMatrix4fv(projectionHandle, 1, false, matrix, 0);
    }

    public void setModelView(float[] matrix) {
        GLES20.glUniformMatrix4fv(modelViewHandle, 1, false, matrix, 0);
    }

    public void setPositionData(FloatBuffer data) {
        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 0, data);
        GLES20.glEnableVertexAttribArray(positionHandle);
    }

    public void setTexCoordData(FloatBuffer data) {
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, data);
        GLES20.glEnableVertexAttribArray(texCoordHandle);
    }

    public void setTexture(int texture) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(textureHandle, GLES20.GL_TEXTURE0);
    }

    public void bind() {
        GLES20.glUseProgram(program);
    }
}
