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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by danacoh1 on 7/7/2017.
 */
public class CustomList extends BaseAdapter {

    private List<Comment> data;
    private static LayoutInflater inflater = null;


    public CustomList(@NonNull Context context, @NonNull List data) {

        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        String tmp;

        if (vi == null)
            vi = inflater.inflate(R.layout.list_single_comment, null);

        TextView comm = (TextView) vi.findViewById(R.id.txt);
        comm.setText(data.get(position).getCom_text());

        return vi;
    }

}