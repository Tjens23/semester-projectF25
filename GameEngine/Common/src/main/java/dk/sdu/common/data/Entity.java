package dk.sdu.common.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javafx.scene.image.ImageView;

public class Entity implements Serializable {
    private final Map<Class<?>, Object> components = new HashMap<>();
    private final UUID ID = UUID.randomUUID();
    private String tag;
    private ImageView entityImage;
    private double x;
    private double y;
    private double rotation;
    private float radius;
    private boolean markedForRemoval = false;

    public String getID() {
        return ID.toString();
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return this.radius;
    }

    public <T> void addComponent(T component) {
        components.put(component.getClass(), component);
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> cls) {
        return (T) components.get(cls);
    }

    public <T> void removeComponent(Class<T> cls) {
        components.remove(cls);
    }

    public boolean hasComponent(Class<?> cls) {
        return components.containsKey(cls);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean hasTag(String queryTag) {
        return tag != null && tag.equalsIgnoreCase(queryTag);
    }

    public String getTag() {
        return tag;
    }

    public void markForRemoval() {
        this.markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public void setView(ImageView entityImage) {
        this.entityImage = entityImage;
    }

    public ImageView getView() {
        return entityImage;
    }
}