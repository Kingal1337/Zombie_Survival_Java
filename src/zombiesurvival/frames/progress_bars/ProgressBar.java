package zombiesurvival.frames.progress_bars;

import java.awt.Color;
import java.awt.Graphics2D;

public class ProgressBar {
    private double currentValue;
    private double maxValue;
    
    private Color progressColor;
    private Color backgroundColor;
    
    private double x;
    private double y;
    private double width;
    private double height;

    public ProgressBar(double currentValue, double maxValue, double x, double y, double width, double height) {
        this.currentValue = currentValue;
        this.maxValue = maxValue;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        this.progressColor = Color.PINK;
        this.backgroundColor = Color.WHITE;
    }
    
    public void render(Graphics2D gd){
        gd.setColor(backgroundColor);
        gd.fillRect((int)x, (int)y, (int)width, (int)height);
        gd.setColor(progressColor);
        gd.fillRect((int)x, (int)y, (int)((currentValue/maxValue)*width), (int)height);
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public Color getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(Color progressColor) {
        this.progressColor = progressColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
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
}
