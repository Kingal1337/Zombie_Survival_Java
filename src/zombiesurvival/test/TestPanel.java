/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiesurvival.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Alan
 */
public class TestPanel extends JPanel implements ActionListener{
    public static void main(String[] args) {
        Main.main(args);
    }
    private Timer timer;
    private ProgressBarTest progress;
    
    public TestPanel(){
        progress = new ProgressBarTest();
        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        progress.render(gd, 400, 400, 100, 100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
