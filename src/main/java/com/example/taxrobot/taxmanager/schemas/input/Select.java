package com.example.taxrobot.taxmanager.schemas.input;


import java.util.Objects;


public class Select extends Input<String>{
    public final String[] options;


    public Select(String[] options){
        super(false);
        this.options = options;
    }

    public Select(String[] options, boolean required){
        super(required);
        this.options = options;
    }

    @Override
    public void set(String option){
        this.value = option.trim();
    }


    public int getIndex(){
        for (int i=0; i<options.length; i++) {
            if (Objects.equals(options[i], value)){
                return i;
            }
        }

        return -1;
    }
}
