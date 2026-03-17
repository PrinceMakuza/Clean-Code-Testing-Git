package models;

/**
 * Abstract base class representing a bank customer.
 */
public abstract class Customer {
  private String customerId;
  private String name;
  private int age;
  private String contact;
  private String address;

  private static int customerCounter = 0;

  /**
   * Generates a unique customer ID.
   *
   * @return Formatted customer ID string.
   */
  private static String generateCustomerId() {
    customerCounter++;
    return String.format("CUS%03d", customerCounter);
  }

  /**
   * Constructs a new Customer.
   *
   * @param name The customer's name.
   * @param age The customer's age.
   * @param contact The customer's contact information.
   * @param address The customer's address.
   */
  public Customer(String name, int age, String contact, String address) {
    this.customerId = generateCustomerId();
    this.name = name;
    this.age = age;
    this.contact = contact;
    this.address = address;
  }

  /**
   * Displays the customer's details.
   */
  public abstract void displayCustomerDetails();

  /**
   * Returns the type of customer.
   *
   * @return Customer type as a string.
   */
  public abstract String getCustomerType();

  /**
   * Checks if the customer has waived fees.
   *
   * @return True if fees are waived, false otherwise.
   */
  public abstract boolean hasWaivedFees();

  /**
   * Gets the customer ID.
   *
   * @return The customer ID.
   */
  public String getCustomerId() {
    return customerId;
  }

  /**
   * Gets the customer's name.
   *
   * @return The name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the customer's age.
   *
   * @return The age.
   */
  public int getAge() {
    return age;
  }

  /**
   * Gets the customer's contact information.
   *
   * @return The contact info.
   */
  public String getContact() {
    return contact;
  }

  /**
   * Gets the customer's address.
   *
   * @return The address.
   */
  public String getAddress() {
    return address;
  }
}