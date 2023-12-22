/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiesurvival.test;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Alan
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Zombie Survival");
        TestPanel panel = new TestPanel();
        panel.setPreferredSize(new Dimension(800,600));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
