package dao;

import model.Booking;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean addBooking(Booking b) {
        String sql = "INSERT INTO booking (passenger_id, flight_id, booking_date, seat_no, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, b.getPassengerId());
            ps.setInt(2, b.getFlightId());
            ps.setString(3, b.getBookingDate());
            ps.setString(4, b.getSeatNo());
            ps.setString(5, b.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error adding booking: " + e.getMessage());
            return false;
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setPassengerId(rs.getInt("passenger_id"));
                b.setFlightId(rs.getInt("flight_id"));
                b.setBookingDate(rs.getString("booking_date"));
                b.setSeatNo(rs.getString("seat_no"));
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching bookings: " + e.getMessage());
        }
        return list;
    }

    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE booking SET status = 'CANCELLED' WHERE booking_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error cancelling booking: " + e.getMessage());
            return false;
        }
    }

    public List<Booking> getBookingsByPassenger(int passengerId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE passenger_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, passengerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setPassengerId(rs.getInt("passenger_id"));
                b.setFlightId(rs.getInt("flight_id"));
                b.setBookingDate(rs.getString("booking_date"));
                b.setSeatNo(rs.getString("seat_no"));
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching bookings: " + e.getMessage());
        }
        return list;
    }

    public boolean isSeatAlreadyBooked(int flightId, String seatNo) {
        String sql = "SELECT COUNT(*) FROM booking WHERE flight_id = ? AND seat_no = ? AND status = 'CONFIRMED'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, flightId);
            ps.setString(2, seatNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error checking seat: " + e.getMessage());
        }
        return false;
    }
}