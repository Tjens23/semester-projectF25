import dk.sdu.common.services.IPostEntityProcessingService;

module CollisionSystem {
    requires Map;
    requires Common;
    requires Bullet;
    provides IPostEntityProcessingService with dk.sdu.collisionsystem.CollisionDetector;
  
  exports dk.sdu.collisionsystem;
}


