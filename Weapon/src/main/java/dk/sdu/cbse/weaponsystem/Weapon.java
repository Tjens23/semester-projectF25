package dk.sdu.cbse.weaponsystem;

public class Weapon implements IWeapon {
    private int ammo;
    private String name;
    private int damage;


    public Weapon(String name, int ammo, int damage){
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

    public boolean isEmpty() {
        return ammo <= 0;
    }

    public boolean canFire() {
        return ammo > 0;
    }
    private float cooldown = 0.5f; // seconds
    private float timeSinceLastShot = 0;

    public boolean canShoot(float deltaTime) {
        timeSinceLastShot += deltaTime;
        if (timeSinceLastShot >= cooldown && ammo > 0) {
            timeSinceLastShot = 0;
            return true;
        }
        return false;
    }
}

