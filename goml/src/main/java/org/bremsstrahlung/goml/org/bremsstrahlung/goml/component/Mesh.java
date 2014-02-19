package org.bremsstrahlung.goml.org.bremsstrahlung.goml.component;

import java.nio.FloatBuffer;

/**
 * Created by gupe7789 on 2014-02-19.
 */
public class Mesh extends Component {
    private FloatBuffer vertices;
    private FloatBuffer texCoords;
    private int texture;

    public FloatBuffer getVertices() {
        return vertices;
    }

    public void setVertices(FloatBuffer vertices) {
        this.vertices = vertices;
    }

    public FloatBuffer getTexCoords() {
        return texCoords;
    }

    public void setTexCoords(FloatBuffer texCoords) {
        this.texCoords = texCoords;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }
}
