module Enemy {
    requires Common;
    requires javafx.graphics;
    requires Player;
    requires Pathfinding;
    exports dk.sdu.enemy;

    provides dk.sdu.common.services.IGamePluginService
            with dk.sdu.enemy.ZombiePlugin;
    provides dk.sdu.common.services.IEntityProcessingService
            with dk.sdu.enemy.ZombieControlSystem,
                 dk.sdu.enemy.ZombieSpawner;
}
