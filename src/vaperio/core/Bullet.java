package vaperio.core;

public class Bullet extends Collideable {
    private FloatPoint position;
    private FloatPoint velocity;
    private final boolean isNether;

    public Bullet(FloatPoint position, FloatPoint velocity, boolean isNether, float width, float height){
        super(position, width, height);
        this.position = position;
        this.velocity = velocity;
        this.isNether = isNether;
    }
}
