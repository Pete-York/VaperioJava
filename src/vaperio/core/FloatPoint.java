package vaperio.core;

public class FloatPoint {
    public final float x;
    public final float y;

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
}
