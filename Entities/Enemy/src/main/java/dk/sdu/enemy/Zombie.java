package dk.sdu.enemy;
import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;


public class Zombie {
    private List<Component > components = new ArrayList<>();

    public void addComponent(Component component){
        components.add(component);
    }
    public void update(){
        for(Component component : components){
            component.update(this);
        }
    }

    public int health;
    public int speed;
    public String size;
    public Position position;


}







