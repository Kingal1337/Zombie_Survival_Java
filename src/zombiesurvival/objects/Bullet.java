package zombiesurvival.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import zombiesurvival.config.Collision;
import zombiesurvival.config.Images;

public class Bullet extends MovingEntity{
    private HitboxEntity owner;
    private boolean setInitalPoints;
    private double damage;
    private double origx;
    private double origy;
    private double distance;
    private double speedX;
    private double speedY;

    public Bullet(HitboxEntity owner, double damage, boolean damagable, double health, double maxHealth, double armor, double maxArmor, String[] imageIDs, double x, double y, double width, double height, double speed, double maxSpeed, double angle, boolean passable, String name) {
        super(damagable, health, maxHealth, armor, maxArmor, imageIDs, x, y, width, height, speed, maxSpeed, angle, passable, name);
        this.owner = owner;
        this.damage = damage;
    }
    
    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed){
        super.update(entities, tickSpeed);
        for(int i=0;i<entities.size();i++){
            if(!entities.get(i).equals(this)){
                if(Collision.intersects(this, entities.get(i))){
                    if(!owner.equals(entities.get(i))){
                        if(!entities.get(i).isRemove()){
                            if(entities.get(i) instanceof HealthEntity){
                                HealthEntity damagableEntity = (HealthEntity)entities.get(i);
                                damagableEntity.takeDamage(damage);
                                setRemove(true);
                            }
                            else{                            
                                setRemove(true);
                            }
                            break;
                        }
                    }
                }
            }
        }
        if(!setInitalPoints){
            setInitalPoints = true;
            distance += getSpeed();
            setSpeedX(calcSpeedX(getAngle()));
            setSpeedY(calcSpeedY(getAngle()));
        }
        if(speedY > 0){
            Collision.move(1, this, entities, (speedY));
        }
        if(speedY < 0){
            Collision.move(3, this, entities, (-speedY));
        }
        if(speedX > 0){
            Collision.move(2, this, entities, (speedX));
        }
        if(speedX < 0){
            Collision.move(4, this, entities, (-speedX));
        }
    }

    @Override
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height) {
        super.render(gd, offsetX, offsetY, width, height);
        if(getImage() == null || (getImage().length != 0 && Images.getImage(getImage()[0]) == null)){
            Graphics2D gd2 = (Graphics2D)gd.create();
            gd2.setColor(Color.WHITE);
            gd2.rotate(getAngle(), (int)((getWidth()/2)+(getX()-offsetX)), (int)((getHeight()/2)+(getY()-offsetY)));
            gd2.fillRect((int)(getX()-offsetX), (int)(getY()-offsetY), (int)getWidth(), (int)getHeight());
        }
    }
    
    public double calcSpeedX(double angle){
        return distance*Math.cos(angle);
    }
    
    public double calcSpeedY(double angle){
        return distance*Math.sin(angle);
    }  

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }
}
