package zombiesurvival.objects;

public abstract class Weapon {
    private WielderEntity owner;
    
    private String name;
    
    private int canShootCounter;
    private boolean canShoot;
    
    private boolean reloadInBatches;
    private double amountToReloadBatch;
    
    private int reloadingSecondsCounter;
    private int reloadingCounter;
    private boolean isReloading;    
    private int secondsLeftInReloading;

    private double damage;
    private int bullets;    
    private int maxBullets;
    private int firingSpeed;//in milliseconds
    private int reloadSpeed;//in milliseconds
    public Weapon(WielderEntity owner, String name, double damage, int bullets, int maxBullets, int firingSpeed, int reloadSpeed, boolean reloadInBatches, int amountToReloadBatch){
        this.name = name;
        this.owner = owner;
        this.damage = damage;
        this.bullets = bullets;
        this.maxBullets = maxBullets;
        this.firingSpeed = firingSpeed;
        this.reloadSpeed = reloadSpeed;
        this.reloadInBatches = reloadInBatches;
        this.amountToReloadBatch = amountToReloadBatch;
        secondsLeftInReloading = reloadSpeed/1000;
        
        canShoot = true;
    }
    
    public Weapon(Weapon weapon){
        this.owner = weapon.owner;
        
        this.canShoot = weapon.canShoot;
        
        this.isReloading = weapon.isReloading;
        
        this.damage = weapon.damage;
        this.bullets = weapon.bullets;
        this.maxBullets = weapon.maxBullets;
        this.firingSpeed = weapon.firingSpeed;
        this.reloadSpeed = weapon.reloadSpeed;
    }
    
    public void reloadInstantly(){
        this.bullets = maxBullets;
    }
    
    public abstract Weapon copy();
    
    public void update(int tickSpeed){
        if(!canShoot){
            canShootCounter += tickSpeed;
            if(canShootCounter >= firingSpeed){
                canShootCounter = 0;
                canShoot = true;
            }
        }
        if(isReloading){
            if(bullets != maxBullets){
                reloadingSecondsCounter += tickSpeed;
                reloadingCounter += tickSpeed;
                if(reloadingSecondsCounter >= 1000){
                    reloadingSecondsCounter = 0;
                    secondsLeftInReloading--;
                }
                if(reloadingCounter >= reloadSpeed){
                    secondsLeftInReloading = reloadSpeed/1000;
                    reloadingCounter = 0;
                    if(reloadInBatches){
                        bullets+=amountToReloadBatch;
                        if(bullets >= maxBullets){
                            bullets = maxBullets;
                            isReloading = false;
                        }
                    }
                    else{
                        bullets = maxBullets;
                        isReloading = false;
                    }
                }
            }
            else{
                isReloading = false;
            }            
        }
    }
    
    public Bullet[] preShoot(double x, double y, double angle){
        if(!isReloading){
            if(canShoot){
                bullets--;
                if(bullets <= 0){
                    bullets = 0;
                }
                else if(bullets > 0){
                    canShoot = false;
                    return shoot(x, y, angle);
                }
            }
            
        }
        else{
            if(bullets != 0){
                reloadingCounter = 0;
                isReloading = false;
                secondsLeftInReloading = reloadSpeed/1000;
            }
        }
        
        return null;
    }
    
    public abstract Bullet[] shoot(double x, double y, double angle);
    
    public int secondsLeftUntilReloadFinished(){
        return secondsLeftInReloading;
    }

    public WielderEntity getOwner() {
        return owner;
    }

    public void setOwner(WielderEntity owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public void setIsReloading(boolean isReloading) {
        this.isReloading = isReloading;
    }

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    public int getMaxBullets() {
        return maxBullets;
    }

    public void setMaxBullets(int maxBullets) {
        this.maxBullets = maxBullets;
    }

    public int getFiringSpeed() {
        return firingSpeed;
    }

    public void setFiringSpeed(int firingSpeed) {
        this.firingSpeed = firingSpeed;
    }

    public int getReloadSpeed() {
        return reloadSpeed;
    }

    public void setReloadSpeed(int reloadSpeed) {
        this.reloadSpeed = reloadSpeed;
    }

    public boolean isReloadInBatches() {
        return reloadInBatches;
    }

    public void setReloadInBatches(boolean reloadInBatches) {
        this.reloadInBatches = reloadInBatches;
    }

    public double getAmountToReloadBatch() {
        return amountToReloadBatch;
    }

    public void setAmountToReloadBatch(double amountToReloadBatch) {
        this.amountToReloadBatch = amountToReloadBatch;
    }
}
