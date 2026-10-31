# Sarkari Sahayak 🇮🇳

### Government Scheme Eligibility Checker

Sarkari Sahayak is an Android application designed to help users check their eligibility for government schemes based on their personal and financial information. The application aims to make government welfare schemes more accessible by simplifying the eligibility-checking process.

## 📌 Overview

Finding suitable government schemes and understanding their eligibility criteria can be challenging. Sarkari Sahayak provides a convenient mobile interface through which users can enter their details, check eligibility, and access relevant information about government schemes.

The application uses a Django backend to process requests and communicate with the Android frontend.

## ✨ Features

- **User Authentication:** Register and log in to access the application.
- **Eligibility Checking:** Submit personal and financial information to check eligibility for government schemes.
- **Scheme Information:** Access information about available government schemes.
- **Eligibility History:** View previous eligibility-checking results, subject to the implemented backend functionality.
- **Android Interface:** Access the application's functionality through a mobile-friendly interface.
- **Backend Integration:** Communicate with the backend API to process user requests and retrieve results.

## 🛠️ Tech Stack

### Android Application
- **Language:** Java
- **Development Environment:** Android Studio
- **API Communication:** Retrofit
- **JSON Parsing:** Gson
- **HTTP Client:** OkHttp

### Backend
- **Framework:** Django
- **API Integration:** REST API

### Development Tools
- Git
- GitHub
- Android SDK

## 🏗️ Project Structure

```text
SarkariSahayak/
├── app/
│   └── src/
│       └── main/
│           ├── java/          # Java source code
│           ├── res/           # Android layouts and resources
│           └── AndroidManifest.xml
├── gradle/                    # Gradle wrapper configuration
├── build.gradle.kts           # Project build configuration
├── settings.gradle.kts        # Gradle project settings
├── gradle.properties          # Gradle properties
├── gradlew                    # Gradle wrapper for Unix systems
├── gradlew.bat                # Gradle wrapper for Windows
└── README.md
```

*The exact structure may vary depending on the project configuration.*

## ⚙️ Getting Started

### Prerequisites

Before running the application, make sure you have:

- Android Studio or another compatible Android development environment.
- Android SDK and a compatible JDK.
- The Django backend running and accessible to the Android application.
- An Android emulator or a physical Android device.

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/VaradrajVelhal/SarkariSahayak.git
```

**2. Navigate to the project directory**

```bash
cd SarkariSahayak
```

**3. Open the project**

Open the project directory in Android Studio and allow Gradle to synchronize the dependencies.

**4. Configure the backend URL**

The application communicates with a Django backend through Retrofit.

For an Android emulator, the host machine's localhost is generally accessible using:

```text
http://10.0.2.2:8000/
```

For a physical Android device, configure the URL using your development machine's accessible local IP address or your deployed backend URL.

Ensure the backend URL matches the configuration in the application.

**5. Start the Django backend**

Navigate to your Django backend project and run:

```bash
python manage.py runserver
```

Ensure the backend is configured correctly and that the required database and API endpoints are available.

**6. Run the application**

Launch an Android emulator or connect a physical device, then build and run the application from your Android development environment.

## 🔄 Application Workflow

1. The user registers or logs in to the application.
2. The user enters the required personal and financial details.
3. The application sends the information to the Django backend through API requests.
4. The backend processes the eligibility request.
5. The application displays the eligibility results returned by the backend.
6. Users can access their eligibility history if that functionality is available and working in the configured backend.

## 🎯 Project Objectives

- Simplify access to government scheme eligibility information.
- Provide a mobile-based interface for checking eligibility.
- Demonstrate Android application development using Java.
- Integrate an Android frontend with a Django REST API.
- Apply API communication and JSON parsing in a practical application.

## 🔮 Future Enhancements

- Add notifications about newly available government schemes.
- Improve the user experience with multilingual support.
- Expand the database of government schemes and eligibility criteria.
- Improve eligibility history and result management.
- Deploy the backend to a production environment.
- Add comprehensive application and API testing.

## 👨‍💻 Developer

**Varadraj Velhal**

MCA Student | Android Development | Python & Django

- GitHub: [@VaradrajVelhal](https://github.com/VaradrajVelhal)
- Project Repository: [SarkariSahayak](https://github.com/VaradrajVelhal/SarkariSahayak)

---

*Disclaimer: This application is a software project intended to assist users in exploring government scheme eligibility. Eligibility results should be verified against the relevant official government scheme guidelines. This project is not an official government application.*