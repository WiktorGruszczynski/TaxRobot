package com.example.taxrobot.taxmanager.resources.forms;

import com.example.taxrobot.app.personalData.PersonalDataEntity;
import com.example.taxrobot.taxmanager.schemas.form.Form;
import com.example.taxrobot.taxmanager.schemas.input.RadioInput;
import com.example.taxrobot.taxmanager.schemas.input.Select;
import com.example.taxrobot.taxmanager.schemas.input.TextInput;
import com.example.taxrobot.taxmanager.util.Options;



public class PersonalData extends Form {
    private TextInput name = new TextInput(true);
    private TextInput vorname = new TextInput(true);
    private TextInput strasse = new TextInput();
    private TextInput nummer = new TextInput();
    private TextInput zusatz = new TextInput();
    private TextInput PLZ = new TextInput();
    private TextInput ort = new TextInput();
    private TextInput geburtsdatum = new TextInput('.');
    private TextInput AHVN13 = new TextInput('.', true);
    private Select zivilstand = new Select(Options.ZIVILSTAND, true);
    private Select konfession = new Select(Options.KONFESSION, true);
    private TextInput beruf = new TextInput();
    private TextInput telefon = new TextInput();
    private TextInput email = new TextInput();
    private TextInput PID = new TextInput(true);
    private RadioInput pensionskasse = new RadioInput(true);
    private Select gemeinde = new Select(Options.GEMEINDE, true);
    private Select gemeinde2 = new Select(Options.GEMEINDE);


    public void fill(){
        open();

        fillInput(name);
        fillInput(vorname);
        fillInput(strasse);
        fillInput(nummer);
        fillInput(zusatz);
        fillInput(PLZ);
        fillInput(ort);
        fillInput(geburtsdatum);
        fillInput(AHVN13);
        fillInput(zivilstand);
        fillInput(konfession);
        fillInput(beruf);
        fillInput(telefon);
        fillInput(email);

        move();

        fillInput(PID);
        fillInput(pensionskasse);

        move();

        fillInput(gemeinde);
        fillInput(gemeinde2);

        close();
    }
}