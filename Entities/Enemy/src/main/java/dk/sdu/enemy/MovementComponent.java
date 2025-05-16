package dk.sdu.enemy;

public class MovementComponent implements Component {

    @Override

    public void update(Zombie zombie) {
        System.out.println("Zombie moves with speed:" + zombie.getSpeed() + "and size" + zombie.getSize());
    }
}
