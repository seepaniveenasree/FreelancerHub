# Freelancer Hub
## Introduction
The modern world is witnessing a remarkable shift toward online freelancing platforms where individuals can offer their skills and clients can find the right talent for their work.
Freelancer Hub is a web-based system designed to bridge the gap between clients and freelancers. It acts as a common space where clients can post project requirements, and freelancers can browse available projects, apply for suitable ones, and collaborate efficiently.
This project simplifies the process of hiring freelancers and managing freelance projects by creating a user-friendly and efficient online platform.
## Key Features:
- Two user roles: Client and Freelancer
- Clients: post projects, assign freelancers, track progress
- Freelancers: register, create profiles, browse projects, apply, and communicate with clients
- Modular architecture with Spring Boot and MongoDB backend
- Frontend built with HTML, CSS, JavaScript for a clean, interactive UI

# Project Objective
The main objective of the Freelancer Hub project is to develop a digital workspace that connects clients and freelancers seamlessly, reducing communication gaps and creating transparency in hiring and work management.
## Specific Objectives:
- Develop a web application that connects freelancers with clients effectively.
- Enable clients to post, manage, and track project details online.
- Allow freelancers to register, showcase skills, and apply for relevant projects.
- Ensure clear role separation with respective dashboards.
- Store all user and project information securely in MongoDB.
- Implement backend logic using Spring Boot with RESTful APIs.
- Create a simple frontend using HTML, CSS, JavaScript with fetch() calls for backend communication.

# Technology Stack
## Frontend
- HTML: Structures web pages and defines layout
- CSS: Provides styling, color, alignment, and professional look
- JavaScript: Adds dynamic behavior and fetches data from backend APIs
## Backend
- Spring Boot: Java-based framework for building REST APIs and handling CRUD operations
- Handles business logic and communication between frontend and database
## Database
- MongoDB: NoSQL database storing data in collections instead of tables
- Collections include: User, FreelancerProfile, Project
## Tools & Frameworks
- Maven: Dependency management
- Postman: Testing REST APIs and verifying endpoints
- Git & GitHub: Version control and project maintenance

# System Architecture
The architecture follows a three-tier model ensuring separation between user interface, application logic, and database management.
1. Presentation Layer (Frontend)
- Built with HTML, CSS, JavaScript
- Provides user interfaces for registration, login, dashboards, and project management
- Communicates with backend via HTTP requests
2. Application Layer (Backend)
- Implemented using Spring Boot
- Components:
- Controllers: Handle REST API requests
- Services: Contain business logic
- Repositories: Manage data access with Spring Data MongoDB
3. Database Layer
- MongoDB stores collections for Users, Freelancer Profiles, Projects
- Schema-less design ensures flexibility and scalability
# Data Flow:
Frontend → Backend Controller → Service → Repository → MongoDB → Response back to Frontend

## Features Implemented
- Registration and Login: Secure role-based registration (Client/Freelancer) with dynamic fields for freelancers.
- Role-Based Dashboards:
- Client Dashboard: Post projects, assign freelancers, track progress, provide feedback.
- Freelancer Dashboard: Browse projects, apply, track assignments, submit work, provide feedback.
- Project Management: Structured workflow with statuses: Open → Assigned → In Progress → Completed.
- Profile Management: Update and manage client/freelancer profiles.
- Data Persistence and Security: Secure storage in MongoDB with validation and error handling.
- API Communication: Frontend communicates with backend via REST APIs using fetch().
- Scalability and Maintainability: Modular design allows easy extension (e.g., chat, payments).
- User-Friendly Interface: Responsive design for desktop and mobile.

# Database Design
The database is implemented using MongoDB with collections:
- User Collection: Stores name, email, password, and role (Client/Freelancer).
- FreelancerProfile Collection: Stores skills, experience, hourly rate, portfolio description.
- Project Collection: Stores project title, description, client ID, freelancer ID, budget, deadline, and status.
# REST APIs Overview
The system follows a RESTful API architecture for smooth communication between frontend and backend.
## 1. User Management APIs
- POST /api/auth/register – Register new users (client/freelancer)
- POST /api/auth/login – Authenticate user credentials
- GET /api/users/{id} – Retrieve user profile
- PUT /api/users/{id} – Update user information
- DELETE /api/users/{id} – Delete user account
## 2. Project Management APIs
- POST /api/projects – Create new project
- GET /api/projects – Retrieve all projects
- GET /api/projects/{id} – Fetch project details
- PUT /api/projects/{id} – Update project details
- DELETE /api/projects/{id} – Remove project
## 3. Dashboard APIs
- GET /api/client/dashboard – Client dashboard view
- GET /api/freelancer/dashboard – Freelancer dashboard view
## 4. Freelancer Profile APIs
- GET /api/freelancers/{id} – Retrieve freelancer profile
- PUT /api/freelancers/{id} – Update freelancer profile
- GET /api/freelancers – List all freelancers
## 5. Application Management APIs
- POST /api/applications – Apply for a project
- GET /api/applications/project/{projectId} – Get applications for a project
- GET /api/applications/freelancer/{freelancerId} – Get freelancer’s applications
- PUT /api/applications/{id} – Update application status
## 7. Search and Filter APIs
- GET /api/search/freelancers?skill=Java – Search freelancers by skill
- GET /api/search/projects?keyword=web – Search projects by keyword
## 8. Admin Management APIs
- GET /api/admin/users – View all users
- GET /api/admin/projects – View all projects
- GET /api/admin/feedback – View all feedback
- DELETE /api/admin/user/{id} – Remove a user

# RESULT(ScreenShots)
<img width="1066" height="503" alt="image" src="https://github.com/user-attachments/assets/1746cc73-3463-4ba2-9ed5-e5fce0327597" />

<img width="1075" height="492" alt="image" src="https://github.com/user-attachments/assets/312708d4-68d6-49bc-8498-e15979475d50" />

<img width="1077" height="485" alt="image" src="https://github.com/user-attachments/assets/7b8bb6e0-b5e2-4df9-af9d-d9bedd960c77" />

<img width="1067" height="487" alt="image" src="https://github.com/user-attachments/assets/d5fc69ea-24a0-430c-9b47-01aa15bddefc" />

<img width="1051" height="507" alt="image" src="https://github.com/user-attachments/assets/2b1c3324-8b1c-4b98-b14a-88675a505abe" />

<img width="1066" height="555" alt="image" src="https://github.com/user-attachments/assets/0f324233-a412-4955-bb70-1c4da3bfee6d" />

<img width="1060" height="440" alt="image" src="https://github.com/user-attachments/assets/1efa60b7-a2de-479a-924c-940863bc7831" />

<img width="1052" height="648" alt="image" src="https://github.com/user-attachments/assets/3f858fc4-b281-4545-aeb6-19ef0b1296a5" />

<img width="1059" height="533" alt="image" src="https://github.com/user-attachments/assets/62c4c262-7b83-47ac-8407-5012bca57b70" />

<img width="1036" height="569" alt="image" src="https://github.com/user-attachments/assets/2149b20c-bbc1-41a5-a41c-760d4d5383ce" />

<img width="1062" height="569" alt="image" src="https://github.com/user-attachments/assets/c194b212-121c-4f86-bc97-928e23cecfea" />

