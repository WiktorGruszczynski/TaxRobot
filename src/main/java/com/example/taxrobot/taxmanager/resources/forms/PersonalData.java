package com.example.taxrobot.taxmanager.resources.forms;

import com.example.taxrobot.taxmanager.schemas.form.Form;
import com.example.taxrobot.taxmanager.schemas.input.RadioInput;
import com.example.taxrobot.taxmanager.schemas.input.Select;
import com.example.taxrobot.taxmanager.schemas.input.TextInput;
import lombok.Getter;


@Getter
public class PersonalData extends Form {
    private Long id;

    @TextInput(required = true)
    private String name;

    @TextInput(required = true)
    private String vorname;

    @TextInput
    private String strasse;

    @TextInput
    private String nummer;

    @TextInput
    private String zusatz;

    @TextInput
    private String PLZ;

    @TextInput(required = true)
    private String ort;

    @TextInput
    private String geburtsdatum;

    @TextInput(required = true)
    private String AHVN13;

    @Select(options = "ZIVILSTAND", required = true)
    private String zivilstand;

    @Select(options = "KONFESSION", required = true)
    private String konfession;

    @TextInput
    private String beruf;

    @TextInput
    private String telefon;

    @TextInput
    private String email;

    @TextInput(required = true)
    private String PID;

    @RadioInput(required = true)
    private Boolean pensionskasse;

    @Select(options = "GEMEINDE", required = true)
    private String gemeinde;

    @Select(options = "GEMEINDE")
    private String gemeinde2;


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
