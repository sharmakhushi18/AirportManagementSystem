import dao.BookingDAO;
import dao.FlightDAO;
import dao.PassengerDAO;
import model.Booking;
import model.Flight;
import model.Passenger;
import util.DBConnection;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static PassengerDAO passengerDAO = new PassengerDAO();
    static FlightDAO flightDAO = new FlightDAO();
    static BookingDAO bookingDAO = new BookingDAO();

    public static void main(String[] args) {
        System.out.println("✈️  Welcome to Airport Management System");
        System.out.println("==========================================");

        int choice;
        do {
            showMenu();
            choice = getInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addPassenger();
                case 2 -> viewAllPassengers();
                case 3 -> addFlight();
                case 4 -> viewAllFlights();
                case 5 -> bookFlight();
                case 6 -> viewAllBookings();
                case 7 -> cancelBooking();
                case 8 -> viewBookingsByPassenger();
                case 0 -> {
                    DBConnection.closeConnection();
                    System.out.println("👋 Goodbye!");
                }
                default -> System.out.println("❌ Invalid choice!");
            }
        } while (choice != 0);
    }

    static void showMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Add Passenger");
        System.out.println("2. View All Passengers");
        System.out.println("3. Add Flight");
        System.out.println("4. View All Flights");
        System.out.println("5. Book Flight");
        System.out.println("6. View All Bookings");
        System.out.println("7. Cancel Booking");
        System.out.println("8. My Bookings (by Passenger ID)");
        System.out.println("0. Exit");
        System.out.println("================================");
    }

    static void addPassenger() {
        System.out.println("\n--- Add Passenger ---");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Passport No: ");
        String passport = sc.nextLine();
        Passenger p = new Passenger(name, email, phone, passport);
        if (passengerDAO.addPassenger(p))
            System.out.println("✅ Passenger added!");
        else
            System.out.println("❌ Failed.");
    }

    static void viewAllPassengers() {
        System.out.println("\n--- All Passengers ---");
        System.out.printf("| %-5s | %-20s | %-25s | %-15s | %-12s |%n",
                "ID", "Name", "Email", "Phone", "Passport");
        System.out.println("-".repeat(90));
        List<Passenger> list = passengerDAO.getAllPassengers();
        if (list.isEmpty()) System.out.println("No passengers found.");
        else list.forEach(System.out::println);
    }

    static void addFlight() {
        System.out.println("\n--- Add Flight ---");
        System.out.print("Flight Number (e.g. AI101): ");
        String fn = sc.nextLine();
        System.out.print("Source: ");
        String src = sc.nextLine();
        System.out.print("Destination: ");
        String dest = sc.nextLine();
        System.out.print("Departure (yyyy-MM-dd HH:mm:ss): ");
        String dep = sc.nextLine();
        System.out.print("Arrival (yyyy-MM-dd HH:mm:ss): ");
        String arr = sc.nextLine();
        int total = getInt("Total Seats: ");
        Flight f = new Flight(fn, src, dest, dep, arr, total, total);
        if (flightDAO.addFlight(f))
            System.out.println("✅ Flight added!");
        else
            System.out.println("❌ Failed.");
    }

    static void viewAllFlights() {
        System.out.println("\n--- All Flights ---");
        System.out.printf("| %-5s | %-10s | %-15s | %-15s | %-20s | %-20s | %-5s |%n",
                "ID", "Flight No", "Source", "Destination", "Departure", "Arrival", "Seats");
        System.out.println("-".repeat(100));
        List<Flight> list = flightDAO.getAllFlights();
        if (list.isEmpty()) System.out.println("No flights found.");
        else list.forEach(System.out::println);
    }

    static void bookFlight() {
        System.out.println("\n--- Book Flight ---");
        viewAllFlights();
        int flightId = getInt("Enter Flight ID to book: ");
        Flight f = flightDAO.getFlightById(flightId);
        if (f == null) { System.out.println("❌ Flight not found."); return; }
        if (f.getAvailableSeats() <= 0) { System.out.println("❌ No seats available."); return; }
        viewAllPassengers();
        int passengerId = getInt("Enter Passenger ID: ");
        Passenger p = passengerDAO.getPassengerById(passengerId);
        if (p == null) { System.out.println("❌ Passenger not found."); return; }
        System.out.print("Seat Number (e.g. A1): ");
        String seat = sc.nextLine();
        if (bookingDAO.isSeatAlreadyBooked(flightId, seat)) {
            System.out.println("❌ Seat already booked."); return;
        }
        Booking b = new Booking(passengerId, flightId, LocalDate.now().toString(), seat, "CONFIRMED");
        if (bookingDAO.addBooking(b)) {
            flightDAO.updateAvailableSeats(flightId, f.getAvailableSeats() - 1);
            System.out.println("✅ Booking successful!");
            System.out.println("Passenger : " + p.getName());
            System.out.println("Flight    : " + f.getFlightNumber() + " (" + f.getSource() + " → " + f.getDestination() + ")");
            System.out.println("Seat      : " + seat);
            System.out.println("Date      : " + LocalDate.now());
        } else {
            System.out.println("❌ Booking failed.");
        }
    }

    static void viewAllBookings() {
        System.out.println("\n--- All Bookings ---");
        System.out.printf("| %-10s | %-12s | %-10s | %-15s | %-8s | %-12s |%n",
                "Booking ID", "Passenger ID", "Flight ID", "Date", "Seat", "Status");
        System.out.println("-".repeat(80));
        List<Booking> list = bookingDAO.getAllBookings();
        if (list.isEmpty()) System.out.println("No bookings found.");
        else list.forEach(System.out::println);
    }

    static void cancelBooking() {
        int bookingId = getInt("Enter Booking ID to cancel: ");
        if (bookingDAO.cancelBooking(bookingId))
            System.out.println("✅ Booking cancelled!");
        else
            System.out.println("❌ Failed.");
    }

    static void viewBookingsByPassenger() {
        int passengerId = getInt("Enter Passenger ID: ");
        List<Booking> list = bookingDAO.getBookingsByPassenger(passengerId);
        if (list.isEmpty()) System.out.println("No bookings found.");
        else list.forEach(System.out::println);
    }

    static int getInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("❌ Enter a valid number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }
}