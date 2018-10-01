package com.olamide.mybakingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.olamide.mybakingapp.BundleConstants;
import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.activity.IngredientAndStepsActivity;
import com.olamide.mybakingapp.activity.IngredientStepsDetailsActivity;
import com.olamide.mybakingapp.activity.MainActivity;
import com.olamide.mybakingapp.adapter.StepAdapter;
import com.olamide.mybakingapp.bean.Recipe;
import com.olamide.mybakingapp.bean.Step;
import com.olamide.mybakingapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment implements StepAdapter.StepAdapterOnClickListener,
        StepDetailsFragment.OnFragmentInteractionListener {


    LinearLayoutManager layoutManager;
    StepAdapter stepAdapter;
    @BindView(R.id.rv_steps)
    RecyclerView mRvSteps;

    private Recipe recipe;
    List<Step> stepList = new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    public StepsFragment() {
        // Required empty public constructor
    }


//    // TODO: Rename and change types and number of parameters
//    public static StepsFragment newInstance(String param1, String param2) {
//        StepsFragment fragment = new StepsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        if (getArguments() != null) {
            recipe = getArguments().getParcelable(BundleConstants.RECIPE_STRING);
            stepList = recipe.getSteps();
        }

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this,rootView);
//        IngredientAndStepsActivity parentActivity = (IngredientAndStepsActivity) getActivity();
//        stepList = parentActivity.getRecipe().getSteps();

        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvSteps.setHasFixedSize(true);
        mRvSteps.setLayoutManager(layoutManager);

        stepAdapter = new StepAdapter(stepList,getContext(),R.layout.step_item,this);
        mRvSteps.setAdapter(stepAdapter);
        stepAdapter.setStepList(stepList);
        // Inflate the layout for this fragment
        return rootView;


    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickListener(Step step, int adapterPositon) {

        Toast.makeText(getContext(), step.getShortDescription() + "  has been chosen", Toast.LENGTH_SHORT).show();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(BundleConstants.STEP_INT, adapterPositon);
        editor.putString(BundleConstants.TYPE_STRING,"step");
        editor.apply();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleConstants.STEP_STRING,step);
        bundle.putInt(BundleConstants.STEP_INT, adapterPositon);
        bundle.putParcelable(BundleConstants.RECIPE_STRING, recipe);
        if(Utils.isTablet(getContext())){

            StepDetailsFragment detailsFragment = new StepDetailsFragment();
            detailsFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.ingredients_details_container, detailsFragment)
                    .commit();
        }else {
            Intent intent = new Intent(getContext(), IngredientStepsDetailsActivity.class);
            bundle.putString(BundleConstants.TYPE_STRING,"step");
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
