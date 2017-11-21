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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private List<Step> steps;
    private int itemIndex;

    private static final String VIDEO_FRAGMENT_CLASS_STEPS_INSTANCE_KEY ="stepslist";
    private static final String VIDEO_FRAGMENT_CLASS_INDEX_INSTANCE_KEY ="itemindex";


    public VideoDisplayFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_video_display, container, false);
        ButterKnife.bind(this, rootView);

        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.default_image));

        if(savedInstanceState == null){
            Bundle bundle = getArguments();
            if(bundle != null){
                itemIndex = bundle.getInt("STEP_INDEX_ACTIVITY");
                steps = bundle.getParcelableArrayList("STEP_LIST_ACTIVITY");
                Step temp = steps.get(itemIndex);
                String videoUrl = temp.getStep_videoURL();
                initializePlayer(Uri.parse(videoUrl));
            } else {
                Intent intent = getActivity().getIntent();
                itemIndex = intent.getIntExtra("step_index", 0);
                steps = intent.getParcelableArrayListExtra("step_list");
                initializePlayer(Uri.parse(steps.get(itemIndex).getStep_videoURL()));
            }
        } else{
            steps = savedInstanceState.getParcelableArrayList(VIDEO_FRAGMENT_CLASS_STEPS_INSTANCE_KEY);
            itemIndex = savedInstanceState.getInt(VIDEO_FRAGMENT_CLASS_INDEX_INSTANCE_KEY);
            Step temp = steps.get(itemIndex);
            String videoUrl = temp.getStep_videoURL();
            initializePlayer(Uri.parse(videoUrl));
        }

        return rootView;
    }

    private void initializePlayer(Uri uri){
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);

        String userAgent = Util.getUserAgent(getActivity(), "Recipe Video");
        MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getActivity(), userAgent),
                new DefaultExtractorsFactory(), null, null);

        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {

        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

        @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(VIDEO_FRAGMENT_CLASS_STEPS_INSTANCE_KEY, (ArrayList)steps);
        outState.putInt(VIDEO_FRAGMENT_CLASS_INDEX_INSTANCE_KEY, itemIndex );
    }


}
