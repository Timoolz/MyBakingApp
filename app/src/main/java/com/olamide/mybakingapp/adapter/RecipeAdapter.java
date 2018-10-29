package com.olamide.mybakingapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Context context;
    private List<Recipe> recipes;
    private Integer layoutToInflate;
    private final RecipeAdapterOnClickListener recipeOnClickListener;

    public RecipeAdapter(List<Recipe> recipes, Context context, int layout, RecipeAdapterOnClickListener recipeOnClickListener){
        this.recipes = recipes;
        this.context = context;
        this.layoutToInflate = layout;
        this.recipeOnClickListener = recipeOnClickListener;
    }


    public void setRecipeList(List<Recipe> recipeList){
        this.recipes = recipeList;
        notifyDataSetChanged();
    }


    public interface  RecipeAdapterOnClickListener {
        void onClickListener(Recipe recipe);
    }


    public  class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.tv_recipe_name)
        TextView tRecipeName;

        @BindView(R.id.tv_serving)
        TextView tServing;

        @BindView((R.id.iv_recipe))
        ImageView ivRecipe;


        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            recipeOnClickListener.onClickListener(recipes.get(adapterPosition));
        }
    }





    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutToInflate, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {

        Recipe recipe = recipes.get(holder.getAdapterPosition());

        //Set values
        holder.tRecipeName.setText(recipe.getName());
        holder.tServing.setText(recipe.getServings());

        if(!recipe.getImage().isEmpty()){
            holder.ivRecipe.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(Uri.parse(recipe.getImage()))
                    .placeholder(R.drawable.loader)
                    .error(R.drawable.bake_error)
                    .into(holder.ivRecipe);
        }

    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }
        return recipes.size();
    }
}
