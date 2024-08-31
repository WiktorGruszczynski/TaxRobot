package com.example.taxrobot.taxmanager.schemas;


import com.example.taxrobot.taxmanager.util.Options;
import com.example.taxrobot.taxmanager.annotations.RadioInput;
import com.example.taxrobot.taxmanager.annotations.Select;
import com.example.taxrobot.taxmanager.annotations.TextInput;
import com.example.taxrobot.tools.DataReader;
import com.example.taxrobot.tools.Keyboard;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;


public abstract class Form{
    private String className;

    @JsonIgnore
    public boolean isEmpty(){
        for (Field field: this.getClass().getDeclaredFields()){
            field.setAccessible(true);

            if (!isInput(field)) continue;

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


    /**
     * Checks if all required fields are not null
     */
    @JsonIgnore
    public boolean isValid(){
        for (Field field: this.getClass().getDeclaredFields()){
            field.setAccessible(true);

            try {
                if (isRequired(field) && field.get(this)==null){
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }



    private void setTextInput(Field field, Object value) throws IllegalAccessException {
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


    private void setSelectOption(Field field, String value) throws IllegalAccessException {
        Options optionsEnum = field.getAnnotation(Select.class).options();
        String[] options = optionsEnum.getOptions();

        for (String option: options){
            if (Objects.equals(option, value)){
                field.set(this, value);
                return;
            }
        }

        throw new RuntimeException(className + "  " + value + " is not present in options - " + optionsEnum);
    }

    /**
    * Checks if field of an object is annotated as an input
    *
     */
    private boolean isInput(Field field){
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


    private void setField(Field field, Object value) throws IllegalAccessException {
        if (isRequired(field) && Objects.isNull(value)){
            throw new RuntimeException("Missing required field: " + className + "." + field.getName());
        }

        if (value == null) return;

        if (field.isAnnotationPresent(TextInput.class)){
            setTextInput(field, value);
        }

        if (field.isAnnotationPresent(RadioInput.class)){
            setRadioInput(field, String.valueOf(value));
        }

        if (field.isAnnotationPresent(Select.class)){
            setSelectOption(field, String.valueOf(value));
        }
    }


    public void loadFromMap(Map<String, Object> map) {
        className = this.getClass().getName();

        for (Field field: this.getClass().getDeclaredFields()){
            Object value = map.get(field.getName());

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

    /**
     * Loads form from another object
     *
     */
    public void loadFromEntity(Object object){
        Map<String, Object> map = new HashMap<>();

        for (Field field: object.getClass().getDeclaredFields()){
            try {
                field.setAccessible(true);

                String name = field.getName();
                Object value = field.get(object);

                map.put(name, value);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        loadFromMap(map);
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

    private String dateToString(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);

        String dayStr, monthStr;

        if (day<10){
            dayStr = "0"+day;
        }
        else{
            dayStr = String.valueOf(day);
        }

        if (month<10){
            monthStr = "0"+month;
        }
        else{
            monthStr = String.valueOf(month);
        }

        return dayStr + "." + monthStr + "." + year;
    }


    private void fillTextInput(Object value){
        if (value instanceof Date date){
            Keyboard.writeText(
                    dateToString(date)
            );
        }
        else{
            Keyboard.writeText(String.valueOf(value));
        }
    }

    private void fillRadioInput(boolean value){
        if (value){
            Keyboard.arrowRight(2);
        }
        else {
            Keyboard.arrowRight();
        }
    }

    private void fillSelect(Field field, String inputValue){
        String[] options = field.getAnnotation(Select.class).options().getOptions();
        int index = 0;

        for (int i = 0; i < options.length; i++) {
            if (Objects.equals(options[i], inputValue)) {
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


    protected void fillInput(Object input){
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field: fields){
            try {
                field.setAccessible(true);

                if (field.get(this)!=null && field.get(this)==input){
                    if (field.isAnnotationPresent(TextInput.class)){
                        fillTextInput(input);
                    }

                    if (field.isAnnotationPresent(RadioInput.class)){
                        fillRadioInput((boolean) input);
                    }

                    if (field.isAnnotationPresent(Select.class)){
                        fillSelect(field, (String) input);
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