package dk.sdu.cbse.healthsystem;

public class HealthComponent {
    private double health;
    private double maxHealth;

    public HealthComponent(){
        this.health=maxHealth;
        this.maxHealth=maxHealth;
    }

    public double getHealth() {
        return health;
    }
    public void takeDamage(double damage){
        health = Math.max(0,health-damage);
    }
    public void heal(double amount){
        health= Math.min(maxHealth, health+amount);
    }
}
