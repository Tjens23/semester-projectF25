package dk.sdu.enemy;

public class HealthComponent implements Component {
    private int maxhealth;

    public HealthComponent(int maxhealth){
        this.maxhealth = maxhealth;
    }

    @Override
    public void update(Zombie zombie) {
        if (zombie.getHealth() <=0) {
            System.out.println("Zombie is dead");
        }
    }
}
