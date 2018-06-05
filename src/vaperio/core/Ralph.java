package vaperio.core;

public class Ralph implements Cloneable {
    public int health;
    public int position;

    public Ralph(int health, int position){
        this.health = health;
        this.position = position;
    }

    @Override
    public Ralph clone() {
        return new Ralph(this.health, this.position);
    }
}
