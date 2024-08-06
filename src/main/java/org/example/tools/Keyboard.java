package org.example.tools;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;
import static java.util.Map.entry;

public class Keyboard {
    private static final Robot robot;
    private final static Map<Character, Integer> SHIFT_CHARACTERS_MAP = Map.ofEntries(
            entry('!',KeyEvent.VK_1),
            entry('@',KeyEvent.VK_2),
            entry('#',KeyEvent.VK_3),
            entry('$',KeyEvent.VK_4),
            entry('%',KeyEvent.VK_5),
            entry('^',KeyEvent.VK_6),
            entry('&',KeyEvent.VK_7),
            entry('*',KeyEvent.VK_8),
            entry('(',KeyEvent.VK_9),
            entry(')',KeyEvent.VK_0),
            entry('_',KeyEvent.VK_UNDERSCORE),
            entry('+',KeyEvent.VK_EQUALS),
            entry('{',KeyEvent.VK_OPEN_BRACKET),
            entry('}',KeyEvent.VK_CLOSE_BRACKET),
            entry('|',KeyEvent.VK_BACK_SLASH),
            entry(':',KeyEvent.VK_COLON),
            entry('"',KeyEvent.VK_QUOTE),
            entry('<',KeyEvent.VK_COMMA),
            entry('>',KeyEvent.VK_PERIOD),
            entry('?',KeyEvent.VK_SLASH)
    );

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private static void pressKeycodes(int... keycodes){
        for (int keyCode: keycodes){
            robot.keyPress(keyCode);
        }

        robot.delay(5);

        for (int keyCode: keycodes){
            robot.keyRelease(keyCode);
        }

    }

    private static int[] charToKeycodes(char chr){
        if (SHIFT_CHARACTERS_MAP.containsKey(chr)){
            return new int[]{
                    KeyEvent.VK_SHIFT,
                    SHIFT_CHARACTERS_MAP.get(chr)
            };
        }

        return new int[]{
                KeyEvent.getExtendedKeyCodeForChar(chr)
        };
    }

    public static void writeText(String text){
        for (char chr: text.toCharArray()){
            pressKeycodes(
                    charToKeycodes(chr)
            );
        }
    }

    public static void sleep(int ms){
        robot.delay(ms);
    }

    public static void tab(){
        pressKeycodes(KeyEvent.VK_TAB);
        robot.delay(10);
    }

    public static void tab(int steps){
        for (int i=0; i<steps; i++){
            tab();
        }
    }

    public static void shiftTab(){
        pressKeycodes(KeyEvent.VK_SHIFT, KeyEvent.VK_TAB);
        robot.delay(5);
    }

    public static void space(){
        pressKeycodes(KeyEvent.VK_SPACE);
        robot.delay(300);
    }

    public static void enter(){
        pressKeycodes(KeyEvent.VK_ENTER);
    }

    public static void arrowDown(){
        pressKeycodes(KeyEvent.VK_DOWN);
        robot.delay(5);
    }

    public static void arrowDown(int steps){
        for (int i=0; i<steps; i++){
            arrowDown();
        }
    }

    public static void arrowRight(){
        pressKeycodes(KeyEvent.VK_RIGHT);
        robot.delay(5);
    }

    public static void arrowRight(int steps){
        for (int i=0; i<steps; i++){
            arrowRight();
        }
    }

    public static void altLeft(){
        pressKeycodes(KeyEvent.VK_ALT, KeyEvent.VK_LEFT);
    }

    public static void alrRight(){
        pressKeycodes(KeyEvent.VK_ALT, KeyEvent.VK_RIGHT);
    }

}
