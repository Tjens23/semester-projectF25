package dk.sdu.shop;

public class shopUI {
    /*private AnnotationConfigApplicationContext ctx;

    public GameFeatures(AnnotationConfigApplicationContext ctx) {
        this.ctx = ctx;
    }
    
    public void openShop() {
        // Get the Shop bean from the Spring context
        //ShopServiceImpl shop = ctx.getBean(ShopServiceImpl.class);

        // Create new stage for the shop
        Stage shopStage = new Stage();
        shopStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window
        shopStage.setTitle("Shop");

        // ListView to display shop items
        ListView<Item> shopItems = new ListView<>(); //Change to = ShopService.Items list in the object
        //shopItems.getItems().addAll(shop.getShopItems)); //Needs function to return all unique items avaialable in the shop

        // Create button to close the shop
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> shopStage.close());

        // Set up VBox Layout to show the listview and button
        VBox layout = new VBox(10, shopItems, closeButton);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Set up scene and show the stage
        Scene scene = new Scene(layout, 300, 400);
        shopStage.setScene(scene);
        shopStage.showAndWait();
    }*/
}
