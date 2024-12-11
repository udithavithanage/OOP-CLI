package TicketingSystem;

public class Vendor implements Runnable {
    public static Ticketpool pool;
    public static int ticketReleaseRate;
    private final String vendorID;


    public Vendor(String vendorID){
        this.vendorID = vendorID;
    }

    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
            pool.addTicket(this.vendorID);
            try {
                Thread.sleep(Vendor.ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

