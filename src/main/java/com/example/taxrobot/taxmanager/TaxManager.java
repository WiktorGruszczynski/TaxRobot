package com.example.taxrobot.taxmanager;

import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import com.example.taxrobot.taxmanager.resources.tables.WageStatementTable;
import com.example.taxrobot.tools.Keyboard;
import com.example.taxrobot.tools.Launcher;



public class TaxManager {
    private final Launcher launcher;
    private final String EXTENSION = ".ptax23";
    public PersonalData personalData = new PersonalData();
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

    private void nextPage(){
        Keyboard.alrRight();
        Keyboard.sleep(800);
    }

    private void next(){
        Keyboard.tab();
    }


    private void openTargetWindow(){
        Keyboard.sleep(500);
        Keyboard.space();

        Keyboard.sleep(6500);

        Keyboard.tab(2);

        Keyboard.space();

        Keyboard.sleep(2500);
    }

    public void fill(){
        launcher.start();
        launcher.listen();

        openTargetWindow();

        personalData.fill();

        nextPage();
        next();

        wageStatementTable.fill();

        launcher.stop();
    }
}
