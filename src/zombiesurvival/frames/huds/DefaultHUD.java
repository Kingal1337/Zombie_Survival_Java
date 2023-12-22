package zombiesurvival.frames.huds;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import zombiesurvival.config.Config;
import zombiesurvival.config.GameStats;
import zombiesurvival.frames.progress_bars.CircleProgressBar;
import zombiesurvival.frames.progress_bars.ProgressBar;
import zombiesurvival.objects.HitboxEntity;
import zombiesurvival.objects.Weapon;
import zombiesurvival.objects.player.Player;

public class DefaultHUD extends HUD{
    private CircleProgressBar healthBar;
    private CircleProgressBar flashLightBar;
    private ProgressBar ammoBar;
    
    private double previousWidth;
    private double previousHeight; 
    
    private int reloadingCounter;
    private int reloadingInterval;
    
    private int flashLightChargingCounter;
    private int flashLightInterval;
    
    private double width;
    private double height;
    public DefaultHUD(String name, GameStats stats) {
        super(name, stats);
        healthBar = new CircleProgressBar(100, 100, 10, height-150, 100, 100);
        healthBar.setProgressColor(Color.RED);
        healthBar.setBackgroundColor(Color.BLACK);
        
        flashLightBar = new CircleProgressBar(100, 100, healthBar.getX()+((healthBar.getWidth()-(healthBar.getWidth()*.8))/2), healthBar.getY()-((healthBar.getHeight()-(healthBar.getHeight()*.8))/2), healthBar.getWidth()*.8, healthBar.getHeight()*.8);
        flashLightBar.setProgressColor(Color.YELLOW);
        flashLightBar.setBackgroundColor(Color.BLACK);
        
        ammoBar = new ProgressBar(100, 100, healthBar.getWidth()+10, height-30, 200, 20);
        ammoBar.setProgressColor(Color.YELLOW);
        ammoBar.setBackgroundColor(Color.BLACK);
        
        previousWidth = 800.0;
        previousHeight = 600.0;
    }

    @Override
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height) {
        double scaleWidth = (double)width / previousWidth;
        double scale = (double)width / (double)Config.DEFAULT_SCREEN_SIZE.width;
        
        Player player = getStats().getPlayer();
        
        previousWidth = width;
        
        gd.setFont(Config.SIMPLIFICA.deriveFont(1, (int)(16.0*scale)));
        gd.setColor(Color.WHITE);
        gd.drawString("FPS : "+getStats().getFps()+" | Spawners Left : "+getStats().getNumberOfSpawner(), 5, (int)(20.0*scale));
        
        String weaponName = "";
        if(player.getWeapons() != null){
            if(player.getWeapons().getCurrentWeapon() != null){
                weaponName = " : "+(player.getWeapons().getCurrentWeaponIndex()+1)+"/"+player.getWeapons().getAllWeapons().size()+" - "+player.getWeapons().getCurrentWeapon().getName();
            }
        }
        gd.drawString("Weapon"+weaponName, (int)ammoBar.getX(), (int)ammoBar.getY()-5);
        
        if(this.height != height || this.width != width){
            this.width = width;
            this.height = height;
            
            healthBar.setWidth(healthBar.getWidth()*scaleWidth);
            flashLightBar.setWidth(healthBar.getWidth()*.77);
            flashLightBar.setX(healthBar.getX()+((healthBar.getWidth()-(flashLightBar.getWidth()))/2));
            
            healthBar.setY(height-10);
            healthBar.setHeight(healthBar.getHeight()*scaleWidth);
            
            flashLightBar.setHeight(healthBar.getHeight()*.77);
            flashLightBar.setY(healthBar.getY()-((healthBar.getHeight()-(flashLightBar.getHeight()))/2));
            
            ammoBar.setWidth(ammoBar.getWidth()*scaleWidth);
            ammoBar.setHeight(ammoBar.getHeight()*scaleWidth);
            ammoBar.setX(healthBar.getWidth()+20);
            ammoBar.setY(height-ammoBar.getHeight()-10);
            
        }
        healthBar.render((Graphics2D)gd.create());
        flashLightBar.render((Graphics2D)gd.create());
        ammoBar.render((Graphics2D)gd.create());
        
        if(!player.getLight().getState() && !player.getLight().isFullyCharged()){
            flashLightChargingCounter += flashLightInterval;
            if(flashLightChargingCounter <= 0){
                flashLightChargingCounter = 0;
                flashLightInterval = 5;
            }
            if(flashLightChargingCounter >= 255){
                flashLightChargingCounter = 255;
                flashLightInterval = -5;
            }
            gd.setColor(new Color(255,255,0,flashLightChargingCounter));
            double newScale = scale/5;
            double x = flashLightBar.getX()+((flashLightBar.getWidth()-(25*newScale))/2);
            double y = flashLightBar.getY()-((flashLightBar.getHeight()+(60*newScale))/2);
            Polygon polygon = getLightningSymbol((int)x, (int)y, newScale);
            gd.fill(polygon);
        }
        
        if(player.getWeapons() != null && player.getWeapons().getCurrentWeapon() != null){
            Weapon currentWeapon = player.getWeapons().getCurrentWeapon();
            if(currentWeapon.isReloading()){
                reloadingCounter += reloadingInterval;
                if(reloadingCounter <= 0){
                    reloadingCounter = 0;
                    reloadingInterval = 5;
                }
                if(reloadingCounter >= 255){
                    reloadingCounter = 255;
                    reloadingInterval = -5;
                }
                gd.setColor(new Color(128, 128, 128, reloadingCounter));
                gd.drawString("Reloading... "+(!currentWeapon.isReloadInBatches()? currentWeapon.secondsLeftUntilReloadFinished() : ""), (int)(ammoBar.getX()+5), (int)(ammoBar.getY()+ammoBar.getHeight()-ammoBar.getHeight()/3));
            }
        }
        
    }
    
    public Polygon getLightningSymbol(int x, int y, double scale){
        int[] xpoints = new int[]{x, x+(int)(34.0*scale), x+(int)(24.0*scale), x+(int)(44*scale), x+(int)(9*scale), x+(int)(16*scale), x-(int)(6*scale)};
        int[] ypoints = new int[]{y, y, y+(int)(30.0*scale), y+(int)(30.0*scale), y+(int)(85.0*scale), y+(int)(40.0*scale), y+(int)(40.0*scale)};
        Polygon polygon = new Polygon(xpoints, ypoints, xpoints.length);
        return polygon;
    }

    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed) {
        Player player = getStats().getPlayer();
        healthBar.setProgressColor(player.getCurrentHeathColor(player.getHealth(), player.getMaxHealth()));
        healthBar.setCurrentValue(player.getHealth());
        healthBar.setMaxValue(player.getMaxHealth());
        flashLightBar.setCurrentValue(player.getLight().getCurrentPower());
        flashLightBar.setMaxValue(player.getLight().getMaxPower());
        
        
        if(player.getWeapons() != null && player.getWeapons().getCurrentWeapon() != null){
            ammoBar.setCurrentValue(player.getWeapons().getCurrentWeapon().getBullets());
            ammoBar.setMaxValue(player.getWeapons().getCurrentWeapon().getMaxBullets());
        }
        else{
            ammoBar.setCurrentValue(0);
            ammoBar.setMaxValue(0);
        }
    }
    
}
