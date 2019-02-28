package com.example.week3daily3.database;

import java.util.Locale;

public class DBContract {

    public static final String DB_NAME = "animals_db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Animals";
    public static final String COL_ID = "id";
    public static final String COL_TYPE = "type";
    public static final String COL_NAME = "name";
    public static final String COL_SOUND = "sound";
    public static final String COL_IMAGE = "image";

    public static String createQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(TABLE_NAME);
        builder.append(" ( ");
        builder.append(COL_ID);
        builder.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        builder.append(COL_TYPE);
        builder.append(" TEXT, ");
        builder.append(COL_NAME);
        builder.append(" TEXT, ");
        builder.append(COL_SOUND);
        builder.append(" TEXT, ");
        builder.append(COL_IMAGE);
        builder.append(" TEXT )");

        System.out.println(builder.toString());

        return builder.toString();
    }

    public static String getAllAnimalsQuery() {
        return "SELECT * FROM " + TABLE_NAME;
    }

    public static String getAnimalById(int id) {
        return String.format("SELECT * FROM %s WHERE %s = \"%d\"", TABLE_NAME, COL_ID, id);
    }

    public static String getWhereClauseById() {
        return String.format(Locale.US, "%s = ", COL_ID);
    }

    // Debugging purposes
    public static void main(String[] args) {
        createQuery();
        System.out.println(getAnimalById(1));
    }
}
