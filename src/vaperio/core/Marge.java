package vaperio.core;

import java.awt.*;
import java.util.Random;

public class Marge implements Cloneable{
    private final Random random = new Random();

    private int speed = 300;
    private int spikeSpeed = 8000;
    private int returnSpeed = 3000;
    private int distanceThreshold = 100;
    private int spikeHeight = 7000;
    private int spikeSustainFrames = 60 * 2;

    private Point position;

    private MargeBehaviour currentBehaviour;
    private int waitPosition;
    private int wobbleTargetPosition;
    private int wobbleCount = 0;
    private int wobbleNumber = 3;
    private int flip = 1;
    private boolean spiking = false;
    private int spikeSustainedFrames = 0;

    public Marge() {
        this.position = new Point();
        this.currentBehaviour = MargeBehaviour.APPROACHING;
    }

    public Marge(Point position, MargeBehaviour currentBehaviour) {
        this.position = position;
        this.currentBehaviour = currentBehaviour;
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
        wobbleTargetPosition = (int) (position.x + wobbleDistance * flip);
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
        return new Marge(new Point(position.x, position.y), this.currentBehaviour);
    }
}
