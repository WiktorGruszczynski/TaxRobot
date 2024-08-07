package com.example.taxrobot.taxmanager.schemas.input;

public abstract class Input<T> {
    private final boolean required;
    protected T value;

    public Input(boolean required){
        this.required = required;
    }

    public boolean isRequired(){
        return required;
    }

    public void set(T value){
        this.value = value;
    }

    public T get(){
        return value;
    }

    public String toString(){
        return String.valueOf(value);
    }
}
