package org.example.tools;


import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;


public class Keyboard {
    public static void sleep(int ms){
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        }
        catch (InterruptedException e){
            throw new RuntimeException();
        }
    }

    private static void pressKeycodes(int... keycodes){
        for (int keyCode: keycodes){
            WindowsApi.keyDown(keyCode);
        }

        for (int keyCode: keycodes){
            WindowsApi.keyUp(keyCode);
        }

        sleep(1);

    }

    public static void writeText(String text){
        WindowsApi.postCharacters(text);
    }


    public static void tab(){
        pressKeycodes(KeyEvent.VK_TAB);
        sleep(50);
    }

    public static void tab(int steps){
        for (int i=0; i<steps; i++){
            tab();
        }
    }

    public static void shiftTab(){
        WindowsApi.combination(KeyEvent.VK_SHIFT, KeyEvent.VK_TAB);
        sleep(20);
    }

    public static void space(){
        pressKeycodes(KeyEvent.VK_SPACE);
        sleep(200);
    }

    public static void enter(){
        pressKeycodes(KeyEvent.VK_ENTER);
    }

    public static void arrowDown(){
        pressKeycodes(KeyEvent.VK_DOWN);
    }

    public static void arrowDown(int steps){
        for (int i=0; i<steps; i++){
            arrowDown();
        }
    }

    public static void arrowRight(){
        pressKeycodes(KeyEvent.VK_RIGHT);
    }

    public static void arrowRight(int steps){
        for (int i=0; i<steps; i++){
            arrowRight();
        }
    }

    public static void altLeft(){
        WindowsApi.combination(KeyEvent.VK_ALT, KeyEvent.VK_LEFT);
    }

    public static void alrRight() {
        WindowsApi.combination(KeyEvent.VK_ALT, KeyEvent.VK_RIGHT);
    }
}
