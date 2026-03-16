# Bank Account Management System

A console-based banking application built with Java 21, following clean code principles, modular design, and robust error handling.

## Features
- **Account Management**: Create and view Savings and Checking accounts.
- **Transactions**: Perform deposits, withdrawals, and transfers between accounts.
- **Statement Generation**: Generate detailed transaction history for any account.
- **Error Handling**: Custom exceptions for invalid amounts, insufficient funds, and overdraft limits.
- **Unit Testing**: JUnit 5 test suite for core logic verification.

## Project Structure
```
bank-account-management-system
│
├── src
│   ├── Main.java                # Application Entry Point
│   ├── models/                  # Account and Transaction models
│   ├── exceptions/              # Custom Exceptions
│   ├── services/                # Business Logic Managers
│   ├── utils/                   # Validation Utilities
│   └── test/java/               # JUnit 5 Test Suite (AccountTest, etc.)
│
├── docs
│   └── git-workflow.md          # Git documentation
└── README.md
```

## How to Run
1. Ensure Java 21 is installed.
2. Compile the source: `javac -d out src/**/*.java`
3. Run the application: `java -cp out Main`

## Running Tests
JUnit 5 tests are located in `src/test/java`.
To run tests manually:
1. Include JUnit 5 library in your classpath.
2. Run the test classes using the JUnit console launcher.

## Design Principles
- **Clean Code**: Meaningful naming, modular methods (under 25 lines), and JavaDoc comments.
- **Modular Design**: Separated responsibilities between models, services, and UI.
- **Exception Handling**: Robust use of try-catch blocks and custom exceptions.
