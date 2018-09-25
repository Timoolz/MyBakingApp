package com.olamide.mybakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {
    private Context context;
    private List<Ingredient> ingredients;
    private Integer layoutToInflate;


    public IngredientAdapter(List<Ingredient> ingredients, Context context, int layout){
        this.ingredients = ingredients;
        this.context = context;
        this.layoutToInflate = layout;

    }


    public void setIngredientList(List<Ingredient> ingredientList){
        this.ingredients = ingredientList;
        notifyDataSetChanged();
    }




    public  class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_ingredient_name)
        TextView tIngredientName;

        @BindView(R.id.tv_ingredient_measure)
        TextView tMeasure;

        @BindView(R.id.tv_ingredient_quantity)
        TextView tQuantity;


        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }





    @NonNull
    @Override
    public IngredientAdapter.IngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutToInflate, parent, false);
        return new IngredientAdapter.IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientAdapterViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(holder.getAdapterPosition());

        //Set values
        holder.tIngredientName.setText(ingredient.getIngredient());
        holder.tMeasure.setText(ingredient.getMeasure());
        holder.tQuantity.setText(ingredient.getQuantity());

    }

    @Override
    public int getItemCount() {
        if (ingredients == null) {
            return 0;
        }
        return ingredients.size();
    }

}
