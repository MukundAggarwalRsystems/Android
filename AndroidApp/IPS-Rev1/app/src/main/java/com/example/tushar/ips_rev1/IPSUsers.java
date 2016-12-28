package com.example.tushar.ips_rev1;

import java.util.List;

/**
 * Created by birendra on 7/28/2016.
 */
public class IPSUsers {
    public long UserID;
    public String UserName;
    public List<Floors> Floors;
}

class Floors {
    public long FloorID;
    public String FloorName;
    public List<Rooms> Rooms;
}

class Rooms {
    public long RoomID;
    public String RoomName;
    public int RoomLength;
    public int RoomWidth;
    public int RoomHeight;
    public String RoomImage;
    public List<Devices> Devices;
}

class Devices {
    public String DeviceAddress;
    public String DeviceName;
    public int XValue;
    public int YValue;
}

