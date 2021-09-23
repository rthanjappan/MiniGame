package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Room {
    private int RoomID;
    private String RoomName;
    private ArrayList<String> roomDescription;
    private HashMap<String,Integer> navTable;
    private boolean visted;
    private ArrayList<String> roomInventory;


    public Room() {

        roomDescription=new ArrayList<>();
        navTable=new HashMap<>();
        roomInventory=new ArrayList<>();
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }
    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public ArrayList<String> getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(ArrayList<String> roomDescription) {
        this.roomDescription = roomDescription;
    }

    public HashMap<String, Integer> getNavTable() {
        return navTable;
    }

    public void setNavTable(HashMap<String, Integer> navTable) {
        this.navTable = navTable;
    }

    public boolean isVisted() {
        return visted;
    }

    public void setVisted(boolean visted) {
        this.visted = visted;
    }

    @Override
    public String toString() {
        return "Room{" +
                "RoomID=" + RoomID +
                ", roomDescr='" + roomDescription + '\'' +
                "\n, navTable=" + navTable +
                "\n, visted=" + visted +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return RoomID == room.RoomID && visted == room.visted && Objects.equals(roomDescription, room.roomDescription) && Objects.equals(navTable, room.navTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RoomID, roomDescription, navTable, visted);
    }
}
