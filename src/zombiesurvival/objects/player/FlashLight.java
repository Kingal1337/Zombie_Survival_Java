package zombiesurvival.objects.player;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import zombiesurvival.objects.HitboxEntity;
import zombiesurvival.objects.interfaces.Renderer;
import zombiesurvival.objects.interfaces.Updater;

public class FlashLight implements Renderer, Updater{
    private HitboxEntity owner;
    private long maxPower;//in seconds
    private long currentPower;//in seconds
    private long speedPowerReducesAndIncreases;//int milliseconds - speed power drains
    private long powerToDeductAndIncrease;
    private int darkness;
    
    private int counter;
    
    private boolean state;
    
    private static boolean disableLighting = false;
    public FlashLight(HitboxEntity owner, long currentPower, long maxPower, long speedPowerReducesAndIncreases, long powerToDeductAndIncrease, int darkness){
        this.owner = owner;
        this.currentPower = currentPower;
        this.maxPower = maxPower;
        this.speedPowerReducesAndIncreases = speedPowerReducesAndIncreases;
        this.powerToDeductAndIncrease = powerToDeductAndIncrease;
        this.darkness = darkness > 255 ? 255 : darkness < 0 ? 0 : darkness;
        state = true;
    }
    
    public FlashLight(FlashLight light){
        this.owner = light.owner;
        this.maxPower = light.maxPower;
        this.speedPowerReducesAndIncreases = light.speedPowerReducesAndIncreases;
        this.powerToDeductAndIncrease = light.powerToDeductAndIncrease;
        this.state = light.state;
        this.darkness = light.darkness;
    }
    
    public FlashLight copy(){
        return new FlashLight(this);
    }

    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed) {
        counter+=tickSpeed;
        if(state){
            if(counter >= speedPowerReducesAndIncreases){
                counter = 0;
                currentPower -= powerToDeductAndIncrease;
                if(currentPower < 0){
                    currentPower = 0;
                }
            }
        }
        if(!state){
            if(counter >= speedPowerReducesAndIncreases){
                counter = 0;
                currentPower += powerToDeductAndIncrease;
                if(currentPower > maxPower){
                    currentPower = maxPower;
                }
            }
        }
        if(currentPower <= 0){
            state = false;
        }
    }
    
    @Override
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height) {
        if(!disableLighting){
            BufferedImage shadow = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);

            drawLights(shadow, offsetX, offsetY, width, height);

            gd.drawImage(shadow, 0, 0, null);
        }
    }
    
    private void drawLights(BufferedImage shadow, double offsetX, double offsetY, int width, int height) {
        Graphics2D gd = shadow.createGraphics();
        gd.setComposite(AlphaComposite.Src);
        gd.setColor(new Color(0, 0, 16, (state && currentPower != 0) ? darkness : darkness-2));
        gd.fillRect(0, 0, width, height);
        
        if(state && currentPower != 0){  
            drawLight((Graphics2D)gd.create(), offsetX, offsetY, width, height);
        }
        
        gd.dispose();
    }
    
    private void drawLight(Graphics2D gd, double offsetX, double offsetY, int width, int height) {
        double radius = (Math.min(1920, 1080) * 1.3)/2;
        
        double centerX = getOwner().getCenterX()-offsetX;
        double centerY = getOwner().getCenterY()-offsetY;
        
        RadialGradientPaint rgp = new RadialGradientPaint(
                        new Point((int)(centerX), (int)(centerY)),
                        (float)radius*2,
                        new float[]{0.0f, 1.0f},
                        new Color[]{new Color(255, 255, 255, 200), new Color(0, 0, 0, 0)}
                        );
        gd.setComposite(AlphaComposite.DstOut);
        gd.setPaint(rgp);
        gd.fill(new Arc2D.Float((int)(centerX-radius), (int)(centerY-radius), (float)(radius*2), (float)(radius*2), (float)(Math.toDegrees(-owner.getAngle())-(75/2)), 75, Arc2D.PIE));
        gd.dispose();
    }
    
    public boolean isFullyCharged(){
        return currentPower >= maxPower;
    }
    
    public HitboxEntity getOwner() {
        return owner;
    }

    public void setOwner(HitboxEntity owner) {
        this.owner = owner;
    }

    public long getMaxPower() {
        return maxPower;
    }

    public long getSpeedPowerReducesAndIncreases() {
        return speedPowerReducesAndIncreases;
    }

    public void setSpeedPowerReducesAndIncreases(long speedPowerReducesAndIncreases) {
        this.speedPowerReducesAndIncreases = speedPowerReducesAndIncreases;
    }

    public long getPowerToDeductAndIncrease() {
        return powerToDeductAndIncrease;
    }

    public void setPowerToDeductAndIncrease(long powerToDeductAndIncrease) {
        this.powerToDeductAndIncrease = powerToDeductAndIncrease;
    }

    public void setMaxPower(long maxPower) {
        this.maxPower = maxPower;
    }

    public long getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(long currentPower) {
        this.currentPower = currentPower;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        counter = 0;
    }

    public int getDarkness() {
        return darkness;
    }

    public void setDarkness(int darkness) {
        this.darkness = darkness > 255 ? 255 : darkness < 0 ? 0 : darkness;
    }
}
