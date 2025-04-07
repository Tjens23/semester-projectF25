/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.collisionsystem.services;

//import dk.sdu.map.World;
//import dk.sdu.entity.Entity;


/**
 *
 * @author tubnielsen
 */
public interface ICollidable {
    
    void process(GameData gameData, World, world);
    
    boolean collision(Entity e1, Entity e2);
   
}
