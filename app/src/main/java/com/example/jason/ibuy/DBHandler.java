package com.example.jason.ibuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import static com.example.jason.ibuy.MainActivity.listname;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Lists";
    private static final String TABLE_SHOPS = "shoplist";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SHOPS + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT"
         + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPS);
// Creating tables again
        onCreate(db);
    }
    // Adding new shop
    public void addShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, shop.getId());
        values.put(KEY_NAME, shop.getName()); // Shop Name

// Inserting Row
        db.insert(TABLE_SHOPS, null, values);
        db.close(); // Closing database connection
    }
    // Getting one shop
    public Shop getShop(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHOPS, new String[]{KEY_ID,
                        KEY_NAME}, KEY_NAME + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Shop contact = new Shop(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
// return shop
        return contact;
    }
    // Getting All Shops
    public ArrayList<Shop> getAllShops() {
        List<Shop> shopList = new ArrayList<Shop>();
// Select All Query
        String selectQuery = "SELECT Name FROM " + TABLE_SHOPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Shop shop = new Shop();
                //shop.setId(Integer.parseInt(cursor.getString(0)));
                shop.setName(cursor.getString(0));

// Adding contact to list
                shopList.add(shop);
            } while (cursor.moveToNext());
        }

// return contact list
        return (ArrayList<Shop>) shopList;
    }

    public String[] getShops() {
        String[] shopList;
// Select All Query
        String selectQuery = "SELECT Name FROM " + TABLE_SHOPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        int length = getShopsCount();
        int counter = 0;
        shopList = new String[length];
        if (cursor.moveToFirst()) {
            do {
                Shop shop = new Shop();
                //shop.setId(Integer.parseInt(cursor.getString(0)));
                shop.setName(cursor.getString(0));

// Adding contact to list
                shopList[counter] = shop.getName();
                counter++;
            } while (cursor.moveToNext());
        }

// return contact list
        return shopList;
    }
    // Getting shops Count
    public int getShopsCount() {
        String countQuery = "SELECT * FROM " + TABLE_SHOPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

// return count
        return cursor.getCount();
    }

    // Deleting a shop
    public void deleteShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPS, KEY_ID + " = ?",
        new String[] { String.valueOf(shop.getId()) });
        db.execSQL("DROP TABLE IF EXISTS " + listname);
        db.close();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////Items List//////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void listtablecreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + listname + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + "item" + " TEXT,"
                + "amount" + " INTEGER,"
                + "need" + " INTEGER "
                + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    public int getItemsCount(String name) {
        String countQuery = "SELECT * FROM " + name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public ArrayList<items> getAllitems(String name) {
        List<items> itemList = new ArrayList<>();
// Select All Query
        String selectQuery = "SELECT * FROM " + name;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                items item = new items();
                item.setAmount(Integer.parseInt(cursor.getString(2)));
                item.setName(cursor.getString(1));
                item.setNeed(Integer.parseInt(cursor.getString(3)));

// Adding contact to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

// return contact list
        return (ArrayList<items>) itemList;
    }
    public void additems(items item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("amount", item.getAmount());
        values.put("id", item.getId());
        values.put("item", item.getName());
        values.put("need",item.getNeed());


// Inserting Row
        db.insert(listname, null, values);
        db.close(); // Closing database connection
    }
    public items getitem(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(listname, new String[]{KEY_ID,"name",
                        "amount","need"}, KEY_NAME + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        items contact = new items(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)));
// return shop
        return contact;
    }
    public int updateitems(items item, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("amount", item.getAmount());

// updating row
        return db.update(name, values, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }
    public void deleteitems(items item, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(name, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();
    }
}