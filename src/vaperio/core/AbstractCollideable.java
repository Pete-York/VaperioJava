package vaperio.core;

public abstract class AbstractCollideable{
    private FloatPoint position;
    private final float width;
    private final float height;

    public AbstractCollideable(FloatPoint position, float width, float height){
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public FloatPoint getPosition(){
        return position;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public boolean checkCollision(AbstractCollideable that) {
        float xDistance = Math.abs(this.position.x - that.getPosition().x);
        float yDistance = Math.abs(this.position.y - that.getPosition().y);
        boolean xCollide = xDistance <= this.width / 2 + that.getWidth() / 2;
        boolean yCollide = yDistance <= this.height / 2 + that.getHeight() / 2;
        return xCollide && yCollide;
    }

    public void moveTo(FloatPoint position) {
        this.position = position;
    }
}
