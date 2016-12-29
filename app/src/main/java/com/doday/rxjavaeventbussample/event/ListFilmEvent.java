package com.doday.rxjavaeventbussample.event;

import com.doday.rxjavaeventbussample.model.Film;

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
