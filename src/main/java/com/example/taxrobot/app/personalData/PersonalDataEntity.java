package com.example.taxrobot.app.personalData;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.*;



import java.util.Date;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "personal_data")
public class PersonalDataEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String vorname;
    private String strasse;
    private String nummer;
    private String zusatz;

    @Column(name = "PLZ")
    @JsonAlias("PLZ")
    private String PLZ;
    private String ort;

    @Column(nullable = false)
    private Date geburtsdatum;

    @Column(nullable = false, name = "AHVN13")
    @JsonAlias("AHVN13")
    private String AHVN13;

    @Column(nullable = false)
    private String zivilstand;

    @Column(nullable = false)
    private String konfession;
    private String beruf;

    @Column(length = 10)
    private String telefon;
    private String email;

    @Column(nullable = false, name = "PID")
    @JsonAlias("PID")
    private String PID;

    @Column(nullable = false)
    private boolean pensionskasse;

    @Column(nullable = false)
    private String gemeinde;


    private String gemeinde2;

}