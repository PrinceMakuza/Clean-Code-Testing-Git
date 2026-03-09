package models;

public abstract class Customer {
    // Private fields - encapsulation
    private String customerId;
    private String name;
    private int age;
    private String contact;
    private String address;

    // Static field for ID generation
    private static int customerCounter = 0;

    // Constructor
    public Customer(String name, int age, String contact, String address) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.address = address;
    }

    // Static method to generate unique ID
    private static String generateCustomerId() {
        customerCounter++;
        return String.format("CUS%03d", customerCounter);
    }

    // Abstract methods
    public abstract void displayCustomerDetails();
    public abstract String getCustomerType();
    public abstract boolean hasWaivedFees();

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }

    // Reset counter for testing
    public static void resetCounter() {
        customerCounter = 0;
    }
}