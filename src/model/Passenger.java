package model;

public class Passenger {
    private int passengerId;
    private String name;
    private String email;
    private String phone;
    private String passportNo;

    public Passenger() {}

    public Passenger(String name, String email, String phone, String passportNo) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.passportNo = passportNo;
    }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassportNo() { return passportNo; }
    public void setPassportNo(String passportNo) { this.passportNo = passportNo; }

    @Override
    public String toString() {
        return String.format("| %-5d | %-20s | %-25s | %-15s | %-12s |",
                passengerId, name, email, phone, passportNo);
    }
}