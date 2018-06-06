package vaperio.core;

public class Spaceship extends Collideable implements Cloneable {
    private final float drag;
    private final float thrust;
    private final int lagDuration;
    private static final float height = 0.5f;
    private static final float width = 0.6f;

    private FloatPoint velocity;

    private boolean isCollidingWithMarge = false;

    public Spaceship(VaperioParams vaperioParams, FloatPoint position){
        super(position, width, height);
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

    public boolean collideWithMarge(Marge marge){
        boolean wasAlreadyColliding = isCollidingWithMarge;
        isCollidingWithMarge = checkCollision(marge);
        return !wasAlreadyColliding && isCollidingWithMarge;
    }
}
