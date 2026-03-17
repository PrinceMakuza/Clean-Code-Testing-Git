package models;

/**
 * Represents a premium customer with priority services and fee waivers.
 */
public class PremiumCustomer extends Customer {
  private static final double MINIMUM_BALANCE = 10000.0;

  /**
   * Constructs a new PremiumCustomer.
   *
   * @param name The customer's name.
   * @param age The customer's age.
   * @param contact The customer's contact information.
   * @param address The customer's address.
   */
  public PremiumCustomer(String name, int age, String contact, String address) {
    super(name, age, contact, address);
  }

  @Override
  public void displayCustomerDetails() {
    System.out.println("Customer ID: " + getCustomerId());
    System.out.println("Name: " + getName());
    System.out.println("Type: Premium");
    System.out.println("Age: " + getAge());
    System.out.println("Contact: " + getContact());
    System.out.println("Address: " + getAddress());
    System.out.println("Minimum Balance: $" + MINIMUM_BALANCE);
    System.out.println("Benefits: No monthly fees, priority service");
  }

  @Override
  public String getCustomerType() {
    return "Premium";
  }

  @Override
  public boolean hasWaivedFees() {
    return true;
  }
}