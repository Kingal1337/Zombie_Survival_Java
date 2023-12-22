package zombiesurvival.objects.zombies;

import java.util.ArrayList;
import zombiesurvival.config.Images;
import zombiesurvival.objects.HealthEntity;
import zombiesurvival.objects.HitboxEntity;

public class ZombieSpawner extends HealthEntity{
    private HitboxEntity target;
    private int rateOfSpawnStart;
    private int rateOfSpawnEnd;
    
    private int currentAngle;
    
    private int currentRandomRateOfSpawn;
    
    private int spawnCounter;
    private boolean canSpawn;

    public ZombieSpawner(int rateOfSpawnStart, int rateOfSpawnEnd, boolean damagable, double health, double maxHealth, double armor, double maxArmor, String[] imageIDs, double x, double y, double width, double height, double speed, double maxSpeed, double angle, boolean passable, String name) {
        super(damagable, health, maxHealth, armor, maxArmor, imageIDs, x, y, width, height, speed, maxSpeed, angle, passable, name);
        this.rateOfSpawnStart = rateOfSpawnStart;
        this.rateOfSpawnEnd = rateOfSpawnEnd;
        canSpawn = true;
        
        currentRandomRateOfSpawn = rateOfSpawnStart + (int)(Math.random() * ((rateOfSpawnEnd - rateOfSpawnStart) + 1));
    }
    
    public ZombieSpawner(ZombieSpawner yard){
        super(yard);
        this.target = yard.target;
        this.rateOfSpawnStart = yard.rateOfSpawnStart;
        this.rateOfSpawnEnd = yard.rateOfSpawnEnd;
        
        this.currentRandomRateOfSpawn = yard.currentRandomRateOfSpawn;
        
        this.canSpawn = yard.canSpawn;
    }

    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed) {
        super.update(entities, tickSpeed);
        if(canSpawn){
            spawnCounter+=tickSpeed;
            if(spawnCounter >= currentRandomRateOfSpawn){
                spawnCounter = 0;
                spawnZombie(entities);
                currentRandomRateOfSpawn = rateOfSpawnStart + (int)(Math.random() * ((rateOfSpawnEnd - rateOfSpawnStart) + 1));
            }
        }
        if(getImage() != null && (getImage().length != 0 && Images.getImage(getImage()[0]) != null)){
            currentAngle++;
            if(currentAngle >= 360){
                currentAngle = 0;
            }
            setAngle(Math.toRadians(currentAngle));
        }
    }
    
    public void spawnZombie(ArrayList<HitboxEntity> entities){
        Zombie zombie = null;
        if(target != null){
            /**
             * if 0-15 = super charged
             * if 16-45 = gurl zombie
             * if 45-100 = regular
             */
            int zombieChance = (int)(Math.random() * (100));
            if(zombieChance >= 0 && zombieChance <= 15){
                zombie = new Zombie(Zombie.SUPER_CHARGED_ZOMBIE);
            }
            else if(zombieChance >= 16 && zombieChance <= 45){
                zombie = new Zombie(Zombie.GURL_ZOMBIE);
            }
            else{
                zombie = new Zombie(Zombie.REGULAR_ZOMBIE);
            }
            zombie.setX(getCenterX());
            zombie.setY(getCenterY());
            zombie.setTarget(target);
            entities.add(zombie);
        }
    }

    public HitboxEntity getTarget() {
        return target;
    }

    public void setTarget(HitboxEntity target) {
        this.target = target;
    }

    public int getRateOfSpawnStart() {
        return rateOfSpawnStart;
    }

    public void setRateOfSpawnStart(int rateOfSpawnStart) {
        this.rateOfSpawnStart = rateOfSpawnStart;
    }

    public int getRateOfSpawnEnd() {
        return rateOfSpawnEnd;
    }

    public void setRateOfSpawnEnd(int rateOfSpawnEnd) {
        this.rateOfSpawnEnd = rateOfSpawnEnd;
    }

    public int getCurrentRandomRateOfSpawn() {
        return currentRandomRateOfSpawn;
    }

    public void setCurrentRandomRateOfSpawn(int currentRandomRateOfSpawn) {
        this.currentRandomRateOfSpawn = currentRandomRateOfSpawn;
    }

    public boolean isCanSpawn() {
        return canSpawn;
    }

    public void setCanSpawn(boolean canSpawn) {
        this.canSpawn = canSpawn;
    }
}
