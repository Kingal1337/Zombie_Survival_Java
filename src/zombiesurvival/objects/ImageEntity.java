package zombiesurvival.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.ImageIcon;
import zombiesurvival.config.Config;
import zombiesurvival.config.Images;
import zombiesurvival.objects.player.Player;

public class ImageEntity extends HitboxEntity{
    private String[] imageIDs;
    private int currentImage;
    private double imageUpdateSpeed;
    private boolean reverseImages;
    
    private int counter;

    public ImageEntity(String[] image, double x, double y, double width, double height, double speed, double maxSpeed, double angle, boolean passable, String name) {
        super(x, y, width, height, speed, maxSpeed, angle, passable, name);
        this.imageIDs = image;
        imageUpdateSpeed = speed*35;
        reverseImages = false;
    }
    
    public ImageEntity(ImageEntity entity){
        super(entity);
        this.imageIDs = entity.imageIDs;
        this.currentImage = entity.currentImage;
        this.imageUpdateSpeed = entity.imageUpdateSpeed;
        this.reverseImages = entity.reverseImages;
    }
    
    @Override
    public Entity copy(){
        return new ImageEntity(this);
    }

    @Override
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed) {
        super.update(entities, tickSpeed);
        if(imageIDs != null && imageIDs.length != 0){
            counter += tickSpeed;
            if(counter > imageUpdateSpeed){
                counter = 0;
                if(!reverseImages){
                    currentImage++;
                }
                else{
                    currentImage--;
                }
                if(currentImage > imageIDs.length-1){
                    currentImage--;
                    reverseImages = true;
                }
                if(currentImage < 0){
                    currentImage++;
                    reverseImages = false;
                }
            }
        }
    }
    
    @Override
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height){
        Graphics2D gd2 = (Graphics2D)gd.create();
        if(imageIDs != null && imageIDs.length != 0 && currentImage >= 0){
//            if(this instanceof Player){
//                System.out.println("In ImageEntity : "+(offsetX));
//                System.out.println("In ImageEntity : "+(getX()));
//                System.out.println("In ImageEntity : "+(getX()-offsetX));
//            }
            gd2.rotate(getAngle(), (int)((getWidth()/2)+(getX()-offsetX)), (int)((getHeight()/2)+(getY()-offsetY)));
            gd2.drawImage(Images.getImage(imageIDs[currentImage]), (int)(getX()-offsetX), (int)(getY()-offsetY), (int)getWidth(), (int)getHeight(), null);
        }
        super.render(gd, offsetX, offsetY, width, height);        
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.imageIDs);
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
        final ImageEntity other = (ImageEntity) obj;
        if (!Objects.equals(this.imageIDs, other.imageIDs)) {
            return false;
        }
        return true;
    }

    public String[] getImage() {
        return imageIDs;
    }

    public void setImage(String[] imageID) {
        this.imageIDs = imageID;
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(int currentImage) {
        this.currentImage = currentImage;
    }

    public double getImageUpdateSpeed() {
        return imageUpdateSpeed;
    }

    public void setImageUpdateSpeed(double imageUpdateSpeed) {
        this.imageUpdateSpeed = imageUpdateSpeed;
    }
    
    @Override
    public void setSpeed(double speed){
        super.setSpeed(speed);
        imageUpdateSpeed = 50/speed*10;
    }
}
