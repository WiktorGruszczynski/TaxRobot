package com.example.taxrobot.tools;


import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;




import java.util.Objects;



class WindowsApi {
    private final static User32 user32 = User32.INSTANCE;
    private final static int KEY_DOWN = 0;
    private final static int KEY_UP = 2;
    private final static WinUser.INPUT input = getInput();
    private final static int HWND_TOP = 0;

    private static WinUser.INPUT getInput(){
        WinUser.INPUT input = new WinUser.INPUT();

        input.type = new DWORD( WinUser.INPUT.INPUT_KEYBOARD );
        input.input.setType("ki");
        input.input.ki.wScan = new WORD( 0 );
        input.input.ki.time = new DWORD( 0 );
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR( 0 );

        return input;
    }


    private static String charArrayToString(char[] arr){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i=0; i<arr.length-1; i++){
            if ( (int) arr[i] != 0 ){
                stringBuilder.append(arr[i]);
            }
        }

        return stringBuilder.toString();
    }


    public static String getWindowTitle(HWND hwnd){
        int length = user32.GetWindowTextLength(hwnd) + 1;
        char[] text = new char[length];
        user32.GetWindowText(hwnd, text, length);
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


    public static void showWindow(HWND hwnd){
        user32.ShowWindow(hwnd, WinUser.SW_SHOWNORMAL);
    }

    public static void setFocus(HWND hwnd){
        user32.SetFocus(hwnd);
    }


    public static HWND getForegroundWindow(){
        return user32.GetForegroundWindow();
    }


    public static int getProcessIdByWindowHandle(HWND hwnd){
        IntByReference ref = new IntByReference();
        user32.GetWindowThreadProcessId(hwnd, ref);
        return ref.getValue();
    }

    public static void postCharacters(String text){
        for (char c: text.toCharArray()){
            user32.PostMessage(getForegroundWindow(), WinUser.WM_CHAR, new WPARAM(c), new LPARAM(0));
        }
    }




    public static void keyDown(int keycode){
        user32.PostMessage(user32.GetForegroundWindow(), WinUser.WM_KEYDOWN, new WPARAM(keycode), new LPARAM(0));
    }

    public static void keyUp(int keycode){
        user32.PostMessage(user32.GetForegroundWindow(), WinUser.WM_KEYUP, new WPARAM(keycode), new LPARAM(0));
    }




    public static void combination(int keycode1, int keycode2) {
        input.input.ki.wVk = new WORD(keycode1);
        input.input.ki.dwFlags = new DWORD(KEY_DOWN);


        user32.SendInput(new DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());

        input.input.ki.wVk = new WORD(keycode2);
        input.input.ki.dwFlags = new DWORD(KEY_DOWN );

        user32.SendInput(new DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());


        input.input.ki.wVk = new WORD(keycode1);
        input.input.ki.dwFlags = new DWORD(KEY_UP);

        user32.SendInput(new DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());

        input.input.ki.wVk = new WORD(keycode2);
        input.input.ki.dwFlags = new DWORD(KEY_UP);

        user32.SendInput(new DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    public static void setWindowPosition(HWND hwnd, int hwndInsertAfter, int x, int y, int cx, int cy, int flags){
        user32.SetWindowPos(hwnd, new HWND(new Pointer(hwndInsertAfter)), x, y, cx, cy, flags);
    }

    public static void setWindowOnTop(HWND hwnd){
        setWindowPosition(hwnd, HWND_TOP, 0, 0, 0, 0, WinUser.SWP_NOMOVE | WinUser.SWP_NOSIZE);
    }
}

