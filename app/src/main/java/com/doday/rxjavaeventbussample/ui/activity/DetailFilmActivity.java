package com.doday.rxjavaeventbussample.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.doday.rxjavaeventbussample.R;
import com.doday.rxjavaeventbussample.model.Film;

import org.joda.time.DateTime;


import rx.Subscription;

/**
 * Created by sessi on 21/10/16.
 */

public class DetailFilmActivity extends AppCompatActivity  {

    public static final String EXTRA_FILM = "EXTRA_FILM";
    private DateTime mDateTime = new DateTime();
    private TextView tvDetailFilm;
    private Film mFilm;
    private ProgressDialog progress;


    public static void startDetailFilm(Context context, Film film) {
        Intent starter = new Intent(context, DetailFilmActivity.class);
        starter.putExtra(EXTRA_FILM, film);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        tvDetailFilm = (TextView) findViewById(R.id.detail_overview);

        restoreFilm(getIntent().getExtras());
    }

    private void restoreFilm(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey(EXTRA_FILM)){
            mFilm = savedInstanceState.getParcelable(EXTRA_FILM);
            tvDetailFilm.setText(mFilm.overview);
        }else{
            mFilm = new Film();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }



    @Override
    protected void onStop() {
        super.onStop();
    }


    private Subscription subscription;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }





}
