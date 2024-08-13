package com.example.taxrobot.taxmanager.schemas.input;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Select {
    boolean required() default false;
    String options();
}
