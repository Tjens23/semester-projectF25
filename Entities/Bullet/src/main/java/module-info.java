import dk.sdu.common.bullet.BulletSPI;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;

module Bullet {
    requires Common;
    provides IGamePluginService with dk.sdu.bullet.BulletPlugin;
    provides BulletSPI with dk.sdu.bullet.BulletControlSystem;
    provides IEntityProcessingService with dk.sdu.bullet.BulletControlSystem;

}