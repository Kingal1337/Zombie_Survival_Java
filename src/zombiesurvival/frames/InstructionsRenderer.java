package zombiesurvival.frames;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import zombiesurvival.config.Config;

public class InstructionsRenderer implements UpdaterAndRenderAndClick{
    private GraphicButton backButton;
    private String[] instructions;
    
    public InstructionsRenderer(){
        backButton = new GraphicButton(5,5, 75, 35, "Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Config.menu.setCurrent(Config.menu.getStartMenuButtons());
            }
        });
        writeInstructions();
    }
    
    private void writeInstructions(){
        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("Your goal is to destroy all the spawners and zombies");
        
        instructions.add("");
        
        instructions.add("The green circle is the Health Bar");
        instructions.add("The yellow circle is the Flashlight Bar");
        instructions.add("The yellow bar is the Ammo Bar");
        
        instructions.add("");
        instructions.add("Your controls are");
        instructions.add("");
        instructions.add("Mouse 1 - Shoot");
        instructions.add("W - Move Up");
        instructions.add("A - Move Left");
        instructions.add("S - Move Down");
        instructions.add("D - Move Right");
        
        instructions.add("R - Reload");
        instructions.add("F - Toggle FlashLight");
        instructions.add("Q - Change Weapons");
        this.instructions = instructions.toArray(new String[0]);
    }
    
    @Override
    public void render(Graphics2D gd, int width, int height) {
        double scale = (double)width / (double)Config.DEFAULT_SCREEN_SIZE.width;
        
        gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(36.0*scale)));
        gd.setColor(Color.WHITE);
        Config.drawCenteredString("Instructions", 0, 0, (int)(width), (int)(height/3), gd);
        int heightOfInstructionText = Config.getStringBounds(gd, "Instructions", 0, 0).height;
        
        gd.setFont(Config.SIMPLIFICA.deriveFont(1, (int)(14.0*scale)));
                
        Config.drawArrayOfStrings(instructions, 0, (int)(height/3)-heightOfInstructionText, width, height-((int)(height/3)-heightOfInstructionText), gd, Config.CENTER);
        
        gd.setFont(Config.ZOMBIE_FONT.deriveFont(1, (int)(24.0*scale)));
        backButton.render(gd, 0, 0, width, height);
    }

    @Override
    public void update(int tickSpeed, int mouseX, int mouseY, int width, int height) {
        backButton.update(mouseX, mouseY, width, height);
        backButton.setX(0);
    }

    @Override
    public void click(MouseEvent e) {
        if(backButton.contains(e.getX(), e.getY())){
            backButton.action();
        }
    }
}
