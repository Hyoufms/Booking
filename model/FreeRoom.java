package model;

public class FreeRoom extends Room{
    public void setPrice() {
        super.setPrice(0);
    }

    @Override
    public String toString() {
        return "FreeRoom{}";
    }
}
