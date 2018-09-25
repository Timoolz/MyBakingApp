package com.olamide.mybakingapp.utils;

import android.content.Context;

import com.olamide.mybakingapp.R;


public  class Utils {
    public static boolean isTablet (Context context) {
      return  context.getResources().getBoolean(R.bool.isTablet);
      }
}
