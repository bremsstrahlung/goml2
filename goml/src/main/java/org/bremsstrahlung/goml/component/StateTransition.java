package org.bremsstrahlung.goml.component;

import org.bremsstrahlung.goml.World;

/**
 * Created by wilco on 2/20/14.
 */
public class StateTransition extends Component {
    private World.GameState transitionTo;

    public StateTransition(World.GameState transitionTo) {
        this.transitionTo = transitionTo;
    }

    public World.GameState getTransitionTo() {
        return transitionTo;
    }

    public void setTransitionTo(World.GameState transitionTo) {
        this.transitionTo = transitionTo;
    }
}
