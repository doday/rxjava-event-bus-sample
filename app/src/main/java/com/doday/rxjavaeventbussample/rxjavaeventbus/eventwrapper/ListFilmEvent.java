package com.doday.rxjavaeventbussample.rxjavaeventbus.eventwrapper;

import com.doday.rxjavaeventbussample.model.pojo.Film;

import java.util.ArrayList;



/**
 * Created by sessi on 08/11/16.
 */

public class ListFilmEvent extends ArrayList<Film> {

    public ListFilmEvent() {
        super();
    }

    public ListFilmEvent(ArrayList<Film> filmsFromJson) {
        super(filmsFromJson);
    }
}
