# Employee Directory App

## Build Tools & Versions Used
- **Android Studio Arctic Fox** | 2020.3.1 Patch 2
- **Kotlin Version**: 1.5.21
- **Gradle Version**: 7.0.2
- **Compile SDK Version**: 34
- **Min SDK Version**: 24
- **Target SDK Version**: 34

## Libraries and Tools
- **Jetpack Compose** for building the UI
- **Retrofit** for network calls
- **Coil** for image loading within Compose
- **Glide** for handling image loading in traditional Android views
- **Accompanist** for additional Compose support tools

## Steps to Run the App
1. Clone the repository from GitHub.
2. Open the project in Android Studio.
3. Ensure that Android Studio is set up with the correct Kotlin and Gradle plugin versions.
4. Connect an Android device or use an emulator.
5. Build and run the application using `Run > Run 'app'`.

## Focus Areas of the App
- **User Interface**: Developed using Jetpack Compose to showcase modern Android development techniques.
- **Image Loading**: Implemented both Coil and Glide to handle image loading effectively within Compose and traditional Android views.
- **Data Fetching**: Utilized Retrofit to manage network operations, fetching data from a predefined API.

## Reason for Focus
- The focus was to create an efficient, responsive, and modern user interface using Jetpack Compose.
- Ensured robust image loading capabilities to handle different network/image scenarios gracefully.
- Aimed to provide a smooth and functional networking layer with Retrofit for reliable data fetching.

## Time Spent on the Project
- Approximately 40 hours spread over two weeks.

## Trade-offs & Future Improvements
- **Trade-offs**: Prioritized core functionality like fetching data and displaying it with images over additional features such as advanced user interactions or animations.
- **With More Time**: Would implement better error handling, caching strategies for network data, and more interactive elements like animations. Also, unit and integration testing would be expanded.

## Weakest Part of the Project
- Error handling and resilience against network failures could be improved. The current setup lacks comprehensive user feedback mechanisms for different network and data fetching states.

## External Code and Dependencies
- Used **Glide** and **Coil** for image loading, licensed under their respective licenses.
- Networking handled by **Retrofit**, which is open source and available under the Apache 2.0 license.

## Additional Information
- This project was a learning endeavor to explore advanced Android development concepts in a practical application. Feedback and contributions are welcome to improve the app further.
