package com.example.danacoh1.qme;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by danacoh1 on 7/7/2017.
 */

public class CustomList extends ArrayAdapter<String> {

    private LinkedList<Comment> web;
    private static LayoutInflater inflater = null;


    public CustomList(@NonNull Context context, @LayoutRes int resource, LinkedList<Comment> web) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.web = web;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.list_single_comment, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web.get(position).getCom_text());

        //imageView.setImageResource(imageId[position]);
        return rowView;
    }
}