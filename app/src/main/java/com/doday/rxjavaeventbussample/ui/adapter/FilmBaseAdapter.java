package com.doday.rxjavaeventbussample.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doday.rxjavaeventbussample.R;
import com.doday.rxjavaeventbussample.model.Film;
import com.doday.rxjavaeventbussample.util.DateUtils;

import java.util.ArrayList;


/**
 * Created by sessi on 19/10/16.
 */

public class FilmBaseAdapter extends BaseAdapter {


    private ArrayList<Film> films =  new ArrayList<>();
    private final LayoutInflater infalter;
    private static final DateUtils dateUtils = DateUtils.getInstance();
    private ViewHolderItem viewHolder;

    public FilmBaseAdapter(Context context, ArrayList<Film> films) {
        this(context);
        this.films = films;
    }

    public FilmBaseAdapter(Context context) {
        infalter = LayoutInflater.from(context);
    }


    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addFilm(Film film) {
        films.add(film);
    }

    public void clearAdapter() {
        films.clear();
        notifyDataSetChanged();
    }

    static class ViewHolderItem{
        TextView title;

    }


    StringBuilder stringBuilder = new StringBuilder();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Film film = (Film) getItem(position);
        if(convertView == null){
            convertView = infalter.inflate(R.layout.item_film,parent,false);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) convertView.findViewById(R.id.list_film_title);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem)convertView.getTag();
        }


        stringBuilder.setLength(0);
        stringBuilder.append(film.title);
        viewHolder.title.setText(stringBuilder.toString());

        String dateFrenchFormat = null;
        /*try {
            dateFrenchFormat = dateUtils.getDateLetterFormat(film.getBirthdayDateFormat());
        } catch (ParseException e) {
            Log.e(FilmBaseAdapter.class,e.getMessage());
        }*/
        convertView.setTag(viewHolder);
        return convertView;
    }


}
