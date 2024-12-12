package TicketingSystem;

import java.util.Queue;

public class Ticketpool {
    public static Queue<String> tickets;
    public static int maxcapacity;
    public static int totalTicketCount;
    public static int addTicketCount;

    public Ticketpool(int maxcapacity, int totalTicketCount, int addTicketCount, Queue<String> tickets){
        Ticketpool.maxcapacity = maxcapacity;
        Ticketpool.totalTicketCount = totalTicketCount;
        Ticketpool.addTicketCount = addTicketCount;
        Ticketpool.tickets = tickets;
    }

    public synchronized void addTicket(String vendorID){
        while (tickets.size() >= maxcapacity || addTicketCount >= totalTicketCount ){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String ticketID = vendorID + "(" + (addTicketCount+1) + ")";
        tickets.add(ticketID);
        addTicketCount++;
        System.out.println("\t\u001B[34m" + "Ticket Added: " + ticketID + " | Remaining Ticket Count: " + tickets.size() + "\u001B[0m");

        notifyAll();
    }

    public synchronized void removeTicket(String customerID){
        while (tickets.isEmpty()){
            try {
                wait(); // Wait if the pool is full or the maximum ticket count is reached
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupt status
            }
        }
        String ticketId = tickets.poll();
        if(totalTicketCount==addTicketCount && tickets.isEmpty()){
            // Check if all tickets have been added to the ticket pool and the ticket list is empty
            System.out.println("\n\t\u001B[36m" + "Tickets Sold Out" + "\u001B[0m");
            System.exit(0); // Terminate the program as there are no more tickets available
        }
        System.out.println("\tCustomer ID: " + customerID + " Retrieved Ticket ID: " + ticketId + " | Remaining Ticket Count: " + tickets.size());
        notifyAll(); // Notify all waiting threads
    }
}

