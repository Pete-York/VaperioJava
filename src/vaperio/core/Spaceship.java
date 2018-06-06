package vaperio.core;

import java.util.List;

public class Spaceship implements Cloneable {
    public final float drag;
    public final float thrust;
    public final int lagDuration;
    public final float height = 0.5f;
    public final float width = 0.6f;

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

    private void checkCollisions(List<Bullet> bulletList, Marge marge){

    }
}
