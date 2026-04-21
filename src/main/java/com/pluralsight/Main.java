package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Prompt user for input and output file names
        System.out.print("Enter the name of the employee file to process: ");
        String inputFile = scanner.nextLine().trim();

        System.out.print("Enter the name of the payroll file to create: ");
        String outputFile = scanner.nextLine().trim();

        scanner.close();

        // Read employees from the input file
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + inputFile))) {

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

                int employeeId     = Integer.parseInt(tokens[0].trim());
                String name        = tokens[1].trim();
                double hoursWorked = Double.parseDouble(tokens[2].trim());
                double payRate     = Double.parseDouble(tokens[3].trim());

                employees.add(new Employee(employeeId, name, hoursWorked, payRate));
            }

        } catch (IOException e) {
            System.out.println("Error: Could not find or read \"" + inputFile + "\"");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Error: The file contains invalid data. Please check the format.");
            return;
        }

        // Write output as CSV
        String outputPath = "src/main/resources/" + outputFile;

        try (FileWriter writer = new FileWriter(outputPath)) {

            writer.write("id|name|gross pay\n");

            for (Employee emp : employees) {
                writer.write(String.format("%d|%s|%.2f%n",
                        emp.getEmployeeId(),
                        emp.getName(),
                        emp.getGrossPay()));
            }

            System.out.println("Payroll file created: " + outputFile);

        } catch (IOException e) {
            System.out.println("Error: Could not create output file \"" + outputFile + "\"");
        }
    }
}