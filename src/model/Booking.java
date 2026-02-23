package model;

public class Booking {
    private int bookingId;
    private int passengerId;
    private int flightId;
    private String bookingDate;
    private String seatNo;
    private String status;

    public Booking() {}

    public Booking(int passengerId, int flightId, String bookingDate, String seatNo, String status) {
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.seatNo = seatNo;
        this.status = status;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("| %-10d | %-12d | %-10d | %-15s | %-8s | %-12s |",
                bookingId, passengerId, flightId, bookingDate, seatNo, status);
    }
}