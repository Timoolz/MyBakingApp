package com.olamide.mybakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Ingredient;

import java.util.List;



/**
 * service class that will update our ListView Widget
 */
public class ingredientViewsService extends RemoteViewsService
{

    /**
     * @param intent intent that triggered this service
     * @return new ListViewsFactory Object with the appropriate implementation
     */
    public IngredientViewsFactory onGetViewFactory(Intent intent)
    {
        return new IngredientViewsFactory(this.getApplicationContext());
    }
}



 class IngredientViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientViewsFactory(Context mContext)
    {
        this.mContext = mContext;
    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mIngredients = IngredientWidget.mIngredients;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredients == null)
            return 0;
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);

        views.setTextViewText(R.id.tv_ingredient_name, mIngredients.get(i).getIngredient());
        views.setTextViewText(R.id.tv_ingredient_measure, mIngredients.get(i).getMeasure());
        views.setTextViewText(R.id.tv_ingredient_quantity, mIngredients.get(i).getQuantity());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
