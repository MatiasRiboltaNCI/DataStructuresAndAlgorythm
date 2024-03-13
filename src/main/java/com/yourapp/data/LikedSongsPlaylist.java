/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yourapp.data;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matia
 */
public class LikedSongsPlaylist implements Playlist {
    private final List<Song> songs;
    
    public LikedSongsPlaylist(){
        this.songs = new ArrayList<>();
    }
    
    @Override
    public void addSong(Song song){
        songs.add(song);
    }
    
    @Override
    public void removeSong(Song song){
        songs.remove(song);
    }
    
    @Override
    public List<Song> getSongs(){
        return new ArrayList<>(songs);
    }
}
