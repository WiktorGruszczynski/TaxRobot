package com.example.taxrobot.taxmanager.schemas.form;


import com.example.taxrobot.taxmanager.schemas.input.RadioInput;
import com.example.taxrobot.taxmanager.schemas.input.Select;
import com.example.taxrobot.taxmanager.schemas.input.TextInput;
import com.example.taxrobot.taxmanager.util.Options;
import com.example.taxrobot.tools.DataReader;
import com.example.taxrobot.tools.Keyboard;
import java.lang.reflect.Field;
import java.util.*;


public abstract class Form{
    private String className;

    public boolean isEmpty(){
        for (Field field: this.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if (field.get(this)!=null){
                    return true;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

    private void setTextInput(Field field, String value) throws IllegalAccessException {
        field.set(this, value);
    }

    private void setRadioInput(Field field, String value) throws IllegalAccessException {
        value = value.toLowerCase();

        if (value.equals("true") || value.equals("1")){
            field.set(this, true);
        }
        else {
            if (value.equals("false") || value.equals("0")){
                field.set(this, false);
            }
            else {
                throw new RuntimeException(className + "  RadioInput has incorrect value: " + field.getName());
            }
        }
    }


    private String[] getSelectOptions(String optionsName){
        for (Field optionsField: Options.class.getDeclaredFields()) {
            if (optionsField.getName().equals(optionsName)) {
                try {
                    return  (String[]) optionsField.get(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    private void setSelectOption(Field field, String value) throws IllegalAccessException {
        String optionsName = field.getAnnotation(Select.class).options();

        for (String option: Objects.requireNonNull(getSelectOptions(optionsName))){
            if (Objects.equals(option, value)){
                field.set(this, value);
                return;
            }
        }

        throw new RuntimeException(className + "  " + value + " is not present in options - " + optionsName);
    }

    private boolean isInput(Field field){
        return (field.isAnnotationPresent(TextInput.class) ||
                field.isAnnotationPresent(RadioInput.class) ||
                field.isAnnotationPresent(Select.class)
        );
    }

    private boolean isInput(Class<?> field){
        return (field.isAnnotationPresent(TextInput.class) ||
                field.isAnnotationPresent(RadioInput.class) ||
                field.isAnnotationPresent(Select.class)
        );
    }

    private boolean isRequired(Field field) {
        if (field.isAnnotationPresent(TextInput.class)){
            return field.getAnnotation(TextInput.class).required();
        }

        if (field.isAnnotationPresent(RadioInput.class)){
            return field.getAnnotation(RadioInput.class).required();
        }

        if (field.isAnnotationPresent(Select.class)){
            return field.getAnnotation(Select.class).required();
        }

        return false;
    }


    private void setField(Field field, String value) throws IllegalAccessException {
        if (isRequired(field) && Objects.isNull(value)){
            throw new RuntimeException("Missing required field: " + className + "." + field.getName());
        }

        if (value == null) return;

        if (field.isAnnotationPresent(TextInput.class)){
            setTextInput(field, value);
        }

        if (field.isAnnotationPresent(RadioInput.class)){
            setRadioInput(field, value);
        }

        if (field.isAnnotationPresent(Select.class)){
            setSelectOption(field, value);
        }
    }


    public void loadFromMap(Map<String, String> map) {
        className = this.getClass().getName();

        for (Field field: this.getClass().getDeclaredFields()){
            String value = map.get(field.getName());

            if (isInput(field)){
                field.setAccessible(true);

                try{
                    setField(field, value);
                }
                catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadFromEntity(Object object){
        Map<String, String> map = new HashMap<>();

        for (Field field: object.getClass().getDeclaredFields()){
            try {
                field.setAccessible(true);

                String name = field.getName();
                String value = Objects.toString(field.get(object), null).trim();

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

            if (!isInput(field)) continue;

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


    private void fillTextInput(String value){
        Keyboard.writeText(value);
    }

    private void fillRadioInput(boolean value){
        if (value){
            Keyboard.arrowRight(2);
        }
        else {
            Keyboard.arrowRight();
        }
    }

    private void fillSelect(Field field){

    }


    protected void fillInput(Object input){
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field: fields){
            try {
                field.setAccessible(true);

                if (field.get(this)!=null && field.get(this)==input){
                    if (field.isAnnotationPresent(TextInput.class)){
                        fillTextInput((String) input);
                    }

                    if (field.isAnnotationPresent(RadioInput.class)){
                        fillRadioInput((boolean) input);
                    }

                    if (field.isAnnotationPresent(Select.class)){
                        String[] options = getSelectOptions(
                                field.getAnnotation(Select.class).options()
                        );
                        String inputValue = (String) input;
                        int index = 0;

                        if (options!=null){
                            for (int i=0; i< options.length; i++){
                                if (Objects.equals(options[i], inputValue)){
                                    index = i;
                                    break;
                                }
                            }
                            int moves = index+2;
                            int arrowMoves = moves%8;
                            int pageUpMoves = (moves - arrowMoves)/8;

                            Keyboard.pageDown(pageUpMoves);
                            Keyboard.arrowDown(arrowMoves);
                        }
                    }

                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
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