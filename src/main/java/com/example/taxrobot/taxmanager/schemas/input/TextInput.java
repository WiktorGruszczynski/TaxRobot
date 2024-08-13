package com.example.taxrobot.taxmanager.schemas.input;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TextInput {
    boolean required() default false;
}
