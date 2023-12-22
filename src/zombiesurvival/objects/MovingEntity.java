package zombiesurvival.objects;

import java.util.ArrayList;
import zombiesurvival.config.Collision;

public class MovingEntity extends HealthEntity{
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public MovingEntity(boolean damagable, double health, double maxHealth, double armor, double maxArmor, String[] imageIDs, double x, double y, double width, double height, double speed, double maxSpeed, double angle, boolean passable, String name) {
        super(damagable, health, maxHealth, armor, maxArmor, imageIDs, x, y, width, height, speed, maxSpeed, angle, passable, name);
    }
    
    public MovingEntity(MovingEntity entity){
        super(entity);
        this.up = entity.up;
        this.down = entity.down;
        this.left = entity.left;
        this.right = entity.right;
    }
    
    @Override
    public Entity copy(){
        return new MovingEntity(this);
    }
        

    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed) {
        super.update(entities, tickSpeed);
        if(up){
            Collision.move(3, this, entities, getSpeed());
        }
        if(down){
            Collision.move(1, this, entities, getSpeed());
        }
        if(left){
            Collision.move(4, this, entities, getSpeed());
        }
        if(right){
            Collision.move(2, this, entities, getSpeed());
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.up ? 1 : 0);
        hash = 17 * hash + (this.down ? 1 : 0);
        hash = 17 * hash + (this.left ? 1 : 0);
        hash = 17 * hash + (this.right ? 1 : 0);
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
        final MovingEntity other = (MovingEntity) obj;
        if (this.up != other.up) {
            return false;
        }
        if (this.down != other.down) {
            return false;
        }
        if (this.left != other.left) {
            return false;
        }
        if (this.right != other.right) {
            return false;
        }
        return true;
    }
    
    public void stop(){
        up = false;
        down = false;
        left = false;
        right = false;
    }
    
    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    
    
    
    
}
