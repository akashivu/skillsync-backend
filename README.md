# 🤝 SkillSync - Team Collaboration Platform (Backend)

A Spring Boot backend for SkillSync – a platform where users collaborate in skill-based teams. Features JWT-based authentication, role-based access control, and team management APIs.

## Features

- User & Recruiter roles
- Register/Login with JWT
- Create Teams
- Add members to team
- Get "My Teams" based on JWT token

## Tech Stack

- Java + Spring Boot
- Spring Security + JWT
- H2 for test
- Maven

## Folder Structure

- `controller/` – REST APIs
- `service/` – Business logic
- `model/` – Entities
- `dto/` – Request/Response bodies
- `security/` – JWT filter, config

## 🧪 Key APIs

| Method | Endpoint         | Description              |
|--------|------------------|--------------------------|
| POST   | `/api/auth/register` | User/Recruiter register |
| POST   | `/api/auth/login`    | Login, receive JWT      |
| POST   | `/api/team/create`   | Create a team           |
| POST   | `/api/team/addMember/{teamId}` | Add member to team  |
| GET    | `/api/team/my`       | Get teams of current user |

## 📫 Developer

Developed by [Akash Patil](https://github.com/akashivu)
