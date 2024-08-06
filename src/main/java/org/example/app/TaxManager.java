package org.example.app;

import org.example.app.forms.PersonalData;
import org.example.app.tables.WageStatementTable;
import org.example.tools.Keyboard;
import org.example.tools.launcher.Launcher;


public class TaxManager {
    private final Launcher launcher;
    public final PersonalData personalData = new PersonalData();
    public final WageStatementTable wageStatementTable = new WageStatementTable();


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
        }
    }

    private void nextPage(){
        Keyboard.alrRight();
        Keyboard.sleep(300);
    }

    private void next(){
        Keyboard.tab();
    }

    public void fill(){
        selectMainMenuOption(0);
        waitForFormularWindow();
        Keyboard.sleep(100);
        selectInnerMenuOption(2);
        Keyboard.sleep(350);

        personalData.fill();

        nextPage();

        next();

        wageStatementTable.fill();
    }
}
