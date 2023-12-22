package zombiesurvival.config;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Images {
    private static HashMap<String, BufferedImage> images = new HashMap<>();
    public static void initImages(){
        try {
            images.put("player", ImageIO.read(Images.class.getResource("/zombiesurvival/res/StickMan.png")));
        
            images.put("reg_zom_1", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/Zombie.png")));
            images.put("reg_zom_2", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/ZombieWalk1.png")));
            images.put("reg_zom_3", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/ZombieWalk2.png")));

            images.put("super_zom_1", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/SuperChargedZombie.png")));
            images.put("super_zom_2", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/SuperChargedZombieWalk1.png")));
            images.put("super_zom_3", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/SuperChargedZombieWalk2.png")));

            images.put("girl_zom_1", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/GurlZombie.png")));
            images.put("girl_zom_2", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/GurlZombieWalk1.png")));
            images.put("girl_zom_3", ImageIO.read(Images.class.getResource("/zombiesurvival/res/zombies/GurlZombieWalk2.png")));

            images.put("grass", ImageIO.read(Images.class.getResource("/zombiesurvival/res/blocks/grass.png")));
            
            images.put("wall", ImageIO.read(Images.class.getResource("/zombiesurvival/res/blocks/stonebrick.png")));
            
            images.put("spawner", ImageIO.read(Images.class.getResource("/zombiesurvival/res/blocks/SpawningPortal.png")));
            
        } catch (IOException ex) {
            Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void putOrReplace(String key, BufferedImage image){
        if(images.containsKey(key)){
            images.replace(key, image);
        }
        else{
            putImage(key, image);
        }
    }
    
    public static void putImage(String key, BufferedImage image){
        images.put(key, image);
    }
    
    public static BufferedImage getImage(String imageID){
        return images.get(imageID);
    }
    
    public static BufferedImage createRowOfImages(BufferedImage baseImage, int width, int height){
        System.out.println(width+" "+height);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gd = image.createGraphics();
        int excessWidth = width%baseImage.getWidth();
        int excessHeight = height%baseImage.getHeight();
        
        boolean hasExcessWidth = excessWidth > 0;
        boolean hasExcessHeight = excessHeight > 0;
        
        int intervalX = width/baseImage.getWidth()+(hasExcessWidth ? 1 : 0);
        int intervalY = height/baseImage.getHeight()+(hasExcessHeight ? 1 : 0);
        
        int currentX = 0;
        int currentY = 0;
        
        for(int i=0;i<intervalY;i++){
            for(int j=0;j<intervalX;j++){
                gd.drawImage(baseImage, currentX, currentY, null);
                if(hasExcessWidth){
                    if(j == intervalX-1){
                        gd.drawImage(baseImage.getSubimage(0, 0, excessWidth, baseImage.getHeight()), currentX, currentY, null);
                    }
                }
                currentX += baseImage.getWidth();
            }
            if(hasExcessHeight){
                if(i == intervalY-1){
                    gd.drawImage(baseImage.getSubimage(0, 0, baseImage.getWidth(), excessHeight), currentX, currentY, null);
                }
            }
            currentX = 0;
            currentY += baseImage.getHeight();
        }
        gd.dispose();
        return image;
    }
}
