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

    public void normalise(){
        float length = getMagnitude();
        x /= length;
        y /= length;
    }

    public float getMagnitude(){
        return (float) Math.sqrt(x * x + y * y);
    }

    public void scale(float factor) {
        x *= factor;
        y *= factor;
    }

    public void add(FloatPoint that){
        x = x + that.x;
        y = y + that.y;
    }

    @Override
    public FloatPoint clone(){
        return new FloatPoint(x, y);
    }
}
