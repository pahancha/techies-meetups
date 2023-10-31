![logo](https://raw.githubusercontent.com/pahancha/techies-meetups-nextjs/0e0e13a05b3e43d50d5801ce2189739bfb058e3e/src/assets/logo.svg)

# techies-meetups

The backend of Techie's Meetups is developed using Spring Boot, Spring Security, Lombok, PostgreSQL, and OAuth 2.0 Resource Server. This RESTful API provides endpoints for managing clubs, events (meetups), user registration, and authentication.

## Technologies Used

- Spring Boot
- Spring Security
- Lombok
- PostgreSQL
- OAuth 2.0 Resource Server

## REST API Endpoints

### Public Endpoints
- `GET /api/clubs` - Retrieve a list of clubs
- `GET /api/events` - Retrieve a list of meetups
- `GET /api/clubs/{clubID}` - Retrieve specific club information
- `GET /api/events/{eventID}` - Retrieve specific meetup information

### User Endpoints
- `POST /api/clubs/new` - Create a new club
- `POST /api/{clubID}/events` - Create a new meetup under a club
- `PUT /api/clubs/{clubID}` - Update club details
- `PUT /api/events/{eventID}` - Update event details
- `DELETE /api/clubs/{clubID}` - Delete a club (User and Admin)
- `DELETE /api/events/{eventID}` - Delete a meetup (User and Admin)

### User Registration and Login Endpoints
- `POST /api/register` - Register a new user
- `POST /api/login` - Login a user

## Project Information

Please note that 'Techie's Meetups' is a personal project built with the intention of learning the associated technologies. 
The front end, implemented using Next.js 13, is available at [Techie's Meetups Next.js Repository](https://github.com/pahancha/techies-meetups-nextjs).
