package models;

public abstract class Customer {
    private String customerId;
    private String name;
    private int age;
    private String contact;
    private String address;

    private static int customerCounter = 0;

    private static String generateCustomerId() {
        customerCounter++;
        return String.format("CUS%03d", customerCounter);
    }

    public Customer(String name, int age, String contact, String address) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.address = address;
    }

    public abstract void displayCustomerDetails();
    public abstract String getCustomerType();
    public abstract boolean hasWaivedFees();

    public String getCustomerId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getContact() {
        return contact;
    }
    public String getAddress() {
        return address;
    }
}