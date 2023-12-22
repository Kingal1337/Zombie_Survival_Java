package zombiesurvival.objects;

import java.util.ArrayList;

public class WeaponSack{
    private ArrayList<Weapon> weapons;
    private int currentWeapon;
    public WeaponSack(ArrayList<Weapon> weapons){
        this.weapons = weapons != null ? weapons : new ArrayList<>();
    }
    
    public WeaponSack(Weapon... weapons){
        ArrayList<Weapon> weaponsList = new ArrayList<>();
        for(int i=0;i<weapons.length;i++){
            weaponsList.add(weapons[i]);
        }
        this.weapons = weaponsList;
    }
    
    public WeaponSack(WeaponSack sack){
        this.weapons = new ArrayList<>();
        for(int i=0;i<sack.weapons.size();i++){
            weapons.add(sack.weapons.get(i).copy());
        }
    }
    
    public WeaponSack copy(){
        return new WeaponSack(this);
    }
    
    
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }
    
    public void incrementCurrentWeapon(){
        if(!weapons.isEmpty()){
            currentWeapon++;
            if(currentWeapon >= weapons.size()){
                currentWeapon = 0;
            }
            setCurrentWeapon(currentWeapon);
        }
    }
    
    public void decrementCurrentWeapon(){
        if(!weapons.isEmpty()){
            currentWeapon--;
            if(currentWeapon < 0){
                currentWeapon = weapons.size()-1;
            }
            setCurrentWeapon(currentWeapon);
        }
    }
    
    public void setCurrentWeapon(int index){
        if(!weapons.isEmpty() && (index >= 0 && index < weapons.size())){
            currentWeapon = index;
        }
        else{
            currentWeapon = -1;
        }
    }
    
    public Weapon getCurrentWeapon(){
        return getWeapon(currentWeapon);
    }
    
    public Weapon getWeapon(int index){
        if(!weapons.isEmpty() && (index >= 0 && index < weapons.size())){
            return weapons.get(index);
        }
        return null;
    }
    
    public ArrayList<Weapon> getAllWeapons(){
        return weapons;
    }
    
    public int getCurrentWeaponIndex(){
        return currentWeapon;
    }
}
