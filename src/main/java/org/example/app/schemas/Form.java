package org.example.app.schemas;


import org.example.app.schemas.input.Input;
import org.example.app.schemas.input.RadioInput;
import org.example.app.schemas.input.Select;
import org.example.app.schemas.input.TextInput;
import org.example.tools.DataReader;
import org.example.tools.Keyboard;

import java.lang.reflect.Field;
import java.util.Map;


public class Form {
    private String className;


    private void setTextInput(Field field, String value) throws IllegalAccessException {
        TextInput textInput = (TextInput) field.get(this);
        textInput.set(value);
    }

    private void setRadioInput(Field field, String value) throws IllegalAccessException {
        RadioInput radioInput = (RadioInput) field.get(this);
        value = value.toLowerCase();

        if (value.equals("true") || value.equals("1")){
            radioInput.set(true);
        }
        else {
            if (value.equals("false") || value.equals("0")){
                radioInput.set(false);
            }
            else {
                throw new RuntimeException("RadioInput has incorrect value: " + field.getName());
            }
        }
    }


    private void setSelectOption(Field field, String value) throws IllegalAccessException {
        Select select = (Select) field.get(this);
        select.set(value);

        if (select.getIndex() == -1){
            throw new RuntimeException(value + " is not present in options - " + className);
        }
    }


    private boolean isFieldRequired(Field field) throws IllegalAccessException {
        return ((Input<?>) field.get(this)).isRequired();
    }


    private void setField(Field field, String value) throws IllegalAccessException {
        if (isFieldRequired(field) && value==null){
            throw new RuntimeException("Missing required field: " + className + "." + field.getName());
        }

        if (value == null) return;

        if (field.getType() == TextInput.class){
            setTextInput(field, value);
        }

        if (field.getType() == RadioInput.class){
            setRadioInput(field, value);
        }

        if (field.getType() == Select.class){
            setSelectOption(field, value);
        }
    }


    public void loadFromFile(String path){
        Map<String, String> map = DataReader.getDataMapFromFile(path);
        className = this.getClass().getName();


        for (Field field: this.getClass().getDeclaredFields()){
            String fieldName = field.getName();
            String value = map.get(fieldName);
            field.setAccessible(true);

            try{
                setField(field, value);
            }
            catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }


    public String toString(){
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder("{\n");

        for (Field field: fields){
            field.setAccessible(true);
            try {
                stringBuilder.append("\t" + field.getName() + ": " +  field.get(this) + ",\n");
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        stringBuilder.append("\n}");
        return stringBuilder.toString();
    }



    protected void fillInput(Input<?> input){
        if (input.get() != null){
            if (input.getClass() == TextInput.class){
                TextInput textInput = (TextInput) input;
                Keyboard.writeText(textInput.get());
            }

            if (input.getClass() == RadioInput.class){
                RadioInput radioInput = (RadioInput) input;
                if (radioInput.get()){
                    Keyboard.arrowRight(2);
                }
                else{
                    Keyboard.arrowRight();
                }
            }

            if (input.getClass() == Select.class){
                Select select = (Select) input;
                Keyboard.arrowDown(select.getIndex()+2);
            }
        }

        move();
    }


    protected void open(){
        Keyboard.space();
    }

    protected void close(){
        Keyboard.space();
    }

    protected void move(){
        Keyboard.tab();
    }

}