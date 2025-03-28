package dk.sdu.cbse.common.data.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
