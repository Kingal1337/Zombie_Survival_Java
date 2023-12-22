package zombiesurvival.objects;

import zombiesurvival.objects.interfaces.Copyable;
import zombiesurvival.objects.interfaces.Renderer;
import zombiesurvival.objects.interfaces.Updater;

public abstract class Entity implements Updater, Renderer, Copyable{
    private String name;
    private boolean remove;
    public Entity(String name){
        this.name = name;
    }
    
    public Entity(Entity entity){
        this.name = entity.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entity other = (Entity) obj;
        return true;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
    
    @Override
    public String toString(){
        return getClass().getName()+" : "+name;
    }
}
