package com.example.taxrobot.taxmanager.schemas.form;


import com.example.taxrobot.taxmanager.schemas.input.Input;
import com.example.taxrobot.taxmanager.schemas.input.RadioInput;
import com.example.taxrobot.taxmanager.schemas.input.Select;
import com.example.taxrobot.taxmanager.schemas.input.TextInput;
import com.example.taxrobot.tools.DataReader;
import com.example.taxrobot.tools.Keyboard;


import java.lang.reflect.Field;
import java.util.*;


public abstract class Form{
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
                throw new RuntimeException(className + "  RadioInput has incorrect value: " + field.getName());
            }
        }
    }


    private void setSelectOption(Field field, String value) throws IllegalAccessException {
        Select select = (Select) field.get(this);
        select.set(value);


        if (select.getIndex() == -1){
            throw new RuntimeException(className + "  " + value + " is not present in options - " + Arrays.toString(select.options));
        }
    }


    private boolean isFieldRequired(Field field) throws IllegalAccessException {
        return ((Input<?>) field.get(this)).isRequired();
    }


    private void setField(Field field, String value) throws IllegalAccessException {

        if (isFieldRequired(field) && Objects.isNull(value)){
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


    public boolean isEmpty(){
        for (Field field: this.getClass().getDeclaredFields()){
            try {
                field.setAccessible(true);

                if (Input.class.isAssignableFrom(field.getType())){
                    Input<?> input = (Input<?>) field.get(this);
                    if (input.get() != null){
                        return false;
                    }
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return true;
    }


    public void loadFromMap(Map<String, String> map) {
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

    public void loadFromEntity(Object object){
        Map<String, String> map = new HashMap<>();

        for (Field field: object.getClass().getDeclaredFields()){
            try {
                field.setAccessible(true);

                String name = field.getName();
                String value = Objects.toString(field.get(object), null);

                map.put(name, value);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        loadFromMap(map);
    }

    public void loadFromFile(String path){
        loadFromMap(DataReader.getDataMapFromFile(path));
    }


    public String toString(){
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder("{\n");

        for (int i=0; i<fields.length; i++){
            Field field = fields[i];
            field.setAccessible(true);
            try {
                stringBuilder
                        .append("\t")
                        .append(field.getName())
                        .append(": ")
                        .append(field.get(this));

                if (i<fields.length-1){
                    stringBuilder.append(",\n");
                }
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
                int movesAmount = select.getIndex()+2;
                int arrowMoves = movesAmount%8;
                int pageDownMoves = (movesAmount - arrowMoves)/8;

                Keyboard.pageDown(pageDownMoves);
                Keyboard.arrowDown(arrowMoves);
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

    protected void move(int steps){
        Keyboard.tab(steps);
    }

    protected void sleep(int ms){
        Keyboard.sleep(ms);
    }

    public abstract void fill();

}