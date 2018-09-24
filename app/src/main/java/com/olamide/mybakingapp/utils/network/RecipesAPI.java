package com.olamide.mybakingapp.utils.network;

import com.olamide.mybakingapp.bean.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesAPI {


    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
