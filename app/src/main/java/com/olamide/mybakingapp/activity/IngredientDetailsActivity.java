package com.olamide.mybakingapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Recipe;
import com.olamide.mybakingapp.fragment.IngredientsFragment;

import butterknife.ButterKnife;
import timber.log.Timber;

public class IngredientDetailsActivity extends AppCompatActivity {

    public Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);

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

        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.RECIPE_STRING, recipe);

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ingredients_details_container__, ingredientsFragment)
                .commit();
    }
}
