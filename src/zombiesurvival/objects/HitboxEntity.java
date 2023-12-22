package zombiesurvival.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import zombiesurvival.config.Images;

public class HitboxEntity extends Entity{
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;
    private double maxSpeed;
    private double angle;
    private boolean passable;
    
    private boolean drawHitBox;

    public HitboxEntity(double x, double y, double width, double height, double speed, double maxSpeed, double angle, boolean passable, String name) {
        super(name);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.maxSpeed = maxSpeed;
        this.angle = angle;
        this.passable = passable;
        
        drawHitBox = false;
    }
    
    public HitboxEntity(HitboxEntity entity){
        super(entity);
        this.x = entity.x;
        this.y = entity.y;
        this.width = entity.width;
        this.height = entity.height;
        this.speed = entity.speed;
        this.maxSpeed = entity.maxSpeed;
        this.angle = entity.angle;
        this.passable = entity.passable;
        this.drawHitBox = entity.drawHitBox;
    }

    @Override
    public Entity copy() {
        return new HitboxEntity(this);
    }


    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed) {
        
    }

    @Override
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height) {
        if(drawHitBox){
            gd.setColor(Color.BLACK);
            gd.drawRect((int)(getX()-offsetX), (int)(getY()-offsetY), (int)getWidth(), (int)getHeight());
        }
    }
    
    public double getCenterX() {
        return getX() + getWidth() / 2.0;
    }
    
    public double getCenterY() {
        return getY() + getHeight() / 2.0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.width) ^ (Double.doubleToLongBits(this.width) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.height) ^ (Double.doubleToLongBits(this.height) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.speed) ^ (Double.doubleToLongBits(this.speed) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.maxSpeed) ^ (Double.doubleToLongBits(this.maxSpeed) >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.angle) ^ (Double.doubleToLongBits(this.angle) >>> 32));
        hash = 73 * hash + (this.passable ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HitboxEntity other = (HitboxEntity) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.width) != Double.doubleToLongBits(other.width)) {
            return false;
        }
        if (Double.doubleToLongBits(this.height) != Double.doubleToLongBits(other.height)) {
            return false;
        }
        if (Double.doubleToLongBits(this.speed) != Double.doubleToLongBits(other.speed)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maxSpeed) != Double.doubleToLongBits(other.maxSpeed)) {
            return false;
        }
        if (Double.doubleToLongBits(this.angle) != Double.doubleToLongBits(other.angle)) {
            return false;
        }
        if (this.passable != other.passable) {
            return false;
        }
        return true;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }
    
}
