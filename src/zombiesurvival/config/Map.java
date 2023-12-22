package zombiesurvival.config;

import java.util.ArrayList;
import zombiesurvival.objects.HitboxEntity;
import zombiesurvival.objects.ImageEntity;
import zombiesurvival.objects.zombies.ZombieSpawner;

public class Map {
    public static ArrayList<HitboxEntity> generateMap(
            int mapWidth, 
            int mapHeight, 
            int numberOfSpawners, 
            ZombieSpawner defaultGraveYard){
        ArrayList<HitboxEntity> entities = new ArrayList<>();
        createWalls(entities, mapWidth, mapHeight);
        if(numberOfSpawners > 0 && defaultGraveYard != null){
            generateSpawners(
                    entities, 
                    numberOfSpawners, 
                    defaultGraveYard, 
                    mapWidth, 
                    mapHeight);
        }
        return entities;
    }
    
    private static void createWalls(
            ArrayList<HitboxEntity> walls, 
            int mapWidth, 
            int mapHeight){
        int width = mapWidth+100;
        int height = mapHeight+100;
        
        Images.putOrReplace(
                "horizontalWall", 
                Images.createRowOfImages(
                        Images.getImage("wall"), 
                        width, 
                        50));
        Images.putOrReplace(
                "verticalWall", 
                Images.createRowOfImages(
                        Images.getImage("wall"), 
                        50, 
                        height));
        
        ImageEntity leftWall = new ImageEntity(
                new String[]{"verticalWall"}, 
                0, 0, 50, height, 0, 0, 0, false, "Left Wall");
        ImageEntity topWall = new ImageEntity(
                new String[]{"horizontalWall"}, 
                50, 0, width-100, 50, 0, 0, 0, false, "Top Wall");
        ImageEntity rightWall = new ImageEntity(
                new String[]{"verticalWall"}, 
                width-50, 0, 50, height, 0, 0, 0, false, "Right Wall");
        ImageEntity bottomWall = new ImageEntity(
                new String[]{"horizontalWall"}, 
                50, height-50, width-100, 50, 0, 0, 0, false, "Bottom Wall");
        
        walls.add(leftWall);
        walls.add(topWall);
        walls.add(rightWall);
        walls.add(bottomWall);
    }
    
    private static void generateSpawners(
            ArrayList<HitboxEntity> entities, 
            int numberOfSpawners, ZombieSpawner 
                    defaultGraveYard, 
            int width, 
                    int height){
        ArrayList<HitboxEntity> graves = new ArrayList<>();
        boolean hasCollided = false;
        for(int i=0;i<numberOfSpawners;i++){
            int randomX = 50 + (int)(Math.random() * 
                    ((width - defaultGraveYard.getWidth() - 50) + 1));
            int randomY = 50 + (int)(Math.random() * 
                    ((height - defaultGraveYard.getHeight() - 50) + 1));
            ZombieSpawner yard = new ZombieSpawner(defaultGraveYard);
            yard.setX(randomX);
            yard.setY(randomY);
            yard.setCanSpawn(true);
            for(int j=0;j<graves.size();){
                if(Collision.intersects(yard, graves.get(j))){
                    hasCollided = true;
                    break;
                }
                else{
                    j++;
                }
            }
            if(!hasCollided){
                graves.add(yard);
            }
        }
        entities.addAll(graves);
    }
}
