import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;

module Player {
    requires Inventory;
    requires Item;
    requires CurrencySystem;
    requires Common;
    requires Bullet;
    requires javafx.graphics;

    provides IGamePluginService with dk.sdu.player.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.player.PlayerControlSystem;
    
    exports dk.sdu.player;
}

