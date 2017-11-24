package com.example.android.bakingapp.fragments;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 10/9/2017.
 */

public class VideoDisplayFragment extends Fragment {

    private static final String TAG = "VideoDisplayFragment";

    private SimpleExoPlayer simpleExoPlayer;
    @BindView(R.id.playerView) SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.thumbnail_view) ImageView thumbnailView;

    private List<Step> steps;
    private int itemIndex;
    private long position;

    private static final String VIDEO_FRAGMENT_CLASS_STEPS_INSTANCE_KEY ="stepslist";
    private static final String VIDEO_FRAGMENT_CLASS_INDEX_INSTANCE_KEY ="itemindex";

    Bundle restoreBundle;


    public VideoDisplayFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_video_display, container, false);
        ButterKnife.bind(this, rootView);

        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.default_image));
        restoreBundle = savedInstanceState;
        if(savedInstanceState == null){
            Bundle bundle = getArguments();
            if(bundle != null){
                itemIndex = bundle.getInt("STEP_INDEX_ACTIVITY");
                steps = bundle.getParcelableArrayList("STEP_LIST_ACTIVITY");
                Step temp = steps.get(itemIndex);
                String videoUrl = temp.getStep_videoURL();
                String thumbnailUrl = temp.getStep_thumbnail();
                if(!TextUtils.isEmpty(videoUrl)){
                    //thumbnailView.setVisibility(View.GONE);
                    simpleExoPlayerView.setVisibility(View.VISIBLE);
                    initializePlayer(Uri.parse(videoUrl), savedInstanceState);
                } else{
                    thumbnailView.setVisibility(View.VISIBLE);
                    if(!TextUtils.isEmpty(thumbnailUrl)){
                        Picasso.with(getContext()).load(thumbnailUrl).placeholder(R.drawable.default_recipe).into(thumbnailView);
                    } else {
                        Picasso.with(getContext()).load(R.drawable.default_recipe).into(thumbnailView);
                    }

                }

            } else {
                Intent intent = getActivity().getIntent();
                itemIndex = intent.getIntExtra("step_index", 0);
                steps = intent.getParcelableArrayListExtra("step_list");
                Step temp = steps.get(itemIndex);
                String videoUrl = temp.getStep_videoURL();
                String thumbnailUrl = temp.getStep_thumbnail();
                if(!TextUtils.isEmpty(videoUrl)){
                    //thumbnailView.setVisibility(View.GONE);
                    simpleExoPlayerView.setVisibility(View.VISIBLE);
                    initializePlayer(Uri.parse(videoUrl), savedInstanceState);
                } else{
                    thumbnailView.setVisibility(View.VISIBLE);
                    if(!TextUtils.isEmpty(thumbnailUrl)){
                        //simpleExoPlayerView.setVisibility(View.GONE);
                        Picasso.with(getContext()).load(thumbnailUrl).placeholder(R.drawable.default_recipe).into(thumbnailView);
                    } else {
                        Picasso.with(getContext()).load(R.drawable.default_recipe).into(thumbnailView);
                    }
                }
            }
        } else{
            steps = savedInstanceState.getParcelableArrayList(VIDEO_FRAGMENT_CLASS_STEPS_INSTANCE_KEY);
            itemIndex = savedInstanceState.getInt(VIDEO_FRAGMENT_CLASS_INDEX_INSTANCE_KEY);
            Step temp = steps.get(itemIndex);
            String videoUrl = temp.getStep_videoURL();
            String thumbnailUrl = temp.getStep_thumbnail();
            if(!TextUtils.isEmpty(videoUrl)){
                //thumbnailView.setVisibility(View.GONE);
                simpleExoPlayerView.setVisibility(View.VISIBLE);
                initializePlayer(Uri.parse(videoUrl), savedInstanceState);
            } else{
                thumbnailView.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(thumbnailUrl)){
                   // simpleExoPlayerView.setVisibility(View.GONE);
                    Picasso.with(getContext()).load(thumbnailUrl).placeholder(R.drawable.default_recipe).into(thumbnailView);
                } else {
                    Picasso.with(getContext()).load(R.drawable.default_recipe).into(thumbnailView);
                }
            }
        }

        return rootView;
    }

    private void initializePlayer(Uri uri, Bundle instanceState){
        if(instanceState == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "Recipe Video");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);

        } else{
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayer.seekTo(instanceState.getLong("VIDEO_POSITION"));
            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "Recipe Video");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }

    }

    private void releasePlayer() {

        if(simpleExoPlayer != null){

            position = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if(restoreBundle != null){
            int videoPos = restoreBundle.getInt("VIDEO_POSITION");
            simpleExoPlayer.seekTo(videoPos);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(VIDEO_FRAGMENT_CLASS_STEPS_INSTANCE_KEY, (ArrayList)steps);
        outState.putInt(VIDEO_FRAGMENT_CLASS_INDEX_INSTANCE_KEY, itemIndex );
        outState.putLong("VIDEO_POSITION", position);
    }


}
