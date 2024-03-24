# Learn to Code Application - Backend

# Before you start the application, you need to create a .env file in the root directory and add this to it:
https://drive.google.com/file/d/14bOALw6FtxHR6_IAbOjHurBQACsYnHVa/view?usp=sharing

![env file example.png](img%2Fenv%20file%20example.png)

## Introduction

Welcome to the backend of "Learn to Code". This application provides a platform for users to access educational
resources on various programming topics and engage with a community forum that includes an integrated AI Chat-bot for
optimal interaction.

## Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK) 17 or later
- Maven for dependency management and running the application
- An IDE of your choice (e.g., IntelliJ IDEA, Eclipse, VSCode)
- A hosted MS SQL Server

## Running the Application

1. Clone the repository to your local machine.
2. Navigate to the root directory of the project in your terminal or command prompt.
3. Run the following command to install the project dependencies:
   ```sh
   mvn clean install
   ```
4. Start the application by running:
   ```sh
   mvn spring-boot:run
   ```

   Alternatively, you can run the application directly from your IDE by executing the `Server` main class.

5. Once the application is running, you can access the backend API at `http://localhost:8080/` by default, or the port
   specified in your `.env` configuration.

# Database Schema

![db relation diagram.png](img%2Fdb%20relation%20diagram.png)

# Controllers Documentation

## PaymentController

Handles payment and donation operations using Stripe.

- **POST** `/api/donate`
    - **Description**: Creates a Stripe payment intent for a donation.
    - **Request Body**: `{"amount": Integer}`
    - **Response**: `{"url": String}` - URL to the Stripe checkout session.

## QuestionController

Manages questions posted by users.

- **POST** `/api/questions/`
    - **Description**: Creates a new question.
    - **Request Body**: `QuestionDto`
    - **Response**: `MessageResponse`

- **GET** `/api/questions/{id}`
    - **Description**: Retrieves a question by its ID.
    - **Path Variable**: `id` - Question ID.
    - **Response**: `QuestionDto`

- **GET** `/api/questions/`
    - **Description**: Retrieves all questions or questions filtered by tags.
    - **Request Param**: `tag` (optional) - Comma-separated tag IDs.
    - **Response**: List of `QuestionDto`

- **PUT** `/api/questions/{id}`
    - **Description**: Updates a question.
    - **Path Variable**: `id` - Question ID.
    - **Request Body**: `QuestionDto`
    - **Response**: `MessageResponse`

- **DELETE** `/api/questions/{id}`
    - **Description**: Deletes a question.
    - **Path Variable**: `id` - Question ID.
    - **Response**: `MessageResponse`

## ReplyController

Handles replies to questions.

- **POST** `/api/replies/`
    - **Description**: Creates a new reply to a question.
    - **Request Body**: `ReplyDto`
    - **Response**: `MessageResponse`

- **POST** `/api/replies/{parentId}`
    - **Description**: Creates a child reply to another reply.
    - **Path Variable**: `parentId` - Parent reply ID.
    - **Request Body**: `ReplyDto`
    - **Response**: `MessageResponse`

- **GET** `/api/replies/{id}`
    - **Description**: Retrieves a reply by its ID.
    - **Path Variable**: `id` - Reply ID.
    - **Response**: `ReplyDto`

- **GET** `/api/replies/`
    - **Description**: Retrieves all replies.
    - **Response**: List of `ReplyDto`

- **GET** `/api/replies/question/{questionId}`
    - **Description**: Retrieves all replies for a specific question.
    - **Path Variable**: `questionId` - Question ID.
    - **Response**: List of `ReplyDto`

- **PUT** `/api/replies/{id}`
    - **Description**: Updates a reply.
    - **Path Variable**: `id` - Reply ID.
    - **Request Body**: `{"reply": String}`
    - **Response**: `MessageResponse`

- **DELETE** `/api/replies/{id}`
    - **Description**: Deletes a reply.
    - **Path Variable**: `id` - Reply ID.
    - **Response**: `MessageResponse`

## TagController

Manages tags.

- **POST** `/api/tags/`
    - **Description**: Creates a new tag.
    - **Request Body**: `TagDto`
    - **Response**: `MessageResponse`

- **GET** `/api/tags/{id}`
    - **Description**: Retrieves a tag by its ID.
    - **Path Variable**: `id` - Tag ID.
    - **Response**: `TagDto`

- **GET** `/api/tags/`
    - **Description**: Retrieves all tags.
    - **Response**: List of `TagDto`

- **PUT** `/api/tags/{id}`
    - **Description**: Updates a tag name.
    - **Path Variable**: `id` - Tag ID.
    - **Request Body**: `TagDto`
    - **Response**: `MessageResponse`

- **DELETE** `/api/tags/{id}`
    - **Description**: Deletes a tag.
    - **Path Variable**: `id` - Tag ID.
    - **Response**: `MessageResponse`

## UserController

Handles operations related to user accounts.

- **GET** `/api/users/`
    - **Description**: Retrieves all users.
    - **Response**: List of `UserDto`

- **GET** `/api/users/{id}`
    - **Description**: Retrieves a user by their ID.
    - **Path Variable**: `id` - User ID.
    - **Response**: `UserDto`

- **GET** `/api/users/email/{email}`
    - **Description**: Retrieves a user by their email.
    - **Path Variable**: `email` - User email.
    - **Response**: `UserDto`

- **PUT** `/api/users/{id}`
    - **Description**: Updates a user's information.
    - **Path Variable**: `id` - User ID.
    - **Request Body**: `UserDto`
    - **Response**: `MessageResponse`

- **DELETE** `/api/users/{id}`
    - **Description**: Deletes a user.
    - **Path Variable**: `id` - User ID.
    - **Response**: `MessageResponse`

- **GET** `/api/users/questions/{id}`
    - **Description**: Retrieves all questions posted by a specific user.
    - **Path Variable**: `id` - User ID.
    - **Response**: List of `QuestionDto`

## WeatherController

Provides weather information.

- **GET** `/api/weather/current`
    - **Description**: Retrieves current weather information.
    - **Request Param**: `q` - Query string (e.g., city name, ip, location).
    - **Response**: `WeatherCurrent`

## AiChatBotController

Manages interactions with the AI chat-bot.

- **POST** `/api/openai/chat`
    - **Description**: Sends a message to the AI chatbot and receives a response.
    - **Request Body**: `ChatMemoDto`
    - **Response**: `{"input": ChatMemoDto, "response": ChatMemoDto}`

- **GET** `/api/openai/user/history/{userId}`
    - **Description**: Retrieves the first message of each session by user ID.
    - **Path Variable**: `userId` - User ID.
    - **Response**: List of `ChatMemoDto`

## ChatEngineController

Handles chat room management for direct messaging between users.

- **PUT** `/api/chat-engine/users/{userId}`
    - **Description**: Creates or retrieves a user for the chat engine.
    - **Path Variable**: `userId` - User ID.
    - **Response**: `ResponseEntity`

- **PUT** `/api/chat-engine/chats/{userId}`
    - **Description**: Creates or retrieves a chat room for the specified user and participants.
    - **Path Variable**: `userId` - User ID.
    - **Request Body**: `Set<Long>` - Set of participant IDs.
    - **Response**: `ResponseEntity`
