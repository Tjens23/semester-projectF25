    package dk.sdu.coreengine;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application{

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);
        
        // Load game features functionality
        GameFeatures gf = new GameFeatures(ctx);
        
        // Prints all Spring beans for debugging
        for (String beanName : ctx.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }

        Game game = ctx.getBean(Game.class);

        // Create a button to open the shop
        Button shopButton = new Button("Open Shop");
        shopButton.setOnAction(e -> gf.openShop());

        game.start(window);
        game.render();
    }
}

