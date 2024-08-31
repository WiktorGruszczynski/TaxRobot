package com.example.taxrobot.taxmanager.resources.forms;

import com.example.taxrobot.taxmanager.schemas.Form;
import com.example.taxrobot.taxmanager.annotations.RadioInput;
import com.example.taxrobot.taxmanager.annotations.Select;
import com.example.taxrobot.taxmanager.annotations.TextInput;
import com.example.taxrobot.taxmanager.util.Options;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personal_data")
public class PersonalData extends Form {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @TextInput(required = true)
    private String name;

    @Column(nullable = false)
    @TextInput(required = true)
    private String vorname;

    @TextInput
    private String strasse;

    @TextInput
    private String nummer;

    @TextInput
    private String zusatz;

    @Column(name = "PLZ")
    @JsonProperty("PLZ")
    @TextInput
    private String PLZ;

    @Column(nullable = false)
    @TextInput(required = true)
    private String ort;

    @TextInput
    private Date geburtsdatum;

    @Column(nullable = false, name = "AHVN13")
    @JsonProperty("AHVN13")
    @TextInput(required = true)
    private String AHVN13;

    @Column(nullable = false)
    @Select(options = Options.ZIVILSTAND, required = true)
    private String zivilstand;

    @Column(nullable = false)
    @Select(options = Options.KONFESSION, required = true)
    private String konfession;

    @TextInput
    private String beruf;

    @TextInput
    private String telefon;

    @TextInput
    private String email;

    @Column(name = "PID", nullable = false)
    @JsonProperty("PID")
    @TextInput(required = true)
    private String PID;

    @Column(nullable = false)
    @RadioInput(required = true)
    private Boolean pensionskasse;

    @Column(nullable = false)
    @Select(options = Options.GEMEINDE, required = true)
    private String gemeinde;

    @Select(options = Options.GEMEINDE)
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
