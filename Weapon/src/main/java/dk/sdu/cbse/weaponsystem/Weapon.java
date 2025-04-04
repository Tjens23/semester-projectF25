package dk.sdu.cbse.weaponsystem;

public class Weapon implements IWeapon {
    private int ammo;
    private String name;
    private int damage;

    public Weapon(){
        this.ammo=ammo;
        this.name=name;
        this.damage=damage;
    }

    public void attack(){
        if (ammo>0){
            //shoot gun
            ammo--;
        }
        else {
            System.out.println("no ammo");
        }
    }
    public void reload(int bullets){
        ammo+=bullets;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public int getAmmo() {
        return ammo;
    }
}

