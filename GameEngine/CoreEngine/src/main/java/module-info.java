/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module CoreEngine {
    requires javafx.graphics;
    requires javafx.controls;
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    requires Map;
    requires Common;
    
    opens dk.sdu.coreengine to javafx.graphics, spring.core;

    uses dk.sdu.common.services.IGamePluginService;
    uses dk.sdu.common.services.IEntityProcessingService;
    uses dk.sdu.common.services.IPostEntityProcessingService;
    uses dk.sdu.common.SPI.PlayerSPI;

    exports dk.sdu.coreengine;
}
