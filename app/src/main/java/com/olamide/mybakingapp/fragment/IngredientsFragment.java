package com.olamide.mybakingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.olamide.mybakingapp.widget.IngredientUpdateService;

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

        startIngredientWidgetService();

        return rootView;
    }

    /**
     * will trigger the WidgetUpdateService to update the Widget to the last recipe that the user has seen.
     */
    void startIngredientWidgetService()
    {
        Intent i = new Intent(getContext(), IngredientUpdateService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BundleConstants.INGREDIENTS, (ArrayList<? extends Parcelable>) ingredientList);
        i.putExtra(BundleConstants.BUNDLE, bundle);
        i.setAction(IngredientUpdateService.ACTION_UPDATE_INGREDIENT_WIDGET);
        getActivity().startService(i);
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
