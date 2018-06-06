package vaperio.core;

public class Bullet implements Collideable {
    private FloatPoint position;
    private FloatPoint velocity;
    private float width;
    private float height;
    private int damage;
    private boolean isNether;

    public Bullet(FloatPoint position, FloatPoint velocity, int damage){
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
    }


    @Override
    public FloatPoint getPosition() {
        return this.position;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }
}
