package vaperio.core;

import java.awt.*;

public class Spaceship implements Cloneable {
    public final int drag;
    public final int thrust;
    public final int lagDuration;

    public Point position;
    public Point velocity;

    public Spaceship(VaperioParams vaperioParams){
        this.drag = vaperioParams.spaceshipDrag;
        this.thrust = vaperioParams.spaceshipthrust;
        this.lagDuration = vaperioParams.lagDuration;
    }
}
