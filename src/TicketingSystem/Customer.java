package TicketingSystem;

public class Customer implements  Runnable{
    public static Ticketpool pool;
    public static int customerRetrievalRate;
    private final String customerID;

    public Customer(String customerID){
        this.customerID = customerID;
    }

    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
            pool.removeTicket(this.customerID);  // Calls the Ticketpool's removeTicket method to retrieve a ticket
            try {
                Thread.sleep(Customer.customerRetrievalRate * 1000); // Pauses execution for the retrieval rate (converted to milliseconds)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

