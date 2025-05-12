package dk.sdu.bullet;

import dk.sdu.common.data.Entity;

public class Bullet extends Entity {
    private static final int DEFAULT_BULLET_SIZE = 2;
    
    public Bullet() {
        float[] shape = new float[]{
            -DEFAULT_BULLET_SIZE, -DEFAULT_BULLET_SIZE,
            DEFAULT_BULLET_SIZE, -DEFAULT_BULLET_SIZE,
            0, DEFAULT_BULLET_SIZE * 2
        };
        
        // Convert float array to double array for polygon coordinates
        double[] coordinates = new double[shape.length];
        for (int i = 0; i < shape.length; i++) {
            coordinates[i] = shape[i];
        }
        
        setPolygonCoordinates(coordinates);
        setRadius(DEFAULT_BULLET_SIZE * 2);
        
        // Debug print
        System.out.println("Created new bullet with radius: " + getRadius());
    }
}
