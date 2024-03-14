/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yourapp.data;

import java.util.Objects;

/**
 *
 * @author matia
 */
public class Song {
    private final String title;
    private final String artist;
    private final String genre;
    
    public Song(String title, String artist, String genre){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getArtist(){
        return artist;
    }
    
    public String getGenre(){
        return genre;
    }
    
    @Override
    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Song song = (Song) obj;
    return title.equals(song.title) && artist.equals(song.artist) && genre.equals(song.genre);
}

    @Override
    public int hashCode() {
    return Objects.hash(title, artist, genre);
}

}
