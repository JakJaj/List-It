# List It

List It is an Android application developed in Kotlin that facilitates shared list management among users who connect in groups. The app is integrated with Firebase to provide real-time data synchronization and storage. Users can create, share, and manage lists collaboratively within their groups, enhancing productivity and coordination.

## Features

- **User Authentication**: Secure user authentication using Firebase Authentication.
- **Group Creation**: Users can create groups and invite others to join.
- **List Management**: Create, edit, and delete lists within groups.
- **Real-time Synchronization**: Changes to lists are instantly synchronized across all group members' devices.
- **Firebase Integration**: Utilizes Firebase Realtime Database to store and sync list data.
- **User Permissions**: Group admins can manage user permissions and access levels.
- **Offline Support**: Basic functionality available even when offline, with automatic sync upon reconnection.

## Requirements

- Android Studio
- Firebase Account
- Kotlin knowledge

## Installation

1. Clone the repository `git clone https://github.com/JakJaj/List-It`.
2. Open the project in Android Studio.
3. Set up Firebase for the project and add your Firebase configuration file to the project.
4. Build and run the application on an Android device or emulator.

## Configuration

Before running the application, ensure you have set up Firebase for the project:

1. Create a new project in the [Firebase Console](https://console.firebase.google.com/).
2. Add an Android app to the Firebase project, following the setup instructions to obtain the `google-services.json` configuration file.
3. Replace the existing `google-services.json` file in the app module of the project with your generated file.

## Usage

1. Launch the application on your Android device or emulator.
2. Sign in with your Firebase account or create a new account if you don't have one.
3. Create a group or join an existing one by entering the group code.
4. Start creating and managing lists within your group.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your proposed changes.