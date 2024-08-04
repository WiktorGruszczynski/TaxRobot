package org.example.tools.launcher;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;


import java.util.Objects;


class WindowsApi {
    private final static User32 user32 = User32.INSTANCE;


    private static String charArrayToString(char[] arr){
        StringBuilder stringBuilder = new StringBuilder();

        for (char chr: arr){
            if ( (int) chr != 0 ){
                stringBuilder.append(chr);
            }
        }

        return stringBuilder.toString();
    }


    public static String getWindowTitle(HWND hwnd){
        int length = user32.GetWindowTextLength(hwnd);
        char[] text = new char[length];
        user32.GetWindowText(hwnd, text, length+1);
        return charArrayToString(text);
    }


    public static HWND findByTitle(String title){
        return user32.FindWindow(null, title);
    }


    public static boolean isForegroundWindow(HWND hwnd){
        return Objects.equals(
                user32.GetForegroundWindow(),
                hwnd
        );
    }


    public static void setForegroundWindow(HWND hwnd){
        user32.SetForegroundWindow(hwnd);
    }


    private static RECT getWindowRect(HWND hwnd){
        RECT rect = new RECT();
        user32.GetWindowRect(hwnd, rect);
        return rect;
    }


    public static boolean isWindowMinimized(HWND hwnd){
        RECT rect = getWindowRect(hwnd);

        return  (rect.left<0 && rect.bottom<0 && rect.right<0 && rect.top<0);
    }


    public static void showWindows(HWND hwnd){
        user32.ShowWindow(hwnd, WinUser.SW_SHOWNORMAL);
    }

    public static HWND getForegroundWindow(){
        return user32.GetForegroundWindow();
    }

    public static int getProcessIdByWindowHandle(HWND hwnd){
        IntByReference ref = new IntByReference();
        user32.GetWindowThreadProcessId(hwnd, ref);
        return ref.getValue();
    }


}

