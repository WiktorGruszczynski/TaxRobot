package com.example.taxrobot.tools;


import java.awt.event.KeyEvent;



public class Keyboard {
    public static void sleep(int ms){
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e){
            throw new RuntimeException();
        }
    }

    private static void pressKeycodes(int... keycodes){
        for (int keyCode: keycodes){
            WindowsApi.keyDown(keyCode);
        }

        sleep(1);

        for (int keyCode: keycodes){
            WindowsApi.keyUp(keyCode);
        }

    }

    public static void writeText(String text){
        WindowsApi.postCharacters(text);
    }


    public static void tab(){
        pressKeycodes(KeyEvent.VK_TAB);
        sleep(80);
    }

    public static void tab(int steps){
        for (int i=0; i<steps; i++){
            tab();
        }
    }

    public static void shiftTab(){
        WindowsApi.hotkey(KeyEvent.VK_SHIFT, KeyEvent.VK_TAB);
        sleep(20);
    }

    public static void altF4(){
        WindowsApi.hotkey(KeyEvent.VK_ALT, KeyEvent.VK_F4);
    }

    public static void space(){
        pressKeycodes(KeyEvent.VK_SPACE);
        sleep(500);
    }

    public static void enter(){
        pressKeycodes(KeyEvent.VK_ENTER);
    }

    public static void arrowDown(){
        pressKeycodes(KeyEvent.VK_DOWN);
    }

    public static void pageDown(){
        pressKeycodes(KeyEvent.VK_PAGE_DOWN);
    }

    public static void pageDown(int steps){
        for (int i=0; i<steps; i++){
            pageDown();
        }
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
        WindowsApi.hotkey(KeyEvent.VK_ALT, KeyEvent.VK_LEFT);
    }

    public static void alrRight() {
        WindowsApi.hotkey(KeyEvent.VK_ALT, KeyEvent.VK_RIGHT);
    }
}
