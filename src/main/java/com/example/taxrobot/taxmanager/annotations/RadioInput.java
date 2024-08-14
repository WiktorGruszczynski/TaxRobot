package com.example.taxrobot.taxmanager.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RadioInput {
    boolean required() default false;
}
