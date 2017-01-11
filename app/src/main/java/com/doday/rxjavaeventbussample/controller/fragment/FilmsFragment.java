package com.doday.rxjavaeventbussample.controller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doday.rxjavaeventbussample.model.proxy.FilmsProxy;
import com.doday.rxjavaeventbussample.R;
import com.doday.rxjavaeventbussample.rxjavaeventbus.eventwrapper.FilmsEvent;
import com.doday.rxjavaeventbussample.controller.activity.DetailFilmActivity;
import com.doday.rxjavaeventbussample.view.adapter.FilmmAdapter;
import com.doday.rxjavaeventbussample.controller.RecyclerItemClickListener;


import rx.Observer;

/**
 * Created by sessi on 20/10/16.
 */

public class FilmsFragment extends Fragment {

    private FilmmAdapter mFilmmAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FilmsProxy mFilmsProxy;
    private Observer<FilmsEvent> mOnFilmsChangeObserver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_films, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_film);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mFilmmAdapter = new FilmmAdapter(getActivity());
        mRecyclerView.setAdapter(mFilmmAdapter);
        mOnFilmsChangeObserver = getOnFilmsChangeObserver(mFilmmAdapter);

        initClickListener(mRecyclerView);

        mFilmsProxy = new FilmsProxy();

        return view;

    }


    private void initClickListener(final RecyclerView recyclerView) {
        RecyclerItemClickListener.OnItemClickListener l = (new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DetailFilmActivity.startDetailFilm(getActivity(), mFilmsProxy.getFilm(position));
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


    @NonNull
    private Observer<FilmsEvent> getOnFilmsChangeObserver(final FilmmAdapter filmmAdapter) {
        return new Observer<FilmsEvent>() {
            @Override
            public void onCompleted() {
                Log.d(getClass().getSimpleName(), "onCompleted via rxjava");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(getClass().getSimpleName(), "onError via rxjava " + throwable);
            }

            @Override
            public void onNext(FilmsEvent films) {
                Log.d(getClass().getSimpleName(), "onNext film récupéré via rxjava : " + films);
                filmmAdapter.setFilms(films);
                filmmAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void onStart() {
        super.onStart();
        try {
            mFilmsProxy.onStart(mOnFilmsChangeObserver);
        } catch (IllegalStateException e) {
            Log.d(getClass().getSimpleName(), e.getMessage());
            //TODO afficher un message d'erreur à l'utilisateur
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        mFilmsProxy.onStop();
        mFilmmAdapter.clearAdapter();

    }





}
