package zombiesurvival.objects.weapons;

import zombiesurvival.objects.Bullet;
import zombiesurvival.objects.Weapon;
import zombiesurvival.objects.WielderEntity;

public class Pistol extends Weapon{

    public Pistol(WielderEntity owner, String name, double damage, int bullets, int maxBullets, int firingSpeed, int reloadSpeed, boolean reloadInBatches, int amountToReloadBatch){
        super(owner, name, damage, bullets, maxBullets, firingSpeed, reloadSpeed, reloadInBatches, amountToReloadBatch);
    }
    
    public Pistol(Pistol pistol){
        super(pistol);
    }

    @Override
    public Bullet[] shoot(double x, double y, double angle) {
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(getOwner(), getDamage(), false, 1, 1, 1, 1, null, x, y, 15, 3, 15, 5, angle, true, "bullet");
        return bullets;
    }

    @Override
    public Weapon copy() {
        return new Pistol(this);
    }
    
}
