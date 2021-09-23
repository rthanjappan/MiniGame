package com.company;

import java.util.Objects;

public class Items {

    String itemName;
    String itemDescription;
    int roomId;

    public Items() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Items{" +
                "itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", roomId=" + roomId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items = (Items) o;
        return roomId == items.roomId && Objects.equals(itemName, items.itemName) && Objects.equals(itemDescription, items.itemDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, itemDescription, roomId);
    }
}
