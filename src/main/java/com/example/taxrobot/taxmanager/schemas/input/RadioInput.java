package com.example.taxrobot.taxmanager.schemas.input;

public class RadioInput extends Input<Boolean> {
    public RadioInput(){
        super(false);
    }

    public RadioInput(boolean required){
        super(required);
    }

}
