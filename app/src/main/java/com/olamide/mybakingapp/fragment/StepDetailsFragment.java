package com.olamide.mybakingapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
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

import static android.support.constraint.Constraints.TAG;


public class StepDetailsFragment extends Fragment implements Player.EventListener {


    private List<Step> stepList = new ArrayList<>();
    private Step currentStep;
    private int currentStepInt = 0;
    private Recipe recipe;


    private Uri videoUri;



    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    private boolean startAutoPlay;
    private int startWindow;
    private long startPosition;



    private SimpleExoPlayer mExoPlayer;

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;


    @BindView(R.id.iv_back_drop)
    ImageView ivBackDrop;

    @BindView((R.id.player_view_step))
    SimpleExoPlayerView playerViewStep;

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


                //trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
                startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
                startWindow = savedInstanceState.getInt(KEY_WINDOW);
                startPosition = savedInstanceState.getLong(KEY_POSITION);


        }else{
            clearStartPosition();

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


        updateStartPosition();
        savedInstanceState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        savedInstanceState.putInt(KEY_WINDOW, startWindow);
        savedInstanceState.putLong(KEY_POSITION, startPosition);

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

        if(!currentStep.getVideoURL().isEmpty() ){

            playerViewStep.setVisibility(View.VISIBLE);
            ivBackDrop.setVisibility(View.INVISIBLE);
            initializeMediaSession();
            videoUri = Uri.parse(currentStep.getVideoURL());
            initializePlayer(videoUri);

        }else if(!currentStep.getThumbnailURL().isEmpty()){

            playerViewStep.setVisibility(View.VISIBLE);
            ivBackDrop.setVisibility(View.INVISIBLE);
            initializeMediaSession();
            videoUri =  Uri.parse(currentStep.getThumbnailURL());
            initializePlayer(videoUri);
        }else {
            playerViewStep.setVisibility(View.INVISIBLE);
            ivBackDrop.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(R.drawable.errorr)
                    .placeholder(R.drawable.loader)
                    .error(R.drawable.errorr)
                    .into(ivBackDrop);
        }



    }



    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(),getActivity().getClass().getSimpleName());

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }





    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            //LoadControl loadControl = new DefaultLoadControl();
            //mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);
            playerViewStep.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), getActivity().getClass().getSimpleName());
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            boolean haveStartPosition = startWindow != C.INDEX_UNSET;
            if (haveStartPosition) {
                mExoPlayer.seekTo(startWindow, startPosition);
            }

            mExoPlayer.prepare(mediaSource,!haveStartPosition,false);
            mExoPlayer.setPlayWhenReady(startAutoPlay);


        }
    }


    private void releasePlayer() {
        if(mExoPlayer!=null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            updateStartPosition();

        }

    }


    @OnClick(R.id.step_prev)
    void goPrevious(){
        currentStepInt--;
        currentStep = stepList.get(currentStepInt);
        releasePlayer();
        populateViews();
    }

    @OnClick(R.id.step_next)
    void goNext(){
        currentStepInt++;
        currentStep = stepList.get(currentStepInt );
        releasePlayer();
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


    private void updateStartPosition() {
        if (mExoPlayer != null) {
            startAutoPlay = mExoPlayer.getPlayWhenReady();
            startWindow = mExoPlayer.getCurrentWindowIndex();
            startPosition = Math.max(0, mExoPlayer.getContentPosition());
        }
    }
    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
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
        //releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());


    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

        if (isBehindLiveWindow(error)) {
            clearStartPosition();
            initializePlayer(videoUri);
        } else {
            updateStartPosition();

        }

    }

    @Override
    public void onPositionDiscontinuity( @Player.DiscontinuityReason int reason) {

//        if (mExoPlayer.get != null) {
//            // The user has performed a seek whilst in the error state. Update the resume position so
//            // that if the user then retries, playback resumes from the position to which they seeked.
//            updateStartPosition();
//        }

    }



    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }



    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }



    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //releasePlayer();
    }

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}
