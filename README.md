# Sharing Surplus

Sharing Surplus is an Android application designed to help individuals to share excess produce with the community.



## Features

- **User Authentication**: Sign up and log in with email and password.  
to test out the application, please use  
username: test@test.ac.uk  
password: 123456
- **Request Management**: Request surplus  from others or add your own surplus.
- **Good Dead Points**: Earn and track Good Deed points for contributing to the community.
- **Status Change**: Get notified when your request is accepted or rejected.

## Requirements

Before you begin, ensure you have the following installed:

- **Android Studio**: [Download Here](https://developer.android.com/studio)
- **JDK 11 or higher**: Required to compile the application.
- **Android SDK**: Included with Android Studio, required for building the app.
- **Android Emulator**: Included with Android Studio, to run the application on a virtual device.

## Installation Instructions

1. **Open the Application**

Open th downloaded Project in Android Studio.
Gradle will automatically download and install the required dependencies when you open the project.
The Firebase and other Google services are by default included in the app files.

2. **Run the Application**  


Connect an Android mobile phone or Start an Emulator
Ensure that your device is connected via USB and USB debugging is enabled, or start an Android emulator from Android Studio.
Build and Run the App
Click on the Run button in Android Studio, or use the shortcut Shift + F10.
The app should compile and install on the connected device or emulator.

## Project Structure

The project is organized into the following modules and directories:

- **`app/`**: Main Android app module containing the codebase.
  - **`src/main/java/com/example/sharingsurplus/`**: Contains all the source code, including:
    - **`data/`**: Data models, repositories, and API interfaces.
    - **`presentation/`**: UI-related code including ViewModels and Composables.
    - **`di/`**: Dependency injection setup with Dagger Hilt.
  - **`src/main/res/`**: Contains all the resources including drawables, custom components and strings.


## Contact

For any questions or feedback, please contact the me at [smb38@st-andrews.ac.uk](mailto:[smb38@st-andrews.ac.uk).