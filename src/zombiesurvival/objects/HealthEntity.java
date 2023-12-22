package zombiesurvival.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class HealthEntity extends ImageEntity{
    private static ArrayList<Color> healthBarColors;
    private static boolean initialized;
    
    private boolean damagable;
    
    private int healthOffset;
    
    public double health;
    public double maxHealth;
    public double armor;
    public double maxArmor;

    public HealthEntity(boolean damagable, double health, double maxHealth, double armor, double maxArmor, String[] imageIDs, double x, double y, double width, double height, double speed, double maxSpeed, double angle, boolean passable, String name) {
        super(imageIDs, x, y, width, height, speed, maxSpeed, angle, passable, name);
        this.damagable = damagable;
        this.health = health;
        this.maxHealth = maxHealth;
        this.armor = armor < 0 ? 0 : armor > 1 ? 1 : armor;
        this.maxArmor = maxArmor;
        healthOffset = 15;
        initColors();
    }
    
    public HealthEntity(HealthEntity entity){
        super(entity);
        this.damagable = entity.damagable;
        this.healthOffset = entity.healthOffset;
        this.health = entity.health;
        this.maxHealth = entity.maxHealth;
        this.armor = entity.armor;
        this.maxArmor = entity.maxArmor;
    }
    
    @Override
    public Entity copy(){
        return new HealthEntity(this);
    }
        
    
    private static void initColors(){
        if(!initialized){
            initialized = true;
            healthBarColors = new ArrayList<>();
            int red = 255;
            int green = 0;
            int stepSize = 15;
            while(green < 255){
                green += stepSize;
                if(green > 255) { 
                    green = 255; 
                }
                healthBarColors.add(new Color(red, green, 0));
            }
            while(red > 0){
                red -= stepSize;
                if(red < 0) { 
                    red = 0; 
                }
                healthBarColors.add(new Color(red, green, 0));
            }
        }
    }
    
    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed){
        super.update(entities, tickSpeed);
        if(damagable){
            if(isDead()){
                setRemove(true);
            }
        }
    }

    @Override
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height) {
        if(damagable){
            int healthOffSet = (int) (getY()-this.healthOffset);
            double maxSize = getWidth();
            int heightOfBar = 3;
            double healthRatio = health/maxHealth;

            double hpBarSize = healthRatio*maxSize;
            
            Color prevColor = gd.getColor();
            gd.setColor(getCurrentHeathColor(health, maxHealth));
            gd.fillRect((int)(getX()-offsetX), (int)(healthOffSet-offsetY), (int)hpBarSize, (int)heightOfBar);
            gd.setColor(prevColor);
        }
        super.render(gd, offsetX, offsetY, width, height);
    }
    
    public Color getCurrentHeathColor(double currentHealth, double maxHealth){
        int arraySize = healthBarColors.size();
        int index = (int)(arraySize/maxHealth*currentHealth);
        index = index >= arraySize ? arraySize-1 : index < 0 ? 0 : index;
        return healthBarColors.get(index);
    }
    
    public boolean isDead(){
        return health <= 0;
    }
    
    public void replenishArmor(double armorToReplenish){
        armor += armorToReplenish;
        if(armor > maxArmor){
            armor = maxArmor;
        }
    }
    
    public void replenishHealth(double healthToReplenish){
        health += healthToReplenish;
        if(health > maxHealth){
            health = maxHealth;
        }
    }
    
    public void takeDamage(double damage){
        if(damagable){
            double randomNumber = (int)(Math.random() * ((int)(armor*100.0)) + 1);
            double damageToTake = randomNumber/100.0;
            health -= damage-(damage*damageToTake);
            if(health < 0){
                health = 0;
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.health) ^ (Double.doubleToLongBits(this.health) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.maxHealth) ^ (Double.doubleToLongBits(this.maxHealth) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.armor) ^ (Double.doubleToLongBits(this.armor) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.maxArmor) ^ (Double.doubleToLongBits(this.maxArmor) >>> 32));
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
        final HealthEntity other = (HealthEntity) obj;
        if (Double.doubleToLongBits(this.health) != Double.doubleToLongBits(other.health)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maxHealth) != Double.doubleToLongBits(other.maxHealth)) {
            return false;
        }
        if (Double.doubleToLongBits(this.armor) != Double.doubleToLongBits(other.armor)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maxArmor) != Double.doubleToLongBits(other.maxArmor)) {
            return false;
        }
        return true;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getMaxArmor() {
        return maxArmor;
    }

    public void setMaxArmor(double maxArmor) {
        this.maxArmor = maxArmor;
    }

    public boolean isDamagable() {
        return damagable;
    }

    public void setDamagable(boolean damagable) {
        this.damagable = damagable;
    }

    public int getHealthOffset() {
        return healthOffset;
    }

    public void setHealthOffset(int healthOffset) {
        this.healthOffset = healthOffset;
    }
}
