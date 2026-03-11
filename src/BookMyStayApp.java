

import java.util.HashMap;
import java.util.Map;

class Room {

    private String roomType;
    private double price;
    private String amenities;

    public Room(String roomType, double price, String amenities) {
        this.roomType = roomType;
        this.price = price;
        this.amenities = amenities;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + price);
        System.out.println("Amenities: " + amenities);
    }
}
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}
class RoomSearchService {

    private RoomInventory inventory;
    private Map<String, Room> roomCatalog;

    public RoomSearchService(RoomInventory inventory) {

        this.inventory = inventory;

        roomCatalog = new HashMap<>();

        roomCatalog.put("Single Room",
                new Room("Single Room", 80.0, "1 Bed, Free WiFi"));

        roomCatalog.put("Double Room",
                new Room("Double Room", 120.0, "2 Beds, Free WiFi, TV"));

        roomCatalog.put("Suite Room",
                new Room("Suite Room", 250.0, "3 Beds, Living Area, Premium View"));
    }
    public void searchAvailableRooms() {

        System.out.println("Available Rooms:\n");

        for (String roomType : inventory.getAllAvailability().keySet()) {

            int availability = inventory.getAvailability(roomType);
            if (availability > 0) {

                Room room = roomCatalog.get(roomType);

                room.displayRoomDetails();
                System.out.println("Available Rooms: " + availability);
                System.out.println("-----------------------------");
            }
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.searchAvailableRooms();
    }
}