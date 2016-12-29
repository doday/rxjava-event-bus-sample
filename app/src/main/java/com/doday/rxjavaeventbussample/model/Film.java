package com.doday.rxjavaeventbussample.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by sessi on 19/10/16.
 */
public class Film  implements Parcelable{

    public String poster_path;//":"\/9aBSaiCw7bLFjYJUDyJmF7VC3Bo.jpg",
    public boolean adult;//":false,
    public String overview;//":"Ils sont père et fils. Ils ne se supportent pas. Leurs entourages leur ont lancé un ultimatum : participer à un stage de réconciliation « Aventures Père Fils » dans les gorges du Verdon où ils devront tenter un ultime rapprochement. Entre mauvaise foi et coups bas, pas évident qu’ils arrivent à se réconcilier.",
    public String release_date;//":"2016-12-28",
    public int[] genre_ids;//":[35],
    public int id;//":395762,
    public String original_title;//":"Père Fils Thérapie !",
    public String original_language;//":"fr",
    public String title;//":"Père Fils Thérapie !",
    public String backdrop_path;//":null,
    public float popularity;//":2.118979,
    public int vote_count;//":0,
    public boolean video;//":false,
    public float vote_average;//":0



    public Film() {

    }


    protected Film(Parcel in) {
        poster_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        release_date = in.readString();
        genre_ids = in.createIntArray();
        id = in.readInt();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readFloat();
        vote_count = in.readInt();
        video = in.readByte() != 0;
        vote_average = in.readFloat();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeIntArray(genre_ids);
        dest.writeInt(id);
        dest.writeString(original_title);
        dest.writeString(original_language);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeFloat(popularity);
        dest.writeInt(vote_count);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeFloat(vote_average);
    }
}
