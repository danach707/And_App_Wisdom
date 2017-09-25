package com.example.danacoh1.qme;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

/**
 * Created by danacoh1 on 6/1/2017.
 */

public class QuestionListAdapter extends BaseAdapter {

    private List<Question> data;
    private static LayoutInflater inflater = null;


    public QuestionListAdapter(@NonNull Context context, @NonNull List data) {

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
            vi = inflater.inflate(R.layout.resource_qlist_view, null);


        TextView question = (TextView) vi.findViewById(R.id.list_question);
        TextView yes = (TextView) vi.findViewById(R.id.list_yes_counter);
        TextView no = (TextView) vi.findViewById(R.id.list_no_counter);


        question.setText(data.get(position).getQuestion());

        tmp = "" + data.get(position).getYes_counter();
        yes.setText(tmp);

        tmp = "" + data.get(position).getNo_counter();
        no.setText(tmp);

        return vi;
    }

}