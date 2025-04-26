import dk.sdu.common.services.IPostEntityProcessingService;

module CollisionSystem {
    requires Common;
    provides IPostEntityProcessingService with dk.sdu.collisionsystem.CollisionDetector;
}