import dk.sdu.common.services.IPostEntityProcessingService;

module CollisionSystem {
    requires Common;
    provides IPostEntityProcessingService with dk.sdu.collisionsystem.CollisionDetector;
    uses dk.sdu.common.SPI.BulletSPI;
    exports dk.sdu.collisionsystem;
}


