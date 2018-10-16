package com.olamide.mybakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Ingredient;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {

    public static List<Ingredient> mIngredients;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetIds[], List<Ingredient> ingredients) {

        mIngredients = ingredients;
        for (int appWidgetId : appWidgetIds)
        {
            Intent intent = new Intent(context, ingredientViewsService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
            views.setRemoteAdapter(R.id.lv_ingredient, intent);
            ComponentName component = new ComponentName(context, IngredientWidget.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_ingredient);
            appWidgetManager.updateAppWidget(component, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

