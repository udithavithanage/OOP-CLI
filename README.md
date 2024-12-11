# Real-Time Ticketing System

A Java-based multithreaded system to manage a ticket pool where vendors add tickets and customers consume them at specified rates. This project demonstrates concurrency control using synchronized methods and dynamic system configurations.

---

## Features

- **Vendor and Customer Threads**:
  - Vendors add tickets to the pool at a specified rate.
  - Customers consume tickets at a specified rate.
- **Dynamic Configurations**:

  - Configure ticket pool size, vendor/customer rates, and other parameters at runtime.
  - Save and load the last five configurations.

- **Command-Based Control**:

  - Start, stop, reset, and exit the system via commands.

- **Thread-Safe Implementation**:
  - Uses synchronized methods to ensure safe access to shared resources.

---

## Prerequisites

- **Java**: JDK 8 or higher.
- **IDE**: Any IDE or text editor with Java support (e.g., IntelliJ IDEA, Eclipse, or VS Code).

---

## How to Run

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/udithavithanage/OOP-CLI.git
   cd OOP_CLI/src
   ```

2. **Compile the Code**:

   ```bash
   javac Config.java
   ```

3. **Run the Program**:

   ```bash
   java Config
   ```

4. **Follow On-Screen Prompts**:
   - Choose to load a saved configuration or create a new one.
   - Use commands to start, stop, reset, or exit the system.

---

## Configuration

During setup, you will be prompted to provide the following details:

- **Total Ticket Count**: Total number of tickets in the system.
- **Maximum Capacity**: Maximum tickets in the pool at any given time.
- **Customer Rate**: Tickets consumed by each customer per second.
- **Ticket Release Rate**: Tickets added by each vendor per second.
- **Number of Vendors**: Total number of vendor threads.
- **Number of Customers**: Total number of customer threads.

Saved configurations will be stored and can be reloaded in future sessions.

---

## Commands

- **Start**: Start the vendor and customer threads.
- **Stop**: Stop all threads.
- **Reset**: Reset the system to initial configurations.
- **Exit**: Terminate the program.

---

## Implementation Details

1. **Thread Synchronization**:

   - `synchronized` methods ensure that multiple threads do not conflict while adding or removing tickets.

2. **Graceful Shutdown**:

   - Threads check a `running` flag to terminate cleanly when stopped.

3. **Configuration Persistence**:
   - The last five configurations are saved in a text file and can be reloaded.

---

## Example Output

```text
Previous configurations:
	1: Total Tickets: 200    Max Capacity: 20    Customer Rate: 4    Release Rate: 2   Number of Vendors: 4   Number of Customers: 10
	2: Total Tickets: 300    Max Capacity: 13    Customer Rate: 2    Release Rate: 3   Number of Vendors: 4   Number of Customers: 13

Do you want to use a previous configuration? (yes/no): yes
Enter the number of the configuration to use: 2

	2 - Configuration loaded.

		Total ticket count:300
		Max capacity:13
		Customer rate:2
		Ticket release rate:3
		Number of vendors:4
		Number of customers:13

Enter command: start
```

---
