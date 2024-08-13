package com.example.taxrobot.taxmanager.schemas.input;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RadioInput {
    boolean required() default false;
}
