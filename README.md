# CloudStorageManager

## Overview

CloudStorageManager is a robust REST API designed to interact with AWS S3 file storage, providing users with seamless access to files and a detailed history of file uploads. The application manages entities such as User, Event, and File, with different access levels defined for ADMIN, MODERATOR, and USER roles.

## Project Details

- **Technology Stack:**
  - Java
  - MySQL
  - Spring (IoC, Data, Security)
  - AWS SDK
  - Docker
  - JUnit
  - Mockito
  - Gradle

## Key Features

- **User Access Levels:**
  - ADMIN: Full access to the application
  - MODERATOR: Rights of USER + Read all User data + Read/Modify/Delete all Events + Read/Modify/Delete all Files
  - USER: Read only access to own data + File upload for personal use

- **Entities:**
  - User (List<Event> events, Status status, …)
  - Event (User user, File file, Status status)
  - File (id, location, Status status ...)

- **AWS S3 Interaction:**
  - Utilizes AWS SDK for seamless interaction with AWS S3, ensuring efficient file storage and retrieval.
