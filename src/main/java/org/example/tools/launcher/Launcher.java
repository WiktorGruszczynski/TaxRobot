package org.example.tools.launcher;

import com.sun.jna.platform.win32.WinDef.*;
import org.example.tools.Keyboard;

import java.io.IOException;


public class Launcher {
    private final String WINDOW_TITLE = "Private Tax 2023 -  Was möchten Sie tun?";
    private final String EXECUTABLE_PATH = "C:\\Program Files (x86)\\Private Tax 2023\\Private Tax 2023.exe";
    private HWND hwnd;


    public Launcher(){
    }


    private void launch() {

        try {
            Runtime.getRuntime().exec(EXECUTABLE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void isAlive(){
        final int PID = WindowsApi.getProcessIdByWindowHandle(hwnd);
        int currentWindowPid = PID;


        while (currentWindowPid == PID){
            currentWindowPid = WindowsApi.getProcessIdByWindowHandle(WindowsApi.getForegroundWindow());
        }

        throw new RuntimeException("Program Interrupted");
    }


    public void start(){
        hwnd = WindowsApi.findByTitle(WINDOW_TITLE);

        if (hwnd == null){
            launch();
        }

        while (hwnd == null){
            hwnd = WindowsApi.findByTitle(WINDOW_TITLE);
        }


        Keyboard.sleep(50);

        if (!WindowsApi.isForegroundWindow(hwnd)) {
            WindowsApi.setForegroundWindow(hwnd);
        }

        if (WindowsApi.isWindowMinimized(hwnd)){
            WindowsApi.showWindows(hwnd);
        }

    }


    public void listen() {
        Thread thread = new Thread(this::isAlive);
        thread.start();
    }


    public String getCurrentWindowTitle(){
        return WindowsApi.getWindowTitle(
                WindowsApi.getForegroundWindow()
        );
    }

}
