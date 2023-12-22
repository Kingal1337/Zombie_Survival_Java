package zombiesurvival.objects;

import java.util.ArrayList;

public class WielderEntity extends MovingEntity{
    private WeaponSack weapons;

    public WielderEntity(WeaponSack weapons, boolean damagable, double health, double maxHealth, double armor, double maxArmor, String[] imageIDs, double x, double y, double width, double height, double speed, double maxSpeed, double angle, boolean passable, String name) {
        super(damagable, health, maxHealth, armor, maxArmor, imageIDs, x, y, width, height, speed, maxSpeed, angle, passable, name);
        this.weapons = weapons;
        if(this.weapons != null){
            for(int i=0;i<this.weapons.getAllWeapons().size();i++){
                this.weapons.getAllWeapons().get(i).setOwner(this);
            }
        }
    }
    
    public WielderEntity(WielderEntity entity){
        super(entity);
        this.weapons = entity.weapons.copy();
    }
    
    @Override
    public Entity copy(){
        return new WielderEntity(this);
    }

    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed) {
        super.update(entities, tickSpeed);
        if(weapons != null){
            if(weapons.getCurrentWeapon() != null){
                weapons.getCurrentWeapon().update(tickSpeed);
            }
        }
    }
    
    public void shoot(ArrayList<HitboxEntity> entitys){
        Weapon weapon = weapons.getCurrentWeapon();
        Bullet[] bullets = weapon.preShoot(getCenterX(), getCenterY(), getAngle());
        if(bullets != null){
            for(int i=0;i<bullets.length;i++){
                entitys.add(bullets[i]);
            }
        }
    }
    
    public void reloadCurrentWeapon(){
        if(weapons.getCurrentWeapon() != null){
            weapons.getCurrentWeapon().setIsReloading(true);
        }
    }

    public WeaponSack getWeapons() {
        return weapons;
    }

    public void setWeapons(WeaponSack weapons) {
        this.weapons = weapons;
    }
    
    
    
}
