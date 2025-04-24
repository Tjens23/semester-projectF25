package dk.sdu.cbse.playersystem;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;

    public PlayerPlugin(){
    }

    @Override
    public void start(GameData gameData, World world){
        player = createPlayer(gameData);
        world.addEntity(player);
    }
    private Entity createPlayer(GameData gameData){
        Entity player1 = new Player();
        player.setPolygonCoordinates(-5,-5,10,0,-5,5);
        player.setX(gameData.getDisplayHeight()/2);
        player.setY(gameData.getDisplayWidth()/2);
        player.setRadius(8);
        return player;

    }
    @Override
    public void stop(GameData gameData, World world){
        world.removeEntity(player);
    }

}
