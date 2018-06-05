package vaperio.core;

import java.awt.*;
import java.util.Random;

public class Marge implements Cloneable{
    private final Random random = new Random();

    private final int speed;
    private final int spikeSpeed;
    private final int returnSpeed;
    private final int distanceThreshold;
    private final int spikeHeight;
    private final int spikeSustainFrames;

    private Point position;

    private MargeBehaviour currentBehaviour;
    private int waitPosition;
    private int wobbleTargetPosition;
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

        this.position = new Point();
        this.currentBehaviour = MargeBehaviour.APPROACHING;
    }

    public Marge(Marge marge) {
        this.speed = marge.speed;
        this.spikeSpeed = marge.spikeSpeed;
        this.returnSpeed = marge.returnSpeed;
        this.distanceThreshold = marge.distanceThreshold;
        this.spikeHeight = marge.spikeHeight;
        this.spikeSustainFrames = marge.spikeSustainFrames;

        this.position = new Point(marge.position.x, marge.position.y);

        this.currentBehaviour = marge.currentBehaviour;
        this.waitPosition = marge.waitPosition;
        this.wobbleTargetPosition = marge.wobbleTargetPosition;
        this.wobbleCount = marge.wobbleCount;
        this.wobbleNumber = marge.wobbleNumber;
        this.flip = marge.flip;
        this.spiking = marge.spiking;
        this.spikeSustainedFrames = marge.spikeSustainedFrames;
    }

    public void next(Point spaceshipPosition){
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

    private void approach(Point spaceshipPosition){
        Point margeToSpaceshipVector = new Point(spaceshipPosition.x - position.x, spaceshipPosition.y - position.y);
        double distance = spaceshipPosition.distance(position);
        double xMovement =  (margeToSpaceshipVector.x / distance) * speed;
        this.position.x = (int) (position.x + xMovement);
    }

    private void finishApproach(Point spaceshipPosition){
        int distance = Math.abs(spaceshipPosition.x - position.x);
        if(distance < distanceThreshold){
            waitPosition = position.x;
            getWobbleTarget();
            currentBehaviour = MargeBehaviour.WAITFORSPIKE;
        }
    }

    private void wobble(){
        int waitDistance = position.x - wobbleTargetPosition;
        position.x = position.x + waitDistance * (speed / 10);
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
            position.y = position.y + spikeSpeed;
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
        position.y = position.y - returnSpeed;
        if(position.y <= 0) {
            currentBehaviour = MargeBehaviour.APPROACHING;
        }
    }

    @Override
    public Marge clone(){
        return new Marge(this);
    }
}
