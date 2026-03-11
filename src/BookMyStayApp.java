import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class CancellationService {
    private Map<String, Reservation> reservations = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();
    private InventoryService inventory;

    public CancellationService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public void cancelReservation(String reservationId) {
        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation failed: reservation not found");
            return;
        }

        Reservation r = reservations.get(reservationId);

        if (!r.isActive()) {
            System.out.println("Cancellation failed: reservation already cancelled");
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.increment(r.getRoomType());
        r.cancel();

        System.out.println("Reservation cancelled: " + reservationId + " Room released: " + rollbackStack.pop());
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        CancellationService service = new CancellationService(inventory);

        Reservation r1 = new Reservation("R201", "Single Room", "SR1");
        Reservation r2 = new Reservation("R202", "Double Room", "DR1");

        service.addReservation(r1);
        service.addReservation(r2);

        service.cancelReservation("R201");
        service.cancelReservation("R201");
        service.cancelReservation("R999");

        inventory.displayInventory();
    }
}