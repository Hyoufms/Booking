package model;

public class Room implements IRoom{
    private String number;
    private double price;
    private RoomType type;

    public Room() {
    }

    public Room(String number, double price, RoomType enumeration) {
        this.number = number;
        this.price = price;
        this.type = enumeration;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Room: "+
                "number: " + number +
                ", price: " + price +
                ", type: " + type;
    }

    @Override
    public String getRoomNumber() {
        return null;
    }

    @Override
    public Double getRoomPrice() {
        return null;
    }

    @Override
    public RoomType getRoomType() {
        return null;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
