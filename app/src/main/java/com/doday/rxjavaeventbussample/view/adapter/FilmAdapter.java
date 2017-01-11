package com.doday.rxjavaeventbussample.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doday.rxjavaeventbussample.R;
import com.doday.rxjavaeventbussample.model.pojo.Film;
import com.doday.rxjavaeventbussample.DateUtils;

import java.util.ArrayList;


/**
 * Created by sessi on 19/10/16.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {


    private ArrayList<Film> films =  new ArrayList<>();
    private static final DateUtils dateUtils = DateUtils.getInstance();
    private View.OnClickListener mOnClickListener;

    public FilmAdapter(Context context, ArrayList<Film> films) {
        this(context);
        this.films = films;
    }

    public FilmAdapter(Context context) {
    }


    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_film, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(films.get(position).title);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void addFilm(Film film) {
        films.add(film);
    }

    public void clearAdapter() {
        films.clear();
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_film_title);

            if(mOnClickListener != null) {
                itemView.setOnClickListener(mOnClickListener);
            }
        }
    }
}
