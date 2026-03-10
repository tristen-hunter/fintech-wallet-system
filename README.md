# Fintech Wallet System

## Description

A backend system for managing digital wallets and money transfers.

The system allows users to create accounts, create wallets, deposit funds, transfer money between wallets, and view transaction history.


## Tech Stack

Backend:
* Spring Boot
* PostgreSQL
* Maven
* Spring Data JPA

Frontend (planned):
* React
* Vite


## Features

* Create users
* Create wallets
* Deposit funds into wallets
* Transfer money between wallets
* View wallet transaction history


## API Endpoints (planned)

POST /users
Create a user account

POST /wallets
Create a wallet for a user

POST /wallets/{id}/deposit
Deposit funds into a wallet

POST /transfer
Transfer funds between wallets

GET /wallets/{id}/transactions
Retrieve transaction history for a wallet


## Running the Project

1. Install PostgreSQL
2. Create a database named `fintech_wallet_db`
3. Configure database connection in `application.properties`
4. Run the Spring Boot application
5. Start the frontend application when implemented


## Project Structure
still busy...
wallet-frontend (planned)
React frontend application
