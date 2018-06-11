package vaperio.core;

public class Ralph extends Collideable implements Cloneable {
    private static final float width = 0.924f;
    private static final float height = 1.56f;
    private final int shootRate;
    private int health;
    private boolean isNether;
    private int framesSinceShoot = 0;
    private VaperioGameState gameState;

    public Ralph(VaperioParams gameParams, FloatPoint position, boolean isNether, VaperioGameState gameState){
        super(position, width, height);
        this.shootRate = gameParams.ralphShootRate;
        this.health = gameParams.ralphHealth;
        this.isNether = isNether;
        this.gameState = gameState;
    }
    public Ralph(Ralph old){
        super(old.getPosition(), width, height);
        this.shootRate = old.shootRate;
        this.health = old.health;
        this.isNether = old.isNether;
        this.gameState = (VaperioGameState) old.gameState.copy();
    }

    @Override
    public Ralph clone() {
        return new Ralph(this);
    }

    public void next(){
        if(framesSinceShoot >= shootRate) {
            gameState.shootRalphBullet(this.getPosition().clone());
            framesSinceShoot = 0;
        }
        framesSinceShoot++;
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
