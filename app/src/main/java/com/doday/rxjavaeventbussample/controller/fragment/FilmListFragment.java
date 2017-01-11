package com.doday.rxjavaeventbussample.controller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doday.rxjavaeventbussample.rxjavaeventbus.EventBus;
import com.doday.rxjavaeventbussample.MyApplication;
import com.doday.rxjavaeventbussample.R;
import com.doday.rxjavaeventbussample.rxjavaeventbus.eventwrapper.ListFilmEvent;
import com.doday.rxjavaeventbussample.model.network.FilmManager;
import com.doday.rxjavaeventbussample.model.pojo.Film;
import com.doday.rxjavaeventbussample.controller.activity.DetailFilmActivity;
import com.doday.rxjavaeventbussample.view.adapter.FilmAdapter;
import com.doday.rxjavaeventbussample.DateUtils;
import com.doday.rxjavaeventbussample.controller.RecyclerItemClickListener;

import java.io.IOException;
import java.util.ArrayList;


import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by sessi on 20/10/16.
 */

public class FilmListFragment extends Fragment {

    private final DateUtils dateUtils = DateUtils.getInstance();
    private ArrayList<Film> films = new ArrayList<>();
    private FilmAdapter mFilmAdapter;
    private Observable<ListFilmEvent> filmsObservable;
    private Subscription subscription;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_films, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_film);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mFilmAdapter = new FilmAdapter(getActivity());
        mRecyclerView.setAdapter(mFilmAdapter);

        initClickListener(mRecyclerView);

        return view;

    }


    private void initClickListener(final RecyclerView recyclerView) {
        RecyclerItemClickListener.OnItemClickListener l = (new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //int position = recyclerView.getChildLayoutPosition(view);
                DetailFilmActivity.startDetailFilm(getActivity(), films.get(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }


        });

        RecyclerItemClickListener onRecyclerMutualFriendClickListener = new RecyclerItemClickListener(getActivity()
                , recyclerView
                , l);
        recyclerView.addOnItemTouchListener(onRecyclerMutualFriendClickListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        filmsObservable = EventBus.sAppBus.observeEvents(ListFilmEvent.class);

    }



    @Override
    public void onStart() {
        super.onStart();
        requestFilms(mFilmAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFilmAdapter.clearAdapter();
        filmsObservable.unsubscribeOn(AndroidSchedulers.mainThread());
        this.subscription.unsubscribe();
        //TODO annuler la requête

    }

    private void requestFilms(final FilmAdapter filmAdapter) {

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
                                filmAdapter.setFilms(films);
                                filmAdapter.notifyDataSetChanged();
                            }
                        });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
