/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yourapp.ui;

import com.yourapp.business.MusicManager;
import com.yourapp.data.Playlist;
import com.yourapp.data.Song;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author matia
 */
public class MainFrame extends JFrame{
    private JButton addButton, moveButton, showButton, deleteButton, refreshButton;
    private JList<String> songList;
    private JTextField titleField, artistField, genreField, searchField;
    private MusicManager musicManager;
    private JComboBox<String> genreComboBox;

    
    public MainFrame(){
        setTitle("Music Manager");
        setSize(1200, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        musicManager = new MusicManager();
        initUI();
    }
    
    private void initUI(){
        setLayout(new BorderLayout());
        
        //Initializing buttons and adding action listeners
        addButton = new JButton("Add Song");
        moveButton = new JButton("Move Song");
        showButton = new JButton("Show Playlist");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete");
        
        //Listener stubs - to be implemented
        addButton.addActionListener(e -> addSong());
        moveButton.addActionListener(e -> moveSong());
        showButton.addActionListener(e -> showPlaylist());
        deleteButton.addActionListener(e -> deleteSong());
        refreshButton.addActionListener(e -> updateSongList());
        
        JPanel topPanel = new JPanel();
        titleField = new JTextField(10);
        artistField = new JTextField(10);
        genreField = new JTextField(10);
        searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        
        searchButton.addActionListener(e -> searchSongs());
        
        topPanel.add(new JLabel("Title:"));
        topPanel.add(titleField);
        topPanel.add(new JLabel("Artist:"));
        topPanel.add(artistField);
        topPanel.add(new JLabel("Genre:"));
        topPanel.add(genreField);
        topPanel.add(addButton);
        topPanel.add(moveButton);
        topPanel.add(showButton);
        topPanel.add(deleteButton);
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(refreshButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        //Song list setup
        songList = new JList<>();
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(songList), BorderLayout.CENTER);
        
        //genreComboBox = new JComboBox<>();
        //updateGenreComboBox();  // Populate it with genres from your music manager
        //genreComboBox.addActionListener(e -> showGenrePlaylist());

        //topPanel.add(new JLabel("Select Genre:"));
        //topPanel.add(genreComboBox);
    }

    //Method stubs - to be implemented
    private void addSong(){
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String genre = genreField.getText().trim();
        if (!title.isEmpty() && !artist.isEmpty() && !genre.isEmpty()) {
            musicManager.addSongToLiked(title, artist, genre);
            titleField.setText("");
            artistField.setText("");
            genreField.setText("");
            updateSongList();
            updateGenreComboBox();
        }else {
            JOptionPane.showMessageDialog(this, "Title, artist, and genre fields must not be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
        }//Method to refresh the song list UI.
    }
    
    private void moveSong(){
    int selectedIndex = songList.getSelectedIndex();
    if(selectedIndex != -1){
        String selectedValue = songList.getModel().getElementAt(selectedIndex);
        String[] parts = selectedValue.split(" - ", 3);
        if (parts.length == 3) {
            String title = parts[0];
            String artist = parts[1];
            String currentGenre = parts[2]; // Added to reflect current genre extraction.

            String targetGenre = (String) JOptionPane.showInputDialog(
                this,
                "Select the target genre for the song",
                "Move Song",
                JOptionPane.QUESTION_MESSAGE,
                null,
                musicManager.getGenrePlaylists().keySet().toArray(new String[0]),
                currentGenre); // Current genre is now a fallback option.

            if(targetGenre != null && !targetGenre.equals(currentGenre)){
                musicManager.moveSong(title, artist, targetGenre);
                updateSongList(); // Reflects the changes in the liked songs or current genre list.
                updateGenreComboBox(); // Ensure the genre combo box is up-to-date.
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "No song selected for moving.", "Warning", JOptionPane.WARNING_MESSAGE);
    }
}
    
    private void showPlaylist(){
        String selectedGenre = (String) JOptionPane.showInputDialog(
            this,
            "Select Genre",
            "Show Playlist",
            JOptionPane.QUESTION_MESSAGE,
            null,
            musicManager.getGenrePlaylists().keySet().toArray(new String[0]),
            null);

    if (selectedGenre != null) {
        showPlaylistWindow(selectedGenre);
    }
        updateSongList();
    }
    
    private void deleteSong() {
    int selectedIndex = songList.getSelectedIndex();
    if (selectedIndex != -1) {
        String selectedValue = songList.getModel().getElementAt(selectedIndex);
        String[] parts = selectedValue.split(" - ", 3);
        if (parts.length == 3) {
            String title = parts[0];
            String artist = parts[1];
            // The genre isn't used for deletion but is included for completeness.

            musicManager.deleteSong(title, artist);
            updateSongList();  // Update the main song list
            updateGenreComboBox();  // Update genres if needed
        }
    } else {
        JOptionPane.showMessageDialog(this, "No song selected for deletion.", "Warning", JOptionPane.WARNING_MESSAGE);
    }
}
    
    private void searchSongs(){
        String searchText = searchField.getText().toLowerCase();
        DefaultListModel<String> model = new DefaultListModel<>();
        
        for(Song song : musicManager.getLikedSongsPlaylist().getSongs()){
            if(song.getTitle().toLowerCase().contains(searchText) || song.getArtist().toLowerCase().contains(searchText)){
                model.addElement(song.getTitle() + " - " + song.getArtist());
            }
        }
        songList.setModel(model);
    }
    
    private void updateSongList() {
    // Assuming you want to show liked songs; adjust if you want to reflect another playlist.
    DefaultListModel<String> model = new DefaultListModel<>();
    for (Song song : musicManager.getLikedSongsPlaylist().getSongs()) {
        model.addElement(song.getTitle() + " - " + song.getArtist() + " - " + song.getGenre());
    }
    songList.setModel(model);
}
        
    private void updateGenreComboBox(){
    genreComboBox.removeAllItems();
    // This assumes your MusicManager correctly updates genres upon song deletion.
    for (String genre : musicManager.getGenrePlaylists().keySet()){
        genreComboBox.addItem(genre);
    }
    // Optionally re-select a default or previously selected genre after update
}
    
    private void showGenrePlaylist(){
        String selectedGenre = (String) genreComboBox.getSelectedItem();
        Playlist genrePlaylist = musicManager.getPlaylist(selectedGenre);
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Song song : genrePlaylist.getSongs()){
            String songRepresentation = String.format("%s - %s - %s", song.getTitle(), song.getArtist(), song.getGenre());
            model.addElement(songRepresentation);
        }
        songList.setModel(model);
    }
    
    private void showPlaylistWindow(String genre) {
    JDialog playlistDialog = new JDialog(this, "Playlist: " + genre, true);
    playlistDialog.setSize(400, 300);
    playlistDialog.setLayout(new BorderLayout());

    DefaultListModel<String> model = new DefaultListModel<>();
    Playlist playlist = musicManager.getPlaylist(genre);
    for (Song song : playlist.getSongs()) {
        model.addElement(song.getTitle() + " - " + song.getArtist());
    }

    JList<String> playlistSongs = new JList<>(model);
    playlistDialog.add(new JScrollPane(playlistSongs), BorderLayout.CENTER);

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> playlistDialog.dispose());
    JPanel bottomPanel = new JPanel();
    bottomPanel.add(closeButton);
    playlistDialog.add(bottomPanel, BorderLayout.SOUTH);

    playlistDialog.setLocationRelativeTo(this);
    playlistDialog.setVisible(true);
}

    
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
