package org.bremsstrahlung.goml.system;

import org.bremsstrahlung.goml.World;

/**
 * Created by wilco on 2/20/14.
 */
public abstract class System {
    protected World world;

    public System(World world) {
        this.world = world;
    }

    public abstract void update(float deltaTime);
}
