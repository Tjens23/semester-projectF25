package dk.sdu.healthsystem;

public class HealthComponent {
    private double health;
    private double maxHealth;

    public HealthComponent(double maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void takeDamage(double damage) {
        health = Math.max(0, health - damage);
    }

    public void heal(double amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public void setHealth(double health) {
        this.health = Math.max(0, Math.min(maxHealth, health));
    }
}
