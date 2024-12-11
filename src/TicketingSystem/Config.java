package TicketingSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.LinkedList;

public class Config {
    private static int total_ticket_count;
    private static int maxCapacity;
    private static int customerRate;
    private static int ticketReleaseRate;
    private static int numberOfVendors;
    private static int numberOfCustomers;
    private static final String CONFIG_FILE = "config.txt";

    public static void main2(){
        Scanner input = new Scanner(System.in);

        List<String> configurations = loadConfigurations();

        if (!configurations.isEmpty()) {
            System.out.println("\nPrevious configurations:");
            displayConfigurations(configurations);

            while (true){
                System.out.print("\nDo you want to use a previous configuration? (yes/no): ");
                String choice = input.nextLine().trim().toLowerCase();
                if (choice.equals("yes")) {
                    int selectedIndex = selectConfiguration(input, configurations);
                    applyConfiguration(configurations.get(selectedIndex), selectedIndex);
                    break;
                } else if (choice.equals("no")){
                    requestConfiguration(input, configurations);
                    break;
                } else{
                    System.out.println("\u001B[31m"+"\n\tInvalid Input. Type 'yes' or 'no'."+"\u001B[0m");
                }
            }

        } else {
            System.out.println("\nNo previous configurations found.");
            requestConfiguration(input, configurations);
        }

        Ticketpool pool = new Ticketpool(maxCapacity, total_ticket_count, 0 ,new LinkedList<>());
        Customer.pool = pool;
        Customer.customerRetrievalRate = customerRate;
        Vendor.pool = pool;
        Vendor.ticketReleaseRate = ticketReleaseRate;
    }

    public static void main(String[] args) {
        boolean flag = false;
        Scanner input = new Scanner(System.in);
        main2();
        List<Thread> threads = new ArrayList<>();
        System.out.print("\nEnter command: ");
        while (true) {

            String command = input.nextLine().trim().toLowerCase();

            if ("start".equals(command)) {
                if(!flag){
                    flag = true;
                    for (int i = 0; i < numberOfVendors; i++) {
                        threads.add(new Thread(new Vendor("Vendor" + (i+1))));
                    };
                    for (int i = 0; i < numberOfCustomers; i++) {
                        threads.add(new Thread(new Customer("Customer" + (i+1))));
                    };
                    System.out.println("\u001B[32m"+"\n\t***** Ticket system started. *****\n"+"\u001B[0m");
                    for (Thread thread : threads) {
                        thread.start();
                    }
                }else{
                    System.out.println("\u001B[31m"+"\n\t***** System already started. *****"+"\u001B[0m");
                }
            } else if ("stop".equals(command)) {
                for (Thread thread : threads) {
                    thread.interrupt();
                }
                System.out.println("\u001B[32m"+"\n\t***** Stopping ticket system... *****"+"\u001B[0m");
                threads.clear();
                Ticketpool newpool = new Ticketpool(Ticketpool.maxcapacity, Ticketpool.totalTicketCount, Ticketpool.addTicketCount, Ticketpool.tickets);
                Customer.pool = newpool;
                Vendor.pool = newpool;
                flag = false;
                System.out.print("\nEnter command: ");
            } else if ("reset".equals(command)) {
                for (Thread thread : threads) {
                    thread.interrupt();
                }
                System.out.println("\u001B[32m"+"\n\t***** Reset ticket system... *****"+"\u001B[0m");
                threads.clear();
                main2();
                flag = false;
                System.out.print("\nEnter command: ");
            } else if ("exit".equals(command)) {
                threads.forEach(Thread::interrupt);
                System.out.println("\u001B[32m"+"\n\t***** Exit ticket system... *****"+"\u001B[0m");
                threads.clear();
                break;
            } else {
                System.out.println("\u001B[31m"+"\n\tInvalid command. Type 'start' , 'stop' , 'reset' or 'exit'."+"\u001B[0m");
                System.out.print("\nEnter command: ");
            }
        }
    }

    private static void displayConfigurations(List<String> configurations) {
        for (int i = 0; i < configurations.size(); i++) {
            String[] values = configurations.get(i).split(",");
            System.out.printf("\t\u001B[32m" + "%d: Total Tickets: %s    Max Capacity: %s    Customer Rate: %s    Release Rate: %s   Number of Vendors: %s   Number of Customers: %s%n" + "\u001B[0m",
                    i + 1, values[0], values[1], values[2], values[3], values[4], values[5]);
        }
    }

