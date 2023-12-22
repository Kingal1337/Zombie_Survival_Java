package zombiesurvival.frames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StartMenu extends JPanel implements ActionListener, MouseListener, MouseMotionListener{    
    private int mouseX;
    private int mouseY;
    
    private Timer timer;
    
    private StartMenuBackground background;
    private MainStartMenuRenderer startMenuButtons;
    private InstructionsRenderer instructions;
    
    private UpdaterAndRenderAndClick current;
    
    private static int TICK_SPEED = 16;
    public StartMenu(){        
        background = new StartMenuBackground();
        startMenuButtons = new MainStartMenuRenderer();
        instructions = new InstructionsRenderer();
        
        current = startMenuButtons;
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        timer = new Timer(20, this);
        timer.start();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        
        gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gd.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        background.render(gd, getWidth(), getHeight());
        
        current.render(gd, getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        background.update(TICK_SPEED, mouseX, mouseY, getWidth(), getHeight());
        current.update(TICK_SPEED, mouseX, mouseY, getWidth(), getHeight());
        repaint();
    }
    
    
    public void start(){
        timer.start();
    }
    
    public void stop(){
        timer.stop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(current instanceof StartMenuClick){
            current.click(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public StartMenuBackground getStartMenuBackground() {
        return background;
    }

    public void setStartMenuBackground(StartMenuBackground background) {
        this.background = background;
    }

    public MainStartMenuRenderer getStartMenuButtons() {
        return startMenuButtons;
    }

    public void setStartMenuButtons(MainStartMenuRenderer startMenuButtons) {
        this.startMenuButtons = startMenuButtons;
    }

    public InstructionsRenderer getInstructions() {
        return instructions;
    }

    public void setInstructions(InstructionsRenderer instructions) {
        this.instructions = instructions;
    }

    public UpdaterAndRenderAndClick getCurrent() {
        return current;
    }

    public void setCurrent(UpdaterAndRenderAndClick current) {
        this.current = current;
    }
}
