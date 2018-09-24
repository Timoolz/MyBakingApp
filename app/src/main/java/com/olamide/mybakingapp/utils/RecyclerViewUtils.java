package com.olamide.mybakingapp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import timber.log.Timber;

public class RecyclerViewUtils {



    public static int getSpanCount(final RecyclerView recyclerView, final float cardWidth ){

        WindowManager wm = (WindowManager) recyclerView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;


        int newSpanCount = (int) Math.floor(width / cardWidth);
        int spanCount = newSpanCount;
//        Timber.e(TAG, "width  " +width);
//        Timber.e(TAG, "cardWidth  %s", cardWidth);
//        Timber.e(TAG, "Spancount  %s", spanCount);

        return spanCount;
    }



}
