package com.olamide.mybakingapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.olamide.mybakingapp.BundleConstants;
import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.activity.MainActivity;
import com.olamide.mybakingapp.bean.Recipe;
import com.olamide.mybakingapp.bean.Step;
import com.olamide.mybakingapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepDetailsFragment extends Fragment {


    private List<Step> stepList = new ArrayList<>();
    private Step currentStep;
    private int currentStepInt = 0;
    private Recipe recipe;

    @BindView(R.id.iv_back_drop)
    ImageView ivBackDrop;

    @BindView(R.id.tv_description_detail)
    TextView tvDescription;

    @BindView(R.id.step_prev)
    FloatingActionButton btPrev;

    @BindView(R.id.step_next)
    FloatingActionButton btNext;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepDetailsFragment.
     */
//    // TODO: Rename and change types and number of parameters
//    public static StepDetailsFragment newInstance(String param1, String param2) {
//        StepDetailsFragment fragment = new StepDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
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


        if (savedInstanceState != null){
            recipe = savedInstanceState.getParcelable(BundleConstants.RECIPE_STRING);
            stepList = recipe.getSteps();
            //currentStep = savedInstanceState.getParcelable(BundleConstants.STEP_STRING);
            //currentStepInt = savedInstanceState.getInt(BundleConstants.STEP_INT);
            //currentStep = stepList.get(currentStepInt);

        }else{
            if (getArguments() != null) {


                Bundle testB = getArguments();
                recipe = testB.getParcelable(BundleConstants.RECIPE_STRING);
                stepList = recipe.getSteps();
                //currentStep = testB.getParcelable(BundleConstants.STEP_STRING);
                //currentStepInt = testB.getInt(BundleConstants.STEP_INT);
            }
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        currentStepInt = preferences.getInt(BundleConstants.STEP_INT,0);
        currentStep = stepList.get(currentStepInt);


        View rootView =  inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this,rootView);

        populateViews();

        return rootView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(BundleConstants.RECIPE_STRING,  recipe);
        savedInstanceState.putParcelable(BundleConstants.STEP_STRING,currentStep);
        savedInstanceState.putInt(BundleConstants.STEP_INT, currentStepInt);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(BundleConstants.STEP_INT, currentStepInt);

        editor.apply();

    }



    void populateViews(){
        if(Utils.isTablet(getContext())){
            hideToggles();
        }else{
            if(currentStepInt <= 0){
                showNextToggle();
            }else if(currentStepInt >= stepList.size()-1){
                showPrevToggle();
            }else {
                showToggles();
            }
        }

        tvDescription.setText(currentStep.getDescription());

        if(!currentStep.getThumbnailURL().isEmpty() ){
            Picasso.with(getContext())
                    .load(currentStep.getThumbnailURL())
                    .placeholder(R.drawable.loader)
                    .error(R.drawable.errorr)
                    .into(ivBackDrop);
        }

    }


    @OnClick(R.id.step_prev)
    void goPrevious(){
        currentStepInt--;
        currentStep = stepList.get(currentStepInt);
        populateViews();
    }

    @OnClick(R.id.step_next)
    void goNext(){
        currentStepInt++;
        currentStep = stepList.get(currentStepInt );
        populateViews();
    }

    void showToggles(){
        btPrev.setVisibility(View.VISIBLE);
        btNext.setVisibility(View.VISIBLE);
    }

    void hideToggles(){
        btPrev.setVisibility(View.INVISIBLE);
        btNext.setVisibility(View.INVISIBLE);
    }

    void showPrevToggle(){
        btPrev.setVisibility(View.VISIBLE);
        btNext.setVisibility(View.INVISIBLE);
    }

    void showNextToggle(){
        btPrev.setVisibility(View.INVISIBLE);
        btNext.setVisibility(View.VISIBLE);
    }


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
