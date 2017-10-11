package com.example.danacoh1.qme;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

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

    public List<Comment> getData() {
        return data;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        String tmp;

        if (vi == null)
            vi = inflater.inflate(R.layout.list_single_comment, null);

        TextView comm = (TextView) vi.findViewById(R.id.comm_txt);
        ImageView like = (ImageView) vi.findViewById(R.id.comm_list_yes_image);
        ImageView unlike = (ImageView) vi.findViewById(R.id.comm_list_no_image);
        final TextView comm_yes_counter = (TextView) vi.findViewById(R.id.comm_list_yes_counter);
        final TextView comm_no_counter = (TextView) vi.findViewById(R.id.comm_list_no_counter);
        comm.setText(data.get(position).getCom_text());
        comm_yes_counter.setText("" + data.get(position).getNum_of_likes());
        comm_no_counter.setText("" + data.get(position).getNum_of_unlikes());

        final int pos = position;
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseUtils.addDataToCommentFirebase(QuestionActivity.questionData,
                        QuestionActivity.comments.get(pos),
                        Constants.LIKE,QuestionActivity.comments.get(pos).getNum_of_likes()+1);
                comm_yes_counter.invalidate();
            }
        });
        unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseUtils.addDataToCommentFirebase(QuestionActivity.questionData,
                        QuestionActivity.comments.get(pos),
                        Constants.UN_LIKE,QuestionActivity.comments.get(pos).getNum_of_unlikes()+1);
                comm_no_counter.invalidate();
            }
        });

        comm_yes_counter.setGravity(Gravity.LEFT);

        return vi;
    }

}