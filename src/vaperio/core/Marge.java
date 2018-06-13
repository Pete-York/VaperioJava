package vaperio.core;

import java.util.Random;

public class Marge extends Collideable implements Cloneable {
    private final Random random = new Random();
    private static final float width = 1.47f;
    private static final float height = 8.82f;

    private final float speed;
    private final float spikeSpeed;
    private final float returnSpeed;
    private final float distanceThreshold;
    private final float defaultHeight;
    private final float spikeHeight;
    private final int spikeSustainFrames;

    private MargeBehaviour currentBehaviour;
    private float waitPosition;
    private float wobbleTargetPosition;
    private int wobbleCount = 0;
    private int wobbleNumber = 3;
    private int flip = 1;
    private boolean spiking = false;
    private int spikeSustainedFrames = 0;

    public Marge(VaperioParams vaperioParams, FloatPoint position) {
        super(position, width, height);
        this.speed = vaperioParams.margeGroundSpeed;
        this.spikeSpeed = vaperioParams.margeSpikeSpeed;
        this.returnSpeed = vaperioParams.margeReturnSpeed;
        this.distanceThreshold = vaperioParams.margeDistanceThreshold;
        this.spikeHeight = vaperioParams.margeSpikeHeight;
        this.defaultHeight = vaperioParams.margeDefaultYPosition;
        this.spikeSustainFrames = vaperioParams.margeSpikeSustainFrames;

        this.currentBehaviour = MargeBehaviour.APPROACHING;
    }

    public Marge(Marge marge) {
        super(marge.getPosition().clone(), width, height);
        this.speed = marge.speed;
        this.spikeSpeed = marge.spikeSpeed;
        this.returnSpeed = marge.returnSpeed;
        this.distanceThreshold = marge.distanceThreshold;
        this.spikeHeight = marge.spikeHeight;
        this.defaultHeight = marge.defaultHeight;
        this.spikeSustainFrames = marge.spikeSustainFrames;

        this.currentBehaviour = marge.currentBehaviour;
        this.waitPosition = marge.waitPosition;
        this.wobbleTargetPosition = marge.wobbleTargetPosition;
        this.wobbleCount = marge.wobbleCount;
        this.wobbleNumber = marge.wobbleNumber;
        this.flip = marge.flip;
        this.spiking = marge.spiking;
        this.spikeSustainedFrames = marge.spikeSustainedFrames;
    }

    public void next(FloatPoint spaceshipPosition){
        switch (currentBehaviour){
            case APPROACHING:
                approach(spaceshipPosition);
                finishApproach(spaceshipPosition);
                break;
            case WAITFORSPIKE:
                wobble();
                finishWobble();
                break;
            case SPIKE:
                spike();
                finishSpike();
                break;
            case RETURN:
                returnToBottom();
                break;
            default:
                break;

        }
    }

    private void approach(FloatPoint spaceshipPosition){
        FloatPoint position = getPosition();
        FloatPoint margeToSpaceshipVector = new FloatPoint(spaceshipPosition.x - position.x, spaceshipPosition.y - position.y);
        double distance = spaceshipPosition.distance(position);
        float xMovement = (float) (margeToSpaceshipVector.x / distance) * speed / VaperioParams.frameRate;
        getPosition().add(new FloatPoint(xMovement, 0f));
    }

    private void finishApproach(FloatPoint spaceshipPosition){
        FloatPoint position = getPosition();
        float distance = Math.abs(spaceshipPosition.x - position.x);
        if(distance < distanceThreshold){
            waitPosition = position.x;
            getWobbleTarget();
            currentBehaviour = MargeBehaviour.WAITFORSPIKE;
        }
    }

    private void wobble(){
        FloatPoint position = getPosition();
        float waitDistance = position.x - wobbleTargetPosition;
        moveTo(position.x + waitDistance * (speed / 10 / VaperioParams.frameRate), position.y);
        if(Math.abs(waitDistance) < distanceThreshold) {

            getWobbleTarget();
            wobbleCount  += 1;
        }
    }

    private void getWobbleTarget(){
        float wobbleDistance = (float) random.nextDouble() * 0.1f;
        wobbleTargetPosition = (waitPosition + wobbleDistance * flip);
        flip *= -1;
    }

    private void finishWobble(){
        if(wobbleCount == wobbleNumber) {
            wobbleCount = 0;
            currentBehaviour = MargeBehaviour.SPIKE;
            spiking = true;
        }
    }

    private void spike(){
        FloatPoint position = getPosition();
        if(spiking){
            moveTo(position.x, position.y + spikeSpeed / VaperioParams.frameRate);
            if(position.y >= spikeHeight + defaultHeight) {
                spiking = false;
            }
        } else {
            spikeSustainedFrames++;
        }
    }

    private void finishSpike(){
        if(!spiking && spikeSustainedFrames >= spikeSustainFrames) {
            currentBehaviour = MargeBehaviour.RETURN;
        }
    }

    private void returnToBottom(){
        FloatPoint position = getPosition();
        moveTo(position.x, position.y - returnSpeed / VaperioParams.frameRate);
        if(position.y <= defaultHeight) {
            currentBehaviour = MargeBehaviour.APPROACHING;
        }
    }

    @Override
    public Marge clone(){
        return new Marge(this);
    }
}
