package com.example.ange.angeunit.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScope {
}
