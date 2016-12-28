package com.example.tushar.ips_rev1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Tushar on 28-Jun-16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IPS";
    private static final String TABLE_ROOMS = "ROOMS";
    private static final String TABLE_FLOORS = "FLOORS";
    private static final String ID = "_id";
    private static final String ROOM_NAME = "roomname";
    private static final String FLOOR_NAME = "floorname";
    private static final String ROOM_WIDTH = "roomwidth";
    private static final String ROOM_LENGTH = "roomlength";

    public DBHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "IPS"
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROOMS_TABLE = "CREATE TABLE "
                + TABLE_ROOMS + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                ROOM_NAME + " VARCHAR2 NOT NULL, " +
                FLOOR_NAME + " VARCHAR2 NOT NULL, " +
                ROOM_WIDTH + " FLOAT NOT NULL, "+
                ROOM_LENGTH + " FLOAT NOT NULL " + ");";
        db.execSQL(CREATE_ROOMS_TABLE);

        String CREATE_FLOORS_TABLE = "CREATE TABLE "
                + TABLE_FLOORS + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                FLOOR_NAME + " VARCHAR2 NOT NULL" + ");";
        db.execSQL(CREATE_FLOORS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        this.onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLOORS);
        this.onCreate(db);
    }

    //Add a room
    public void addRoom(Room room){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ROOM_NAME, room.name);
        values.put(FLOOR_NAME, room.floor);
        values.put(ROOM_WIDTH, room.width);
        values.put(ROOM_LENGTH, room.length);
        db.insert(TABLE_ROOMS, null, values);
        db.close();
    }

    //Retrieve a room on basis of room name
    public Room getRoom(String room_name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ROOMS,
                new String[]{ID,ROOM_NAME, FLOOR_NAME,ROOM_WIDTH,ROOM_LENGTH},
                ROOM_NAME + " = " + room_name,
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Room room = new Room();
        room.id = Integer.parseInt(cursor.getString(0));
        room.name = cursor.getString(1);
        room.floor =cursor.getString(2);
        room.width = Integer.parseInt(cursor.getString(3));
        room.length = Integer.parseInt(cursor.getString(4));

        db.close();
        return room;
    }

    //Retrieve all rooms on basis of particular floor
    public ArrayList getAllRooms(String floor){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_ROOMS,
                new String[]{ID,ROOM_NAME, FLOOR_NAME,ROOM_WIDTH,ROOM_LENGTH},
                FLOOR_NAME + " ='" + floor + "'"
                ,null,null,null,null);


        ArrayList<String> result = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            String value = c.getString(c.getColumnIndex(ROOM_NAME));
            result.add(value);
        }
        db.close();
        return result;

    }

    //Add a floor
    public void addFloor(Floor floor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FLOOR_NAME, floor.name);
        db.insert(TABLE_FLOORS, null, values);
        db.close();
    }

    //Retrieve a floor on basis of floor name
    public Floor getFloor(String floor_name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FLOORS,
                new String[]{ID,FLOOR_NAME},
                FLOOR_NAME + " ='" + floor_name+"' ",
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Floor floor = new Floor();
        floor.id = Integer.parseInt(cursor.getString(0));
        floor.name = cursor.getString(1);

        db.close();
        return floor;
    }

    //Retrieve all floors
    public ArrayList getAllFloors(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_FLOORS,
                new String[]{ID,FLOOR_NAME},
                null ,null,null,null,null);


        ArrayList<String> result = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            String value = c.getString(c.getColumnIndex(FLOOR_NAME));
            result.add(value);
        }
        db.close();
        return result;

    }



}