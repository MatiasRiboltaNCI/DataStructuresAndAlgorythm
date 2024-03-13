/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.yourapp.data;
import java.util.List;

/**
 *
 * @author matia
 */
public interface Playlist {
    void addSong(Song song);
    void removeSong(Song song);
    List<Song> getSongs();
}
