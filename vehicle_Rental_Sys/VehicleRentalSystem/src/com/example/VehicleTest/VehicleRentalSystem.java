package com.example.VehicleTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.vehicle.DatabaseManager;
import com.example.vehicle.Vehicle;

public class VehicleRentalSystem {
    private DatabaseManager dbManager = new DatabaseManager();

    public void addUser(String name, String email) {
        String query = "INSERT INTO users (name, email) VALUES ('" + name + "', '" + email + "')";
        try {
            dbManager.executeUpdate(query);
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    public void addVehicle(String model, String type, double rentPerDay, int availableCount) {
        String query = "INSERT INTO vehicles (model, type, rent_per_day, available_count) VALUES ('" + model + "', '" + type + "', " + rentPerDay + ", " + availableCount + ")";
        try {
            dbManager.executeUpdate(query);
            System.out.println("Vehicle added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        }
    }

    public void viewAvailableVehicles() {
        String query = "SELECT * FROM vehicles WHERE available_count > 0";
        try {
            ResultSet rs = dbManager.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String model = rs.getString("model");
                String type = rs.getString("type");
                double rentPerDay = rs.getDouble("rent_per_day");
                int availableCount = rs.getInt("available_count");
                Vehicle vehicle = new Vehicle(id, model, type, rentPerDay, availableCount);
                System.out.println(vehicle);
            }
        } catch (SQLException e) {
        	
            System.out.println("Error retrieving vehicles: " + e.getMessage());
        }
    }

    public void rentVehicle(int userId, int vehicleId, int days) {
    	
        String selectQuery = "SELECT * FROM vehicles WHERE id = " + vehicleId + " AND available_count > 0";
        
        
        try {
            ResultSet rs = dbManager.executeQuery(selectQuery);
            if (rs.next()) {
                double rentPerDay = rs.getDouble("rent_per_day");
                double totalCost = rentPerDay * days;

                String updateQuery = "UPDATE vehicles SET available_count = available_count - 1 WHERE id = " + vehicleId;
                dbManager.executeUpdate(updateQuery);

                String insertQuery = "INSERT INTO rentals (user_id, vehicle_id, days, total_cost) VALUES (" + userId + ", " + vehicleId + ", " + days + ", " + totalCost + ")";
                dbManager.executeUpdate(insertQuery);

                System.out.println("Vehicle rented successfully. Total cost: " + totalCost);
            } else {
                System.out.println("Vehicle is not available or does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error renting vehicle: " + e.getMessage());
        }
    }
    public void returnVehicle(int rentalId) {
        String selectRentalQuery = "SELECT * FROM rentals WHERE rental_id = " + rentalId;
        try {
            ResultSet rs = dbManager.executeQuery(selectRentalQuery);
            if (rs.next()) {
                int vehicleId = rs.getInt("vehicle_id");

                String updateVehicleQuery = "UPDATE vehicles SET available_count = available_count + 1 WHERE id = " + vehicleId;
                dbManager.executeUpdate(updateVehicleQuery);

                String deleteRentalQuery = "DELETE FROM rentals WHERE rental_id = " + rentalId;
                dbManager.executeUpdate(deleteRentalQuery);

                System.out.println("Vehicle returned successfully.");
            } else {
                System.out.println("Rental record not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error returning vehicle: " + e.getMessage());
        }
    }
    public void viewUsers() {
        String query = "SELECT * FROM users";
        try {
            ResultSet rs = dbManager.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("User [id=" + id + ", name=" + name + ", email=" + email + "]");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
    }
    public void viewRentals() {
        String query = "SELECT r.rental_id, u.name, v.model, r.days, r.total_cost " +
                       "FROM rentals r " +
                       "JOIN users u ON r.user_id = u.id " +
                       "JOIN vehicles v ON r.vehicle_id = v.id";
        try {
            ResultSet rs = dbManager.executeQuery(query);
            while (rs.next()) {
                int rentalId = rs.getInt("rental_id");
                String userName = rs.getString("name");
                String vehicleModel = rs.getString("model");
                int days = rs.getInt("days");
                double totalCost = rs.getDouble("total_cost");
                System.out.println("Rental [rentalId=" + rentalId + ", userName=" + userName + ", vehicleModel=" + vehicleModel + ", days=" + days + ", totalCost=" + totalCost + "]");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rentals: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        VehicleRentalSystem system = new VehicleRentalSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Vehicle Rental System");
            System.out.println("1. Add User");
            System.out.println("2. Add Vehicle");
            System.out.println("3. View Available Vehicles");
            System.out.println("4. Rent Vehicle");
            System.out.println("5. View Rentals");
            System.out.println("6. Return Vehicle");
            System.out.println("7. View Users");
            System.out.println("8. Exit");
            
            
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter user email: ");
                    String email = scanner.nextLine();
                    system.addUser(name, email);
                    break;
                    
                    
                case 2:
                    System.out.print("Enter vehicle model: ");
                    String model = scanner.nextLine();
                    
                    System.out.print("Enter vehicle type: ");
                    String type = scanner.nextLine();
                    
                    System.out.print("Enter rent per day: ");
                    double rentPerDay = scanner.nextDouble();
                    
                    System.out.print("Enter available count: ");
                    int availableCount = scanner.nextInt();
                    
                    system.addVehicle(model, type, rentPerDay, availableCount);
                    break;
                case 3:
                    system.viewAvailableVehicles();
                    break;
                case 4:
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    
                    System.out.print("Enter vehicle ID to rent: ");
                    int vehicleId = scanner.nextInt();
                    
                    System.out.print("Enter number of days: ");
                    int days = scanner.nextInt();
                    
                    system.rentVehicle(userId, vehicleId, days);
                    
                    break;
                case 5:
                    system.viewRentals();
                    break;
                case 6:
                    System.out.print("Enter rental ID to return: ");
                    int rentalId = scanner.nextInt();
                    system.returnVehicle(rentalId);
                    break;
                case 7:    
                    system.viewUsers();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
