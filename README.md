# E-Commerce Web Application with JSF and JPA

## 📝 Introduction

This project is a **web-based e-commerce application** built using **JavaServer Faces (JSF)** for the frontend and **Java Persistence API (JPA)** for database interaction. The application simulates the behavior of an e-commerce website, focusing on features like product catalog management, shopping cart functionality, and user interactions. It demonstrates proficiency in **JSF**, **JPA**, and modern web development practices.

---

## 🎯 Objective

The primary goal of this project is to create a **web application** that simulates an e-commerce platform. The application focuses on:

- Managing product catalogs (products).
- Handling shopping carts (panier).
- Simulating user interactions (internaute).
- the command line (LignedeCommande)

The project adheres to the **MVC (Model-View-Controller)** architecture and utilizes **JPA** for database persistence and **JSF** for dynamic user interfaces.

---

## 🛠️ Tools and Technologies

- **Frontend**: JavaServer Faces (JSF)
- **Backend**: Java Persistence API (JPA)
- **Database**: MySQL
- **Application Server**: WildFly
- **Build Tool**: Maven
- **ORM**: EclipseLink
- **IDE**: IntelliJ IDEA

---

## 📂 Project Structure

The project is organized into the following layers:

1. **Model Layer**:
   - Contains JPA entity classes (e.g., `Product`, `Cart`, `User`) for database interaction.
   - Uses EclipseLink as the JPA provider for ORM (Object-Relational Mapping).

2. **View Layer**:
   - Built using JSF (JavaServer Faces) for dynamic and interactive user interfaces.
   - Includes XHTML files for rendering product catalogs, shopping carts, and user dashboards.

3. **Controller Layer**:
   - Managed beans (JSF backing beans) handle user interactions and business logic.
   - Utilizes CDI (Contexts and Dependency Injection) for dependency management.

---

## 📋 Features

- **Product Catalog (Vitrine)**:
  - Browse and search for products.
  - View product details.
- **Shopping Cart (Panier)**:
  - Add/remove products to/from the cart.
  - Update product quantities.
- **User Interaction (Internaute)**:
  - User registration and login.
  - View order history.
-**Command Line (Ligne de Commande)**:
  - Selected product, its quantity, the cart it belongs to and also the status (e.g., in progress, declined).
    
---

## 📄 Report

A detailed report documenting the project's demo, design decisions, and implementation details is available in the file:  
[**Report.docx**](./LAB2_Report.docx)

---

## 📚 Additional Notes

- **JSF Components**: Used for building dynamic and reusable UI components.
- **JPA Transactions**: Ensures data consistency and integrity during database operations.
- **CDI**: Used for dependency injection and managing bean lifecycles.
  
---
## 🚀 Installation and Setup

### Prerequisites:
1. **Java Development Kit (JDK)**: Ensure JDK 8 or higher is installed.
2. **WildFly Application Server**: Download and configure WildFly (version 26 or higher).
3. **MySQL**: Install MySQL and set up the database schema.
4. **Maven**: Install Maven for dependency management.

### Steps to Run the Project:
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/ECommerceJSFJPA.git


---

🚀 Feel free to explore the code, contribute, or provide feedback. Your suggestions are always welcome! 🚀

