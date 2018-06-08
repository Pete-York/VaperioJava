package vaperio.core;

public class Spaceship extends Collideable implements Cloneable {
    private final float drag;
    private final float thrust;
    private final int lagDuration;
    private final int shootRate;
    private static final float height = 0.5f;
    private static final float width = 0.6f;

    private VaperioGameState gameState;

    private FloatPoint velocity;
    private int framesSinceShot;

    private boolean isCollidingWithMarge = false;

    public Spaceship(VaperioParams vaperioParams, FloatPoint position, VaperioGameState gameState){
        super(position, width, height);
        this.drag = vaperioParams.spaceshipDrag;
        this.thrust = vaperioParams.spaceshipthrust;
        this.lagDuration = vaperioParams.lagDuration;
        this.shootRate = vaperioParams.spaceshipShootRate;
        this.gameState = gameState;
    }

    public Spaceship(Spaceship old){
        super(old.getPosition(), width, height);
        this.drag = old.drag;
        this.thrust = old.thrust;
        this.lagDuration = old.lagDuration;
        this.shootRate = old.shootRate;

        this.velocity = old.velocity.clone();
        this.isCollidingWithMarge = old.isCollidingWithMarge;
    }

    public void next(int action){
        handleMovement(action);
        handleShooting(action);
    }

    private void handleMovement(int action){
        int direction = action % 9;
        handleThrust(direction);
        handleVelocity();
    }

    private void handleThrust(int direction){
        if(direction == 0) return;
        direction--;
        FloatPoint directionVector = getDirectionVector(direction);
        directionVector.scale(thrust);
        velocity.add(directionVector);
    }

    private FloatPoint getDirectionVector(int direction){
        float x = 0f, y = 0f;
        if(direction == 0 || direction == 1 || direction == 7){
            y = 1f;
        }
        if(direction == 1 || direction == 2 || direction == 3){
            x = 1f;
        }
        if(direction == 3 || direction == 4 || direction == 5){
            y = -1f;
        }
        if(direction == 5 || direction == 6 || direction == 7){
            x = -1f;
        }
        FloatPoint result = new FloatPoint(x, y);
        result.normalise();
        return result;
    }

    private void handleVelocity(){
        getPosition().add(velocity);
    }

    private void handleShooting(int action){
        if((action >= 9 && action < 18) || (action >= 27 && action < 36)){
            if(framesSinceShot >= shootRate) shootBullet();
        }
    }

    private void shootBullet(){
        gameState.shootSpaceshipBullet();
    }

    public boolean collideWithMarge(Marge marge){
        boolean wasAlreadyColliding = isCollidingWithMarge;
        isCollidingWithMarge = checkCollision(marge);
        return !wasAlreadyColliding && isCollidingWithMarge;
    }

    public void setGameState(VaperioGameState gameState){
        this.gameState = gameState;
    }

    @Override
    public Spaceship clone(){
        return  new Spaceship(this);
    }
}
