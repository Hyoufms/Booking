import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {
    public static void displayOptions() {
        System.out.println("Admin Menu");
        System.out.println();
        System.out.println("========================================");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to main menu");
        System.out.println("========================================");
        System.out.println("Please select a number for the menu option");
    }

    public static boolean executeOption(Scanner sc, Integer selection) {
        boolean keepRunning = true;
        switch (selection) {
            case 1:
                getAllCustomers(sc);
                break;
            case 2:
                getAllRooms(sc);
                break;
            case 3:
                getAllReservations(sc);
                break;
            case 4:
                addRoom(sc);
                break;
            case 5:
                keepRunning = false;
                break;
            default:
                System.out.println("Please enter a number between 1 and 5 \n");
        }
        return keepRunning;
}

    private static void addRoom(Scanner sc) {
        String roomNumber = null;
        boolean validRoomNumber = false;
        while(!validRoomNumber) {
            System.out.println("Enter room number");
            roomNumber = sc.nextLine();
            IRoom roomExists = HotelResource.getRoom(roomNumber);
            if (roomExists == null) {
                validRoomNumber = true;
            }else{
                System.out.println("That room already exists. Enter y/yes to update it, or any other character to enter another room number: " );
                String choice = sc.nextLine();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                    validRoomNumber = true;
                }
            }
        }

        double price = 0.00;
        boolean validPrice = false;
        while (!validPrice) {
            try {
                System.out.println("Enter room Price per night: ");
                price = Double.parseDouble(sc.nextLine());
                if (price <= 0) {
                    System.out.println("The price must be greater than 0.00");
                }else{
                    validPrice = true;
                }
            }catch (Exception e) {
                System.out.println("Please enter a valid price");
            }
        }

        RoomType roomType = null;
        boolean validRoomType = false;
        while (!validRoomType) {
            try {
                System.out.println("Enter room type, (1 for single, 2 for double)");
                roomType = RoomType.valueForNumberOfBeds(Integer.parseInt(sc.nextLine()));
                if (roomType == null) {
                    System.out.println("Please enter a valid room type");
                }else {
                    validRoomType = true;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid room type");
            }
        }
        Room newRoom = new Room(roomNumber, price, roomType);
        AdminResource.addRoom(newRoom);
    }

    private static void getAllReservations(Scanner sc) {
        Collection<Reservation> allReservations = AdminResource.getAllReservations();
        if (allReservations.isEmpty()) {
            System.out.println("There is no reservations");
        }else{
            for (Reservation reservation : allReservations) {
                System.out.println(reservation.toString());
            }
        }
        System.out.println();
    }

    private static void getAllRooms(Scanner sc) {
        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("There are no rooms");
        } else {
            for (IRoom room : allRooms) {
                System.out.println(room.toString());
            }
        }
        System.out.println();
    }

    private static void getAllCustomers(Scanner sc) {
        Collection<Customer> allCustomers = AdminResource.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("There are no customers");
        }else{
            for (Customer customer : allCustomers) {
                System.out.println(customer.toString());
            }
        }
        System.out.println();
    }
}