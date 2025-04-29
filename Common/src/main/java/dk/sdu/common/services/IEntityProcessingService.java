package dk.sdu.common.services;

import dk.sdu.common.data.GameData;
import dk.sdu.map.GameMap;

public interface IEntityProcessingService {
    void process(GameData gameData, GameMap world);
}
