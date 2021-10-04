package com.company;

import java.util.Objects;

public class GameCharacter {


    private int HP;

    public void attack(){

    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCharacter that = (GameCharacter) o;
        return HP == that.HP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(HP);
    }
}
