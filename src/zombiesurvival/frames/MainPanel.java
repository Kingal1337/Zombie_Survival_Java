package zombiesurvival.frames;

import zombiesurvival.frames.huds.DefaultHUD;
import zombiesurvival.frames.huds.HUD;
import zombiesurvival.objects.player.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import zombiesurvival.config.Collision;
import zombiesurvival.config.Config;
import zombiesurvival.config.Controls;
import zombiesurvival.config.GameStats;
import zombiesurvival.config.Images;
import zombiesurvival.objects.HitboxEntity;
import zombiesurvival.objects.ImageEntity;
import zombiesurvival.objects.zombies.Zombie;
import zombiesurvival.objects.zombies.ZombieSpawner;

public class MainPanel extends JPanel implements ActionListener, MouseListener, KeyListener, MouseMotionListener{
    private Timer timer;
    private Player player;
    private ArrayList<HitboxEntity> entities;
    
    private double playerOffsetX;
    private double playerOffsetY;
    
    private static int TICK_SPEED = Config.DEFAULT_TICK_SPEED;
    
    private HUD hud;
    private Controls controls;
    private ImageEntity grass;
    private GameStats stats;
    
    private int mouseX;
    private int mouseY;
    
    private boolean paused;
    
    private boolean gameOver;
    
    private boolean youWin;
    
    private GraphicButton resume;
    private GraphicButton quit;
    
    private long lastTimeRecorded;
    private double fps;
    private double fakeFps;
    private int fpsCounter;
    
    private int arenaWidth;
    private int arenaHeight;
    
    public MainPanel(Player player, ArrayList<HitboxEntity> entities, Controls controls, int arenaWidth, int arenaHeight){
        this.player = player;
        this.entities = entities;
        this.controls = controls;
        this.stats = new GameStats(0, 0, 0, player);
        
        hud = new DefaultHUD("HUD", stats);
        
        setBackground(Color.BLACK);
        
        Images.putOrReplace("big_grass", Images.createRowOfImages(Images.getImage("grass"), arenaWidth, arenaHeight));
        grass = new ImageEntity(new String[]{"big_grass"}, 50, 50, arenaWidth, arenaHeight, 0, 0, 0, true, "Grass");
        
        this.entities.add(this.player);
        
        timer = new Timer(TICK_SPEED, this);
        
        initPausedButtons();
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        
        timer.start();
    }
    
    public void reset(Player player, ArrayList<HitboxEntity> entities, Controls controls, int arenaWidth, int arenaHeight){
        this.player = player;
        this.entities = entities;
        this.controls = controls;
        this.stats.setFps(0);
        this.stats.setNumberOfSpawner(0);
        this.stats.setNumberOfZombies(0);
        this.stats.setPlayer(player);
        
        this.paused = false;
        this.gameOver = false;
        this.youWin = false;
        
        if(this.arenaWidth != arenaWidth || this.arenaHeight != arenaHeight){
            Images.putOrReplace("big_grass", Images.createRowOfImages(Images.getImage("grass"), arenaWidth, arenaHeight));
            grass = new ImageEntity(new String[]{"big_grass"}, 50, 50, arenaWidth, arenaHeight, 0, 0, 0, true, "Grass");
        }
        
        this.entities.add(this.player);
        timer.start();
    }
    
    public void quit(){
        Config.frame.changePanel(Config.menu);
        timer.stop();
    }
    
