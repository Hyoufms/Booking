package model;

import java.util.HashMap;
import java.util.Map;

public enum RoomType {
    SINGLE (1),
    DOUBLE (2);

    private int numberOfBeds;
    private static final Map<Integer, RoomType> numberAndBeds = new HashMap<>();

    static {
        for(RoomType roomType : values()) {
            numberAndBeds.put(roomType.numberOfBeds, roomType);
        }
    }

    RoomType(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public static RoomType valueForNumberOfBeds(int numberOfBeds) {
        return numberAndBeds.get(numberOfBeds);
    }
}
