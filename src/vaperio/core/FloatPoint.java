package vaperio.core;

public class FloatPoint implements Cloneable {
    public float x;
    public float y;

    public FloatPoint(float x, float y){
        this.x = x;
        this.y = y;
    }

    public double distance(FloatPoint that){
        float xDistance = this.x - that.x;
        float yDistance = this.y - that.y;
        double distanceSquared = xDistance * xDistance + yDistance * yDistance;
        return Math.sqrt(distanceSquared);
    }

    @Override
    public FloatPoint clone(){
        return new FloatPoint(x, y);
    }
}
