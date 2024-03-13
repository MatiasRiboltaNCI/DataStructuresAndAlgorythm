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
    private JButton addButton, moveButton, showButton;
    private JList<String> songList;
    private JTextField titleField, artistField, genreField;
    private MusicManager musicManager;
    private JTextField searchField;
    private JButton searchButton;
    private JButton repeatButton;
    private JButton refreshButton;
    private JComboBox<String> genreComboBox;

    
    public MainFrame(){
        setTitle("Music Manager");
        setSize(1500, 400);
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
        repeatButton = new JButton("Repeat Playlist");
        refreshButton = new JButton("Refresh");
        
        //Listener stubs - to be implemented
        addButton.addActionListener(e -> addSong());
        moveButton.addActionListener(e -> moveSong());
        showButton.addActionListener(e -> showPlaylist());
        repeatButton.addActionListener(e -> togglePlaylistRepeat());
        refreshButton.addActionListener(e -> updateSongList());
        
        JPanel topPanel = new JPanel();
        titleField = new JTextField(10);
        artistField = new JTextField(10);
        genreField = new JTextField(10);
        searchField = new JTextField(10);
        searchButton = new JButton("Search");
        
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
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(repeatButton);
        topPanel.add(refreshButton);
        
        genreComboBox = new JComboBox<>();
        updateGenreComboBox();  // Populate it with genres from your music manager
        genreComboBox.addActionListener(e -> showGenrePlaylist());

        topPanel.add(new JLabel("Select Genre:"));
        topPanel.add(genreComboBox);
        
        add(topPanel, BorderLayout.NORTH);
        
        //Song list setup
        songList = new JList<>();
        add(new JScrollPane(songList), BorderLayout.CENTER);
    }
    
    private void updateGenreComboBox(){
        genreComboBox.removeAllItems();
        for (String genre : musicManager.getGenrePlaylists().keySet()){
            genreComboBox.addItem(genre);
        }
    }
    
    private void showGenrePlaylist(){
        String selectedGenre = (String) genreComboBox.getSelectedItem();
        Playlist genrePlaylist = musicManager.getPlaylist(selectedGenre);
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Song song : genrePlaylist.getSongs()){
            model.addElement(song.getTitle() + " - " + song.getArtist());
        }
        songList.setModel(model);
    }

    
    private void togglePlaylistRepeat(){
        musicManager.toggleRepeat();
        repeatButton.setText(musicManager.isRepeatPlaylist() ? "Disable Repeat" : "Repeat Playlist");
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
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Song song : musicManager.getLikedSongsPlaylist().getSongs()) {
        model.addElement(song.getTitle() + " - " + song.getArtist());
    }
        songList.setModel(model);
}
    
    //Method stubs - to be implemented
    private void addSong(){
        String title = titleField.getText();
        String artist = artistField.getText();
        String genre = genreField.getText();
        musicManager.addSongToLiked(title, artist, genre);
        titleField.setText("");
        artistField.setText("");
        genreField.setText("");
        updateSongList();
        updateGenreComboBox(); //Method to refresh the song list UI.
    }
    
    private void moveSong(){
        String genre = genreField.getText();
        musicManager.moveLastAddedSongToGenre(genre);
        updateSongList(); //Refresh list to reflect changes
    }
    
    private void showPlaylist(){
        updateSongList();
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
