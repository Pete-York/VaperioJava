package vaperio.core;

public class Spaceship extends Collideable implements Cloneable {
    private final float minDrag;
    private final float dragFactor;
    private final float dragExponent;
    private final float thrust;
    private final int lagDuration;
    private final int shootRate;
    private static final float height = 0.5f;
    private static final float width = 0.6f;

    private VaperioGameState gameState;

    private FloatPoint velocity;
    private int framesSinceShot;
    private int[] inputBuffer;
    private int lagIndex = 0;

    private boolean isCollidingWithMarge = false;

    public Spaceship(VaperioParams vaperioParams, FloatPoint position, VaperioGameState gameState){
        super(position, width, height);
        this.minDrag = vaperioParams.minSpaceshipDrag;
        this.dragFactor = vaperioParams.spaceshipDragFactor;
        this.dragExponent = vaperioParams.spaceshipDragExponent;
        this.thrust = vaperioParams.spaceshipThrust;
        this.lagDuration = vaperioParams.lagDuration;
        this.shootRate = vaperioParams.spaceshipShootRate;
        this.gameState = gameState;
        this.velocity = new FloatPoint(0f, 0f);
        this.framesSinceShot = shootRate;
        this.inputBuffer = new int[vaperioParams.lagDuration];
        for(int i = 0; i < lagDuration; i++){
            inputBuffer[i] = 0;
        }
    }

    public Spaceship(Spaceship old){
        super(old.getPosition(), width, height);
        this.minDrag = old.minDrag;
        this.dragFactor = old.dragFactor;
        this.dragExponent = old.dragExponent;
        this.thrust = old.thrust;
        this.lagDuration = old.lagDuration;
        this.shootRate = old.shootRate;

        this.velocity = old.velocity.clone();
        this.framesSinceShot = old.framesSinceShot;

        this.isCollidingWithMarge = old.isCollidingWithMarge;
        this.inputBuffer = old.inputBuffer.clone();
    }

    public void next(int action){
        addToBuffer(action);
        handleMovement(getFromBuffer());
        checkEdges();
        handleShooting(action);
        lagIndex++;
        if (lagIndex == lagDuration) {
            lagIndex = 0;
        }
    }

    private void addToBuffer(int action){
        inputBuffer[lagIndex] = action;
    }

    private int getFromBuffer(){
        return inputBuffer[(lagDuration - 1) - lagIndex];
    }

    private void handleMovement(int action){
        int direction = action % 9;
        handleThrust(direction);
        applyDrag();
        handleVelocity();
    }

    private void handleThrust(int direction){
        if(direction == 0) return;
        direction--;
        FloatPoint directionVector = getDirectionVector(direction);
        directionVector.scale(thrust / VaperioParams.frameRate);
        velocity.add(directionVector);
    }

    private void applyDrag(){
        float speed = velocity.getMagnitude();
        float currentDrag = Math.max ((float) Math.pow(speed, dragExponent) * dragFactor, minDrag) ;
        velocity.scale(Math.max( 1 -  currentDrag / VaperioParams.frameRate, 0));
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
        FloatPoint scaledVelocity = velocity.clone();
        scaledVelocity.scale(1f / (float) VaperioParams.frameRate);
        getPosition().add(scaledVelocity);
    }

    private void handleShooting(int action){
        if((action >= 9 && action < 18) || (action >= 27 && action < 36)
                && framesSinceShot >= shootRate){
            shootBullet();
            framesSinceShot = 0;
        } else {
            framesSinceShot++;
        }
    }

    private void checkEdges(){
        FloatPoint position = getPosition();
        if(position.x > VaperioParams.maxXCoordinate) {
            moveTo(VaperioParams.maxXCoordinate, position.y);
        } else if (position.x < VaperioParams.minXCoordinate) {
            moveTo(VaperioParams.minXCoordinate, position.y);
        }
        if(position.y > VaperioParams.maxYCoordinate) {
            moveTo(position.x, VaperioParams.maxYCoordinate);
        } else if(position.y < VaperioParams.minYCoordinate) {
            moveTo(position.x, VaperioParams.minYCoordinate);
        }
    }

    private void shootBullet(){
        gameState.shootSpaceshipBullet(getPosition().clone());
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
