package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final Map<String, IRoom> roomMap = new HashMap<>();
    private static final Map<String, Collection<Reservation>> reservationMap = new HashMap<>();

    public static void addRoom(IRoom room) {
        roomMap.put(room.getRoomNumber(), room);
    }

    public static IRoom getARoom(String roomNumber) {
        return roomMap.get(roomNumber);
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (isRoomReserved(room, checkInDate, checkOutDate)) {
            return null;
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        Collection<Reservation> customerReservations = new LinkedList<>();
        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }
        customerReservations.add(reservation);
        reservationMap.put(customer.getEmail(), customerReservations);
        return  reservation;
    }


    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> reservedRooms = getAllReservedRooms(checkInDate, checkOutDate);
        Collection<IRoom> availableRooms = new LinkedList<>();
        for (IRoom room : getAllRoom()) {
            if(!reservedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public static Collection<Reservation> getCustomerReservations(Customer customer) {
        return reservationMap.get(customer.getEmail());
    }

    public static Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservation = new LinkedList<>();
        for (Collection<Reservation> customerReservations : reservationMap.values()) {
            allReservation.add((Reservation) customerReservations);
        }
        return allReservation;
    }

    public static Collection<IRoom> getAllRoom() {
        return roomMap.values();
    }

    public static Collection<IRoom> getAllReservedRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> reservedRooms = new LinkedList<>();
        for (Reservation allReservation : getAllReservations()) {
            if (allReservation.isRoomReserved(checkInDate, checkOutDate)) {
                reservedRooms.add(allReservation.getRoom());
            }
        }
        return reservedRooms;
    }

    public static boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
        if(checkInDate.before(checkInDate) && checkOutDate.after(checkOutDate)) {
            return true;
        }
        return false;
    }
}
