package dk.sdu.enemy;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

/**
 * Component for rendering health bars below zombies
 * Shows current health as a visual bar with background and foreground
 */
public class HealthBarComponent implements ZombieComponent {
    private Rectangle healthBarBackground;
    private Rectangle healthBarForeground;
    private Pane gameWindow;
    private boolean isInitialized = false;
    
    // Health bar dimensions and styling
    private static final double HEALTH_BAR_WIDTH = 40;
    private static final double HEALTH_BAR_HEIGHT = 6;
    private static final double HEALTH_BAR_OFFSET_Y = 15; // Distance below zombie
    private static final Color BACKGROUND_COLOR = Color.DARKRED;
    private static final Color FOREGROUND_COLOR = Color.LIME;
    private static final Color LOW_HEALTH_COLOR = Color.RED;
    private static final double LOW_HEALTH_THRESHOLD = 0.3; // 30% health
    
    public HealthBarComponent(Pane gameWindow) {
        this.gameWindow = gameWindow;
    }
    
    @Override
    public void update(Zombie zombie) {
        // Initialize health bar on first update
        if (!isInitialized) {
            initializeHealthBar();
            isInitialized = true;
        }
        
        // Update health bar position and size based on zombie's health and position
        updateHealthBarDisplay(zombie);
    }
    
    private void initializeHealthBar() {
        // Create background rectangle (shows maximum health)
        healthBarBackground = new Rectangle(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        healthBarBackground.setFill(BACKGROUND_COLOR);
        healthBarBackground.setStroke(Color.BLACK);
        healthBarBackground.setStrokeWidth(1);
        
        // Create foreground rectangle (shows current health)
        healthBarForeground = new Rectangle(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        healthBarForeground.setFill(FOREGROUND_COLOR);
        
        // Add to game window
        gameWindow.getChildren().addAll(healthBarBackground, healthBarForeground);
    }
    
    private void updateHealthBarDisplay(Zombie zombie) {
        if (healthBarBackground == null || healthBarForeground == null) {
            return;
        }
        
        // Calculate health percentage
        int currentHealth = zombie.getHealth();
        int maxHealth = 10; // Assuming max health is 10 based on zombie creation
        double healthPercentage = Math.max(0, Math.min(1, (double) currentHealth / maxHealth));
        
        // Hide health bar if zombie is dead or marked for removal
        boolean shouldShowHealthBar = currentHealth > 0 && !zombie.isMarkedForRemoval();
        
        if (!shouldShowHealthBar) {
            healthBarBackground.setVisible(false);
            healthBarForeground.setVisible(false);
            return;
        }
        
        // Position health bar below zombie
        double zombieX = zombie.getX();
        double zombieY = zombie.getY();
        
        // Ensure zombie view exists before calculating position
        if (zombie.getView() == null) {
            healthBarBackground.setVisible(false);
            healthBarForeground.setVisible(false);
            return;
        }
        
        // Center the health bar horizontally relative to zombie
        double healthBarX = zombieX + (zombie.getView().getFitWidth() / 2) - (HEALTH_BAR_WIDTH / 2);
        double healthBarY = zombieY + zombie.getView().getFitHeight() + HEALTH_BAR_OFFSET_Y;
        
        // Update background position
        healthBarBackground.setX(healthBarX);
        healthBarBackground.setY(healthBarY);
        healthBarBackground.setVisible(true);
        
        // Update foreground position and width based on health percentage
        healthBarForeground.setX(healthBarX);
        healthBarForeground.setY(healthBarY);
        healthBarForeground.setWidth(HEALTH_BAR_WIDTH * healthPercentage);
        healthBarForeground.setVisible(true);
        
        // Change color based on health level
        if (healthPercentage <= LOW_HEALTH_THRESHOLD) {
            healthBarForeground.setFill(LOW_HEALTH_COLOR);
        } else {
            healthBarForeground.setFill(FOREGROUND_COLOR);
        }
    }
    
    /**
     * Clean up health bar when zombie is removed
     */
    public void cleanup() {
        if (healthBarBackground != null) {
            gameWindow.getChildren().remove(healthBarBackground);
        }
        if (healthBarForeground != null) {
            gameWindow.getChildren().remove(healthBarForeground);
        }
    }
}

