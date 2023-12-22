package zombiesurvival.objects.zombies;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import zombiesurvival.config.Collision;
import zombiesurvival.objects.Entity;
import zombiesurvival.objects.HealthEntity;
import zombiesurvival.objects.HitboxEntity;
import zombiesurvival.objects.MovingEntity;

public class Zombie extends MovingEntity{
    public static final Zombie REGULAR_ZOMBIE = new Zombie(
            2, 750, 
            true, 100, 100, 0, 0, 
            new String[]{"reg_zom_2","reg_zom_1","reg_zom_3"}, 
            -1, -1, 50, 50, 4, 4, 0, false, "Regular_Zombie");
    public static final Zombie SUPER_CHARGED_ZOMBIE = new Zombie(
            10, 250, true, 5, 5, 0, 0, 
            new String[]{"super_zom_2", 
                "super_zom_1", "super_zom_3"}, 
            -1, -1, 50, 50, 8, 4, 0, false, "Super_Charged_Zombie");
    public static final Zombie GURL_ZOMBIE = new Zombie(2, 750, 
            true, 100, 100, .7, .7, 
            new String[]{"girl_zom_2", "girl_zom_1", "girl_zom_3"}, 
            -1, -1, 50, 50, 4, 4, 0, false, "Gurl_Zombie");
    
    private HitboxEntity target;
    
    private Rectangle2D.Double hitbox;
    
    private double damageToDo;
    private int hitSpeed;//in milliseconds
    private int counter;
    public Zombie(double damageToDo, 
            int hitSpeed, boolean damagable, 
            double health, double maxHealth, 
            double armor, double maxArmor, String[] imageID, 
            double x, double y, double width, double height,
            double speed, double maxSpeed, double angle, 
            boolean passable, String name) {
        super(damagable, health, maxHealth, armor, maxArmor, 
                imageID, x, y, width, height, speed, 
                maxSpeed, angle, passable, name);
        this.damageToDo = damageToDo;
        this.hitSpeed = hitSpeed;
        hitbox = new Rectangle2D.Double(-width, -height, 
                width*3, height*3);
    }
    
    public Zombie(Zombie zombie){
        super(zombie);
        this.target = zombie.target;
        this.hitbox = new Rectangle2D.Double(zombie.hitbox.x, 
                zombie.hitbox.y, zombie.hitbox.width, 
                zombie.hitbox.height);
        this.damageToDo = zombie.damageToDo;
        this.hitSpeed = zombie.hitSpeed;
    }
    
    @Override
    public Entity copy(){
        return new Zombie(this);
    }
    
    @Override
    public void update(ArrayList<HitboxEntity> entities, 
            int tickSpeed) {
        super.update(entities, tickSpeed);
        if(target != null){
            setAngle(Math.atan2(target.getCenterY() - getCenterY(), 
                    target.getCenterX() - getCenterX()));
            if(getX() > target.getX()+target.getWidth() || 
                    getX()+getWidth() < target.getX()){
                if(target.getCenterX() < getCenterX()){
                    setLeft(true);
                    setRight(false);
                }
                else if(target.getCenterX() > getCenterX()){
                    setLeft(false);
                    setRight(true);
                }
            }
            else{
                setLeft(false);
                setRight(false);
            }
            if(getY() > target.getY()+target.getHeight()|| 
                    getY()+getHeight()< target.getY()){
                if(target.getCenterY() < getCenterY()){
                    setUp(true);
                    setDown(false);
                }
                else if(target.getCenterY() > getCenterY()){
                    setUp(false);
                    setDown(true);
                }
            }
            else{
                setUp(false);
                setDown(false);
            }
            
            if(target instanceof HealthEntity){
                HealthEntity healthEntity = (HealthEntity)target;
                if(Collision.intersects(
                        hitbox.getX()+getX(),hitbox.getY()+getY(),
                        hitbox.getWidth(), hitbox.getHeight(),
                        healthEntity.getX(), healthEntity.getY(), 
                        healthEntity.getWidth(), healthEntity.getHeight())){
                    counter+=tickSpeed;
                    if(counter >= hitSpeed){
                        counter = 0;
                        healthEntity.takeDamage(damageToDo);
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics2D gd, 
            double offsetX, double offsetY, 
            int width, int height) {
        super.render(gd, offsetX, offsetY, width, height);
    }    

    public HitboxEntity getTarget() {
        return target;
    }

    public void setTarget(HitboxEntity target) {
        this.target = target;
    }

    public Rectangle2D.Double getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Double hitbox) {
        this.hitbox = hitbox;
    }

    public double getDamageToDo() {
        return damageToDo;
    }

    public void setDamageToDo(double damageToDo) {
        this.damageToDo = damageToDo;
    }

    public int getHitSpeed() {
        return hitSpeed;
    }

    public void setHitSpeed(int hitSpeed) {
        this.hitSpeed = hitSpeed;
    }
}
