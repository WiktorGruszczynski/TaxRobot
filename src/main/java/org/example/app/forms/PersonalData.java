package org.example.app.forms;

import org.example.app.schemas.Form;
import org.example.app.schemas.input.RadioInput;
import org.example.app.schemas.input.TextInput;
import org.example.app.schemas.input.Select;
import org.example.app.util.Options;


public class PersonalData extends Form{
    private final TextInput name = new TextInput(true);
    private final TextInput vorname = new TextInput(true);
    private final TextInput strasse = new TextInput();
    private final TextInput nummer = new TextInput();
    private final TextInput zusatz = new TextInput();
    private final TextInput PLZ = new TextInput();
    private final TextInput ort = new TextInput();
    private final TextInput geburtsdatum = new TextInput('.');
    private final TextInput AHVN13 = new TextInput('.', true);
    private final Select zivilstand = new Select(Options.ZIVILSTAND, true);
    private final Select konfession = new Select(Options.KONFESSION, true);
    private final TextInput beruf = new TextInput();
    private final TextInput telefon = new TextInput();
    private final TextInput email = new TextInput();
    private final TextInput PID = new TextInput(true);
    private final RadioInput pensionskasse = new RadioInput(true);
    private final Select gemeinde = new Select(Options.GEMEINDE, true);
    private final Select gemeinde2 = new Select(Options.GEMEINDE);



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
