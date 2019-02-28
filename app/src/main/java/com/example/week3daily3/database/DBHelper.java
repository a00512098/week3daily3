package com.example.week3daily3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import static com.example.week3daily3.database.DBContract.COL_ID;
import static com.example.week3daily3.database.DBContract.COL_IMAGE;
import static com.example.week3daily3.database.DBContract.COL_NAME;
import static com.example.week3daily3.database.DBContract.COL_SOUND;
import static com.example.week3daily3.database.DBContract.COL_TYPE;
import static com.example.week3daily3.database.DBContract.DB_VERSION;
import static com.example.week3daily3.database.DBContract.TABLE_NAME;
import static com.example.week3daily3.database.DBContract.createQuery;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public long insertAnimalToDB(@NonNull Animal animal) {
        SQLiteDatabase writableDB = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, animal.getName());
        contentValues.put(COL_IMAGE, animal.getImageUrl());
        contentValues.put(COL_SOUND, animal.getSound());
        contentValues.put(COL_TYPE, animal.getType());

        return writableDB.insert(TABLE_NAME, null, contentValues);
    }

    public Animal getAnimalById(int id) {
        SQLiteDatabase readableDB = getReadableDatabase();
        Animal animal = new Animal();
        Cursor cursor = readableDB.rawQuery(DBContract.getAnimalById(id), null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            String img = cursor.getString(cursor.getColumnIndex(COL_IMAGE));
            String sound = cursor.getString(cursor.getColumnIndex(COL_SOUND));
            String type = cursor.getString(cursor.getColumnIndex(COL_TYPE));

            animal = new Animal(id, type, name, sound, img);
        }
        cursor.close();
        return animal;
    }

    public ArrayList<Animal> getAllAnimalsInDB() {
        ArrayList<Animal> animals = new ArrayList<>();
        SQLiteDatabase readableDB = getReadableDatabase();
        Cursor cursor = readableDB.rawQuery(DBContract.getAllAnimalsQuery(), null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                String type = cursor.getString(cursor.getColumnIndex(COL_TYPE));
                String sound = cursor.getString(cursor.getColumnIndex(COL_SOUND));
                String img = cursor.getString(cursor.getColumnIndex(COL_IMAGE));

                animals.add(new Animal(id, type, name, sound, img));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return animals;
    }
}
