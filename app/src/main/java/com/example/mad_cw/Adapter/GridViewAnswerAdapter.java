package com.example.mad_cw.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class GridViewAnswerAdapter extends BaseAdapter {        //user's guessed answer

    private char[] answerCharacter;
    private Context context;


    public GridViewAnswerAdapter(char[] answerCharacter, Context context) {
        this.answerCharacter = answerCharacter;
        this.context = context;

    }


    @Override
    public int getCount() {
        return answerCharacter.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            //new button
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(85, 85));
            button.setPadding(0, 0, 0, 0);
            button.setBackgroundColor(Color.WHITE);
            button.setTextColor(Color.BLUE);
            button.setBottom(Color.BLACK);
            button.setText(String.valueOf(answerCharacter[position]));
        } else button = (Button) convertView;
        return button;
    }
}

/*

REFERENCES

Guessing image by fiiling blanks
https://youtu.be/6RIFhaldHzM

Set on texy changed listener
https://youtu.be/daZOKNBMmsg


*/