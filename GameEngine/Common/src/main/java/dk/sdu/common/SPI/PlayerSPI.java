package dk.sdu.common.SPI;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;

public interface PlayerSPI {
    Entity getPlayer(GameData gameData, World world);
}
