import dk.sdu.common.services.IGamePluginService;

module Map {
    requires Common;
    requires Player;
    
    exports dk.sdu.map;

    requires javafx.graphics;

    provides IGamePluginService with dk.sdu.map.MapPlugin;
}
