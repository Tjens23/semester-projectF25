package dk.sdu.enemy;

public class SpawnComponent implements Component{
    private String spawnType;

    public SpawnComponent(String spawnType) {
        this.spawnType = spawnType;
    }

    @Override

    public void update (Zombie zombie) {
        System.out.println("Spawing zombie using " + spawnType);
    }
}
