package model;

import enums.RoomType;

public class Room {
    int roomNumber;
    double roomPrice12Hours;
    double roomPrice24Hours;
    String roomType;

    boolean isFull;

    public Room(){}

    public Room(int roomNumber, double roomPrice12Hours, double roomPrice24Hours, String roomType, boolean isFull) {
        this.roomNumber = roomNumber;
        this.roomPrice12Hours = roomPrice12Hours;
        this.roomPrice24Hours = roomPrice24Hours;
        this.roomType = roomType;
        this.isFull = isFull;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRoomPrice12Hours() {
        return roomPrice12Hours;
    }

    public void setRoomPrice12Hours(double roomPrice12Hours) {
        this.roomPrice12Hours = roomPrice12Hours;
    }

    public double getRoomPrice24Hours() {
        return roomPrice24Hours;
    }

    public void setRoomPrice24Hours(double roomPrice24Hours) {
        this.roomPrice24Hours = roomPrice24Hours;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    @Override
    public String toString() {
        if(!isFull){
            return "Room{" +
                    "roomNumber=" + roomNumber +
                    ",roomPrice12Hourse="+roomPrice12Hours+"$"+
                    ",roomPrice24Hourse="+roomPrice24Hours+"$"+
                    ", roomType=" + roomType+
                    '}';
        }else{
            return "Room{" +
                    "roomNumber=" + roomNumber +
                    ",roomPrice12Hourse="+roomPrice12Hours+"$"+
                    ",roomPrice24Hourse="+roomPrice24Hours+"$"+
                    ", roomType=" + roomType+'}'+
                    "->This room is Busy !";
        }

    }
}
