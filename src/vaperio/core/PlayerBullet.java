package vaperio.core;

public class PlayerBullet extends Bullet {
    boolean isNether;

    public PlayerBullet(FloatPoint position, FloatPoint velocity, int damage) {
        super(position, velocity, damage);
    }
}
