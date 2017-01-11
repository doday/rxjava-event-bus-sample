package com.doday.rxjavaeventbussample.rxjavaeventbus.eventwrapper;

import com.doday.rxjavaeventbussample.model.pojo.Film;

import java.util.ArrayList;



/**
 * Created by sessi on 08/11/16.
 */

public class FilmsEvent extends ArrayList<Film> {

    public FilmsEvent() {
        super();
    }

    public FilmsEvent(ArrayList<Film> filmsFromJson) {
        super(filmsFromJson);
    }
}
