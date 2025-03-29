# Scratch Lottery Game

A fun scratch-off lottery ticket game built with Kotlin, Spring Boot, and TypeScript. Scratch the gray area to reveal your prize!

## Features

- Interactive scratch-off effect
- Random prize generation
- Mobile-friendly design
- TypeScript for type-safe frontend
- Kotlin/Spring Boot backend
- Responsive layout

## Tech Stack

- Backend:
  - Kotlin
  - Spring Boot 3.2.3
  - Gradle

- Frontend:
  - TypeScript
  - HTML5 Canvas
  - CSS3
  - Thymeleaf templates

## Prerequisites

- JDK 17 or higher
- Node.js 20.11.1 or higher
- npm 10.2.4 or higher
- Gradle 8.x or higher

### Installing Prerequisites

1. Install JDK 17:
```bash
brew tap homebrew/cask-versions
brew install --cask temurin17
```

2. Install Node.js and npm:
```bash
brew install node
```

3. Install Gradle:
```bash
brew install gradle
```

4. Verify installations:
```bash
java -version  # Should show Java 17
node --version  # Should show v20.11.1 or higher
npm --version   # Should show 10.2.4 or higher
gradle --version  # Should show 8.x or higher
```

## Development Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/blair-website.git
cd blair-website
```

2. Create Gradle wrapper:
```bash
gradle wrapper
```

3. Install frontend dependencies:
```bash
cd src/main/resources/static
npm install
```

4. Build the project:
```bash
./gradlew build
```

5. Run the application:
```bash
./gradlew bootRun
```

6. Open your browser and visit: `http://localhost:8080`

## Development Workflow

### Frontend Development

The TypeScript source files are located in `src/main/resources/static/ts/`. To watch for changes during development:

```bash
cd src/main/resources/static
npm run watch
```

### Backend Development

The Kotlin source files are located in `src/main/kotlin/`. The application will automatically reload when you make changes to the Kotlin files.

## Deployment

### Deploying to Heroku

1. Install the Heroku CLI:
```bash
brew tap heroku/brew && brew install heroku
```

2. Login to Heroku:
```bash
heroku login
```

3. Create a new Heroku app:
```bash
heroku create blairgao
```

4. Set the buildpack to Java:
```bash
heroku buildpacks:set heroku/java
```

5. Deploy to Heroku:
```bash
git push heroku main
```

6. Open your app:
```bash
heroku open
```

## Project Structure

```
blair-website/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/
│   │   │       └── blairgao/
│   │   │           ├── Application.kt
│   │   │           ├── LotteryController.kt
│   │   │           └── Prize.kt
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── ts/
│   │       │   │   └── scratch.ts
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   ├── package.json
│   │       │   └── tsconfig.json
│   │       └── templates/
│   │           └── index.html
│   └── test/
├── build.gradle.kts
└── README.md
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
