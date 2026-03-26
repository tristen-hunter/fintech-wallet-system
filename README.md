# Fintech Wallet System

## Description

A backend system for managing digital wallets and money transfers.

The system allows users to create accounts, create wallets, transfer money between wallets, and view transaction history.
There is also a Dashboard to aggregate user data and give simple reports on a users spending habits.


## Tech Stack

Backend:
* Spring Boot
* PostgreSQL
* Maven
* Spring Data JPA

Frontend (planned):
* React
* Vite
* Tailwind


## Features

* Create users
* Create wallets
* Transfer money between wallets
* View wallet transaction history
* Currency Conversion
* ACID Transactions
* User AUTH - with sessions


## API Endpoints (edit here)

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


## Project Structure
wallet-frontend (planned)
React frontend application
