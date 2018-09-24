package com.olamide.mybakingapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Recipe;
import com.olamide.mybakingapp.utils.network.RecipeService;
import com.olamide.mybakingapp.utils.network.RecipesAPI;
import com.olamide.mybakingapp.utils.network.RetrofitBuilder;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private Call<List<Recipe>> recipeCall;

    List<Recipe> recipeList = new ArrayList<>();
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRecipeList();
    }




    private void getRecipeList() {


        recipeCall = RecipeService.getRecipes();
        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = response.body();
                Timber.e(ToStringBuilder.reflectionToString(response.body()));
                //Toast.makeText(getApplicationContext(),"success",  Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e(t);
                //Toast.makeText(getApplicationContext(),"failure",  Toast.LENGTH_LONG).show();

            }

        });
    }
}
