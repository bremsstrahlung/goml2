package org.bremsstrahlung.goml;

import org.bremsstrahlung.goml.org.bremsstrahlung.goml.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gupe7789 on 2014-02-19.
 */
public class Entity {
    private int id;
    List<Component> components = new ArrayList<Component>();

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public boolean hasComponent(Class<?> type) {
        for(Component c: components) {
            if(c.getClass() == type) {
                return true;
            }
        }

        return false;
    }

    public <T extends Component> T getComponent(Class<?> type) {
        for(Component c: components) {
            if(c.getClass() == type) {
                return (T)c;
            }
        }

        return null;
    }
}
