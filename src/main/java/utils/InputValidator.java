package utils;

import java.util.Scanner;

public class InputValidator {

  /**
   * Gets a string input from the user.
   *
   * @param scanner The scanner to read input from.
   * @param prompt The prompt to display to the user.
   * @return The trimmed string input.
   */
  public static String getStringInput(Scanner scanner, String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  /**
   * Gets a validated string input based on a regex pattern.
   *
   * @param scanner The scanner to read input from.
   * @param prompt The prompt to display to the user.
   * @param regex The regex pattern to validate against.
   * @param errorMessage The error message to display if validation fails.
   * @return The validated string input.
   */
  public static String getValidatedStringInput(
          Scanner scanner,  // reads user input
          String prompt,  // message shown to the user
          String regex,  // pattern the input must match
          String errorMessage)  // message shown if input is invalid
  {
    while (true) {
      String input = getStringInput(scanner, prompt);
      if (input.matches(regex)) {
        return input;
      }
      System.out.println(errorMessage);
    }
  }

  /**
   * Gets an integer input within a specified range.
   *
   * @param scanner The scanner to read input from.
   * @param prompt The prompt to display to the user.
   * @param min The minimum allowed value.
   * @param max The maximum allowed value.
   * @return The integer input, or -1.
   */
  public static int getIntInput(Scanner scanner, String prompt, int min, int max) {
    while (true) {
      try {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.equals("00")) {
          return -1; // -1 represents cancel
        }
        int value = Integer.parseInt(input);
        if (value == 0 || (value >= min && value <= max)) {
          return value;
        }
        System.out.printf("Please enter a number between %d and %d\n",
            min, max);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.\n");
      }
    }
  }

  /**
   * Gets a positive double input from the user.
   *
   * @param scanner The scanner to read input from.
   * @param prompt The prompt to display to the user.
   * @return The positive double input.
   */
  public static double getPositiveDoubleInput(Scanner scanner, String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.equals("00")) {
          return -1.0; // -1 represents cancel
        }
        double value = Double.parseDouble(input);
        if (value > 0) {
          return value;
        }
        System.out.println("Please enter a positive amount.\n");
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.\n");
      }
    }
  }

  /**
   * Gets a Y/N input from the user.
   *
   * @param scanner The scanner to read input from.
   * @param prompt The prompt to display to the user.
   * @return True if Y, false if N.
   */
  public static boolean getYesNoInput(Scanner scanner, String prompt) {
    while (true) {
      System.out.print(prompt + " (Y/N): ");
      String input = scanner.nextLine().trim().toUpperCase();

      if (input.equals("Y")) {
        return true;
      }
      if (input.equals("N")) {
        return false;
      }
      System.out.println("Please enter Y or N.\n");
    }
  }
}
