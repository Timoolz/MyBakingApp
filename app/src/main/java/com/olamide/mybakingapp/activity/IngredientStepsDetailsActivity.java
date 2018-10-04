package com.olamide.mybakingapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.olamide.mybakingapp.BundleConstants;
import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Recipe;
import com.olamide.mybakingapp.bean.Step;
import com.olamide.mybakingapp.fragment.IngredientsFragment;
import com.olamide.mybakingapp.fragment.StepDetailsFragment;

import butterknife.ButterKnife;
import timber.log.Timber;

public class IngredientStepsDetailsActivity extends AppCompatActivity implements StepDetailsFragment.OnFragmentInteractionListener {

    public Recipe recipe;

    private String currentType;

    private int currentStepInt = 0;
    private Step currentStep;

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

                currentType = i.getStringExtra(BundleConstants.TYPE_STRING);
                if(currentType.equals("step")){
                    currentStep = i.getParcelableExtra(BundleConstants.STEP_STRING);
                }
                recipe = i.getParcelableExtra(BundleConstants.RECIPE_STRING);
                currentStepInt = i.getIntExtra(BundleConstants.STEP_INT,0);

            } catch (Exception ex) {
                Timber.e(ex);
                Toast.makeText(this,"An Error Has Occured", Toast.LENGTH_LONG).show();
            }
        }


        if (savedInstanceState != null){
            recipe = savedInstanceState.getParcelable(BundleConstants.RECIPE_STRING);
            currentType = savedInstanceState.getString(BundleConstants.TYPE_STRING);
//            if(currentType.equals("step")){
//                currentStep = savedInstanceState.getParcelable(BundleConstants.STEP_STRING);
//                currentStepInt = savedInstanceState.getInt(BundleConstants.STEP_INT);
//            }
        }

        //Timber.e(recipe.getName());
        this.setTitle(recipe.getName());

        if(currentType.equals("ingre")){

            Bundle bundle = new Bundle();
            bundle.putParcelable(BundleConstants.RECIPE_STRING, recipe);

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredients_details_container__, ingredientsFragment)
                    .commit();

        }else if(currentType.equals("step")){

            Bundle bundle = new Bundle();
            bundle.putParcelable(BundleConstants.RECIPE_STRING, recipe);
            bundle.putParcelable(BundleConstants.STEP_STRING, currentStep);
            bundle.putInt(BundleConstants.STEP_INT,currentStepInt);

            if(savedInstanceState == null){
                StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                stepDetailsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ingredients_details_container__, stepDetailsFragment)
                        .commit();
            }



        }


    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(BundleConstants.RECIPE_STRING,  recipe);
        savedInstanceState.putString(BundleConstants.TYPE_STRING,currentType);
        if(currentType.equals("step")){
            savedInstanceState.putParcelable(BundleConstants.STEP_STRING,currentStep);
            savedInstanceState.putInt(BundleConstants.STEP_INT,currentStepInt);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
