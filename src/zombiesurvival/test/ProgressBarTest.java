/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiesurvival.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Alan
 */
public class ProgressBarTest{
    public static void main(String[] args) {
        Main.main(args);
    }
    private int max;
    private int min;
    
    private int current;
    
    public ProgressBarTest(){
        
    }
    
    public void render(Graphics2D gd, int x, int y, int width, int height){
        current++;
        gd.rotate(Math.toRadians(270), x, y);
        Arc2D.Double arc = new Arc2D.Double(x, y, width, height, 0, -current*(360/100), Arc2D.PIE);
        Ellipse2D.Double circle = new Ellipse2D.Double(x+((width-(width*.8))/2), y+((height-(height*.8))/2), width*.8, height*.8);
//        arc.set
//        arc.setFrameFromCenter(0, 0, 120, 120);
gd.setColor(Color.BLACK);
        gd.fill(arc);
gd.setColor(Color.WHITE);
        gd.fill(circle);
    }
}
