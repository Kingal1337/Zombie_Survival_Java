package zombiesurvival.frames.huds;

import zombiesurvival.config.GameStats;
import zombiesurvival.objects.interfaces.Renderer;
import zombiesurvival.objects.interfaces.Updater;

public abstract class HUD implements Renderer, Updater{
    private String name;
    private GameStats stats;
    
    public HUD(String name, GameStats stats){
        this.name = name;
        this.stats = stats;
    }

    public GameStats getStats() {
        return stats;
    }

    public void setStats(GameStats stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
