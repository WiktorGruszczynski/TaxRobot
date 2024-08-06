package org.example;



import org.example.app.TaxManager;
import org.example.tools.launcher.Launcher;



class Main {
    private final static Launcher launcher = new Launcher();
    private final static TaxManager taxManager = new TaxManager(launcher);

    public static void main(String[] args) {
        launcher.start();
        launcher.listen();

        taxManager.personalData.loadFromFile("personal-data.txt");
        taxManager.wageStatementTable.loadFromFile("wage-statement.txt");

        taxManager.fill();
    }
}
