package dao;

import model.Passenger;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {

    public boolean addPassenger(Passenger p) {
        String sql = "INSERT INTO passenger (name, email, phone, passport_no) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getPhone());
            ps.setString(4, p.getPassportNo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error adding passenger: " + e.getMessage());
            return false;
        }
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM passenger";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Passenger p = new Passenger();
                p.setPassengerId(rs.getInt("passenger_id"));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                p.setPassportNo(rs.getString("passport_no"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching passengers: " + e.getMessage());
        }
        return list;
    }

    public Passenger getPassengerById(int id) {
        String sql = "SELECT * FROM passenger WHERE passenger_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Passenger p = new Passenger();
                p.setPassengerId(rs.getInt("passenger_id"));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                p.setPassportNo(rs.getString("passport_no"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching passenger: " + e.getMessage());
        }
        return null;
    }

    public boolean deletePassenger(int id) {
        String sql = "DELETE FROM passenger WHERE passenger_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error deleting passenger: " + e.getMessage());
            return false;
        }
    }
}