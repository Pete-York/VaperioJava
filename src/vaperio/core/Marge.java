package vaperio.core;

import java.awt.*;

public class Marge implements Cloneable{
    Point position;
    MargeBehaviour currentBehaviour;

    public Marge() {
        this.position = new Point();
        this.currentBehaviour = MargeBehaviour.APPROACHING;
    }

    public Marge(Point position, MargeBehaviour currentBehaviour) {
        this.position = position;
        this.currentBehaviour = currentBehaviour;
    }

    @Override
    public Marge clone(){
        return new Marge(new Point(position.x, position.y), this.currentBehaviour);
    }
}
