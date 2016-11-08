package com.example.jason.ibuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.jason.ibuy.MainActivity.listname;
import static com.example.jason.ibuy.MainActivity.newdb;

public class list extends Activity {

    private PopupWindow window;
    private LayoutInflater inflater;
    private RelativeLayout relativeLayout;

    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlist);

        final TextView name = (TextView) findViewById(R.id.textView3);
        name.setMovementMethod(new ScrollingMovementMethod());
        List<items> item = newdb.getAllitems(listname);
        for(items items : item){
            name.setText(name.getText()+items.getName() + " " + items.getAmount()+ "\n");
        }

        relativeLayout = (RelativeLayout) findViewById(R.id.itemlist);

        Button Add = (Button) findViewById(R.id.button4);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final ViewGroup containter = (ViewGroup) inflater.inflate(R.layout.popupitems, null);
                window = new PopupWindow(containter, 1500, 1000, true);
                window.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, 1500, 1500);
                Button submit = (Button) containter.findViewById(R.id.button7);
                final EditText name = (EditText) containter.findViewById(R.id.editText7);
                final EditText amount = (EditText) containter.findViewById(R.id.editText2);

                submit.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        String end = String.valueOf(name.getText());
                        String num = String.valueOf(amount.getText());
                        Log.d("Insert:","Inserting..");
                        newdb.additems(new items(newdb.getItemsCount(listname)+1,end,Integer.parseInt(num)));
                        List<items> item = newdb.getAllitems(listname);
                        for (items items : item) {
                            String log = "Id: " + items.getId() + ",Name: " + items.getName() + ",Amount: " + items.getAmount();
                            Log.d("Item: : ", log);
                        }
                        Intent intent = new Intent(v.getContext(),list.class);
                        startActivityForResult(intent,0);
                    }
                });
                containter.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        window.dismiss();
                        return true;
                    }
                });
            }
        });


    }
}
