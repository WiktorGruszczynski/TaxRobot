package org.example.app.schemas.input;


public class TextInput extends Input<String> {
    private final char ignore;


    public TextInput(){
        super(false);
        this.ignore = (char) 0;
    }

    public TextInput(boolean required){
        super(required);
        this.ignore = (char) 0;
    }

    public TextInput(char ignore){
        super(false);
        this.ignore = ignore;
    }

    public TextInput(char ignore, boolean required){
        super(required);
        this.ignore = ignore;
    }


    @Override
    public String get(){
        if (value != null && ignore != (char) 0){
            return value.replace(String.valueOf(ignore), "");
        }
        return value;
    }

}