    private void initPausedButtons(){
        resume = new GraphicButton(0, 0, 100, 30, "Resume");
        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paused = !paused;
            }
        });
        
        quit = new GraphicButton(0, 0, 100, 30, "Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        fpsCounter+=timer.getDelay();
        if(!paused && !gameOver && !youWin){
            for(int i=entities.size()-1;i>=0;i--){
                if(entities.get(i) != null){
                    if(entities.get(i).isRemove()){
                        if(!(entities.get(i) instanceof Player)){
                            entities.remove(i);
                        }
                    }
                }
            }

            int zombieCount = 0;
            int spawnerCount = 0;

            for(int i=0;i<entities.size();i++){
                if(entities.get(i) != null){
                    if(entities.get(i) instanceof Zombie){
                        zombieCount++;
                    }
                    else if(entities.get(i) instanceof ZombieSpawner){
                        spawnerCount++;
                    }
                    entities.get(i).update(entities, TICK_SPEED);
                }
            }
            stats.setNumberOfZombies(zombieCount);
            stats.setNumberOfSpawner(spawnerCount);
            
            if(stats.getNumberOfSpawner() == 0 && stats.getNumberOfZombies() == 0){
                youWin = true;
            }            
            else if(player.isDead()){
                gameOver = true;
            }

            hud.update(entities, TICK_SPEED);
        }
        else{
            resume.update(mouseX, mouseY, getWidth(), getHeight());
            quit.update(mouseX, mouseY, getWidth(), getHeight());
            repositionPauseButtons();
        }
        repaint();
        fakeFps = 1000000000.0 / (System.nanoTime() - lastTimeRecorded);
        lastTimeRecorded = System.nanoTime();
        if(fpsCounter >= 200){
            fpsCounter = 0;
            fps = fakeFps;
        }
        stats.setFps((int)fps);
    }
    
    @Override
    public void paintComponent(Graphics g){        
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        
        double scale = (double)getWidth() / (double)Config.DEFAULT_SCREEN_SIZE.width;
        
        gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gd.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        playerOffsetX = (((HitboxEntity)player).getCenterX()-((double)(getSize().width)/2.0));
        playerOffsetY = (((HitboxEntity)player).getCenterY()-((double)(getSize().height)/2.0));
//        System.out.println("Mainpanel : Player offset x : "+playerOffsetX);
        
        grass.render(gd, playerOffsetX, playerOffsetY, getWidth(), getHeight());
                    
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) != null){
                if(entities.get(i) != player){
                    if(Collision.intersects(
                            0, 0, getWidth(), getHeight(), 
                            entities.get(i).getX()-playerOffsetX, entities.get(i).getY()-playerOffsetY, entities.get(i).getWidth(), entities.get(i).getHeight())){
                        entities.get(i).render(gd, playerOffsetX, playerOffsetY, getWidth(), getHeight());
                    }
                }
            }
        }
        
        player.render(gd, playerOffsetX, playerOffsetY, getWidth(), getHeight());
        hud.render(gd, playerOffsetX, playerOffsetY, getWidth(), getHeight());
        
        if(gameOver){
            gd.setColor(new Color(230,68,68));
            gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(50.0*scale)));
            Config.drawCenteredString("Game Over", 0, 0, getWidth(), getHeight(), gd);
            
            gd.setColor(Color.WHITE);            
            gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(16.0*scale)));
            Config.drawCenteredString("Press ESC to exit", 0, getHeight()/4, getWidth(), getHeight(), gd);
        }
        
        if(youWin){
            gd.setColor(Color.decode("#34BD34"));
            gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(50.0*scale)));
            Config.drawCenteredString("You Win!", 0, 0, getWidth(), getHeight(), gd);
            
            gd.setColor(Color.WHITE);            
            gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(16.0*scale)));
            Config.drawCenteredString("Press ESC to exit", 0, getHeight()/4, getWidth(), getHeight(), gd);
        }
        
        if(paused){
            gd.setColor(Color.WHITE);
            gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(36.0*scale)));
            Config.drawCenteredString("Paused", 0, 0, getWidth(), getHeight()/2, gd);
            
            gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(20*scale)));
            resume.render(gd, 0, 0, getWidth(), getHeight());
            quit.render(gd, 0, 0, getWidth(), getHeight());
            
        }
    }
    
    private void repositionPauseButtons(){
        resume.setX((getWidth()-resume.getWidth())/2);
        quit.setX((getWidth()-quit.getWidth())/2);
        
        int heightOfButtons = (int)(resume.getHeight()+quit.getHeight());
        int startingY = (getHeight()-heightOfButtons)/2;
        resume.setY(startingY);
        startingY += resume.getHeight() + (8*((double)getWidth() / (double)Config.DEFAULT_SCREEN_SIZE.width));
        quit.setY(startingY);
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        int button = me.getButton();
        if(button == controls.up){
            player.setUp(true);
        }
        if(button == controls.down){
            player.setDown(true);
        }
        if(button == controls.left){
            player.setLeft(true);
        }
        if(button == controls.right){
            player.setRight(true);
        }
        if(button == controls.shoot){
            player.setShooting(true);
        }
        if(button == controls.reload){
            player.reloadCurrentWeapon();
        }
        if(button == controls.flashLight){
            player.getLight().setState(!player.getLight().getState());
        }
        if(button == controls.changeWeapon){
            if(player.getWeapons() != null){
                player.getWeapons().incrementCurrentWeapon();
            }
        }

        if(paused){
            if(resume.contains(mouseX, mouseY)){
                resume.action();
            }

            if(quit.contains(mouseX, mouseY)){
                quit.action();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        int button = me.getButton();
        if(button == controls.up){
            player.setUp(false);
        }
        if(button == controls.down){
            player.setDown(false);
        }
        if(button == controls.left){
            player.setLeft(false);
        }
        if(button == controls.right){
            player.setRight(false);
        }
        if(button == controls.shoot){
            player.setShooting(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
            
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keycode = ke.getKeyCode();
        if(keycode == controls.up){
            player.setUp(true);
        }
        if(keycode == controls.down){
            player.setDown(true);
        }
        if(keycode == controls.left){
            player.setLeft(true);
        }
        if(keycode == controls.right){
            player.setRight(true);
        }
        if(keycode == controls.shoot){
            player.setShooting(true);
        }
        if(keycode == controls.reload){
            player.reloadCurrentWeapon();
        }
        if(keycode == controls.flashLight){
            player.getLight().setState(!player.getLight().getState());
        }
        if(keycode == controls.changeWeapon){
            if(player.getWeapons() != null){
                player.getWeapons().incrementCurrentWeapon();
            }
        }
        if(keycode == KeyEvent.VK_ESCAPE){
            if(youWin || gameOver){
                quit();
            }
                
            paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keycode = ke.getKeyCode();
        if(keycode == controls.up){
            player.setUp(false);
        }
        if(keycode == controls.down){
            player.setDown(false);
        }
        if(keycode == controls.left){
            player.setLeft(false);
        }
        if(keycode == controls.right){
            player.setRight(false);
        }
        if(keycode == controls.shoot){
            player.setShooting(false);
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
        if(!paused && !gameOver && !youWin){
            player.setAngle(Math.atan2(mouseY - player.getCenterY()+playerOffsetY, mouseX - player.getCenterX()+playerOffsetX));
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
        if(!paused && !gameOver && !youWin){
            player.setAngle(Math.atan2(mouseY - player.getCenterY()+playerOffsetY, mouseX - player.getCenterX()+playerOffsetX));
        }
    }
}
