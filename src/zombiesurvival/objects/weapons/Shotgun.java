package zombiesurvival.objects.weapons;

import zombiesurvival.objects.Bullet;
import zombiesurvival.objects.Weapon;
import zombiesurvival.objects.WielderEntity;

public class Shotgun extends Weapon{
    private int amountOfBulletsToShoot;
    private double angleRange;//the arc of where bullets shoot (in degrees)
    public Shotgun(WielderEntity owner, String name, double damage, int bullets, int maxBullets, int firingSpeed, int reloadSpeed, boolean reloadInBatches, int amountToReloadBatch, int amountOfBulletsToShoot, double angleRange) {
        super(owner, name, damage, bullets, maxBullets, firingSpeed, reloadSpeed, reloadInBatches, amountToReloadBatch);
        this.amountOfBulletsToShoot = amountOfBulletsToShoot;
        this.angleRange = angleRange;
    }
    
    public Shotgun(Shotgun gun){
        super(gun);
        this.amountOfBulletsToShoot = gun.amountOfBulletsToShoot;
        this.angleRange = gun.angleRange;
    }

    @Override
    public Weapon copy() {
        return new Shotgun(this);
    }

    @Override
    public Bullet[] shoot(double x, double y, double angle) {
        Bullet[] bullets = new Bullet[amountOfBulletsToShoot];
        for(int i=0;i<bullets.length;i++){
            double randomAngle = Math.toRadians(-(angleRange/2) + (int)(Math.random() * ((angleRange/2 - (-(angleRange/2))) + 1)))+angle;
            bullets[i] = new Bullet(getOwner(), getDamage(), false, 0, 0, 0, 0, null, x, y, 7, 7, 10,10, randomAngle, true, "Shotgun_Bullet");
        }
        return bullets;
    }
    
}
