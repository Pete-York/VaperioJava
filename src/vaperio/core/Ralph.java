package vaperio.core;

public class Ralph extends Collideable implements Cloneable {
    private static final float width = 0.924f;
    private static final float height = 1.56f;
    private int health;
    private boolean isNether;

    public Ralph(int health, FloatPoint position, boolean isNether){
        super(position, width, height);
        this.health = health;
    }

    @Override
    public Ralph clone() {
        return new Ralph(this.health, this.getPosition(), this.isNether);
    }

    public boolean applyDamage(int damage){
        health -= damage;
        return damage <= 0;
    }
}
