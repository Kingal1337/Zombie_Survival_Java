package zombiesurvival.frames;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    private JPanel currentPanel;
    public MainFrame(){
        currentPanel = null;
    }
    
    public MainFrame(JPanel panel){
        currentPanel = panel;
    }
    
    public void changePanel(JPanel panel){
        if(currentPanel != null){
            remove(currentPanel);
            revalidate();
            repaint();
        }
        currentPanel = panel;
        add(currentPanel);
            currentPanel.requestFocusInWindow();
        revalidate();
        repaint();
    }

    public JPanel getCurrentPanel() {
        return currentPanel;
    }
}