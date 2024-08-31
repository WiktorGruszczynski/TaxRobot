package com.example.taxrobot.taxmanager;

import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import com.example.taxrobot.taxmanager.resources.tables.WageStatementTable;
import com.example.taxrobot.tools.Keyboard;
import com.example.taxrobot.tools.Launcher;
import lombok.Getter;

import java.util.Date;


public class TaxManager {
    private final Launcher launcher;
    public PersonalData personalData = new PersonalData();
    public final WageStatementTable wageStatementTable = new WageStatementTable();

    @Getter
    private String filename;

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
        Keyboard.sleep(2000);
    }

    private void next(){
        Keyboard.tab();
    }


    private void openTargetWindow(){
        Keyboard.sleep(500);
        Keyboard.space();

        Keyboard.sleep(7000);

        Keyboard.tab(2);

        Keyboard.space();

        Keyboard.sleep(2500);
    }

    public void save(){
        long currentTime = new Date().getTime()%1000;

        filename = personalData.getName()+ "_" +personalData.getVorname() + "_" + currentTime +".ptax23";

        Keyboard.sleep(300);

        Keyboard.altF4();
        Keyboard.sleep(1500);
        Keyboard.space();
        Keyboard.sleep(1500);
        Keyboard.writeText(filename);
        Keyboard.tab(2);
        Keyboard.space();
    }

    public void fill(){
        launcher.start();
        launcher.listen();

        openTargetWindow();

        personalData.fill();

        nextPage();
        next();

        wageStatementTable.fill();

        save();

        launcher.stop();
    }
}
