package com.example.taxrobot.taxmanager.annotations;

import com.example.taxrobot.taxmanager.util.Options;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Select {
    boolean required() default false;
    Options options();
}
