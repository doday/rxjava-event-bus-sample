package com.doday.rxjavaeventbussample.model.proxy;

import android.util.Log;

import com.doday.rxjavaeventbussample.MyApplication;
import com.doday.rxjavaeventbussample.model.network.FilmsAPI;
import com.doday.rxjavaeventbussample.model.pojo.Film;
import com.doday.rxjavaeventbussample.rxjavaeventbus.EventBus;
import com.doday.rxjavaeventbussample.rxjavaeventbus.eventwrapper.FilmsEvent;

import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Lance les requêtes
 * Met les données en cache
 * Fait une gestion de cache
 * Gere le relancement de requêtes
 * Précise quel type d'erreur est survenu (pas de connection/pas de réponses...)
 * Fait des transfomations
 */

public class FilmsProxy {

    private Observable<FilmsEvent> filmsObservable;
    private ArrayList<Film> films = new ArrayList<>();
    private Subscription subscriptionForFragment;
    private Subscription subscriptionForProxy;

    public FilmsProxy(){
        filmsObservable = EventBus.sAppBus.observeEvents(FilmsEvent.class);
        initOnFilmsUpdated();
    }

    public void onStart(Observer<FilmsEvent> observer) throws IllegalStateException {
        this.subscriptionForFragment = filmsObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        try {
            FilmsAPI.getInstance(MyApplication.getAppContext()).getFilmsWS();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot do the films request ",e);
        }
    }


    public void onStop() {
        //TODO annuler les requêtes
        this.subscriptionForFragment.unsubscribe();
        filmsObservable.unsubscribeOn(AndroidSchedulers.mainThread());
    }


    private void initOnFilmsUpdated() {
        this.subscriptionForProxy = filmsObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FilmsEvent>() {
                    @Override
                    public void onCompleted() {
                        Log.d(getClass().getSimpleName(), "onCompleted via rxjava");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(getClass().getSimpleName(), "onError via rxjava " + throwable);
                        //relancer la requête ?
                    }

                    @Override
                    public void onNext(FilmsEvent films) {
                        Log.d(getClass().getSimpleName(), "onNext film récupéré via rxjava : " + films);
                        //mise en cache
                        FilmsProxy.this.films = films;
                    }
                });

    }


    public ArrayList<Film> getFilms() {
        if(films.size() == 0){
            throw new IllegalAccessError("You cannot get films if you didn't request them");
        }
        return films;
    }


    public Film getFilm(int position) {
        return getFilms().get(position);
    }
}
