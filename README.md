# Vidsummize 🎥✨

![Vidsummize Logo](https://img.shields.io/badge/Vidsummize-Ready%20to%20Use-brightgreen)

Welcome to **Vidsummize**, a powerful tool designed to help you summarize video content efficiently. This repository provides a seamless way to integrate video summarization into your projects using modern technologies like Docker, Spring Boot, and more.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

In a world flooded with video content, finding the key points can be a challenge. Vidsummize addresses this need by providing a straightforward solution to summarize videos, making it easier for users to digest information quickly. Whether you're a developer looking to enhance your application or a researcher needing quick insights, Vidsummize is here to help.

## Features

- **Easy Setup**: Get started quickly with Docker and Docker Compose.
- **Java-Based**: Built on Java, ensuring high performance and reliability.
- **Database Management**: Utilizes Liquibase for database version control.
- **API Integration**: Connects seamlessly with Ollama and its API.
- **Advanced Summarization**: Leverages Whisper and Whisper-CPP for accurate audio transcription.
- **User-Friendly**: Designed with simplicity in mind, making it accessible for all skill levels.

## Technologies Used

Vidsummize incorporates several technologies to deliver its features:

- **Docker**: For containerization and easy deployment.
- **Docker Compose**: To manage multi-container applications.
- **Java**: The core programming language used for development.
- **jOOQ**: For type-safe SQL query generation.
- **Liquibase**: For managing database changes.
- **Ollama**: To provide advanced language models.
- **Ollama API**: For integrating AI capabilities.
- **Spring Boot**: For building the backend application.
- **Whisper**: For transcribing audio.
- **Whisper-CPP**: An optimized version of Whisper for performance.

## Getting Started

To get started with Vidsummize, follow the steps below. Make sure you have Docker and Docker Compose installed on your machine.

### Prerequisites

- Docker: [Install Docker](https://docs.docker.com/get-docker/)
- Docker Compose: [Install Docker Compose](https://docs.docker.com/compose/install/)

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/BluBluBlaBla/vidsummize.git
   cd vidsummize
   ```

2. **Download the Latest Release**:
   Visit [Releases](https://github.com/BluBluBlaBla/vidsummize/releases) to download the latest version. Follow the instructions provided there to execute the necessary files.

3. **Build and Run**:
   Use Docker Compose to build and run the application:
   ```bash
   docker-compose up --build
   ```

## Usage

Once the application is running, you can access the API endpoints to summarize videos. Here’s how to use it:

1. **API Endpoint**: Use the following endpoint to submit a video for summarization:
   ```
   POST /api/summarize
   ```

2. **Request Body**:
   Provide the video URL in the request body:
   ```json
   {
     "videoUrl": "https://example.com/video.mp4"
   }
   ```

3. **Response**:
   The API will return a summary of the video content.

### Example Request

Here’s an example using `curl`:
```bash
curl -X POST http://localhost:8080/api/summarize \
-H "Content-Type: application/json" \
-d '{"videoUrl": "https://example.com/video.mp4"}'
```

### Example Response

```json
{
  "summary": "This video discusses the key points of..."
}
```

## Contributing

We welcome contributions to Vidsummize! If you would like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Push to your branch.
5. Create a pull request.

Please ensure that your code adheres to the existing style and includes tests where applicable.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or suggestions, please open an issue on the repository or contact us directly.

### Download Latest Release

For the latest updates and releases, check out [Releases](https://github.com/BluBluBlaBla/vidsummize/releases) again. Download the necessary files and execute them to stay updated.

---

Thank you for using Vidsummize! We hope it enhances your video summarization experience.