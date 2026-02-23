package dao;

import model.Flight;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    public boolean addFlight(Flight f) {
        String sql = "INSERT INTO flight (flight_number, source, destination, departure_time, arrival_time, total_seats, available_seats) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getFlightNumber());
            ps.setString(2, f.getSource());
            ps.setString(3, f.getDestination());
            ps.setString(4, f.getDepartureTime());
            ps.setString(5, f.getArrivalTime());
            ps.setInt(6, f.getTotalSeats());
            ps.setInt(7, f.getAvailableSeats());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error adding flight: " + e.getMessage());
            return false;
        }
    }

    public List<Flight> getAllFlights() {
        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM flight";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Flight f = new Flight();
                f.setFlightId(rs.getInt("flight_id"));
                f.setFlightNumber(rs.getString("flight_number"));
                f.setSource(rs.getString("source"));
                f.setDestination(rs.getString("destination"));
                f.setDepartureTime(rs.getString("departure_time"));
                f.setArrivalTime(rs.getString("arrival_time"));
                f.setTotalSeats(rs.getInt("total_seats"));
                f.setAvailableSeats(rs.getInt("available_seats"));
                list.add(f);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching flights: " + e.getMessage());
        }
        return list;
    }

    public Flight getFlightById(int id) {
        String sql = "SELECT * FROM flight WHERE flight_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Flight f = new Flight();
                f.setFlightId(rs.getInt("flight_id"));
                f.setFlightNumber(rs.getString("flight_number"));
                f.setSource(rs.getString("source"));
                f.setDestination(rs.getString("destination"));
                f.setDepartureTime(rs.getString("departure_time"));
                f.setArrivalTime(rs.getString("arrival_time"));
                f.setTotalSeats(rs.getInt("total_seats"));
                f.setAvailableSeats(rs.getInt("available_seats"));
                return f;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching flight: " + e.getMessage());
        }
        return null;
    }

    public boolean updateAvailableSeats(int flightId, int seats) {
        String sql = "UPDATE flight SET available_seats = ? WHERE flight_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, seats);
            ps.setInt(2, flightId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error updating seats: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFlight(int id) {
        String sql = "DELETE FROM flight WHERE flight_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error deleting flight: " + e.getMessage());
            return false;
        }
    }
}