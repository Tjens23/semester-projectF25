package dk.sdu.enemy;

public class HealthComponent implements ZombieComponent {
    private int maxhealth;

    public HealthComponent(int maxhealth){
        this.maxhealth = maxhealth;
    }

    @Override
    public void update(Zombie zombie) {
        int currentHealth = zombie.getHealth();
        if (currentHealth <= 0) {
            System.out.println("[HEALTH COMPONENT] Zombie health is " + currentHealth + ", marking for removal");
            zombie.setActive(false);
            zombie.markForRemoval(); // Mark for removal from world
        }
    }
}
