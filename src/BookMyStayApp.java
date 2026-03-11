import java.util.*;

class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    public void addService(String reservationId, Service service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public double calculateTotalCost(String reservationId) {
        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (Service s : services) {
            total += s.getPrice();
        }
        return total;
    }

    public void displayServices(String reservationId) {
        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        System.out.println("Reservation: " + reservationId);
        for (Service s : services) {
            System.out.println("Service: " + s.getName() + " | Cost: " + s.getPrice());
        }
        System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES101";

        manager.addService(reservationId, new Service("Breakfast", 15.0));
        manager.addService(reservationId, new Service("Airport Pickup", 30.0));
        manager.addService(reservationId, new Service("Spa Access", 50.0));

        manager.displayServices(reservationId);
    }
}