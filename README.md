# Welcome to MealsAPI

MealsAPI is your gateway to exploring the world of flavors. Here, food transcends sustenance—it’s an experience that connects people and cultures. From the fiery spice of a curry to the buttery delight of a croissant, every dish tells a unique story.

We celebrate the diversity of food and the people who make it special. Whether it’s a comforting meal shared with loved ones or a bold new dish to try, MealsAPI brings the joy of food to your table.

**Come hungry, leave satisfied. Let’s make every bite count!**

---

## Code Flow

### 1. Controllers and Routes
- **Controllers**: Handle business logic and interact with DAO classes (e.g., `MealController`, `IngredientsController`).
- **Routes**: Define API endpoints and link them to respective controllers (e.g., `MealRoute`, `IngredientsRoute`).

### 2. Data Access Objects (DAO)
- DAO classes (e.g., `MealDAO`, `IngredientsDAO`) interact with the database using an `EntityManagerFactory`.

### 3. DTOs and Entities
- **DTOs**: Act as data carriers between layers (e.g., `MealDTO`, `IngredientsDTO`).
- **Entities**: Represent persistent data in the database (e.g., `Meal`, `Ingredients`).

### 4. Security
- **SecurityController**: Handles authentication, token verification, and role management.
- **SecurityDAO**: Interfaces with the database for user and role-related operations.
- Implements **role-based access control** with roles like `ADMIN`, `USER`, and `ANYONE`.

### 5. Utilities and Configuration
- **Utils**: Provides helper methods for JSON conversion, property reading, etc.
- **AppConfig**: Manages application startup, server configuration, and exception handling.

### 6. Roles and Users
- **Entities**:
    - `Role`: Represents user roles with predefined types (e.g., `ADMIN`, `USER`).
    - `User`: Manages user credentials and role associations.
- Interfaces and classes enforce role-based restrictions to ensure secure actions.

### 7. Exception Management
- **Custom Exceptions**:
    - `ApiException`: Handles general application errors.
    - `NotAuthorizedException`: Manages authentication errors.
- **ExceptionController**: Provides centralized exception handling for the application.

---

## Key Features
- **Modular Design**: Ensures clear separation of concerns with dedicated layers for routing, business logic, data access, and security.
- **Role-Based Security**: Robust authentication and authorization system for secure operations.
- **Database Integration**: Efficient data management with DAOs and DTOs.
- **Custom Error Handling**: Improves user experience and simplifies debugging.

---

# API Endpoints

## Meals Endpoints

| Method | URL                   | Request Body               | Response                                   | Errors       |
|--------|-----------------------|---------------------------|-------------------------------------------|--------------|
| GET    | `api/meals`           | -                         | List of meals (`meals1`, `meals2`, ...)   | 2, 5         |
| GET    | `api/meals/id`        | -                         | Single meal (`meal(id)`)                  | 1, 2, 5      |
| GET    | `api/meals/under25`   | -                         | All meals with under 25 min prep time     | 2, 5         |
| GET    | `api/meals/over25`    | -                         | All meals with over 25 min prep time      | 2, 5         |
| POST   | `api/meals`           | `title: String, prepTime: int` | -                                     | 1, 3, 4, 5   |
| PUT    | `api/meals/id`        | `title: String`           | Updated meal (`meal(id)`)                 | 1, 2, 3, 4, 5|
| DELETE | `api/meals/id`        | -                         | -                                         | 1, 2, 3, 5   |

## Ingredients Endpoints

| Method | URL                     | Request Body             | Response                                   | Errors       |
|--------|-------------------------|-------------------------|-------------------------------------------|--------------|
| GET    | `api/ingredients`       | -                       | List of ingredients (`ingredients1`, `ingredients2`, ...) | 2, 5  |
| GET    | `api/ingredients/id`    | -                       | Single ingredient (`ingredients(id)`)     | 1, 2, 5      |
| POST   | `api/ingredients`       | `title: String`         | -                                         | 1, 3, 4, 5   |
| PUT    | `api/ingredients/id`    | `title: String`         | -                                         | 1, 2, 3, 4, 5|
| DELETE | `api/ingredients/id`    | -                       | -                                         | 1, 2, 3, 5   |

---

## Error Codes

| Code | Description                    |
|------|--------------------------------|
| 1    | Resource not found            |
| 2    | Invalid input                 |
| 3    | Unauthorized access           |
| 4    | Database error                |
| 5    | Internal server error         |

---

Thank you for choosing MealsAPI. Enjoy exploring the flavors!
