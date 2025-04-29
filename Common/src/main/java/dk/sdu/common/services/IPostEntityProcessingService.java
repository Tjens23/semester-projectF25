package dk.sdu.common.services;

import dk.sdu.common.data.GameData;
import dk.sdu.map.GameMap;

public interface IPostEntityProcessingService {

    void process(GameData gameData, GameMap world);
}
