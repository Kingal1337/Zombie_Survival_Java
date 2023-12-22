package zombiesurvival.frames;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import zombiesurvival.config.Config;
import zombiesurvival.config.Map;
import zombiesurvival.objects.HitboxEntity;
import zombiesurvival.objects.WeaponSack;
import zombiesurvival.objects.player.FlashLight;
import zombiesurvival.objects.player.Player;
import zombiesurvival.objects.weapons.Pistol;
import zombiesurvival.objects.weapons.Shotgun;
import zombiesurvival.objects.zombies.ZombieSpawner;

public class MainStartMenuRenderer implements UpdaterAndRenderAndClick{
    private ArrayList<GraphicButton> buttons;
    
    public MainStartMenuRenderer(){
        initButtons(0,0);
    }
    
    private void initButtons(int width, int height){
        buttons = new ArrayList<>();
        
        GraphicButton startButton = new GraphicButton((width-100)/2, ((height-100)/2)-55, 100, 50, "Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.gc();
                WeaponSack weapon = new WeaponSack();
                weapon.addWeapon(new Pistol(null, "Pistol", 60, 8, 8, 80, 5, true, 1));
                weapon.addWeapon(new Shotgun(null, "Shotgun", 20, 24, 24, 200, 125, true, 2, 12, 45));
                weapon.addWeapon(new Pistol(null,"The Water Hose" , 2, 2000, 2000, 0, 16, true, 1));
                weapon.addWeapon(new Pistol(null,"AK-47" , 50, 50, 50, 80, 1000, false, 1));
//                weapon.addWeapon(new Shotgun(null, "Flower", 5000, 50, 50, 250, 125, true, 2, 200, 360));

                FlashLight light = new FlashLight(null, 180, 180, 500, 1, 228);//243

                Player entity = new Player(light, weapon, true, 100, 100, 1, 1, new String[]{"player"}, 50, 50, 50, 50, 6, 6, 0, false, "Player");

                ArrayList<HitboxEntity> entities = new ArrayList<>();

                ZombieSpawner spawner = new ZombieSpawner(5000, 20000, true, 1000, 1000, 1, 1, new String[]{"spawner"}, 0, 0, 100, 100, 0, 0, 0, true, "Spawner");
                spawner.setTarget(entity);
                
                int arenaWidth = 5000;
                int arenaHeight = 5000;
                
                entities.addAll(Map.generateMap(arenaWidth, arenaHeight, 15, spawner));
                
                if(Config.mainPanel == null){
                    Config.mainPanel = new MainPanel(entity, entities, Config.controls, arenaWidth, arenaHeight);
                }
                else{
                    Config.mainPanel.reset(entity, entities, Config.controls, arenaWidth, arenaHeight);
                }
                Config.frame.changePanel(Config.mainPanel);
                System.gc();
            }
        });
        
        GraphicButton instructionsButton = new GraphicButton((width-150)/2, (height-100)/2, 160, 50, "Instructions");
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config.menu.setCurrent(Config.menu.getInstructions());
            }
        });
        
        GraphicButton quitButton = new GraphicButton((width-100)/2, ((height-100)/2)+55, 100, 50, "Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        buttons.add(startButton);
        buttons.add(instructionsButton);
        buttons.add(quitButton);
        
    }
    
    private void repositionButtons(int width, int height){
        int sizeOfAllButtonsTogether = (int)(buttons.size()*buttons.get(0).getHeight());
        int startingY = (height-sizeOfAllButtonsTogether)/2;
        for(int i=0;i<buttons.size();i++){
            buttons.get(i).setX((width-buttons.get(i).getWidth())/2);
            buttons.get(i).setY(startingY);
            startingY+=buttons.get(i).getHeight();
        }
    }

    @Override
    public void update(int tickSpeed, int mouseX, int mouseY, int width, int height) {
        for(int i=0;i<buttons.size();i++){
            buttons.get(i).update(mouseX, mouseY, width, height);
        }
        repositionButtons(width, height);
    }

    @Override
    public void render(Graphics2D gd, int width, int height) {     
        double scale = (double)width / (double)Config.DEFAULT_SCREEN_SIZE.width;
        
        gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(36.0*scale)));
        gd.setColor(Color.WHITE);
        Config.drawCenteredString(Config.TITLE, 0, 0, (int)(width), (int)(height/3), gd);
        
        
        gd.setColor(Color.WHITE);
        gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(24.0*scale)));
        gd.drawString(Config.CREATOR, 5, height);    
        
        for(int i=0;i<buttons.size();i++){
            buttons.get(i).render(gd, 0, 0, width, height);
        }   
    }

    @Override
    public void click(MouseEvent e) {
        for(int i=0;i<buttons.size();i++){
            if(buttons.get(i).contains(e.getX(), e.getY())){
                buttons.get(i).action();
                break;
            }
        }
    }
}
