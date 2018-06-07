package vaperio.core;

public class Bullet extends Collideable implements Cloneable {
    private FloatPoint velocity;
    private final boolean isNether;

    public Bullet(FloatPoint position, FloatPoint velocity, boolean isNether, float width, float height){
        super(position, width, height);
        this.velocity = velocity;
        this.isNether = isNether;
    }

    public Bullet(Bullet old){
        super(old.getPosition().clone(), old.getWidth(), old.getHeight());
        this.velocity = old.velocity.clone();
        this.isNether = old.isNether;
    }

    public boolean getIsNether(){
        return isNether;
    }

    @Override
    public Bullet clone(){
        return new Bullet(this);
    }
}
