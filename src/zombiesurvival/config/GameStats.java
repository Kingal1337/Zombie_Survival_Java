package zombiesurvival.config;

import java.util.Objects;
import zombiesurvival.objects.player.Player;

public class GameStats {
    private double fps;
    private int numberOfSpawner;
    private int numberOfZombies;
    private Player player;

    public GameStats(int numberOfSpawner, int numberOfZombies, double fps, Player player) {
        this.numberOfSpawner = numberOfSpawner;
        this.numberOfZombies = numberOfZombies;
        this.player = player;
        this.fps = fps;
    }

    public int getNumberOfSpawner() {
        return numberOfSpawner;
    }

    public void setNumberOfSpawner(int numberOfSpawner) {
        this.numberOfSpawner = numberOfSpawner;
    }

    public int getNumberOfZombies() {
        return numberOfZombies;
    }

    public void setNumberOfZombies(int numberOfZombies) {
        this.numberOfZombies = numberOfZombies;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.fps) ^ (Double.doubleToLongBits(this.fps) >>> 32));
        hash = 53 * hash + this.numberOfSpawner;
        hash = 53 * hash + this.numberOfZombies;
        hash = 53 * hash + Objects.hashCode(this.player);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameStats other = (GameStats) obj;
        if (Double.doubleToLongBits(this.fps) != Double.doubleToLongBits(other.fps)) {
            return false;
        }
        if (this.numberOfSpawner != other.numberOfSpawner) {
            return false;
        }
        if (this.numberOfZombies != other.numberOfZombies) {
            return false;
        }
        if (!Objects.equals(this.player, other.player)) {
            return false;
        }
        return true;
    }
    
}
