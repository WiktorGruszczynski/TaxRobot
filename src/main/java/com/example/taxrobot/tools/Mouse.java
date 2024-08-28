package com.example.taxrobot.tools;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

public class Mouse {
        public static final int MOUSEEVENTF_LEFTDOWN = 0x0002;
        public static final int MOUSEEVENTF_LEFTUP = 0x0004;

        public interface MyUser32 extends User32 {
            MyUser32 INSTANCE = Native.load("user32", MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);

            // Native method for mouse_event function in user32.dll
            void mouse_event(int dwFlags, int dx, int dy, int dwData, int dwExtraInfo);
        }


        public static void setCursorPos(int x, int y){
            WindowsApi.setCursorPos(x, y);
        }


        public static void click() {
            WinDef.POINT point = new WinDef.POINT();
            User32.INSTANCE.GetCursorPos(point);

            // Simulate left button down
            MyUser32.INSTANCE.mouse_event(MOUSEEVENTF_LEFTDOWN, point.x, point.y, 0, 0);

            // Simulate left button up
            MyUser32.INSTANCE.mouse_event(MOUSEEVENTF_LEFTUP, point.x, point.y, 0, 0);
        }
}