    private static int selectConfiguration(Scanner input, List<String> configurations) {
        int selectedIndex = -1;
        while (true) {
            try {
                System.out.print("Enter the number of the configuration to use: ");
                selectedIndex = input.nextInt() - 1;
                if (selectedIndex < 0 || selectedIndex >= configurations.size()) {
                    throw new IndexOutOfBoundsException("Invalid configuration number.");
                }
                break;
            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                System.out.println("\tPlease enter a valid number corresponding to a configuration.");
                input.nextLine(); // Clear invalid input
            }
        }
        return selectedIndex;
    }

    private static void applyConfiguration(String config, int selectedIndex) {
        System.out.println("\n\t\u001B[33m" + (selectedIndex+1) + " - Configuration loaded." + "\u001B[0m");
        String[] values = config.split(",");
        total_ticket_count = Integer.parseInt(values[0]);
        System.out.println("\n\t\t\u001B[33m" + "Total ticket count:"  + total_ticket_count  + "\u001B[0m");
        maxCapacity = Integer.parseInt(values[1]);
        System.out.println("\t\t\u001B[33m" + "Max capacity:"  + maxCapacity  + "\u001B[0m");
        customerRate = Integer.parseInt(values[2]);
        System.out.println("\t\t\u001B[33m" + "Customer rate:"  + customerRate  + "\u001B[0m");
        ticketReleaseRate = Integer.parseInt(values[3]);
        System.out.println("\t\t\u001B[33m" + "Ticket release rate:"  + ticketReleaseRate  + "\u001B[0m");
        numberOfVendors = Integer.parseInt(values[4]);
        System.out.println("\t\t\u001B[33m" + "Number of vendors:"  + numberOfVendors  + "\u001B[0m");
        numberOfCustomers = Integer.parseInt(values[5]);
        System.out.println("\t\t\u001B[33m" + "Number of customers:"  + numberOfCustomers  + "\u001B[0m");
    }

    private static void requestConfiguration(Scanner input, List<String> configurations) {
        int ch = 0;
        while (true) {
            try {
                switch (ch){
                    case 0:
                        System.out.print("\tEnter Total ticket count: ");
                        total_ticket_count = input.nextInt();
                        validateInput(total_ticket_count, "Total ticket count");
                        ch+=1;
                    case 1:
                        System.out.print("\tEnter Max Capacity: ");
                        maxCapacity = input.nextInt();
                        validateInput(maxCapacity, "Max Capacity");
                        ch+=1;
                    case 2:
                        System.out.print("\tEnter Customer Rate: ");
                        customerRate = input.nextInt();
                        validateInput(customerRate, "Customer Rate");
                        ch+=1;
                    case 3:
                        System.out.print("\tEnter Ticket Release Rate: ");
                        ticketReleaseRate = input.nextInt();
                        validateInput(ticketReleaseRate, "Ticket Release Rate");
                        ch+=1;
                    case 4:
                        System.out.print("\tEnter Number of Vendors: ");
                        numberOfVendors = input.nextInt();
                        validateInput(numberOfVendors, "Number of Vendors");
                        ch+=1;
                    case 5:
                        System.out.print("\tEnter Number of Customers: ");
                        numberOfCustomers = input.nextInt();
                        validateInput(numberOfVendors, "Number of Customers");
                        ch+=1;
                }
                saveConfiguration(configurations);
                break;
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m"+"\tInvalid Input. Please enter valid input."+"\u001B[0m");
                input.nextLine(); // Clear invalid input
            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m" + "\t" + e.getMessage()+"\u001B[0m");
            }
        }
    }

    private static void validateInput(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }

    private static void saveConfiguration(List<String> configurations) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE))) {
            configurations.add(total_ticket_count + "," + maxCapacity + "," + customerRate + "," + ticketReleaseRate + "," + numberOfVendors + "," + numberOfCustomers);

            // Keep only the last five configurations
            if (configurations.size() > 5) {
                configurations = configurations.subList(configurations.size() - 5, configurations.size());
            }

            // Write configurations to the file
            for (String config : configurations) {
                writer.println(config);
            }
            System.out.println("Configuration saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    private static List<String> loadConfigurations() {
        List<String> configurations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                configurations.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading configurations: " + e.getMessage());
        }
        return configurations;
    }
}

