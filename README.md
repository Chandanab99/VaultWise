# 💰 VaultWise

**VaultWise** is a secure, smart expense tracking desktop application built using JavaFX for the frontend and Spring Boot for the backend.  
It features full user authentication with JWT, MySQL integration, and per-user expense management.

---

## 🚀 Features

- ✅ User Registration & Login (JWT-based)
- ✅ Add, View, Delete Expenses
- ✅ Secure REST APIs with Spring Security
- ✅ JavaFX TableView integration with backend data
- ✅ MySQL database support with sample data
- ✅ Java 17 + Maven + Spring Boot 3.x compatible

---

## 🛠️ Technologies Used

| Layer         | Technology                     |
|---------------|--------------------------------|
| Frontend      | JavaFX (FXML, SceneBuilder)    |
| Backend       | Spring Boot, Spring Security   |
| Authentication| JWT (JSON Web Tokens)          |
| Database      | MySQL                          |
| Build Tools   | Maven                          |
| Java Version  | Java 17                        |

---

## 📦 Folder Structure

```
VaultWise/
├── VaultWise_Backend/      # Spring Boot app
└── VaultWise_Frontend/     # JavaFX app
```

---

## 🧪 How to Run

### 🔹 Backend (Spring Boot)
```bash
cd VaultWise_Backend
mvn clean install
mvn spring-boot:run
```
Update `application.properties` with your MySQL credentials if needed.

### 🔹 Frontend (JavaFX)
- Open `VaultWise_Frontend` in Eclipse or IntelliJ
- Make sure you have JavaFX SDK set up
- Run `VaultwiseApp.java`

---

## 📂 Sample User (for Testing)

You can register via API or insert into the MySQL table:

```json
{
  "username": "demo",
  "password": "password123"
}
```

---

## 🔐 Authentication

All endpoints (except login/register) require a JWT token.

Use `/api/authenticate` to get token and pass it in the header:

```http
Authorization: Bearer <your_token>
```

---

## 👩‍💻 Author

Developed by **Chandana B**  
📧 chandanabatthala99@gmail.com  
🔗 [LinkedIn]  www.linkedin.com/in/chandana-priya-batthala

---

## 🌟 If this helped you, give it a ⭐ star!
