package dk.sdu.common.SPI;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;

public interface BulletSPI {
    Entity createBullet(Entity player, GameData gameData);
}
