package com.example.taxrobot.taxmanager.resources.forms;

import com.example.taxrobot.taxmanager.schemas.Form;
import com.example.taxrobot.taxmanager.annotations.TextInput;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wage_statement")
public class WageStatement extends Form {
    @Id
    @GeneratedValue
    private Long id;

    private Long personalDataId;

    @TextInput(required = true)
    private String von;

    @TextInput
    private String bis;

    @TextInput
    private String arbeitGeber;

    @TextInput
    private String nettolohn;


    public void fill(){
        fillInput(von);
        fillInput(bis);
        fillInput(arbeitGeber);
        fillInput(nettolohn);
    }
}
