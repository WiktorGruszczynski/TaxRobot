package org.example.app;

import org.example.app.forms.PersonalData;
import org.example.tools.Keyboard;
import org.example.tools.launcher.Launcher;

import java.util.concurrent.TimeUnit;

public class TaxManager {
    private final Launcher launcher;
    public final PersonalData personalData = new PersonalData();

    public TaxManager(Launcher launcher){
        this.launcher = launcher;
    }

    private void selectMainMenuOption(int index){
        Keyboard.tab(index);
        Keyboard.space();
    }

    private void selectInnerMenuOption(int index){
        Keyboard.tab(index);
        Keyboard.space();
    }

    private void waitForFormularWindow(){
        while (!launcher.getCurrentWindowTitle().endsWith(".ptax23")){
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void fill(){
        selectMainMenuOption(0);
        waitForFormularWindow();
        Keyboard.sleep(100);
        selectInnerMenuOption(2);
        Keyboard.sleep(350);

        personalData.fill();
    }
}
