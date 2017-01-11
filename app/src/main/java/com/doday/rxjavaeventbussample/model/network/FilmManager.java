package com.doday.rxjavaeventbussample.model.network;

import android.content.Context;


import com.doday.rxjavaeventbussample.rxjavaeventbus.EventBus;
import com.doday.rxjavaeventbussample.rxjavaeventbus.eventwrapper.ListFilmEvent;
import com.doday.rxjavaeventbussample.model.pojo.Film;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;


/**
 * Created by sessi on 21/10/16.
 */

public class FilmManager {



    private int createUpdatestatus;

    private static FilmManager ourInstance;

    public static FilmManager getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new FilmManager(context);
        }
        return ourInstance;
    }
    private FilmManager(Context context) {
    }


    private ListFilmEvent films;



    Observable<ListFilmEvent> getFilmsObservable = Observable.create(
            new Observable.OnSubscribe<ListFilmEvent>() {
                @Override
                public void call(Subscriber<? super ListFilmEvent> sub) {
                    sub.onNext(films);
                    sub.onCompleted();
                }
            }
    );

    private void sendFilms() {
        getFilmsObservable.subscribe(new Action1<ListFilmEvent>() {
            @Override
            public void call(ListFilmEvent film) {
                EventBus.sAppBus.post(film);
            }
        });
    }




    class FilmsRoot{
        public int page;//":1,
        public int total_results;//":6,
        public int total_pages;//":1
        public ArrayList<Film> results;
    }


    //https://api.themoviedb.org/3/discover/movie?api_key=189ec91ba809cb4d27ef56780e4aa516&language=fr-FR&region=FR&release_date.gte=2016-12-26&release_date.lte=2016-12-31&with_release_type=2|3

    //("189ec91ba809cb4d27ef56780e4aa516", "fr-FR", "FR", "2016-12-26", "2016-12-31")
    public interface GitHubService {
        @GET("/3/discover/movie?with_release_type=2|3")
        Call<FilmsRoot> listFilm(@Query("api_key") String apikey
                , @Query("language") String language
                , @Query("region") String region
                , @Query("release_date.gte") String releaseDateGte
                , @Query("release_date.lte") String releaseDateLte);
    }

    public void getFilmsWS() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                 //client.networkInterceptors().add(new StethoInterceptor());


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GitHubService service = retrofit.create(GitHubService.class);

                Call<FilmsRoot> repos = service.listFilm("189ec91ba809cb4d27ef56780e4aa516", "fr-FR", "FR", "2016-12-01", "2016-12-31");


                try {
                    films = new ListFilmEvent(repos.execute().body().results);
                    sendFilms();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
