package zombiesurvival.frames.progress_bars;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

public class CircleProgressBar extends ProgressBar{
    private double currentValue;
    private double maxValue;
    
    private Arc2D.Double progressBar;
    private Ellipse2D.Double arcCover;
        
    public CircleProgressBar(double currentValue, double maxValue, double x, double y, double width, double height){
        super(currentValue, maxValue, x, y, width, height);
        
        progressBar = new Arc2D.Double(x, y, width, height, 0, -currentValue*(360/maxValue), Arc2D.PIE);
        arcCover = new Ellipse2D.Double(x+((width-(width*.8))/2), y+((height-(height*.8))/2), width*.8, height*.8);
    }
    
    @Override
    public void render(Graphics2D gd){
        gd.rotate(Math.toRadians(270), getX(), getY());
        gd.setColor(getProgressColor());
        gd.fill(progressBar);
        gd.setColor(getBackgroundColor());
        gd.fill(arcCover);
    }
    
    @Override
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
        progressBar.setAngleExtent(-currentValue*(360/maxValue));
    }
    
    @Override
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
        progressBar.setAngleExtent(-currentValue*(360/maxValue));
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        progressBar.x = x;
        arcCover.x = progressBar.x+((getWidth()-(getWidth()*.8))/2);
    }
    
    @Override
    public void setY(double y) {
        super.setY(y);
        progressBar.y = y;
        arcCover.y = progressBar.y+((getHeight()-(getHeight()*.8))/2);
    }
    
    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        progressBar.width = width;
        arcCover.width = width*.8;
        arcCover.x = progressBar.x+((width-(arcCover.width))/2);
    }
    
    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        progressBar.height = height;
        arcCover.height = height*.8;
        arcCover.y = progressBar.y+((height-(arcCover.height))/2);
    }
}
