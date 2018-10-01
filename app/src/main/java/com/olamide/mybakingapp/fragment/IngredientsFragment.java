package com.olamide.mybakingapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olamide.mybakingapp.BundleConstants;
import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.activity.MainActivity;
import com.olamide.mybakingapp.adapter.IngredientAdapter;
import com.olamide.mybakingapp.bean.Ingredient;
import com.olamide.mybakingapp.bean.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsFragment extends Fragment {


    LinearLayoutManager layoutManager;
    IngredientAdapter ingredientAdapter;
    @BindView(R.id.rv_ingredients)
    RecyclerView mRvIngredients;

    List<Ingredient> ingredientList = new ArrayList<>();

    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (getArguments() != null) {
            Recipe recipe = getArguments().getParcelable(BundleConstants.RECIPE_STRING);
            ingredientList = recipe.getIngredients();
        }

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this,rootView);


        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvIngredients.setHasFixedSize(true);
        mRvIngredients.setLayoutManager(layoutManager);

        ingredientAdapter = new IngredientAdapter(ingredientList,getContext(),R.layout.ingredient_item);
        mRvIngredients.setAdapter(ingredientAdapter);
        ingredientAdapter.setIngredientList(ingredientList);

        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
