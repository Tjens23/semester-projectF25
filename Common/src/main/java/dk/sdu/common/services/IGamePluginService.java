package dk.sdu.common.services;

import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);
    void stop(GameData gameData, World world);
}

