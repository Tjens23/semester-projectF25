/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.data;

/**
 *
 * @author tubnielsen
 */
public class GameKeys {

    private static boolean[] keys;
    private static boolean[] pkeys;

    private static final int NUM_KEYS = 9;
    public static final int UP = 0;         // KeyCode.W 
    public static final int LEFT = 1;       // KeyCode.A 
    public static final int RIGHT = 2;      // KeyCode.D
    public static final int DOWN = 3;       // KeyCode.S
    public static final int SHOOT = 4;      // KeyCode.SPACE
    public static final int RELOAD = 5;     // KeyCode.R
    public static final int LEFT_CLICK = 6; // MouseButton.PRIMARY
    public static final int RIGHT_CLICK = 7;// MouseButton.SECONDARY
    public static final int ESC = 8;        // KeyCode.ESCAPE

    public GameKeys() {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    public void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            pkeys[i] = keys[i];
        }
    }

    public void setKey(int k, boolean b) {
        keys[k] = b;
    }

    public boolean isDown(int k) {
        return keys[k];
    }

    public boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }

}
