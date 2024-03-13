# Music Management Application - Data Structures and Algorithms

## Description

The Music Management application is a Java-based desktop application designed to organize and manage music playlists. It allows users to add songs to a 'liked' playlist, categorize songs into genre-specific playlists, search for songs, and manage playlists through a user-friendly graphical interface.

## Features

* **Add Songs:** Users can add songs to their liked playlist, specifying the title, artist, and genre for each song.
* **Automatic Genre Categorization:** Songs added to the liked playlist are automatically categorized into genre-based playlists.
* **Search Functionality:** Users can search for songs within their liked playlist or within specific genre playlists.
* **Delete Songs:** Users can remove songs from their liked playlist or any genre playlist.
* **Playlist Management:** The application supports viewing and managing multiple playlists, including the ability to toggle repeat for individual playlists.
* **Manual Refresh:** A refresh button allows users to update the displayed song list to reflect the current state of the playlists.

## Architecture

The application follows a layered architecture model, including:
* **Data Layer:** Defines the core data structures and interfaces used within the application.
* **Business Logic Layer:** Implements the application logic for managing songs and playlists.
* **User Interface Layer:** Provides a graphical interface for user interaction with the application, built using Java Swing.
* 
## Classes and Interfaces

* **Song:** Represents a song with title, artist, and genre attributes.
* **Playlist Interface:** Defines the standard operations for playlist management.
* **LikedSongsPlaylist:** Implements the Playlist interface to manage the liked songs playlist.
* **MusicManager:** Coordinates the application logic for song and playlist operations.
* **MainFrame:** The main window of the application, handling user input and interface rendering.
