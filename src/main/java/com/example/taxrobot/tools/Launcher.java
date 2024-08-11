package com.example.taxrobot.tools;


import com.sun.jna.platform.win32.WinDef.HWND;

public class Launcher {
    private final String WINDOW_TITLE = "Private Tax 2023 -  Was möchten Sie tun?";
    private final String EXECUTABLE_PATH = "C:\\Program Files (x86)\\Private Tax 2023\\Private Tax 2023.exe";
    private HWND hwnd;


    public Launcher(){
    }

    private void launch(int delay) {
        try {
            ProcessBuilder pb = new ProcessBuilder(EXECUTABLE_PATH);
            pb.start();

            Thread.sleep(delay);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void isAlive(){
        final int PID = WindowsApi.getProcessIdByWindowHandle(hwnd);
        int currentWindowPid;
        String currentWindowTitle;



        while (true){
            HWND foregroundWindow = WindowsApi.getForegroundWindow();

            currentWindowPid = WindowsApi.getProcessIdByWindowHandle(foregroundWindow);
            currentWindowTitle = WindowsApi.getWindowTitle(foregroundWindow);


            if (currentWindowPid!=PID){
                throw new RuntimeException("Program Interrupted");
            }

            if (currentWindowTitle.equals("Probleme")){
                throw new RuntimeException("Problem occured while filling tax declaration");
            }

            if (currentWindowTitle.equals("Information")){
                Keyboard.space();
            }

            }
    }

    public void start(){
        hwnd = WindowsApi.findByTitle(WINDOW_TITLE);

        if (hwnd == null){
            launch(2500);
        }

        while (hwnd == null){
            hwnd = WindowsApi.findByTitle(WINDOW_TITLE);
        }

        Keyboard.sleep(250);

        WindowsApi.showWindow(hwnd);
        Keyboard.sleep(10);

        WindowsApi.setWindowOnTop(hwnd);
        Keyboard.sleep(10);

        WindowsApi.setForegroundWindow(hwnd);
        Keyboard.sleep(10);

        WindowsApi.setFocus(hwnd);

        Keyboard.sleep(100);
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
