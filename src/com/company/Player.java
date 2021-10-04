package com.company;

import java.util.ArrayList;

public class Player extends GameCharacter {

    private ArrayList<Item> playerInventory;
    private static int roomID;
    Map map= new Map();//The map object containing all room objects with puzzles,items and monsters.
    public Player() {
        this.playerInventory = new ArrayList<>();
    }

    public static int getPlayerLocation(){
        return roomID;
    }
    public static void setPlayerLocation(int roomid){
        roomID=roomid;
    }

    public ArrayList<Item> getPlayerInventory() {
        return playerInventory;
    }

    public void setPlayerInventory(ArrayList<Item> playerInventory) {
        this.playerInventory = playerInventory;
    }
}
