# Bron24 Android Client

**Bron24** is a mobile app for booking football stadiums across Tashkent, Uzbekistan.

This was a team project started in **July 2024**. I initially built the Android client from scratch and later collaborated with a second developer. This README documents the MVP version and lessons learned from development.

## 🌟 Features

- OTP Authentication via phone number
- Stadium listing with filter and search
- Stadium detail view with images, location, and schedule
- Booking flow (w/ backend confirmation, no payment integration yet)
- View current and past bookings
- User profile management
- Interactive map using **Yandex Maps**
- Multilingual UI: English, Russian, Uzbek

## 🧠 Architecture

- **Clean Architecture**: split into `presentation`, `domain`, `data` layers
- **MVVM** initially, then partially migrated to **MVI** with **Orbit MVI** and **Voyager Navigation**
- Followed **SOLID** principles
- **ViewModel + StateFlow** for reactive state management
- **Navigation Component** and later **Voyager** for screen routing
- Dependency Injection via **Hilt**

Key reflection: logic started leaking into UI and repository layers. More architecture maturity leads to cleaner, abstracted domain logic.

## 🛠 Tech Stack

- Jetpack Compose (first production use – declarative and clean)
- Kotlin Coroutines & Flow (reactive and concise)
- RESTful API integration
- **Chucker** for HTTP debugging
- Custom UI based on Figma designs (not using Material Design system)

## 📉 What Didn’t Go Smoothly

- Over-engineered MVP: focused too much on “doing it right” than “shipping it fast”
- Missed sync points with backend & iOS team
- Kanban process lacked flexibility in Trello (originally used ClickUp)
- Time wasn't allocated for testing and CI/CD integration

## 📌 Roadmap & Unfinished Plans

- Add local cache with Room DB
- CI/CD with GitLab Pipelines
- Firebase Crashlytics setup
- Dark theme support
- Google Play release (we reached Beta Test stage)
- Online payment integration (Click / Payme)

## 🧠 Lessons Learned

- It’s OK to simplify features — but not architecture
- Jetpack Compose needs performance mindfulness (recompositions matter)
- Coroutines are powerful but easy to misuse without understanding the internals
- The architecture you build reflects your "pattern recognition" across projects

## 📸 Screenshots

> Screenshots and demo videos are available in the repository's `media/` folder.

## 📂 Repository

→ [GitHub: bron24_android](https://github.com/tisenres/bron24_android)

---

If you're curious about building apps in Central Asia, Clean Architecture, or Kotlin Compose workflows — this repo is a practical showcase with real-world lessons.
