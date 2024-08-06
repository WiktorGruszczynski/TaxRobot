package org.example.tools.launcher;

import com.sun.jna.platform.win32.WinDef.*;
import org.example.tools.Keyboard;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


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
        int currentWindowPid;
        String currentWindowTitle = "";




        while (true){
            HWND foregroundWindow = WindowsApi.getForegroundWindow();

            currentWindowPid = WindowsApi.getProcessIdByWindowHandle(foregroundWindow);
            currentWindowTitle = WindowsApi.getWindowTitle(foregroundWindow);


            if (currentWindowPid!=PID){
                throw new RuntimeException("Program Interrupted");
            }

            if (currentWindowTitle.startsWith("Probleme")){
                throw new RuntimeException("Problem occured while filling tax declaration");
            }






        }
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
