package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    public static void addRoom(IRoom room) {
        ReservationService.addRoom(room);
    }

    public static Collection<IRoom> getAllRooms() {
        return ReservationService.getAllRoom();
    }

    public static Collection<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    public static void displayAllReservations() {
        for (Reservation allReservation : ReservationService.getAllReservations()) {
            System.out.println(allReservation);
        }
    }

    public static Collection<Reservation> getAllReservations() { return ReservationService.getAllReservations(); }
}
