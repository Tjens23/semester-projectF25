package dk.sdu.enemy;

public class PathfindingComponent implements Component{

    @Override

    public void update(Zombie zombie){
        //simulate calculating path using A*
        System.out.println(""+zombie.position);

    }
}
