package com.example.taxrobot.taxmanager.resources.forms;

import com.example.taxrobot.taxmanager.schemas.form.Form;
import com.example.taxrobot.taxmanager.schemas.input.TextInput;


public class WageStatement extends Form {
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
