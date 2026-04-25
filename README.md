# 🛒 E-Commerce REST API (Spring Boot)

This is the backend for the **E-Commerce**, designed to handle secure data persistence, user authentication, and product management. It serves as the RESTful API for the [Flutter Mobile Client](https://github.com/Doeun-Panha/e-commerce-flutter).

---

## 🛠️ Tech Stack & Architecture
* **Language:** Java 25
* **Framework:** Spring Boot 4.0.5 (Spring Web, Spring Data JPA)
* **Security:** Spring Security 6 with **JWT (JSON Web Tokens)**
* **Database:** Microsoft SQL Server
* **Build Tool:** Maven

### **Layered Architecture**
The project follows a strict separation of concerns to ensure scalability and maintainability:
1. **Controller Layer:** REST Endpoints and Request Mapping.
2. **Service Layer:** Business logic, password encoding, and JWT generation.
3. **Repository Layer:** Data persistence using Spring Data JPA.
4. **Security Layer:** Custom JWT Filters and Role-Based Access Control (RBAC).

---

## ✨ Key Technical Implementations

### **🔒 Security & Authentication**
* **JWT Stateless Auth:** Custom `JwtAuthenticationFilter` that intercepts requests to validate Bearer tokens.
* **Role-Based Access (RBAC):** Configured `SecurityConfig` to restrict product/category management to `ADMIN` users while allowing `USER` access for browsing.
* **Global Exception Handling:** Implemented `@RestControllerAdvice` to provide clean, consistent JSON error responses for database conflicts (e.g., duplicate usernames) and runtime errors.

### **📁 Media & Resource Handling**
* **Multi-Part File Support:** Handles image uploads for products using `MultipartFile`.
* **Static Resource Mapping:** A custom `WebMvcConfigurer` maps internal storage folders (`user-photos/`) to public URLs (`/uploads/**`) for consumption by the Flutter app.

### **💾 Database Design**
The system manages relationships between Users, Products, and Categories with a focus on data integrity:
* **Many-to-One Relationships:** Products are linked to Categories.
* **Transactional Integrity:** Uses `@Transactional` to safely handle category deletions while preserving linked product data.

---

## 🚀 API Endpoints (Samples)

| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Public | Register new user (Default: ROLE_USER) |
| `POST` | `/api/auth/login` | Public | Authenticate and receive JWT |
| `GET` | `/api/products` | Public | View all products |
| `POST` | `/api/products` | Admin | Add new product with image upload |
| `DELETE` | `/api/categories/{id}` | Admin | Safely remove category (cascades to products) |

---

## ⚙️ How to Run

1. **Clone the repo:**
   ```bash
   git clone [https://github.com/Doeun-Panha/e-commerce-spring.git](https://github.com/Doeun-Panha/e-commerce-spring.git)
