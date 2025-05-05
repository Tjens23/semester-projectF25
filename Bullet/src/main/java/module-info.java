import dk.sdu.common.bullet.BulletSPI;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with dk.sdu.bulletsystem.BulletPlugin;
    provides BulletSPI with dk.sdu.bulletsystem.BulletControlSystem;
    provides IEntityProcessingService with dk.sdu.bulletsystem.BulletControlSystem;

}