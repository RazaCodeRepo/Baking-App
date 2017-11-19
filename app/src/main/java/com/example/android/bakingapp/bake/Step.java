package com.example.android.bakingapp.bake;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kontrol on 9/20/2017.
 */

public class Step implements Parcelable {
    private int stepID;
    private String step_shortDescription;
    private String step_desc;
    private String step_videoURL;
    private String step_thumbnail;


    public Step(int id, String short_desc, String desc,  String url, String thumbnail){
        stepID = id;
        step_shortDescription = short_desc;
        step_desc = desc;
        step_videoURL = url;
        step_thumbnail = thumbnail;
    }

    public int getStepID(){
        return stepID;
    }

    public String getStep_shortDescription(){
        return step_shortDescription;
    }

    public String getStep_videoURL(){
        return step_videoURL;
    }

    public String getStep_desc(){
        return step_desc;
    }

    public String getStep_thumbnail(){
        return step_videoURL;
    }

    protected Step(Parcel in) {
        stepID = in.readInt();
        step_shortDescription = in.readString();
        step_desc = in.readString();
        step_videoURL = in.readString();
        step_thumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepID);
        dest.writeString(step_shortDescription);
        dest.writeString(step_desc);
        dest.writeString(step_videoURL);
        dest.writeString(step_thumbnail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}