# Banking Management System Using Java

## 1. Project overview
The Banking Management System is a menu-driven Java console application that models everyday banking operations. It supports customer registration, savings/current account creation, deposits, withdrawals, account-to-account transfers, balance enquiries and transaction history.

## 2. Problem statement
Banks need to maintain customer and account information accurately while processing financial operations safely. The application demonstrates how core Java and object-oriented programming can be used to model these operations and handle common failures such as duplicate account numbers, invalid amounts, unknown accounts and insufficient funds.

## 3. Objectives
- Build a menu-based Java application for common banking tasks.
- Model customers, generic bank accounts, savings accounts and current accounts.
- Demonstrate OOP principles and appropriate use of interfaces and abstract classes.
- Store and retrieve records using Java Collections.
- Handle invalid operations through meaningful exceptions.
- Demonstrate concurrent transaction activity and synchronized balance updates.
- Maintain transaction records and provide sample execution evidence.

## 4. Functional scope
| Feature | Description |
|---|---|
| Customer registration | Registers a customer with a unique customer ID |
| Account creation | Creates Savings or Current accounts |
| Deposit | Adds a positive amount to an account |
| Withdrawal | Deducts an amount subject to account rules and available funds |
| Transfer | Moves funds between two distinct accounts |
| Balance enquiry | Displays the current account balance |
| Account listing | Lists available accounts |
| Transaction history | Displays recorded deposits, withdrawals and transfers |
| Concurrent deposit demo | Runs multiple deposit threads against one account |

## 5. Design and Java concepts
### Abstraction
`BankAccount` is an abstract class. It contains shared account state and common deposit/withdrawal behavior while requiring subclasses to provide account type and minimum-balance information.

### Inheritance
`SavingsAccount` and `CurrentAccount` extend `BankAccount`, reusing common account behavior and adding account-specific fields.

### Interface
`BankingOperations` defines the service contract. `BankingService` implements it, separating the application menu from banking operations.

### Polymorphism and overriding
The service stores accounts using the `BankAccount` parent type. The concrete account implementation supplies behavior through overridden methods such as `getAccountType()` and `withdraw()`.

### Constructors and overloading
Constructors initialize customers and accounts. `SavingsAccount` provides both a constructor with an explicit interest rate and an overloaded convenience constructor using a default rate.

### Collections
- `ConcurrentHashMap` stores customers and accounts using unique IDs/account numbers.
- `CopyOnWriteArrayList` stores transaction history safely for concurrent reads and writes.
- `List.copyOf()` returns read-only snapshots to callers.

### Exception handling
Custom exceptions derive from `BankingException`: `InsufficientFundsException`, `InvalidAmountException`, `AccountNotFoundException` and `DuplicateAccountException`. The menu catches expected input and banking errors and displays a readable message.

### Multithreading and synchronization
The concurrent demo starts five threads that each deposit ₹100 into the same savings account. Account balance changes are synchronized. Transfers are coordinated using a shared lock so the debit and credit occur as one coordinated operation within this in-memory demo. This is a teaching example, not a production-grade distributed banking ledger.

### Object class methods
`Customer` and `BankAccount` override `toString()`, `equals()` and `hashCode()`. Equality is based on the stable customer ID or account number, not mutable balances or names.

## 6. Class/design diagram
See `class-diagram.png` for the rendered UML-style diagram and `class-diagram.puml` for editable PlantUML source.

## 7. Project structure
```text
Banking-Management-System/
├── pom.xml
├── README.md
├── src/main/java/com/bankingsystem/
│   ├── BankingApplication.java
│   ├── model/
│   │   ├── Customer.java
│   │   ├── BankAccount.java
│   │   ├── SavingsAccount.java
│   │   ├── CurrentAccount.java
│   │   └── Transaction.java
│   ├── service/
│   │   ├── BankingOperations.java
│   │   └── BankingService.java
│   └── exception/
│       ├── BankingException.java
│       ├── InsufficientFundsException.java
│       ├── InvalidAmountException.java
│       ├── AccountNotFoundException.java
│       └── DuplicateAccountException.java
├── docs/
│   ├── project-report.md
│   ├── class-diagram.png
│   └── class-diagram.puml
└── sample-output/
    └── sample-transactions.txt
```

## 8. Build and run
Requirements: JDK 17 or later and Maven 3.8+.

```bash
mvn clean compile
mvn exec:java
```

The application starts with sample accounts `1001001` (Savings) and `2002001` (Current). Use menu option `0` to exit.

## 9. Validation and test scenarios
| Scenario | Expected behavior |
|---|---|
| Deposit a positive amount | Balance increases and transaction is recorded |
| Withdraw within available balance | Balance decreases |
| Withdraw more than available funds | Insufficient-funds message |
| Deposit/withdraw zero or negative amount | Invalid-amount message |
| Use unknown account number | Account-not-found message |
| Create an existing account number | Duplicate-account message |
| Transfer to the same account | Transfer rejected |
| Run concurrent deposit demo | Five deposits complete; total increases by ₹500 |

## 10. Sample transaction outputs
The exact transaction IDs and timestamps are generated at runtime. A representative sequence is provided in `sample-output/sample-transactions.txt`.

## 11. Multithreading demonstration
Choose menu option `8`. Five worker threads each deposit ₹100 into savings account `1001001`. Since each balance update is synchronized, the expected total increase is ₹500. Starting from ₹5,000, the expected ending balance after one demo run is ₹5,500, provided no other transactions are performed in between.

## 12. Deliverables checklist
- [x] Java source code
- [x] Class/design diagram (PNG and editable PlantUML)
- [x] Explanation of Java concepts used
- [x] Sample transaction output
- [x] Multithreading demonstration
- [x] Menu-based team demonstration flow
- [x] README and build/run instructions

## 13. Team demonstration plan
1. Explain the banking problem and class design.
2. Register a customer and open an account.
3. Demonstrate deposit, withdrawal and balance enquiry.
4. Demonstrate a successful transfer and transaction history.
5. Trigger an invalid amount, unknown account or insufficient-funds case.
6. Run the concurrent deposit demonstration and explain synchronization.
7. Walk through the UML diagram and Java concepts.

## 14. Repository details
**GitHub repository:**  
`[Add repository URL here]`

**Repository owner/team:** `[Add team or owner]`  
**Branch used for submission:** `[Add branch name]`

Suggested Git commands:
```bash
git init
git add .
git commit -m "Initial Banking Management System implementation"
git branch -M main
git remote add origin <YOUR_REPOSITORY_URL>
git push -u origin main
```

## 15. Limitations and future enhancements
This is an in-memory educational console application. Data is reset when the program exits. A production implementation would require persistent database storage, authentication and authorization, audit-grade transaction handling, currency/rounding policies, account-specific minimum-balance enforcement, stronger transfer rollback guarantees, logging, and automated unit/integration tests.
