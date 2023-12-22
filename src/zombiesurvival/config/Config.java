package zombiesurvival.config;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import zombiesurvival.frames.MainFrame;
import zombiesurvival.frames.MainPanel;
import zombiesurvival.frames.StartMenu;

public class Config {
    public static MainFrame frame;
    
    public static StartMenu menu;
    
    public static MainPanel mainPanel;
    
    public static Controls controls = new Controls();
    
    public static final String TITLE = "Zombie Survival";
    
    public static final String CREATOR = "";
    
    public static final Dimension DEFAULT_SCREEN_SIZE = new Dimension(800,600);
    
    public static final Dimension LARGEST_SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    
    public static final int DEFAULT_TICK_SPEED = 16;
    
    public static Font SIMPLIFICA;
    
    public static Font ZOMBIE_FONT;
    
    public static final int LEFT = 0;
    
    public static final int CENTER = 1;
    
    public static final int RIGHT = 2;
    
    public static void initFonts(){
        try {
            SIMPLIFICA = Font.createFont(Font.TRUETYPE_FONT, Config.class.getResourceAsStream("/zombiesurvival/res/fonts/simplifica.ttf"));
            ZOMBIE_FONT = Font.createFont(Font.TRUETYPE_FONT, Config.class.getResourceAsStream("/zombiesurvival/res/fonts/zombie.ttf"));
        } catch (FontFormatException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void drawCenteredString(String string,int x,int y, int width, int height, Graphics2D gd) {
        Point point = getStringPoint(string, x, y, width, height, gd);
        gd.drawString(string, point.x, point.y);
    }
    
    public static void drawCenteredLeftString(String string,int x,int y, int width, int height, Graphics2D gd) {
        Point point = getStringPoint(string, x, y, width, height, gd);
        gd.drawString(string, x, point.y);
    }
    
    public static void drawCenteredRightString(String string,int x,int y, int width, int height, Graphics2D gd) {
        Point point = getStringPoint(string, x, y, width, height, gd);
        int lengthInPixels = getLengthOfString(string, gd);
        gd.drawString(string, width-lengthInPixels, point.y);
    }
    
    private static Point getStringPoint(String string,int x,int y, int width, int height, Graphics2D gd) {
        FontMetrics fm = gd.getFontMetrics();
        int newX = ((width - fm.stringWidth(string)) / 2) + x;
        int newY = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2) + y;
        return new Point(newX,newY);
    }
    
    public static int getLengthOfString(String string, Graphics2D gd) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.getGraphics().getFontMetrics(gd.getFont());
        int width = fm.stringWidth(string);
        return width;
    }
    
    public static Rectangle getStringBounds(Graphics2D gd, String string, float x, float y){
        FontRenderContext frc = gd.getFontRenderContext();
        GlyphVector gv = gd.getFont().createGlyphVector(frc, string);
        return gv.getPixelBounds(null, x, y);
    }
    
    public static int getHeightOfStrings(String[] strings, Font font){
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        Graphics2D gd = (Graphics2D)g;
        gd.setFont(font);
        
        int height = 0;
        for(int i=0;i<strings.length;i++){
            height += getStringBounds(gd,strings[i],0,0).height;
        }
        return height;
    }
    
    public static void drawArrayOfStrings(String[] strings, int x, int y, int width, int height, Graphics2D gd, int align){
        int currentX = x;
        int currentY = y;
        int allHeights = getHeightOfStrings(strings, gd.getFont());
        if(allHeights > height){
            return;
        }
        int stringHeight = getStringBounds(gd, strings[0], 0, 0).height;
        for(String s : strings){
            if(align == LEFT){
                drawCenteredLeftString(s, currentX, currentY, width, stringHeight, gd);
            }
            if(align == CENTER){
                drawCenteredString(s, currentX, currentY, width, stringHeight, gd);
            }
            if(align == RIGHT){
                drawCenteredRightString(s, currentX, currentY, width, stringHeight, gd);
            }
            currentY+=stringHeight;
        }
    }
}
