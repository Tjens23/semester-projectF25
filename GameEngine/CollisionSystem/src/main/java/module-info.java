import dk.sdu.common.services.IPostEntityProcessingService;

module CollisionSystem {
    requires Map;
    requires Common;
    //requires Bullet;
    provides IPostEntityProcessingService with dk.sdu.collisionsystem.CollisionDetector;
    uses dk.sdu.common.SPI.BulletSPI;
    exports dk.sdu.collisionsystem;
}


