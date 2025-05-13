package dk.sdu.player;

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
        player1.setPolygonCoordinates(
            -5, -10,  // Head (top-left)
            5, -10,  // Head (top-right)
            0,  0,   // Body (center)
            -5,  10,  // Left leg
            5,  10,  // Right leg
            10, -5    // Gun (extended arm)
        );
        /*Image playerImage = new Image("assets/player.png");
        player1 =  new ImageView(playerImage);*/
        player1.setX(10/*gameData.getDisplayHeight()/2*/);
        player1.setY(10/*gameData.getDisplayWidth()/2*/);
        player1.setRadius(8);
        return player1;

    }
    @Override
    public void stop(GameData gameData, World world){
        world.removeEntity(player);
    }

}
