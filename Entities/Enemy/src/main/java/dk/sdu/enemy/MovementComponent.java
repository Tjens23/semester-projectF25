package dk.sdu.enemy;

public class MovementComponent implements Component {

    @Override
    public void update(Zombie zombie) {
        // Get the zombie's current position and attributes
        double x = zombie.getX();
        double y = zombie.getY();
        double speed = zombie.getSpeed();
        double rotation = zombie.getRotation();

        // Calculate movement direction based on rotation
        double dx = Math.cos(Math.toRadians(rotation));
        double dy = Math.sin(Math.toRadians(rotation));

        // Apply movement
        zombie.setX(x + dx * speed);
        zombie.setY(y + dy * speed);

        System.out.println("Zombie moves with speed: " + zombie.getSpeed() + " and size: " + zombie.getSize());
        System.out.println("Position updated to: (" + zombie.getX() + ", " + zombie.getY() + ")");
    }
}