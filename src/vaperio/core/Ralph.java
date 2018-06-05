package vaperio.core;

public class Ralph implements Cloneable {
    public int health;
    public int position;
    public boolean isNether;

    public Ralph(int health, int position, boolean isNether){
        this.health = health;
        this.position = position;
    }

    @Override
    public Ralph clone() {
        return new Ralph(this.health, this.position, this.isNether);
    }
}
