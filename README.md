# REST Assured API Automation — Daily Finance

---

## 📄 Project Overview

This project performs **API automation testing using REST Assured** on the **Daily Finance** web application.  
The objective is to inspect, document, and automate key backend features using a structured testing framework.  
All APIs were first analyzed through **Postman**, then automated in **Java** using **REST Assured**, **TestNG**, and **Allure Reports**, following a **Page Object Model (POM)** architecture.

---

## Base URL: https://dailyfinance.roadtocareer.net/

---

## Implemented Tasks

The following functionalities were identified from the browser **Network tab** and automated:

1. **Register a new user**  
2. **Login by admin** (`email: admin@test.com`, `password: admin123`)  
3. **Get user list**  
4. **Search the newly created user by user ID**  
5. **Edit user information** (e.g., firstname, phonenumber)  
6. **Login by any user**  
7. **Get item list**  
8. **Add an item**  
9. **Edit an existing item name**  
10. **Delete an item from the list**  

All these features were implemented in Postman and automated using REST Assured.

---

##  Test Scenarios

###  Positive Test Cases
| # | Scenario | Method | Expected Result |
|---|-----------|---------|----------------|
| 1 | Register new user | `POST /api/auth/register` | User created successfully |
| 2 | Admin login with valid credentials | `POST /api/auth/login` | Returns access token |
| 3 | Get all users | `GET /api/users` | Returns user list |
| 4 | Search user by ID | `GET /api/users/{id}` | Returns user info |
| 5 | Edit user details | `PUT /api/users/{id}` | Updates successfully |
| 6 | User login with valid credentials | `POST /api/auth/login` | Token generated |
| 7 | Get item list | `GET /api/items` | Returns item list |
| 8 | Add new item | `POST /api/items` | Item added |
| 9 | Edit item name | `PUT /api/items/{id}` | Item updated |
| 10 | Delete item | `DELETE /api/items/{id}` | Item deleted successfully |

---

###  Negative Test Cases

| # | Scenario | Method | Endpoint | Expected Result |
|---|-----------|---------|-----------|----------------|
| 1 | Search user by invalid ID | `GET` | `/api/users/{invalidId}` | **404 Not Found** |
| 2 | Delete item by invalid ID | `DELETE` | `/api/items/{invalidId}` | **404 Not Found** |
| 3 | Create item with invalid fields | `POST` | `/api/items` | **400 Bad Request** |
| 4 | Create duplicate user | `POST` | `/api/auth/register` | **409 Conflict** |
| 5 | User login with blank credentials | `POST` | `/api/auth/login` | **400 Bad Request** |
| 6 | Admin login with wrong password | `POST` | `/api/auth/login` | **401 Unauthorized** |
| 7 | Admin login with wrong email | `POST` | `/api/auth/login` | **401 Unauthorized** |

Each negative case verifies that proper **validation and error handling** exist in the API responses.

---

## Postman Collection

All APIs were first tested and validated in Postman before automation.  
**[Postman Collection Documentation](https://documenter.getpostman.com/view/12885463/2sB3Wk14Lg)**

---

##  Test Case Documentation

Test case link:  
**[Google Sheet – Test Cases](https://docs.google.com/spreadsheets/d/1Cd1tHlCzbTWhudkZKpQI1VWDld2rI-CN/edit?usp=sharing&ouid=114143908846834533694&rtpof=true&sd=true)**

---

## Allure Reports
<img width="1358" height="650" alt="Allure Report Overview" src="https://github.com/user-attachments/assets/80c5130f-5ddd-4cdf-8b47-4e50147aa5e6" />

<img width="1358" height="688" alt="Allure Report Details" src="https://github.com/user-attachments/assets/f7278758-85a9-4b44-a6c8-6185a2017d12" />

---

##  Tools & Technologies

| Tool / Library | Purpose |
|-----------------|----------|
| **Java 11+** | Programming language |
| **REST Assured** | API automation library |
| **TestNG** | Test execution framework |
| **Maven** | Dependency management |
| **Allure Report** | Reporting and analytics |
| **Postman** | API exploration and documentation |



