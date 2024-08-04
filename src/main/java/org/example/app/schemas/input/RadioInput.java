package org.example.app.schemas.input;

public class RadioInput extends Input<Boolean> {
    public RadioInput(){
        super(false);
    }

    public RadioInput(boolean required){
        super(required);
    }

}
