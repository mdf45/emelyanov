package com.emelyanov.raspisanie.ui;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item {
    String name;
    String age;
    public Item(String name, String time) {
        this.name = name;
        this.age = time;
    }

    public static List<Item> items;

    public static List<Item> initialize(Context context, String date) {
        SQLiteHelper sql = new SQLiteHelper(context);
        Cursor cursor = sql.readAllDataWhere(date);

        if (cursor.getCount() == 0) return new ArrayList<>();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();

        while (cursor.moveToNext()) {
            names.add(cursor.getString(2));
            times.add(cursor.getString(3));
        }

        List<String> itemNames = Arrays.asList(names.get(0).split(";"));
        List<String> itemTimes = Arrays.asList(times.get(0).split(";"));

        List<Item> _items = new ArrayList<>();

        for (int i = 0; i < itemNames.size(); ++i) {
            _items.add(new Item(itemNames.get(i), itemTimes.get(i)));
        }

        items = _items;

        return _items;
    }

    public static String getNames(Context context, String date) {
        SQLiteHelper sql = new SQLiteHelper(context);
        Cursor cursor = sql.readAllDataWhere(date);

        if (cursor.getCount() == 0) return "";

        ArrayList<String> names = new ArrayList<>();

        while (cursor.moveToNext()) {
            names.add(cursor.getString(2));
        }

        return names.get(0);
    }

    public static String getTimes(Context context, String date) {
        SQLiteHelper sql = new SQLiteHelper(context);
        Cursor cursor = sql.readAllDataWhere(date);

        if (cursor.getCount() == 0) return "";

        ArrayList<String> times = new ArrayList<>();

        while (cursor.moveToNext()) {
            times.add(cursor.getString(3));
        }

        return times.get(0);
    }
}

