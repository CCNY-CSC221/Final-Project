# CSC211 Final Project / Personal Finance Manager (PFM)

## Overview
This project is a **Java console application** called **Personal Finance Manager (PFM)**. The program helps users manage their personal finances by storing and analyzing their income and expenses for different years. It is made for both **Windows** and **Mac** computers. The system is also **multi-user**, which means different users can create their own accounts and keep their own financial data separate.

## What the Program Does
The PFM program allows a user to:
- create an account
- log in with username and password
- reset or change a password
- upload a CSV file of income and expenses
- validate and preview the CSV file before saving it
- store budget data between sessions
- load data for multiple years
- overwrite or delete a year of data
- analyze a selected year of financial data
- print reports to the console or to a CSV file
- detect duplicates and unusual data entries
- generate financial insights from yearly spending patterns

All interaction is done through the **console terminal**. No GUI is required.

## CSV File Format
Before using the program, the user must prepare a CSV file with this header:

```csv
Date,Category,Amount
```

Rules for the CSV file:
- `Date` must use the format `MM/DD/YYYY`
- `Category` must be one of the allowed income or expense categories
- `Amount` must be a whole dollar value with **no cents**
- income values are **positive**
- expense values are **negative**
- all records in one file must be from the **same calendar year**

The order of the rows does not matter.

## Income Categories
- Compensation
- Allowance
- Investments
- Other

## Expense Categories
- Home
- Utilities
- Food
- Appearance
- Work
- Education
- Transportation
- Entertainment
- Professional Services
- Other

One-time capital expenses should **not** be included in the budget.

## Main Features

### 1. Account Management
Users can:
- create an account
- log in
- change password
- recover password by answering a secret question
- delete their account

User data must be stored between sessions, and passwords must be saved in an **obfuscated** way.

### 2. Data Storage
Users can upload yearly finance CSV files. The program checks the file first, shows problems if there are any, and then lets the user decide whether to continue or cancel. If a year already exists, the user can overwrite it or keep the old one. Stored data stays available between sessions.

### 3. Validation
The program checks that user input and uploaded data are correct and well-formed. It should be able to catch invalid records and let the user choose whether to ignore bad records or cancel and fix the file later.

### 4. Reports
The program can summarize a year of data and show:
- monthly income minus expenses
- yearly totals by category
- overall budget performance

Reports can be shown on screen or exported to a CSV file.

### 5. Data Audit
The system can check for:
- duplicate entries
- anomalous or unusual data

For example, if compensation is usually `$10,000` per month, then a `$100,000` entry should be flagged as unusual. Users may also exclude some categories from anomaly checking.

### 6. Insights
The program can also provide insights such as:
- category expense percentages
- how much more could be spent in some categories if the year ends in surplus
- how much less should be spent in some categories if the year ends in deficit

These insights help the user better understand their spending habits.

## Team Modules
The full project is divided into 8 teams/modules:
- **Accounts**: user account CRUD and authentication
- **Data Audit**: find duplicate and anomalous records
- **Insights**: analyze patterns in income and spending
- **Integration**: main menu, JAR assembly, team coordination
- **Reports**: output reports to console or file
- **Storage**: file handling and budget object CRUD
- **Test**: testing, bug tracking, test data, Javadocs
- **Validation**: validate user input and data correctness

Each team is responsible for its own module contract, delivery, and standup updates.

## Code Style Requirements
The project follows these naming and documentation rules:
- use `camelCase` for variables and methods
- use `CapitalCase` for classes, interfaces, enums, and records
- use `SCREAMING_SNAKE_CASE` for constants
- all classes, constructors, interfaces, and methods should be Javadoc-ready
- bug fixes should be documented with `@bug`

The code should be correct, robust, efficient, and user-friendly.

## How to Run
1. Make sure Java is installed.
2. Compile the Java files.
3. Run the main class from the console.
4. Create an account or log in.
5. Upload a yearly CSV finance file.
6. Validate, store, and analyze the data.
7. Generate reports or insights.

## Example CSV
```csv
Date,Category,Amount
01/05/2025,Compensation,3000
01/07/2025,Food,-120
01/10/2025,Home,-1000
01/15/2025,Utilities,-150
01/20/2025,Investments,200
```

## Notes
- This is a **console-based** project.
- The software should work on both **Windows** and **Mac**.
- Data should be stored between sessions.
- Each uploaded CSV file should only contain data from **one year**.

## Project Goal
The main goal of this project is to help users organize their yearly financial data, check for mistakes, and better understand their spending and income using reports and insights.
