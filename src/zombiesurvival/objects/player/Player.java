package zombiesurvival.objects.player;

import java.awt.Graphics2D;
import java.util.ArrayList;
import zombiesurvival.objects.Entity;
import zombiesurvival.objects.HitboxEntity;
import zombiesurvival.objects.WeaponSack;
import zombiesurvival.objects.WielderEntity;

public class Player extends WielderEntity{
    private FlashLight light;
    private boolean shooting;
    public Player(FlashLight light, WeaponSack weapons, boolean damagable, double health, double maxHealth, double armor, double maxArmor, String[] imageIDs, double x, double y, double width, double height, double speed, double maxSpeed, double angle, boolean passable, String name) {
        super(weapons, damagable, health, maxHealth, armor, maxArmor, imageIDs, x, y, width, height, speed, maxSpeed, angle, passable, name);
        this.light = light;
        if(this.light != null){
            this.light.setOwner(this);
        }
    }
    
    public Player(Player player){
        super(player);
        this.light = player.light.copy();
    }
    
    @Override
    public Entity copy(){
        return new Player(this);
    }

    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed) {
        super.update(entities, tickSpeed);
        
        if(shooting){
            shoot(entities);
        }
        light.update(entities, tickSpeed);
    } 

    @Override
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height) {
        super.render(gd, offsetX, offsetY, width, height);
        if(light != null){
            light.render(gd, offsetX, offsetY, width, height);
        }
    }

    public FlashLight getLight() {
        return light;
    }

    public void setLight(FlashLight light) {
        this.light = light;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
    
}
