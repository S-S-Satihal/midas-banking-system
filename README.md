# Midas Banking System

## Overview

Midas Banking System is a Spring Boot backend banking application developed as part of the **JPMorgan Chase Software Engineering Virtual Experience (Forage)**.

The project demonstrates event-driven transaction processing using Apache Kafka, integrates an external Incentive API, stores transactions using Spring Data JPA with an H2 database, and exposes REST endpoints for querying user balances.

## Features

* Process banking transactions with Apache Kafka
* Validate transactions before processing
* Integrate with an external Incentive REST API
* Store transaction history using Spring Data JPA
* Expose REST API for retrieving user balances
* Unit testing with JUnit
* Maven-based project structure

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* Apache Kafka
* REST APIs
* H2 Database
* Maven
* JUnit
* Git & GitHub

## Architecture

```
Kafka Producer
      │
      ▼
Kafka Topic
      │
      ▼
Kafka Listener
      │
      ▼
Transaction Validation
      │
      ▼
Incentive API
      │
      ▼
Database (H2)
      │
      ▼
Balance REST API
```

## REST Endpoint

### GET /balance

```
/balance?userId=5
```

Example Response

```json
{
  "amount": 627.86
}
```

## Learning Outcomes

* Event-driven architecture with Kafka
* REST API development using Spring Boot
* Database integration with Spring Data JPA
* External API integration using RestTemplate
* Backend debugging and testing
* Maven project management

## Author

**Sandeep S Satihal**

- Java Full Stack Developer
- GitHub: https://github.com/S-S-Satihal
- https://www.linkedin.com/in/sandeep-satihal/

- ## License

This project was developed for educational purposes as part of the JPMorgan Chase Software Engineering Virtual Experience (Forage).
