package com.olamide.mybakingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.olamide.mybakingapp.BundleConstants;
import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.adapter.RecipeAdapter;
import com.olamide.mybakingapp.bean.Recipe;
import com.olamide.mybakingapp.utils.network.RecipeService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickListener{



    private Call<List<Recipe>> recipeCall;

    List<Recipe> recipeList = new ArrayList<>();
    GridLayoutManager layoutManager;
    RecipeAdapter recipeAdapter;


    @BindView(R.id.rv_recipe)
    RecyclerView mRvRecipeList;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.button_retry)
    Button retryButton;
    @BindView(R.id.tv_connectionError)
    TextView textViewConnectionError;
    @BindView(R.id.textView_welcome)
    TextView textViewWelcome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        int spanCount = this.getResources().getInteger(R.integer.span_count);

        layoutManager = new GridLayoutManager(this, spanCount);
        mRvRecipeList.setLayoutManager(layoutManager);
        recipeAdapter = new RecipeAdapter(recipeList,this,R.layout.recipe_item,this);
        mRvRecipeList.setAdapter(recipeAdapter);

        if( savedInstanceState != null){
            recipeList = savedInstanceState.getParcelableArrayList(BundleConstants.RECIPE_LIST_STRING);
            recipeAdapter.setRecipeList(recipeList);
            stopLoading();
        }else {
            getRecipeList();
        }


    }




    private void getRecipeList() {


        startLoading();
        recipeCall = RecipeService.getRecipes();
        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = response.body();
                //Timber.e(ToStringBuilder.reflectionToString(response.body()));
                recipeAdapter.setRecipeList(recipeList);
                stopLoading();
                //Toast.makeText(getApplicationContext(),"success",  Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e(t);
                showError();
                //Toast.makeText(getApplicationContext(),"failure",  Toast.LENGTH_LONG).show();

            }

        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(BundleConstants.RECIPE_LIST_STRING, (ArrayList<? extends Parcelable>) recipeList);
    }

    @Override
    public void onClickListener(Recipe recipe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(BundleConstants.TYPE_STRING,"ingre");
        editor.apply();

        Intent intent = new Intent(this, IngredientAndStepsActivity.class);
        intent.putExtra(BundleConstants.RECIPE_STRING, recipe);
        startActivity(intent);
    }


    private void startLoading() {
        textViewWelcome.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);
        textViewConnectionError.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {
        textViewWelcome.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        textViewWelcome.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.VISIBLE);
        textViewConnectionError.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
    }



}
