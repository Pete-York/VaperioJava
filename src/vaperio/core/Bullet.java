package vaperio.core;

public abstract class Bullet {
    private FloatPoint position;
    private FloatPoint velocity;
    private int damage;

    public Bullet(FloatPoint position, FloatPoint velocity, int damage){
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
    }


}
