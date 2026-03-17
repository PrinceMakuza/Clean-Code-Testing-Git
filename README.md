# Bank Account Management System

A console-based banking application built with Java 21, following clean code principles, modular design, and robust error handling.

## Features
- **Account Management**: Create and view Savings and Checking accounts.
- **Transactions**: Perform deposits, withdrawals, and transfers between accounts.
- **Statement Generation**: Generate detailed transaction history for any account.
- **Error Handling**: Custom exceptions for invalid amounts, insufficient funds, and overdraft limits.
- **Unit Testing**: JUnit 5 test suite for core logic verification.

## Project Structure
```text
bank-account-management-system
│
├── src/main/java
│   ├── Main.java                # Application Entry Point
│   ├── models/                  # Account and Transaction models
│   ├── exceptions/              # Custom Exceptions
│   ├── services/                # Business Logic Managers
│   └── utils/                   # Validation Utilities
│
├── src/test/java                # JUnit 5 Test Suite (AccountTest, etc.)
├── docs/                        # Documentation
├── pom.xml                      # Maven Configuration
└── README.md
```

## How to Run
1. Ensure Java 21 is installed.
2. Compile and run using Maven:
   ```bash
   mvn clean compile exec:java -Dexec.mainClass="Main"
   ```
   Or manually:
   ```bash
   javac -d out -cp "lib/*" src/main/java/**/*.java
   java -cp out Main
   ```

## Git Commands
To manage the project history and store test results, use the following commands:
```bash
# Initialize git (if not already done)
git init

# Track all project files
git add .

# Commit changes with test summary
git commit -m "feat: implement validations and refactor according to Google Java Style"

# To push to a remote repository
# git remote add origin <your-repo-url>
# git push -u origin main
```

## Design Principles
- **Style Guide**: Adheres to Google Java Style with 2-space indentation.
- **Documentation**: Comprehensive Javadoc for all public methods.
- **Clean Code**: Meaningful naming, modular methods, and robust error handling.
- **Modular Design**: Separated responsibilities between models, services, and UI.
- **Exception Handling**: Robust use of try-catch blocks and custom exceptions.

