package com.example.taxrobot.tools;


import com.sun.jna.platform.win32.WinDef.HWND;

public class Launcher {
    private final String WINDOW_TITLE = "Private Tax 2023 -  Was möchten Sie tun?";
    private final String EXECUTABLE_PATH = "C:\\Program Files (x86)\\Private Tax 2023\\Private Tax 2023.exe";
    private HWND hwnd;


    public Launcher(){
    }

    private void launch() {
        try {
            ProcessBuilder pb = new ProcessBuilder(EXECUTABLE_PATH);
            pb.start();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void isAlive(){
        Keyboard.sleep(250);
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

            Keyboard.sleep(500);
            }
    }

    public void start(){
        hwnd = WindowsApi.findByTitle(WINDOW_TITLE);

        if (hwnd == null){
            launch();
        }

        Keyboard.sleep(2500);

        while (hwnd == null){
            hwnd = WindowsApi.findByTitle(WINDOW_TITLE);
        }

        System.out.println("Window is here");
        Keyboard.sleep(500);

        WindowsApi.displayWindow(hwnd);

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
