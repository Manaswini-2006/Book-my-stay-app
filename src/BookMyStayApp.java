import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {
    private String fileName = "system_state.dat";

    public void save(SystemState state) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(state);
            out.close();
            System.out.println("System state saved.");
        } catch (Exception e) {
            System.out.println("Failed to save system state.");
        }
    }

    public SystemState load() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            SystemState state = (SystemState) in.readObject();
            in.close();
            System.out.println("System state restored.");
            return state;
        } catch (Exception e) {
            System.out.println("No valid saved state found. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        SystemState restored = persistence.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (restored != null) {
            inventory = restored.inventory;
            bookings = restored.bookings;
        } else {
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);

            bookings = new ArrayList<>();
            bookings.add(new Reservation("R301", "Alice", "Single Room"));
            bookings.add(new Reservation("R302", "Bob", "Double Room"));
        }

        System.out.println("Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        SystemState state = new SystemState(inventory, bookings);
        persistence.save(state);
    }
}