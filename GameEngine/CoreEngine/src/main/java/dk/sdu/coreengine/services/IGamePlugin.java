/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.coreengine.services;

/**
 *
 * @author tubnielsen
 */
import dk.sdu.coreengine.data.GameData;
//import dk.sdu.data.World; // Find out how to import java class from a different component.

public interface IGamePlugin {

    void start(GameData gameData/*, World world*/);

    void stop(GameData gameData/*, World world*/);
}
