package dk.sdu.enemy;

public class HealthComponent implements ZombieComponent {
    private int maxhealth;

    public HealthComponent(int maxhealth){
        this.maxhealth = maxhealth;
    }

    @Override
    public void update(Zombie zombie) {
        if (zombie.getHealth() <=0) {
            zombie.setActive(false);
            System.out.println("Zombie is dead.");
        } else {
            System.out.println("Zombie health: " + zombie.getHealth() + "/" + maxhealth);
        }
    }
}
