package com.olamide.mybakingapp.utils.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public static final String RECIPES_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static Retrofit retrofit = null;
    private static RecipesAPI recipesAPI = getRecipeClient().create(RecipesAPI.class);


    public static Retrofit getRecipeClient() {
        if (retrofit==null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(RECIPES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


    public static RecipesAPI getRecipesApi(){
        return recipesAPI;
    }

}
