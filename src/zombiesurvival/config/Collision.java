package zombiesurvival.config;

import java.util.ArrayList;
import zombiesurvival.objects.HitboxEntity;

public class Collision {
    
    public static boolean intersects(HitboxEntity r1,HitboxEntity r2){
        boolean isTouching = false;
        if((r1.getX()<r2.getX()+r2.getWidth() && r1.getX()+r1.getWidth()>r2.getX()) && (r1.getY()<r2.getY()+r2.getHeight() && r1.getY()+r1.getHeight()>r2.getY())){
            isTouching = true;
        }
        return isTouching;
    }
    
    public static boolean intersects(HitboxEntity r1, ArrayList<HitboxEntity> entitys){
        boolean isTouching = false;
        for (HitboxEntity entity : entitys) {
            isTouching = intersects(r1, entity);
            if(isTouching){
               break; 
            }
        }
        return isTouching;
    } 
    
    public static boolean intersects(
            double r1x,double r1y, double r1w, double r1h, 
            double r2x, double r2y, double r2w, double r2h){
        boolean isTouching = false;
        if((r1x<r2x+r2w && r1x+r1w>r2x) && (r1y<r2y+r2h && r1y+r1h>r2y)){
            isTouching = true;
        }
        return isTouching;
    }
    
    /**
     * Will move the CollisionBox and do collision
     * @param direction  the direction the entity is going in
     * 1 = down; 
     * 2 = right; 
     * 3 = up; 
     * 4 = left;
     * 
     * @param r1  CollisionBox 1
     * @param rr2  all entitys that need to be checked
     * @param speed  the speed that entity 1 is going in
     * @return  returns true if <code>r1</code> is colliding with another CollisionBox
     */
    public static boolean move(int direction, HitboxEntity r1, ArrayList<? extends HitboxEntity> rr2, double speed){
        boolean alreadyColliding = false;
//        if(!rr2.isEmpty()){
//            if(rr2.get(0) instanceof Entity){
                HitboxEntity rectangle = null;
                for(int i=0;i<rr2.size();i++){
                    if(alreadyColliding){
                        break;
                    }
                    if(!r1.equals(rr2.get(i)) && !r1.isPassable() && !rr2.get(i).isPassable()){
                        HitboxEntity currentBox = rr2.get(i);
                        if(direction == 3){
                            if(willCollide(r1,currentBox,3,speed)){ 
                                alreadyColliding = true;
                                rectangle = currentBox;
                            }
                        }
                        if(direction == 1){
                            if(willCollide(r1,currentBox,1,speed)){ 
                                alreadyColliding = true;
                                rectangle = currentBox;
                            }
                        }
                        if(direction == 4){
                            if(willCollide(r1,currentBox,4,speed)){
                                alreadyColliding = true;
                                rectangle = currentBox;
                            }
                        }
                        if(direction == 2){
                            if(willCollide(r1,currentBox,2,speed)){  
                                alreadyColliding = true;
                                rectangle = currentBox;
                            }
                        }
                    }
                }

                if(direction == 3){
                    if(rectangle != null){
                        if(willCollide(r1,rectangle,3,speed)){ 
                            double something = rectangle.getY()+rectangle.getHeight()-r1.getY();
                            if(something <= 0){
                                r1.setY(r1.getY()+something);
                            }
                        }
                        else{
                            r1.setY(r1.getY()-speed);
                        }
                    }
                    else{
                        r1.setY(r1.getY()-speed);
                    }
                }

                if(direction == 1){
                    if(rectangle != null){
                        if(willCollide(r1,rectangle,1,speed)){ 
                            double something = r1.getY()+r1.getHeight()-rectangle.getY();
                            if(something <= 0){
                                r1.setY(r1.getY()-something);
                            }     
                        }
                        else{       
                            r1.setY(r1.getY()+speed);
                        }
                    }
                    else{       
                        r1.setY(r1.getY()+speed);
                    }
                }

                if(direction == 4){
                    if(rectangle != null){
                        if(willCollide(r1,rectangle,4,speed)){
                            double something = rectangle.getX()+rectangle.getWidth()-r1.getX();
                            if(something <= 0){
                                r1.setX(r1.getX()+something);
                            }
                        }
                        else{
                            r1.setX(r1.getX()-speed);
                        }
                    }
                    else{
                        r1.setX(r1.getX()-speed);
                    }
                }

                if(direction == 2){
                    if(rectangle != null){
                        if(willCollide(r1,rectangle,2,speed)){  
                            double something = r1.getX()+r1.getWidth()-rectangle.getX();
                            if(something <= 0){
                                r1.setX(r1.getX()-something);
                            }
                        }
                            else{
                                r1.setX(r1.getX()+speed);
                            }
                    }
                    else{
                        r1.setX(r1.getX()+speed);
                    }
                }
//            }
//        }
        return alreadyColliding;
    }
    public static boolean willCollide(HitboxEntity r1, HitboxEntity r2, int direction, double speed) {
        if(!r2.isPassable()){
            int trueHorizontalDirection = getDirectionHorizontal(r1, r2);
            int trueVerticalDirection = getDirectionVertical(r1, r2);
            if(direction == 1 && trueVerticalDirection == 1){
                if((r1.getX()+r1.getWidth() > r2.getX()) && (r1.getX() < r2.getX()+r2.getWidth())){
                    if(r1.getY()+r1.getHeight()+speed > r2.getY()){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            else if(direction == 2 && trueHorizontalDirection == 2){
                if((r1.getY()+r1.getHeight() > r2.getY()) && (r1.getY() < r2.getY()+r2.getHeight())){
                    if(r1.getX()+r1.getWidth()+speed > r2.getX()){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            else if(direction == 3 && trueVerticalDirection == 3){
                if((r1.getX()+r1.getWidth() > r2.getX()) && (r1.getX() < r2.getX()+r2.getWidth())){
                    if((r1.getY()-speed) < (r2.getY()+r2.getHeight())){
                        return true;
                    }
                    else{
                        return false;
                    }  
                }
                else{
                    return false;
                }
            }
            else if(direction == 4 && trueHorizontalDirection == 4){
                if((r1.getY()+r1.getHeight() > r2.getY()) && (r1.getY() < r2.getY()+r2.getHeight())){
                    if(r1.getX()-speed < r2.getX()+r2.getWidth()){
                        return true;
                    }
                    else{
                        return false;
                    }       
                }
                else{
                    return false;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }
    
    private static int getDirectionHorizontal(HitboxEntity rect1,HitboxEntity rect2){
        double rect1CenterX = rect1.getCenterX();
        double rect2CenterX = rect2.getCenterX();
        if(rect1CenterX < rect2CenterX){
            return 2;
        }
        if(rect1CenterX > rect2CenterX){
            return 4;
        }
        
        else{
            return 0;
        }
    }
    
    private static int getDirectionVertical(HitboxEntity rect1,HitboxEntity rect2){
        double rect1CenterY = rect1.getCenterY();
        double rect2CenterY = rect2.getCenterY();
        if(rect1CenterY < rect2CenterY){
            return 1;
        }
        if(rect1CenterY > rect2CenterY){
            return 3;
        }
        
        else{
            return 0;
        }
    }
}
