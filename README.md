# Bron24 Android

**Bron24** is a native Android application built for booking football stadiums in Uzbekistan. The platform aims to connect players with venue owners by digitizing the offline rental process and simplifying stadium scheduling.

> 🚧 MVP project built by a small team of university developers.  
> No CI/CD or automated testing was used.

## 🚀 Key Features

- 📍 Search football stadiums via map or list view
- 📅 View availability and weekly schedules
- 🕒 Book time slots with instant updates
- 🧑‍💼 Stadium owners can add and manage their venues
- 🔐 User registration, login, and role-based access
- 📦 Built with modern Android stack (see below)

## 🛠 Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose  
- **Architecture:** Clean Architecture + MVVM  
- **Networking:** Retrofit  
- **State Management:** ViewModel + StateFlow  
- **Dependency Injection:** Koin  
- **Date Handling:** kotlinx-datetime  
- **Backend:** Custom REST API (not open-sourced)  

## 🧱 Architecture Overview

Following Clean Architecture principles, the codebase is divided into:

- **Presentation layer**  
  - Jetpack Compose screens  
  - ViewModels with `StateFlow`  
- **Domain layer**  
  - Use cases and business logic  
  - Abstract repository interfaces  
- **Data layer**  
  - Concrete repository implementations  
  - API service with Retrofit  

## 📂 Folder Structure (Simplified)

/presentation // Jetpack Compose screens and ViewModels
/domain // Use cases and data interfaces
/data // Repositories and API
/di // Koin modules
/utils // Constants and mappers


## 📲 Installation

Clone the repo and open in Android Studio:

```bash
git clone https://github.com/tisenres/bron24_android.git

```

Run on Android 7.0+ (API 24+) device or emulator.

Note: Backend endpoints are currently not public.

🤝 Team & Context
This project was developed in 2024 as part of a university initiative.
Team size: 4–6 rotating members.
Role of main contributor: Android Developer → CTO.

💡 Lessons Learned
Working in a startup without contracts leads to burnout.

MVP without user feedback or clear validation is risky.

Clean architecture pays off in long-term refactoring and team onboarding.

📌 Roadmap (not implemented)
Stadium reviews & ratings

Payment integration

Booking reminders

Admin dashboard

📫 For questions or collaboration: LinkedIn


Let me know if you want a shorter version for a portfolio or CV link, or want to add screenshots or badges.
