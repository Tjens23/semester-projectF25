package dk.sdu.enemy;

import java.util.Random;

public class SpawnComponent implements ZombieComponent {
    private final String spawnType;
    private final Random random = new Random();

    // Different spawn types (e.g., "random", "cluster", "wave")
    public static final String RANDOM_SPAWN = "random";
    public static final String CLUSTER_SPAWN = "cluster";
    public static final String WAVE_SPAWN = "wave";

    public SpawnComponent(String spawnType) {
        this.spawnType = spawnType;
    }

    @Override
    public void update(Zombie zombie) {
        // Check if the zombie needs to be spawned
        if (!zombie.isActive()) {
            spawnZombie(zombie);
        }
    }

    private void spawnZombie(Zombie zombie) {
        switch (spawnType) {
            case CLUSTER_SPAWN:
                spawnInCluster(zombie);
                break;
            case WAVE_SPAWN:
                spawnInWave(zombie);
                break;
            case RANDOM_SPAWN:
            default:
                spawnRandomly(zombie);
                break;
        }

        // After spawning, mark the zombie as active
        zombie.setActive(true);
    }

    private void spawnRandomly(Zombie zombie) {
        // Spawn at random position on the map edges
        boolean spawnOnHorizontalEdge = random.nextBoolean();
        int mapWidth = 1000;  // Example map dimensions
        int mapHeight = 800;

        if (spawnOnHorizontalEdge) {
            zombie.setX(random.nextInt(mapWidth));
            zombie.setY(random.nextBoolean() ? 0 : mapHeight);
        } else {
            zombie.setX(random.nextBoolean() ? 0 : mapWidth);
            zombie.setY(random.nextInt(mapHeight));
        }
    }

    private void spawnInCluster(Zombie zombie) {
        // Spawn close to other zombies
        // For simplicity, we'll spawn at a slightly offset position from a fixed point
        int baseX = 500;
        int baseY = 500;
        int spread = 100;

        zombie.setX(baseX + (random.nextInt(spread) - (double) spread /2));
        zombie.setY(baseY + (random.nextInt(spread) - (double) spread /2));
    }

    private void spawnInWave(Zombie zombie) {
        // Spawn in a coordinated wave (e.g., all from one side)
        int mapWidth = 1000;
        int mapHeight = 800;

        // Choose one of the four sides randomly
        int side = random.nextInt(4);

        switch (side) {
            case 0: // Top
                zombie.setX(random.nextInt(mapWidth));
                zombie.setY(0);
                break;
            case 1: // Right
                zombie.setX(mapWidth);
                zombie.setY(random.nextInt(mapHeight));
                break;
            case 2: // Bottom
                zombie.setX(random.nextInt(mapWidth));
                zombie.setY(mapHeight);
                break;
            case 3: // Left
                zombie.setX(0);
                zombie.setY(random.nextInt(mapHeight));
                break;
        }
    }
}