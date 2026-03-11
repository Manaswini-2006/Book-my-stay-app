import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class BookingService {
    private InventoryService inventory;
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();
    private Set<String> allRoomIds = new HashSet<>();
    private int idCounter = 1;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation r) {
        String type = r.getRoomType();

        if (inventory.getAvailability(type) <= 0) {
            System.out.println("No availability for " + type + " for " + r.getGuestName());
            return;
        }

        String roomId = type.replaceAll(" ", "").substring(0,2).toUpperCase() + idCounter++;

        while (allRoomIds.contains(roomId)) {
            roomId = type.replaceAll(" ", "").substring(0,2).toUpperCase() + idCounter++;
        }

        allRoomIds.add(roomId);

        allocatedRooms.putIfAbsent(type, new HashSet<>());
        allocatedRooms.get(type).add(roomId);

        inventory.decrement(type);

        System.out.println("Reservation confirmed for " + r.getGuestName() + " | Room: " + roomId + " | Type: " + type);
    }
}

public class BookMyStayApp{
    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Single Room"));

        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }
    }
}