import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    public static void displayOptions() {
        System.out.println("Welcome to Hotel Reservation Application");
        System.out.println();
        System.out.println("========================================");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("========================================");
        System.out.println("Please select a number for the menu option");
    }

    public static boolean executeOption(Scanner sc, Integer selection) {
        boolean keepRunning = true;
        switch (selection) {
            case 1:
                findAndReserveRoom(sc);
                break;
            case 2:
                getCustomerReservation(sc);
                break;
            case 3:
                createAccount(sc);
                break;
            case 4:
                runAdminMenu(sc);
                break;
            case 5:
                keepRunning = false;
                break;
            default:
                System.out.println("Please enter a number between 1 and 5 \n");
        }
        return keepRunning;
    }
// 4
    private static void runAdminMenu(Scanner sc) {
        boolean keepAdminRunning = true;
        while (keepAdminRunning) {
            try {
                AdminMenu.displayOptions();
                int adminSelect = Integer.parseInt(sc.nextLine());
                keepAdminRunning = AdminMenu.executeOption(sc, adminSelect);
            }catch (Exception e) {
                System.out.println("Please enter a number between 1 and 5 \n");
            }
        }
    }
// 3
    private static void createAccount(Scanner sc) {
        System.out.println("First Name: ");
        String fistName = sc.nextLine();
        System.out.println("Last Name: ");
        String lastName = sc.nextLine();
        System.out.println("Email (format: name@example.com): ");
        boolean validEmail = false;
        while (!validEmail) {
            String email = sc.nextLine();
            String emailRegEx = "^(.+)@(.+).(.+)$";
            Pattern pattern = Pattern.compile(emailRegEx);
            if (pattern.matcher(email).matches()) {
                validEmail = true;
                System.out.println("Account created successfully!\n");
                HotelResource.createACustomer(email,fistName,lastName);
            }else {
                System.out.println("Wrong email address");
            }
        }
    }
// 2
    private static void getCustomerReservation(Scanner sc) {
        System.out.println("Please enter your Email  (format: name@example.com): ");
        String email = sc.nextLine();
        Customer customer = HotelResource.getCustomer(email);
        if (customer == null) {
            System.out.println("Sorry, no account exists for that email");
            return;
        }
        Collection<Reservation> reservations = HotelResource.getCustomersReservations(email);
        if (reservations.isEmpty()) {
            System.out.println("You don't have any reservations at the moment");
            return;
        }
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
// 1
    private static void findAndReserveRoom(Scanner sc) {
        Date checkInDate = getValidCheckInDate(sc);
        Date checkOutDate = getValidCheckOutDate(sc, checkInDate);
        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);
        boolean wantsToBook = false;
        if (availableRooms == null) {
            System.out.println("There are no available rooms for those dates!");
        } else {
            System.out.println("Available rooms for check-in on " + checkInDate + ", check-out on " + checkOutDate);
            wantsToBook = showRoomAndAskToBook(sc, availableRooms);
        }
        if (!wantsToBook) {
            return;
        }
        Customer customer = checkHasAccount(sc);
        if (customer == null) {
            System.out.println("Sorry, no account exists for that email");
            return;
        }
        IRoom room = checkRoom(sc, availableRooms);
        Reservation reservation = HotelResource.bookARoom(customer.getEmail(), room, checkInDate, checkOutDate);
        if (reservation == null) {
            System.out.println("Couldn't process your booking, the room is not available");
        }else {
            System.out.println("Thank you! Your room was booked successfully!");
            System.out.println(reservation);
        }
    }

    private static IRoom checkRoom(Scanner sc, Collection<IRoom> availableRooms) {
        IRoom room = null;
        String roomNumber = null;
        boolean validRoomNumber = false;
        while (!validRoomNumber) {
            System.out.println("What room would you like to reserve? Enter the room number: ");
            roomNumber = sc.nextLine();
            room = HotelResource.getRoom(roomNumber);
            if (room == null) {
                System.out.println("That room doesn't exists, please enter a valid room number");
            } else {
                if (!availableRooms.contains(room)) {
                    System.out.println("That room is not available, please enter another room number");
                } else {
                    validRoomNumber = true;
                }
            }
        }
        return room;
    }

    private static Customer checkHasAccount(Scanner sc) {
        String email = null;
        boolean hasAccount = false;
        System.out.println("Do you already have an account with us? y/n");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            hasAccount = true;
        }
        if(hasAccount) {
            System.out.println("Please enter your email: ");
            String emailRegEx = "^(.+)@(.+).(.+)$";
            Pattern pattern = Pattern.compile(emailRegEx);
            boolean isValidEmail = false;
            while (!isValidEmail) {
                String emailTemp = sc.nextLine();
                if (pattern.matcher(email).matches()) {
                    email = emailTemp;
                    isValidEmail = true;
                }else {
                    System.out.println("Wrong email format");
                }
            }
        }else {
            return null;
        }
        return HotelResource.getCustomer(email);
    }

    private static boolean showRoomAndAskToBook(Scanner sc, Collection<IRoom> availableRooms) {
        for (IRoom availableRoom : availableRooms) {
            System.out.println(availableRoom.toString());
            System.out.println();
            System.out.println("Would you like to book a room? Enter y/n");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                return true;
            }
        }
        return false;
    }

    public static Date getValidCheckInDate(Scanner sc) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        boolean isValidDate = false;
        while (!isValidDate) {
            System.out.println("Please input date: ");
            String inputDate = sc.nextLine();
            try {
                date = dateFormat.parse(inputDate);
                Date today = new Date();
                if (date.before(today)) {
                    System.out.println("The check-in date cannot be in the past");
                } else {
                    isValidDate = true;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format, please use dd/mm/yyyy");
            }
        }
        return date;
    }

    public static Date getValidCheckOutDate(Scanner sc, Date checkInDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        boolean isValidDate = false;
        while (!isValidDate) {
            System.out.println("Please input date: ");
            String inputDate = sc.nextLine();
            try {
                date = dateFormat.parse(inputDate);
                if (date.before(checkInDate)) {
                    System.out.println("The check-in date cannot be before the check-in date");
                } else {
                    isValidDate = true;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format, please use mm/dd/yyyy");
            }
        }
        return date;
    }
}
