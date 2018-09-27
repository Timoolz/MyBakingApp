package com.olamide.mybakingapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Recipe;
import com.olamide.mybakingapp.fragment.IngredientsFragment;
import com.olamide.mybakingapp.fragment.StepsFragment;
import com.olamide.mybakingapp.utils.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class IngredientAndStepsActivity extends AppCompatActivity implements StepsFragment.OnFragmentInteractionListener{

    public Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_and_steps);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            try {
                Intent i = getIntent();
                recipe = i.getParcelableExtra(MainActivity.RECIPE_STRING);
                Timber.e(recipe.getName());
                this.setTitle(recipe.getName());

            } catch (Exception ex) {
                Timber.e(ex);
                Toast.makeText(this,"An Error Has Occured", Toast.LENGTH_LONG).show();
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.RECIPE_STRING, recipe);
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.steps_container, stepsFragment)
                .commit();


        if(Utils.isTablet(this)){
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.ingredients_details_container, ingredientsFragment)
                    .commit();
        }

    }


    @OnClick(R.id.tv_ingredient)
    void displayIngredientDetails(){

        Toast.makeText(this,"Ingredients are to be displayed", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.RECIPE_STRING, recipe);
        if(Utils.isTablet(getApplicationContext())){

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredients_details_container, ingredientsFragment)
                    .commit();
        }else {
            Intent intent = new Intent(this, IngredientDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
