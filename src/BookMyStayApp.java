import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No availability for " + roomType);
        }
    }

    public void allocateRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class BookingService {
    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processBooking(String guestName, String roomType) {
        try {
            inventory.validateRoomType(roomType);
            inventory.checkAvailability(roomType);
            inventory.allocateRoom(roomType);
            System.out.println("Booking confirmed for " + guestName + " | Room Type: " + roomType);
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        bookingService.processBooking("Alice", "Single Room");
        bookingService.processBooking("Bob", "Suite Room");
        bookingService.processBooking("Charlie", "Luxury Room");
    }
}