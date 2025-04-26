package dk.sdu.common.services;

import dk.sdu.common.data.GameData;
import dk.sdu.map.GameMap;

public interface IGamePluginService {
    void start(GameData gameData, GameMap world);
    void stop(GameData gameData, GameMap world);
}

