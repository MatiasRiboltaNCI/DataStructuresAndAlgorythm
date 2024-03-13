/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yourapp.business;
import com.yourapp.data.Playlist;
import com.yourapp.data.Song;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author matia
 */
public class MusicManager {
    private Playlist likedSongsPlaylist;
    private Map<String, Playlist> genrePlaylists;
    
    public MusicManager(Playlist likedSongsPlaylist){
        this.likedSongsPlaylist = likedSongsPlaylist;
        this.genrePlaylists = new HashMap<>();
    }
    
    public void addSongToLiked(Song song){
        likedSongsPlaylist.addSong(song);
    }
    
    public void moveLastAddedSongToGenre(String genre){
        if(likedSongsPlaylist.getSongs().isEmpty()){
            return; //No songs to move.
        }
        
        Song lastAdded = likedSongsPlaylist.getSongs().get(likedSongsPlaylist.getSongs().size() - 1);
        
        //Checking if the playlist for this genre exists, if not, has to be created
        genrePlaylists.putIfAbsent(genre, new com.yourapp.data.LikedSongsPlaylist());
        
        //Move the song to the specific genreplaylist
        Playlist genrePlaylist = genrePlaylists.get(genre);
        genrePlaylist.addSong(lastAdded);
        
        //Assuming it would remain in liked songs too.
    }
    
    public Playlist getGenrePlaylist(String genre){
        return genrePlaylists.getOrDefault(genre, new com.yourapp.data.LikedSongsPlaylist());
    }
}
