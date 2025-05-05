package dk.sdu.cbse.weaponsystem;

public interface IWeapon {
    //Should have interface for weapon so player can have many weapons without caring what type of weapon it is
    //And since we want as low coupling as possible it is good.
    void attack();
    int getDamage();
    String getName();
    void reload(int bullets);
    int getAmmo();
}
