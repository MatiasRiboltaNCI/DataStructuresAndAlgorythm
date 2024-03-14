/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yourapp.business;
import com.yourapp.data.LikedSongsPlaylist;
import com.yourapp.data.Playlist;
import com.yourapp.data.Song;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author matia
 */
public class MusicManager {
    private Playlist likedSongsPlaylist;
    private Map<String, Playlist> genrePlaylists;
    private boolean repeatPlaylist = false;
    
    public MusicManager(){
        this.likedSongsPlaylist = new LikedSongsPlaylist();
        this.genrePlaylists = new HashMap<>();
    }

    public void addSongToLiked(String title, String artist, String genre){
        Song song = new Song(title, artist, genre);
        likedSongsPlaylist.addSong(song);
        System.out.println("Added song: " + song.getTitle());
        categorizeSongByGenre(song);
    }
    
    private void categorizeSongByGenre(Song song){
        genrePlaylists.putIfAbsent(song.getGenre(), new LikedSongsPlaylist());
        Playlist genrePlaylist = genrePlaylists.get(song.getGenre());
        genrePlaylist.addSong(song);
    }
    
    public void moveSong(String title, String artist, String targetGenre){
        Song songToMove = null;
        
        for(Playlist playlist : genrePlaylists.values()){
            for(Song song : playlist.getSongs()){
                if(song.getTitle().equals(title) && song.getArtist().equals(artist)){
                    songToMove = song;
                    playlist.removeSong(song);
                    break;
                }
            }
            if(songToMove != null){
                break;
            }
        }
        
        if (songToMove != null){
            Song updateSong = new Song(title, artist, targetGenre);
            getGenrePlaylists().get(targetGenre).addSong(updateSong);
            
            if(likedSongsPlaylist.getSongs().contains(songToMove)){
                likedSongsPlaylist.removeSong(songToMove);
                likedSongsPlaylist.addSong(updateSong);
            }
        }
    }
    
    public void moveLastAddedSongToGenre(String genre){
        if(likedSongsPlaylist.getSongs().isEmpty()){
            System.out.println("Liked playlist is empty.");
            return;
        }
        
        Song lastAddedSong = likedSongsPlaylist.getSongs().get(likedSongsPlaylist.getSongs().size() - 1);
        
        //Checking if the playlist for this genre exists, if not, has to be created
        genrePlaylists.putIfAbsent(genre, new LikedSongsPlaylist());
        
        //Move the song to the specific genreplaylist
        Playlist genrePlaylist = genrePlaylists.get(genre);
        genrePlaylist.addSong(lastAddedSong);
        //Assuming it would remain in liked songs too.
    }

    public void toggleRepeat(){
        repeatPlaylist = !repeatPlaylist;
    }
    
    public boolean isRepeatPlaylist(){
        return repeatPlaylist;
    }
    
    public Playlist getLikedSongsPlaylist(){
        return likedSongsPlaylist;
    }
    
    public Map<String, Playlist> getGenrePlaylists() {
    return genrePlaylists;
    }
    
    public Playlist getPlaylist(String genre){
        return genrePlaylists.getOrDefault(genre, new LikedSongsPlaylist());
    }
    
    public List<Song> searchSongs(String keyword){
        List<Song> searchResults = new ArrayList<>();
        keyword = keyword.toLowerCase();
        
        for (Song song : likedSongsPlaylist.getSongs()){
            if (song.getTitle().toLowerCase().contains(keyword) || song.getArtist().toLowerCase().contains(keyword)){
                searchResults.add(song);
            }
        }
        return searchResults;
    }
    
    public void deleteSong(String title, String artist) {
        Song toDelete = null;
        // Find the song in the liked songs playlist
        for (Song song : likedSongsPlaylist.getSongs()) {
            if (song.getTitle().equals(title) && song.getArtist().equals(artist)) {
                toDelete = song;
                break;
            }
        }

        if (toDelete != null) {
            // Remove from liked songs playlist
            likedSongsPlaylist.removeSong(toDelete);
            // Remove from all genre playlists
            for (Playlist genrePlaylist : genrePlaylists.values()) {
                genrePlaylist.removeSong(toDelete);
            }
        }
    }
}
