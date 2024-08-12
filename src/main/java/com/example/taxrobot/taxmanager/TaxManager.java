package com.example.taxrobot.taxmanager;

import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import com.example.taxrobot.taxmanager.resources.tables.WageStatementTable;
import com.example.taxrobot.tools.Keyboard;
import com.example.taxrobot.tools.Launcher;



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
        Keyboard.sleep(1000);
        selectMainMenuOption(0);
        waitForFormularWindow();
        Keyboard.sleep(1000);
        selectInnerMenuOption(2);
        Keyboard.sleep(500);

        personalData.fill();


        nextPage();

        next();



        wageStatementTable.fill();
    }
}
