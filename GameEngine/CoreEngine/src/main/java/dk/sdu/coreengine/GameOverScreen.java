package dk.sdu.coreengine;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameOverScreen {
    
    private Stage gameOverStage;
    private Runnable onRestartCallback;
    private Runnable onExitCallback;
    
    public GameOverScreen(Runnable onRestartCallback, Runnable onExitCallback) {
        this.onRestartCallback = onRestartCallback;
        this.onExitCallback = onExitCallback;
    }
    
    public void show(String reason) {
        if (gameOverStage != null && gameOverStage.isShowing()) {
            return; // Prevent multiple game over screens
        }
        
        gameOverStage = new Stage();
        gameOverStage.initModality(Modality.APPLICATION_MODAL);
        gameOverStage.setTitle("Game Over");
        gameOverStage.setResizable(false);
        
        // Create UI elements
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gameOverLabel.setTextFill(Color.DARKRED);
        
        Label reasonLabel = new Label(reason);
        reasonLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        reasonLabel.setTextFill(Color.BLACK);
        
        Button restartButton = new Button("Restart Game");
        restartButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        restartButton.setPrefWidth(150);
        restartButton.setOnAction(e -> {
            gameOverStage.close();
            if (onRestartCallback != null) {
                onRestartCallback.run();
            }
        });
        
        Button exitButton = new Button("Exit Game");
        exitButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        exitButton.setPrefWidth(150);
        exitButton.setOnAction(e -> {
            gameOverStage.close();
            if (onExitCallback != null) {
                onExitCallback.run();
            }
        });
        
        // Layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 40;");
        layout.getChildren().addAll(gameOverLabel, reasonLabel, restartButton, exitButton);
        
        Scene scene = new Scene(layout, 400, 300);
        gameOverStage.setScene(scene);
        
        // Center the stage on screen
        gameOverStage.centerOnScreen();
        
        gameOverStage.showAndWait();
    }
    
    public void hide() {
        if (gameOverStage != null) {
            gameOverStage.close();
        }
    }
}

