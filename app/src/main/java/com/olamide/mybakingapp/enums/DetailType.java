package com.olamide.mybakingapp.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DetailType {

    public static final String STEP = "STEP";
    public static final String INGREDIENT = "INGREDIENT";

    @StringDef({STEP, INGREDIENT})
    @Retention(RetentionPolicy.SOURCE)

    public @interface DetType {}

    public final String detailType;

    public DetailType(@DetType String detailType) {
        this.detailType = detailType;
    }


}



