package com.example.taxrobot.taxmanager.resources.forms;

import com.example.taxrobot.taxmanager.schemas.form.Form;
import com.example.taxrobot.taxmanager.schemas.input.TextInput;


public class WageStatement extends Form {
    private TextInput von = new TextInput('.',true);
    private TextInput bis = new TextInput('.');
    private TextInput arbeitGeber = new TextInput();
    private TextInput nettolohn = new TextInput();

    public void fill(){
        fillInput(von);
        fillInput(bis);
        fillInput(arbeitGeber);
        fillInput(nettolohn);
    }
}
