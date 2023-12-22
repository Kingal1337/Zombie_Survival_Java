package zombiesurvival.objects.interfaces;

import java.util.ArrayList;
import zombiesurvival.objects.HitboxEntity;

public interface Updater {
    public void update(ArrayList<HitboxEntity> entities, int tickSpeed);
}
