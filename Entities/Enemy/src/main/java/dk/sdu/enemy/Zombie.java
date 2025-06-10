package dk.sdu.enemy;
import dk.sdu.common.data.Entity;

import java.util.ArrayList;
import java.util.List;

/*
 * Zombie class representing a zombie entity in the game.
 * This class extends the Entity class and implements the Component interface.
 * It contains properties like health, speed, size, and position.
 * It also manages components that can be added to the zombie.
 * Author: [Aisha]
 * Last Modified By: [Tobias Emad Jensen]
 */
public class Zombie extends Entity {
    private int speed;
    private String size;
    private final List<ZombieComponent> components = new ArrayList<>();

    public void addComponent(ZombieComponent component){
        components.add(component);
    }
    public void update(){
        for(ZombieComponent component : components){
            component.update(this);
        }
    }

    public Zombie(int health, int speed, String size) {
        this.speed = speed;
        super.setHealth(health); // Use Entity's health system
        this.size = size;
        this.setTag("ZOMBIE"); // Set the tag for collision detection
        this.setCollidable(true); // Make zombie collidable
        System.out.println("[ZOMBIE] Created new zombie with health: " + health + ", speed: " + speed + ", size: " + size);
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }



    public List<ZombieComponent> getComponents() {
        return this.components;
    }
    public boolean isActive() {
        return this.getHealth() > 0;
    }

    public void setActive(boolean active) {
        if (active) {
            this.setHealth(100);
        } else {
            this.setHealth(0);
        }
    }
    
    /**
     * Clean up resources when zombie is being removed
     */
    public void cleanup() {
        // Clean up health bar component if it exists
        for (ZombieComponent component : components) {
            if (component instanceof HealthBarComponent) {
                ((HealthBarComponent) component).cleanup();
            }
        }
    }
}







