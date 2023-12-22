package zombiesurvival.frames;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import zombiesurvival.config.Config;
import zombiesurvival.config.Images;
import zombiesurvival.objects.HitboxEntity;
import zombiesurvival.objects.MovingEntity;
import zombiesurvival.objects.player.FlashLight;
import zombiesurvival.objects.player.Player;
import zombiesurvival.objects.zombies.Zombie;

public class StartMenuBackground implements UpdaterAndRenderAndClick{
    private BufferedImage grassBackground;
    private Player dummy;
    private ArrayList<HitboxEntity> dummyEntities;
    
    private double currentAngle;
    
    private int offset = 800;
    
    public StartMenuBackground(){
        init();
    }
    
    public void init(){
        grassBackground = Images.createRowOfImages(Images.getImage("grass"), Config.LARGEST_SCREEN_SIZE.width, Config.LARGEST_SCREEN_SIZE.height);
        initEntitys();
        newLocation(Config.LARGEST_SCREEN_SIZE.width, Config.LARGEST_SCREEN_SIZE.height);
    }
    
    private void initEntitys(){
        FlashLight light = new FlashLight(null, 180, 180, 1000, 0, 200);
        dummy = new Player(light, null, false, 100, 100, 100, 100, new String[]{"player"}, -100, -100, 50, 50, 12, 8, currentAngle, true, "Dummy Player");
        
        dummyEntities = new ArrayList<>();
        
        Zombie zombie = new Zombie(Zombie.REGULAR_ZOMBIE);
        zombie.setTarget(dummy);
        zombie.setSpeed(12);
        zombie.setPassable(true);
        zombie.setDamagable(false);
        dummyEntities.add(zombie);
        
        zombie = new Zombie(Zombie.REGULAR_ZOMBIE);
        zombie.setTarget(dummy);
        zombie.setSpeed(12);
        zombie.setPassable(true);
        zombie.setDamagable(false);
        dummyEntities.add(zombie);
        
        zombie = new Zombie(Zombie.REGULAR_ZOMBIE);
        zombie.setTarget(dummy);
        zombie.setSpeed(12);
        zombie.setPassable(true);
        zombie.setDamagable(false);
        dummyEntities.add(zombie);
        
        zombie = new Zombie(Zombie.REGULAR_ZOMBIE);
        zombie.setTarget(dummy);
        zombie.setSpeed(12);
        zombie.setPassable(true);
        zombie.setDamagable(false);
        dummyEntities.add(zombie);
        
        zombie = new Zombie(Zombie.REGULAR_ZOMBIE);
        zombie.setTarget(dummy);
        zombie.setSpeed(12);
        zombie.setPassable(true);
        zombie.setDamagable(false);
        dummyEntities.add(zombie);
    }
    
    @Override
    public void update(int tickSpeed, int mouseX, int mouseY, int width, int height){
        for(int i=0;i<dummyEntities.size();i++){
            dummyEntities.get(i).update(dummyEntities, tickSpeed);
        }
        dummy.update(dummyEntities, tickSpeed);
        if(dummy.getX() < -(offset+50)){
            stopMovement();
            newLocation(width, height);
        }
        if(dummy.getX()+dummy.getWidth() > width+(offset+50)){
            stopMovement();
            newLocation(width, height);
        }
        if(dummy.getY() < -(offset+50)){
            stopMovement();
            newLocation(width, height);
        }
        if(dummy.getY()+dummy.getHeight() > height+(offset+50)){
            stopMovement();
            newLocation(width, height);
        }
    }
    
    @Override
    public void render(Graphics2D gd, int width, int height){
        
        
        gd.drawImage(grassBackground, 0, 0, null);
        for(int i=0;i<dummyEntities.size();i++){
            if(!(dummyEntities.get(i) instanceof Player)){
                dummyEntities.get(i).render(gd, 0, 0, width, height);
            }
        }
        dummy.render(gd, 0, 0, width, height);
    }
    
    private void stopMovement(){
        for(int i=0;i<dummyEntities.size();i++){
            if(dummyEntities.get(i) instanceof MovingEntity){
                ((MovingEntity)(dummyEntities.get(i))).stop();
            }
        }
        dummy.stop();
    }
    
    private void newLocation(int width, int height){
        int[] possibleX = new int[]{-offset, width/3, (width/3)*2, width+offset};
        int[] possibleY1 = new int[]{-offset, height+offset};
        int[] possibleY2 = new int[]{height/3, (height/3)*2};
        int x = possibleX[(int)(Math.random() * 4)];
        int y = 0;
        int distanceBack = 150;
        if(x == possibleX[0] || x == possibleX[3]){
            y = possibleY2[(int)(Math.random() * 2)];
            if(x == possibleX[0]){
                dummy.setAngle(0);
                dummy.setRight(true);
                
                for(int i=0;i<dummyEntities.size();i++){
                    if(dummyEntities.get(i) instanceof MovingEntity){
                        MovingEntity entity = (MovingEntity)(dummyEntities.get(i));
                        entity.setAngle(Math.toRadians(180));
                        entity.setRight(true);
                        entity.setY(y);
                        entity.setX(x-distanceBack);
                        distanceBack += entity.getWidth();
                    }
                }
            }
            else{
                dummy.setAngle(Math.toRadians(180));
                dummy.setLeft(true);
                
                for(int i=0;i<dummyEntities.size();i++){
                    if(dummyEntities.get(i) instanceof MovingEntity){
                        MovingEntity entity = (MovingEntity)(dummyEntities.get(i));
                        entity.setAngle(Math.toRadians(180));
                        entity.setLeft(true);
                        entity.setY(y);
                        entity.setX(x+distanceBack);
                        distanceBack += entity.getWidth();
                    }
                }
            }
        }
        else if(x == possibleX[1] || x == possibleX[2]){
            int number = (int)(Math.random() * 2);
            y = possibleY1[number];
            if(y == possibleY1[0]){
                dummy.setAngle(Math.toRadians(90));
                dummy.setDown(true);
                
                for(int i=0;i<dummyEntities.size();i++){
                    if(dummyEntities.get(i) instanceof MovingEntity){
                        MovingEntity entity = (MovingEntity)(dummyEntities.get(i));
                        entity.setAngle(Math.toRadians(90));
                        entity.setDown(true);
                        entity.setX(x);
                        entity.setY(y-distanceBack);
                        distanceBack += entity.getHeight();
                    }
                }
            }
            else{
                dummy.setAngle(Math.toRadians(270));
                dummy.setUp(true);
                
                for(int i=0;i<dummyEntities.size();i++){
                    if(dummyEntities.get(i) instanceof MovingEntity){
                        MovingEntity entity = (MovingEntity)(dummyEntities.get(i));
                        entity.setAngle(Math.toRadians(270));
                        entity.setUp(true);
                        entity.setX(x);
                        entity.setY(y+distanceBack);
                        distanceBack += entity.getHeight();
                    }
                }
            }
        }
        dummy.setX(x);
        dummy.setY(y);
    }

    @Override
    public void click(MouseEvent e) {
        
    }
}
