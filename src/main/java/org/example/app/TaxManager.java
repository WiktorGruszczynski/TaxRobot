package org.example.app;

import org.example.app.resources.forms.PersonalData;
import org.example.app.resources.tables.WageStatementTable;
import org.example.tools.Keyboard;
import org.example.tools.Launcher;


public class TaxManager {
    private final Launcher launcher;
    private final String EXTENSION = ".ptax23";
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
        while (!launcher.getCurrentWindowTitle().endsWith(EXTENSION)){
        }
    }

    private void nextPage(){
        waitForFormularWindow();
        Keyboard.alrRight();
        Keyboard.sleep(800);
    }

    private void next(){
        Keyboard.tab();
    }

    public void fill(){
        selectMainMenuOption(0);
        waitForFormularWindow();
        Keyboard.sleep(250);
        selectInnerMenuOption(2);
        Keyboard.sleep(350);

        personalData.fill();


        nextPage();

        next();

        wageStatementTable.fill();
    }
}
