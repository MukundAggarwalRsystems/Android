package com.example.tushar.ips_rev1;

/**
 * Created by Tushar on 28-Jun-16.
 */
public class Room {
    int id;
    String name;
    String floor;
    float width;
    float length;

    public Room(){}

    public Room(String name, String floor, float width, float length)
    {
        this.name = name;
        this.floor = floor;
        this.width = width;
        this.length = length;
    }
}
