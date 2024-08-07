package org.example;



import org.example.app.TaxManager;
import org.example.tools.Launcher;

import java.util.Date;


class Main {
    private final static Launcher launcher = new Launcher();
    private final static TaxManager taxManager = new TaxManager(launcher);

    public static void main(String[] args) {
        long start, end;

        start = new Date().getTime();

        launcher.start();
        launcher.listen();

        taxManager.personalData.loadFromFile("personal-data.txt");
        taxManager.wageStatementTable.loadFromFile("wage-statement.txt");


        taxManager.fill();

        end = new Date().getTime();
        System.out.println("Program executed in " + (end-start) + " ms ");


    }
}
