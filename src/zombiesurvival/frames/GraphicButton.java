package zombiesurvival.frames;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import zombiesurvival.config.Collision;
import zombiesurvival.config.Config;
import zombiesurvival.objects.interfaces.Renderer;

public class GraphicButton implements Renderer{
    private double x;
    private double y;
    private double width;
    private double height;
    private String text;
    
    private Color hoveringColor;
    private Color idleColor;
    private Color textColor;//will be ignored if fillbackground is true
    
    private Color currentColor;
    
    private boolean fillBackground;//if false only text will be colored
    
    private ArrayList<ActionListener> actionListeners;
    
    private int previousWidth;
    private int previousHeight;

    public GraphicButton(double x, double y, double width, double height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        
        hoveringColor = Color.GRAY;
        idleColor = Color.WHITE;
        textColor = Color.BLACK;
        
        currentColor = idleColor;
        actionListeners = new ArrayList<>();
        
        previousWidth = Config.DEFAULT_SCREEN_SIZE.width;
        previousHeight = Config.DEFAULT_SCREEN_SIZE.height;
    }
    
    public void action(){
        for(int i=0;i<actionListeners.size();i++){
            actionListeners.get(i).actionPerformed(new ActionEvent(this, 0, "Graphic Button"));
        }
    }
    
    public void update(int mouseX, int mouseY, int screenWidth, int screenHeight){
        if(contains(mouseX, mouseY)){
            currentColor = hoveringColor;
        }
        else{
            currentColor = idleColor;
        }
    }
    
    @Override
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height) {
        if(previousWidth != width || previousHeight != height){
            if(previousWidth != 0 && previousHeight != 0){
                double scaleWidth = (double)width/(double)previousWidth;
                double scaleHeight = (double)height/(double)previousHeight;
            
                this.width *= scaleWidth;
                this.height *= scaleHeight;
            }
            
            previousWidth = width;
            previousHeight = height;
        }
        
        gd.setColor(currentColor);
        if(fillBackground){
            gd.fillRect((int)x, (int)y, (int)this.width, (int)this.height);
            gd.setColor(textColor);
        }
//            gd.drawRect((int)x, (int)y, (int)this.width, (int)this.height);
        Config.drawCenteredString(text, (int)x, (int)y, (int)this.width, (int)this.height, gd);
    }
    
    public boolean contains(int x, int y){
        return Collision.intersects(this.x, this.y, width, height, x, y, 1, 1);
    }
    
    public void addActionListener(ActionListener a){
        actionListeners.add(a);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getHoveringColor() {
        return hoveringColor;
    }

    public void setHoveringColor(Color hoveringColor) {
        this.hoveringColor = hoveringColor;
    }

    public Color getIdleColor() {
        return idleColor;
    }

    public void setIdleColor(Color idleColor) {
        this.idleColor = idleColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public boolean isFillBackground() {
        return fillBackground;
    }

    public void setFillBackground(boolean fillBackground) {
        this.fillBackground = fillBackground;
    }
    
}
