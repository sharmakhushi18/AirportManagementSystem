# ✈️ Airport Management System

A console-based Java application simulating core airport operations — passenger registration, flight management, seat booking, and cancellation — built with strict DAO architecture and JDBC.

---

## 📌 What Is This?

A backend Java system that manages the full lifecycle of airport operations:
- Register passengers and manage their profiles
- Add and track flights with seat availability
- Book seats with duplicate validation
- Cancel bookings and auto-restore seat count

> Built to demonstrate clean Java architecture — no framework shortcuts, pure JDBC and DAO pattern.

---

## 🛠️ Tech Stack

| Technology | Usage |
|------------|-------|
| Java | Core language |
| JDBC | Database connectivity |
| MySQL | Relational database |
| DAO Pattern | Architecture |
| OOP | Design principles |

---

## 📐 Architecture

```
Console Input (Main.java)
        ↓
   Service Layer          ← Business logic & validation
        ↓
   DAO Layer              ← Database operations (JDBC)
        ↓
   MySQL Database         ← Persistent storage
```

**Each layer has a single responsibility:**
- Main handles user input only
- Service handles all business rules
- DAO handles all SQL queries — no logic here

---

## 🗄️ Database Schema

```
passengers
├── id (PK, auto-increment)
├── name
├── email (unique)
├── phone
└── passport_number (unique)

flights
├── id (PK, auto-increment)
├── flight_number (unique)
├── source
├── destination
├── departure_time
├── total_seats
└── available_seats

bookings
├── id (PK, auto-increment)
├── passenger_id (FK)
├── flight_id (FK)
├── seat_number (unique per flight)
└── booking_time (auto-set)
```

---

## 🚀 Features

- Add, view, and manage passengers
- Add and track flights with seat availability
- Book a seat — validates duplicates before confirming
- Cancel booking — auto-restores available seat count
- View all bookings per passenger or per flight

---

## ⚙️ How to Run Locally

**Prerequisites**
- Java 17+
- MySQL 8.0
- Any Java IDE (IntelliJ IDEA recommended)

**Steps**

```bash
# 1. Clone the repository
git clone https://github.com/sharmakhushi18/AirportManagementSystem.git

# 2. Create MySQL database
mysql -u root -p
CREATE DATABASE airport_db;

# 3. Update DB credentials in DBConnection.java
String url = "jdbc:mysql://localhost:3306/airport_db";
String user = "root";
String password = "your_password";

# 4. Run Main.java
```

---

## 💡 Key Design Decisions

**Why DAO Pattern?**
Separates database logic from business logic completely. If MySQL is swapped for PostgreSQL tomorrow — only the DAO layer changes, nothing else.

**Why JDBC over JPA/Hibernate?**
JDBC gives full control over SQL queries. Understanding raw database communication before using ORM abstractions builds stronger fundamentals.

**Why unique constraint on seat_number per flight?**
Duplicate seat prevention must happen at the database level — not just application level. Two simultaneous bookings for the same seat will be caught by the constraint even if application-level check passes.

**Why auto-restore seats on cancellation?**
Seat count is a derived value — it must always reflect actual bookings. Cancellation triggers an immediate seat count update to keep data consistent.

---

## 🔮 Future Improvements

- [ ] Spring Boot migration — replace JDBC with JPA/Hibernate
- [ ] REST API layer — expose operations via HTTP endpoints
- [ ] JWT Authentication — secure passenger and admin access
- [ ] Flight status tracking — DELAYED, CANCELLED, DEPARTED states
- [ ] Automated alerts — notify passengers on flight changes

---

## 👩‍💻 Author

**Khushi Sharma**
Full Stack Developer | Java + React
Final Year ECE · LNCT Bhopal
[github.com/sharmakhushi18](https://github.com/sharmakhushi18)
