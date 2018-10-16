package com.olamide.mybakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.olamide.mybakingapp.BundleConstants;
import com.olamide.mybakingapp.bean.Ingredient;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 *
 * helper methods.
 */
public class IngredientUpdateService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS

    private List<Ingredient> mIngredients;
    public static final String ACTION_UPDATE_INGREDIENT_WIDGET = "com.olamide.mybakingapp.widget.action.update_ing_widget";



    public IngredientUpdateService() {
        super("IngredientUpdateService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getAction().equals(ACTION_UPDATE_INGREDIENT_WIDGET))
        {
            Bundle bundle = intent.getBundleExtra(BundleConstants.BUNDLE);
            mIngredients = bundle.getParcelableArrayList(BundleConstants.INGREDIENTS);


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidget.class));
            IngredientWidget.updateAppWidget(this, appWidgetManager, appWidgetIds,mIngredients);
        }
    }


}
