package com.example.jason.ibuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Button open;
    private Button delete;
    private PopupWindow window;
    private LayoutInflater inflater;
    private RelativeLayout relativeLayout;
    DBHandler db = new DBHandler(this);
    static DBHandler newdb;
    static String listname;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<String> adapter = new ArrayList<String>();
        adapter.add("Create New List");
        int length = db.getShopsCount();
        String[] hold = db.getShops();
        for(int i= 0; i<length;i++){
            adapter.add(hold[i]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, adapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        open = (Button) findViewById(R.id.button);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem().toString().equals("Create New List")) {
                    inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    final ViewGroup containter = (ViewGroup) inflater.inflate(R.layout.popup, null);
                    window = new PopupWindow(containter, 1500, 1000, true);
                    window.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, 1500, 1500);
                    Button submit = (Button) containter.findViewById(R.id.button3);
                    final EditText name = (EditText) containter.findViewById(R.id.editText);

                    submit.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            String end = String.valueOf(name.getText());
                            listname = end;
                            Log.d("Insert:","Inserting..");
                            db.addShop(new Shop(db.getShopsCount()+1,end));
                            db.listtablecreate(db.getReadableDatabase());
                            List<Shop> shops = db.getAllShops();
                            for (Shop shop : shops) {
                                String log = "Id: " + shop.getId() + ",Name: " + shop.getName() ;
                                Log.d("Shop: : ", log);
                            }
                            newdb = db;
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

                }else{
                    listname = spinner.getSelectedItem().toString() ;
                    newdb = db;
                    Intent intent = new Intent(v.getContext(),list.class);
                    startActivityForResult(intent,0);
                }
            }
        });
        delete = (Button) findViewById(R.id.button2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem().toString() !="Create New List") {
                    List<Shop> shoplist = db.getAllShops();
                    for(Shop shop : shoplist){
                        if (shop.getName().equals(spinner.getSelectedItem().toString())){
                            listname = spinner.getSelectedItem().toString();
                            db.deleteShop(db.getShop(spinner.getSelectedItem().toString()));
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }

                    }


                }
            }
        });

        }


}
