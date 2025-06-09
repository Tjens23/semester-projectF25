import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;

module Player {
    requires Inventory;
    requires Item;
    requires CurrencySystem;
    requires Common;
    requires javafx.graphics;

    provides IGamePluginService with dk.sdu.player.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.player.PlayerControlSystem;
    
    uses dk.sdu.common.SPI.BulletSPI;
    
    exports dk.sdu.player;
}

