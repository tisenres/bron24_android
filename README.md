# Bron24 Android Client

**Bron24** is a mobile app for booking football stadiums across Tashkent, Uzbekistan.

This was a team project initiated in July 2024. I initially built the Android client from scratch and later collaborated with a second developer. This README document outlines the MVP version and lessons learned from the development process.

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

- Over-engineered MVP: focused too much on “doing it right” rather than “shipping it fast”
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

- It’s OK to simplify features, but not architecture
- Jetpack Compose needs performance mindfulness (recompositions matter)
- Coroutines are powerful but easy to misuse without understanding the internals
- The architecture you build reflects your "pattern recognition" across projects

## 📸 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/f58b4b93-a02c-4d6e-8fdd-22136ed6e748" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/134b9697-4750-4702-acdf-5db56ecde9f2" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/c444e295-496d-4d3c-bb77-3f078fade979" width="250" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/1a549a5d-56e8-4f2e-b3e7-2e23b4bd7b9e" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/69a8876d-5125-4f86-a396-83b7ae034d80" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/b6ef77d6-5013-4a3c-b3f8-88f10b0b6fbf" width="250" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/d22bba98-09d3-46a8-8e18-18f60b3fdbc9" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/277276cc-8d08-4f58-84dd-90a119ac4669" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/2f31a460-f8f3-4ed4-87f5-68ec196de2d8" width="250" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/15a95bd0-782f-43d7-b1fd-86ea5d2b7894" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/af24ceb5-7527-4be5-a763-2b92dc648c3c" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/1e13f4a6-07d0-4b2e-b082-b30f412e3bff" width="250" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/8641b769-3d0e-4668-9882-c1309e350ecd" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/189e5a7c-6b73-4745-86b1-6ce5dad7cc2a" width="250" style="margin-right: 10px;" />
  <img src="https://github.com/user-attachments/assets/a5220668-d547-4f47-b1c4-135e93d644f5" width="250" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/676633ae-b254-4650-907a-7e4e3f50dc2e" width="250" />
</p>


> Screenshots and demo videos are available in the repository's `media/` folder.

---

If you're curious about building apps with Clean Architecture or Kotlin Compose workflows, this repo is a practical showcase with real-world lessons.
