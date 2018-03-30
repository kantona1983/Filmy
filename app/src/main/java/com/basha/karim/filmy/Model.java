package com.basha.karim.filmy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karim on 2/5/2018.
 */

class Model implements Parcelable {

    private String title;
    private String releaseData;
    private String descrption;
    private double voteAverage;
    private final String posterUrl;
    private String id;


    protected Model(Parcel in) {
        title = in.readString();
        releaseData = in.readString();
        descrption = in.readString();
        voteAverage = in.readDouble();
        posterUrl = in.readString();
        id = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getId() {
        return id;
    }


    public Model(String title, String posterUrl, String releaseData, double voteAverage, String descrption, String Id) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.releaseData = releaseData;
        this.voteAverage = voteAverage;
        this.descrption = descrption;
        this.id = Id;
    }

    public Model(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getReleaseData() {
        return this.releaseData;
    }

    public String getPosterUrl() {
        return this.posterUrl;
    }

    public String getDescrption() {
        return this.descrption;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(releaseData);
        dest.writeString(posterUrl);
        dest.writeDouble(voteAverage);
        dest.writeString(id);
    }
}
