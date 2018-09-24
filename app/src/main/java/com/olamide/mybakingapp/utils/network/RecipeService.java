package com.olamide.mybakingapp.utils.network;

import com.olamide.mybakingapp.bean.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RecipeService {


    public static Call<List<Recipe>> getRecipes( ){
       return RetrofitBuilder.getRecipesApi().getRecipes();
    }
}
