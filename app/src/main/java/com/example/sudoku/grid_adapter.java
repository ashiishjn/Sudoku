package com.example.sudoku;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class grid_adapter extends BaseAdapter {
    private int number[];
    private boolean fixed[] = new boolean[81];
    private Context context;
    private LayoutInflater inflater;
    public grid_adapter(Context context, int number[]){
        this.number = number;
        this.context = context;
    }

    @Override
    public int getCount() {
        return number.length;
    }

    @Override
    public Object getItem(int i) {
        return number[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView = view;
        if(view == null)
        {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_layout, null);
        }
        TextView num = gridView.findViewById(R.id.number);
        if (number[i]!=0)
            fixed[i]=true;
        else
            fixed[i]=false;

        if(fixed[i])
            num.setText(String.valueOf(number[i]));
        else
        {
            num.setTextColor(Color.parseColor("#466b97"));
            num.setText("");
        }
        return gridView;
    }
    public boolean completed()
    {
        for(int i=0;i<81;i++)
            if(number[i] == 0)
                return false;
        return true;
    }
    public void ColorSet(int i, View view)
    {
        View gridView = view;
        TextView num = gridView.findViewById(R.id.number);
        num.setTextColor(Color.parseColor("#d13d49"));
    }

    public int getNum(int i)
    {
        return number[i];
    }
    public void setItem(View view, int i)
    {
        if(fixed[i])
            return;
        View gridView = view;
        if(number[i] == 9)
            number[i]=1;
        else
            number[i]++;
        TextView num = gridView.findViewById(R.id.number);
        num.setTextColor(Color.parseColor("#466b97"));
        num.setText(String.valueOf(number[i]));
    }
}
