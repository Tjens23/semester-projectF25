/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module CoreEngine {
    requires javafx.graphicsEmpty;
    requires javafx.graphics;
    requires javafx.baseEmpty;
    requires javafx.base;
    
    exports dk.sdu.player;
    exports dk.sdu.map;
    exports dk.sdu.collisionsystem;
    exports dk.sdu.scoresystem;
}
