# Travel Itinerary Backend App

A Spring Boot application for managing travel itineraries with user authentication and itinerary history tracking.

## Features

- User authentication with JWT
- Travel itinerary management
- User itinerary history
- Search functionality
- RESTful API endpoints

## Database Schema

### User Table
- id (Primary Key)
- username, email, password
- firstName, lastName, phoneNumber
- createdAt, updatedAt
- enabled, role

### Itinerary Table
- id (Primary Key)
- user_id (Foreign Key to User)
- destination, title, description
- fullItinerary (TEXT - stores complete AI-generated itinerary)
- startDate, endDate, numberOfDays
- budgetRange, travelStyle
- createdAt, updatedAt

## API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login

### Itineraries
- `POST /api/itineraries?userId={userId}` - Save new itinerary
- `GET /api/itineraries?userId={userId}` - Get all user itineraries
- `GET /api/itineraries/{itineraryId}?userId={userId}` - Get specific itinerary
- `GET /api/itineraries/search?userId={userId}&searchTerm={term}` - Search itineraries
- `DELETE /api/itineraries/{itineraryId}?userId={userId}` - Delete itinerary

## Setup Instructions

1. **Database Setup**
   - Install MySQL
   - Update `application.properties` with your database credentials
   - The database will be created automatically on first run

2. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

3. **API Usage**
   - The frontend should send the complete AI-generated itinerary in the `fullItinerary` field
   - User authentication is required for all itinerary operations
   - All responses are wrapped in `ApiResponse<T>` format

## Example Request

```json
POST /api/itineraries?userId=1
{
  "destination": "Paris, France",
  "title": "Weekend in Paris",
  "description": "A romantic weekend getaway",
  "fullItinerary": "Complete AI-generated itinerary details...",
  "startDate": "2024-06-15",
  "endDate": "2024-06-17",
  "numberOfDays": 3,
  "budgetRange": "1000-2000",
  "travelStyle": "Luxury"
}
```

## Example Response

```json
{
  "success": true,
  "data": {
    "id": 1,
    "destination": "Paris, France",
    "title": "Weekend in Paris",
    "description": "A romantic weekend getaway",
    "fullItinerary": "Complete AI-generated itinerary details...",
    "startDate": "2024-06-15",
    "endDate": "2024-06-17",
    "numberOfDays": 3,
    "budgetRange": "1000-2000",
    "travelStyle": "Luxury",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  },
  "message": null
}
```