package vaperio.core;

import java.awt.*;

public class Spaceship implements Cloneable {
    public final int drag;
    public final int thrust;
    public final int lagDuration;

    public FloatPoint position;
    public FloatPoint velocity;

    public Spaceship(VaperioParams vaperioParams){
        this.drag = vaperioParams.spaceshipDrag;
        this.thrust = vaperioParams.spaceshipthrust;
        this.lagDuration = vaperioParams.lagDuration;
    }

    public void next(int action){

    }

    private void handleMovement(int action){

    }

    private void handleShooting(int action){

    }
}
