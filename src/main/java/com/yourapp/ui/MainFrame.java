/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yourapp.ui;

import com.yourapp.business.MusicManager;
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
    private JButton deleteButton, repeatButton;

    
    public MainFrame(){
        setTitle("Music Manager");
        setSize(1200, 600);
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
        
        //Listener stubs - to be implemented
        addButton.addActionListener(e -> addSong());
        moveButton.addActionListener(e -> moveSong());
        showButton.addActionListener(e -> showPlaylist());
        
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
        
        deleteButton = new JButton("Delete Song");
        deleteButton.addActionListener(e -> deleteSong());
        topPanel.add(deleteButton);
        
        repeatButton = new JButton("Repeat Playlist");
        repeatButton.addActionListener(e -> togglePlaylistRepeat());
        topPanel.add(repeatButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        //Song list setup
        songList = new JList<>();
        add(new JScrollPane(songList), BorderLayout.CENTER);
    }
    
    private void deleteSong(){
        String selectedValue = songList.getSelectedValue();
        if(selectedValue == null || selectedValue.isEmpty()){
            return;
        }
        
        String[] parts = selectedValue.split(" - ", 2);
        String title = parts[0];
        String artist = parts.length > 1 ? parts[1] : "";
        
        musicManager.deleteSongFromLiked(title, artist);
        updateSongList();
    }
    
    private void togglePlaylistRepeat(){
        musicManager.toggleRepeat();
        repeatButton.setText(musicManager.isRepeatPlaylist() ? "Disabble Repeat" : "Repeat Playlist");
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
    
    private void updateSongList(){
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Song song : musicManager.getLikedSongsPlaylist().getSongs()){
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
        updateSongList(); //Method to refresh the song list UI.
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
