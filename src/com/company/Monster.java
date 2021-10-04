package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Monster extends GameCharacter {

    private int monsterID;
    private String monsterName;
    private ArrayList<String> monsterDescription;
    private ArrayList<String> defeatedDescription;
    private int monsterRoomID;
    private boolean defeated;

    public Monster() {
    }

    public int getMonsterID() {
        return monsterID;
    }

    public void setMonsterID(int monsterID) {
        this.monsterID = monsterID;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public ArrayList<String> getMonsterDescription() {
        return monsterDescription;
    }

    public void setMonsterDescription(ArrayList<String> monsterDescription) {
        this.monsterDescription = monsterDescription;
    }

    public ArrayList<String> getDefeatedDescription() {
        return defeatedDescription;
    }

    public void setDefeatedDescription(ArrayList<String> defeatedDescription) {
        this.defeatedDescription = defeatedDescription;
    }

    public int getMonsterRoomID() {
        return monsterRoomID;
    }

    public void setMonsterRoomID(int monsterRoomID) {
        this.monsterRoomID = monsterRoomID;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "monsterID=" + monsterID +
                "\n, monsterName='" + monsterName + '\'' +
                "\n, monsterDescription=" + monsterDescription +
                "\n, defeatedDescription=" + defeatedDescription +
                "\n, monsterRoomID=" + monsterRoomID +
                ", defeated=" + defeated +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Monster monster = (Monster) o;
        return monsterID == monster.monsterID && monsterRoomID == monster.monsterRoomID && defeated == monster.defeated && Objects.equals(monsterName, monster.monsterName) && Objects.equals(monsterDescription, monster.monsterDescription) && Objects.equals(defeatedDescription, monster.defeatedDescription);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), monsterID, monsterName, monsterDescription, defeatedDescription, monsterRoomID, defeated);
    }
}
