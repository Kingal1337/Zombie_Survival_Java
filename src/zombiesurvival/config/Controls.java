package zombiesurvival.config;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Controls {
    public static final int UP = KeyEvent.VK_W;
    public static final int DOWN = KeyEvent.VK_S;
    public static final int LEFT = KeyEvent.VK_A;
    public static final int RIGHT = KeyEvent.VK_D;
    
    public static final int RELOAD = KeyEvent.VK_R;
    public static final int CHANGE_WEAPON = KeyEvent.VK_Q;
    public static final int FLASH_LIGHT = KeyEvent.VK_F;
    public static final int SHOOT = MouseEvent.BUTTON1;
    
    public int up;
    public int down;
    public int left;
    public int right;
    
    public int reload;
    public int changeWeapon;
    public int flashLight;
    public int shoot;
    
    public Controls(){
        resetToDefault();
    }

    public Controls(int up, int down, int left, int right, int reload, int changeWeapon, int flashLight, int shoot) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.reload = reload;
        this.changeWeapon = changeWeapon;
        this.flashLight = flashLight;
        this.shoot = shoot;
    }
    
    public void resetToDefault(){
        up = UP;
        down = DOWN;
        left = LEFT;
        right = RIGHT;
        
        reload = RELOAD;
        changeWeapon = CHANGE_WEAPON;
        flashLight = FLASH_LIGHT;
        shoot = SHOOT;
    }
}
