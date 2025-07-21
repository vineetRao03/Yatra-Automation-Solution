# ✈️ Yatra Fare Comparison Automation

This project automates the process of comparing the lowest flight prices for two consecutive months on [Yatra.com](https://www.yatra.com), using Java and Selenium WebDriver.

## 🚀 Features

- Opens the calendar widget and selects the current and next month blocks
- Extracts lowest available fare (₹) from each month
- Compares both prices and prints the cheaper option
- Handles UI popups dynamically
- Uses modular methods for maintainability

## 🛠 Tech Stack

- Java  
- Selenium WebDriver   
- WebDriverWait

## 📚 Learning Highlights

- DOM traversal using relative XPath
- WebElement chaining for nested structures
- Exception handling (for popup detection)
- Text extraction and data parsing (`₹`, `aria-label`, etc.)
