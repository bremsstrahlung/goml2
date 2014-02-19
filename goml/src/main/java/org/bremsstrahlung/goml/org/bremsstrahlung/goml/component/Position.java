package org.bremsstrahlung.goml.org.bremsstrahlung.goml.component;

import org.bremsstrahlung.goml.org.bremsstrahlung.goml.component.Component;

/**
 * Created by gupe7789 on 2014-02-19.
 */
public class Position extends Component {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
