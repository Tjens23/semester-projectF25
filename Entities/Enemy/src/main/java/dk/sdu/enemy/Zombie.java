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
    private int health;
    private int speed;
    private  String size;
    private final List<Component> components = new ArrayList<>();

    public void addComponent(Component component){
        components.add(component);
    }
    public void update(){
        for(Component component : components){
            component.update(this);
        }
    }

    public Zombie(int health, int speed, String size) {
        this.speed = speed;
        this.health = health;
        this.size = size;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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



    public List<Component> getComponents() {
        return this.components;
    }
    public boolean isActive() {
        return this.health > 0;
    }

    public void setActive(boolean active) {
        if (active) {
            this.health = 100;
        } else {
            this.health = 0;
        }
    }
}







