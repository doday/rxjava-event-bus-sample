package com.doday.rxjavaeventbussample.ui.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.doday.rxjavaeventbussample.EventBus;
import com.doday.rxjavaeventbussample.MyApplication;
import com.doday.rxjavaeventbussample.R;
import com.doday.rxjavaeventbussample.event.ListFilmEvent;
import com.doday.rxjavaeventbussample.manager.FilmManager;
import com.doday.rxjavaeventbussample.model.Film;
import com.doday.rxjavaeventbussample.ui.activity.DetailFilmActivity;
import com.doday.rxjavaeventbussample.ui.adapter.FilmBaseAdapter;
import com.doday.rxjavaeventbussample.util.DateUtils;

import java.io.IOException;
import java.util.ArrayList;


import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by sessi on 20/10/16.
 */

public class FilmListFragment extends ListFragment {

    private final DateUtils dateUtils = DateUtils.getInstance();
    private ArrayList<Film> films = new ArrayList<>();
    private FilmBaseAdapter filmBaseAdapter;
    private Observable<ListFilmEvent> filmsObservable;
    private Subscription subscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_films, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        filmBaseAdapter = new FilmBaseAdapter(getActivity());
        setListAdapter(filmBaseAdapter);
        filmsObservable = EventBus.sAppBus.observeEvents(ListFilmEvent.class);

    }



    @Override
    public void onStart() {
        super.onStart();
        requestFilms(filmBaseAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        filmBaseAdapter.clearAdapter();
        filmsObservable.unsubscribeOn(AndroidSchedulers.mainThread());
        this.subscription.unsubscribe();
        //TODO annuler la requête

    }

    private void requestFilms(final FilmBaseAdapter filmBaseAdapter) {

        try {
            FilmManager.getInstance(MyApplication.getAppContext()).getFilmsWS();

            this.subscription = filmsObservable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ListFilmEvent>() {
                            @Override
                            public void onCompleted() {
                                Log.d(getClass().getSimpleName(), "onCompleted via rxjava");
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Log.d(getClass().getSimpleName(), "onError via rxjava " + throwable);
                            }

                            @Override
                            public void onNext(ListFilmEvent films) {
                                Log.d(getClass().getSimpleName(), "onNext film récupéré via rxjava : " + films);
                                FilmListFragment.this.films = films;
                                filmBaseAdapter.setFilms(films);
                                filmBaseAdapter.notifyDataSetChanged();
                            }
                        });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        DetailFilmActivity.startDetailFilm(getActivity(), films.get(position));
    }


}
