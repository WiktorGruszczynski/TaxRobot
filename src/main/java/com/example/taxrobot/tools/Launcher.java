package com.example.taxrobot.tools;


import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;


public class Launcher {
    private final String WINDOW_TITLE = "Private Tax 2023 -  Was möchten Sie tun?";
    private final String EXECUTABLE_PATH = "C:\\Program Files (x86)\\Private Tax 2023\\Private Tax 2023.exe";
    private boolean running = true;
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

    private void terminateApplication(int PID){
        WindowsApi.terminateProcess(PID);
    }

    private void isAlive(){
        Keyboard.sleep(250);
        final int PID = WindowsApi.getProcessIdByWindowHandle(hwnd);
        int currentWindowPid;
        String currentWindowTitle;

        while (running){
            HWND foregroundWindow = WindowsApi.getForegroundWindow();

            currentWindowPid = WindowsApi.getProcessIdByWindowHandle(foregroundWindow);
            currentWindowTitle = WindowsApi.getWindowTitle(foregroundWindow);

            if (currentWindowPid!=PID){
                terminateApplication(PID);
                throw new RuntimeException("Program Interrupted");
            }

            if (currentWindowTitle.equals("Probleme")){
                terminateApplication(PID);
                throw new RuntimeException("Problem occured while filling tax declaration");
            }

            if (currentWindowTitle.equals("Information")){
                Keyboard.space();
            }

            Keyboard.sleep(50);
            }

        System.out.println("Finished program");
    }

    public void clickWindow(HWND hwnd){
        WinDef.RECT rect = WindowsApi.getWindowRect(hwnd);
        int width = rect.right - rect.left;

        int x = rect.left + (width/2);
        int y = rect.top + 30;

        Mouse.setCursorPos(x, y);
        Mouse.click();
    }

    public void start(){
        hwnd = WindowsApi.findByTitle(WINDOW_TITLE);

        if (hwnd == null){
            launch();

            while (hwnd == null){
                hwnd = WindowsApi.findByTitle(WINDOW_TITLE);
            }
        }

        Keyboard.sleep(250);

        WindowsApi.displayWindow(hwnd);
        clickWindow(hwnd);

        Keyboard.sleep(100);
    }


    public void listen() {
        Thread thread = new Thread(this::isAlive);
        thread.start();
    }

    public void stop(){
        Keyboard.sleep(105);
        running = false;
    }
}
