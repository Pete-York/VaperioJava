package vaperio.core;

import java.awt.*;
import java.util.Random;

public class Marge implements Cloneable, Collideable {
    private final Random random = new Random();

    private final float speed;
    private final float spikeSpeed;
    private final float returnSpeed;
    private final float distanceThreshold;
    private final float spikeHeight;
    private final int spikeSustainFrames;

    private FloatPoint position;

    private MargeBehaviour currentBehaviour;
    private float waitPosition;
    private float wobbleTargetPosition;
    private int wobbleCount = 0;
    private int wobbleNumber = 3;
    private int flip = 1;
    private boolean spiking = false;
    private int spikeSustainedFrames = 0;

    public Marge(VaperioParams vaperioParams) {
        this.speed = vaperioParams.margeGroundSpeed;
        this.spikeSpeed = vaperioParams.margeSpikeSpeed;
        this.returnSpeed = vaperioParams.margeReturnSpeed;
        this.distanceThreshold = vaperioParams.margeDistanceThreshold;
        this.spikeHeight = vaperioParams.margeSpikeHeight;
        this.spikeSustainFrames = vaperioParams.margeSpikeSustainFrames;

        this.position = new FloatPoint(0f, 0f);
        this.currentBehaviour = MargeBehaviour.APPROACHING;
    }

    public Marge(Marge marge) {
        this.speed = marge.speed;
        this.spikeSpeed = marge.spikeSpeed;
        this.returnSpeed = marge.returnSpeed;
        this.distanceThreshold = marge.distanceThreshold;
        this.spikeHeight = marge.spikeHeight;
        this.spikeSustainFrames = marge.spikeSustainFrames;

        this.position = new FloatPoint(marge.position.x, marge.position.y);

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
        FloatPoint margeToSpaceshipVector = new FloatPoint(spaceshipPosition.x - position.x, spaceshipPosition.y - position.y);
        double distance = spaceshipPosition.distance(position);
        float xMovement = (float) (margeToSpaceshipVector.x / distance) * speed;
        this.position = new FloatPoint(position.x + xMovement, position.y);
    }

    private void finishApproach(FloatPoint spaceshipPosition){
        float distance = Math.abs(spaceshipPosition.x - position.x);
        if(distance < distanceThreshold){
            waitPosition = position.x;
            getWobbleTarget();
            currentBehaviour = MargeBehaviour.WAITFORSPIKE;
        }
    }

    private void wobble(){
        float waitDistance = position.x - wobbleTargetPosition;
        this.position = new FloatPoint(position.x + waitDistance * (speed / 10), position.y);
        if(Math.abs(waitDistance) < distanceThreshold) {

            getWobbleTarget();
            wobbleCount  += 1;
        }
    }

    private void getWobbleTarget(){
        double wobbleDistance = random.nextDouble() * 500;
        wobbleTargetPosition = (int) (waitPosition + wobbleDistance * flip);
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
        if(spiking){
            this.position = new FloatPoint(position.x, position.y + spikeSpeed);
            if(position.y >= spikeHeight) {
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
        this.position = new FloatPoint(position.x, position.y - returnSpeed);
        if(position.y <= 0) {
            currentBehaviour = MargeBehaviour.APPROACHING;
        }
    }

    @Override
    public Marge clone(){
        return new Marge(this);
    }

    @Override
    public FloatPoint getPosition() {
        return this.position;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }
}
