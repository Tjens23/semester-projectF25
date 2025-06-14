import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires javafx.graphics;
    provides IGamePluginService with dk.sdu.bullet.BulletPlugin;
    //provides BulletSPI with dk.sdu.bullet.BulletControlSystem;
    provides IEntityProcessingService with dk.sdu.bullet.BulletControlSystem;
    provides dk.sdu.common.SPI.BulletSPI with dk.sdu.bullet.BulletControlSystem;
    
    exports dk.sdu.bullet;
}