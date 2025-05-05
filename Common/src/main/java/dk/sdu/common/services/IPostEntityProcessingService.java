package dk.sdu.common.services;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
public interface IPostEntityProcessingService {
    void process(GameData gameData, World world);

}
