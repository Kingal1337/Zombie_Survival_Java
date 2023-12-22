package zombiesurvival.objects.interfaces;

import java.awt.Graphics2D;

public interface Renderer {
    public void render(Graphics2D gd, double offsetX, double offsetY, int width, int height);
}
