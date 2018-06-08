package vaperio.core;

public class Ralph extends Collideable implements Cloneable {
    private static final float width = 0.924f;
    private static final float height = 1.56f;
    private int health;
    private boolean isNether;
    private VaperioGameState gameState;

    public Ralph(int health, FloatPoint position, boolean isNether, VaperioGameState gameState){
        super(position, width, height);
        this.health = health;
        this.isNether = isNether;
        this.gameState = gameState;
    }
    public Ralph(Ralph old){
        super(old.getPosition(), width, height);
        this.health = old.health;
        this.isNether = old.isNether;
    }

    @Override
    public Ralph clone() {
        return new Ralph(this);
    }

    public void next(){

    }

    public boolean applyDamage(int damage){
        health -= damage;
        return damage <= 0;
    }

    public boolean getIsNether(){
        return isNether;
    }

    public void setGameState(VaperioGameState gameState){
        this.gameState = gameState;
    }
}
