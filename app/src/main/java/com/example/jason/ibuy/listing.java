package com.example.jason.ibuy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.parseColor;
import static com.example.jason.ibuy.MainActivity.listname;
import static com.example.jason.ibuy.MainActivity.newdb;

public class listing extends BaseAdapter implements ListAdapter {
    private List<items> list = new ArrayList<>();
    private Context context;



    public listing(List<items> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public items getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listingofitems, null);
        }

        final Button button = (Button)view.findViewById(R.id.button8);
        button.setText( getItem(position).getName());
        if(getItem(position).getNeed() == 1){
            button.setBackgroundColor(parseColor("#20ba01"));
        }else{
            button.setBackgroundColor(parseColor("#bf0013"));
        }
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(getItem(position).getNeed() == 0){
                    button.setBackgroundColor(parseColor("#20ba01"));
                    newdb.updateitems(getItem(position),listname);
                    List<items> item = newdb.getAllitems(listname);
                    for (items items : item) {
                        String log = "Id: " + items.getId() + ",Name: " + items.getName() + ",Amount: " + items.getAmount() + ",Need: " + items.getNeed();
                        Log.d("Item: : ", log);
                    }
                }else{
                    button.setBackgroundColor(parseColor("#bf0013"));
                    newdb.updateitems(getItem(position),listname);
                    List<items> item = newdb.getAllitems(listname);
                    for (items items : item) {
                        String log = "Id: " + items.getId() + ",Name: " + items.getName() + ",Amount: " + items.getAmount() + ",Need: " + items.getNeed();
                        Log.d("Item: : ", log);
                    }
                }
            }
        });

        return view;
    }
}