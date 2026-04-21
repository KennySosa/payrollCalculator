package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String fileName = "src/main/resources/Employees.csv";

        System.out.printf("%-5s %-20s %10s%n", "ID", "Name", "Gross Pay");
        System.out.println("-".repeat(38));

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {

                // Skip the header row
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                // Split each line by the | delimiter
                String[] tokens = line.split("\\|");

                // Parse each field into the correct data type
                int employeeId     = Integer.parseInt(tokens[0].trim());
                String name        = tokens[1].trim();
                double hoursWorked = Double.parseDouble(tokens[2].trim());
                double payRate     = Double.parseDouble(tokens[3].trim());

                // Create a new Employee object
                Employee emp = new Employee(employeeId, name, hoursWorked, payRate);

                // Display the employee's payroll info
                System.out.printf("%-5d %-20s $%9.2f%n",
                        emp.getEmployeeId(),
                        emp.getName(),
                        emp.getGrossPay());
            }

        } catch (IOException e) {
            System.out.println("Error: Could not find or read \"" + fileName + "\"");
            System.out.println("Make sure the file is in your project root folder.");
        } catch (NumberFormatException e) {
            System.out.println("Error: The file contains invalid data. Please check the format.");
        }
    }
}