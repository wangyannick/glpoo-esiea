package main.java.musichub.business.controller;

import java.io.IOException;
import java.util.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.java.musichub.business.model.Album;
import main.java.musichub.business.model.AudioElement;
import main.java.musichub.business.model.PlayList;
import main.java.musichub.business.model.Song;
import main.java.musichub.business.view.AlbumView;
import main.java.musichub.business.view.AudioElementView;
import main.java.musichub.business.view.PlaylistView;
import main.java.musichub.exception.NoAlbumFoundException;
import main.java.musichub.exception.NoElementFoundException;
import main.java.musichub.exception.NoPlayListFoundException;
import main.java.musichub.util.SimpleAudioPlayer;

public class MusicHub {

	AudioElementController audioElementController = new AudioElementController();
	AudioElementView audioElementView = new AudioElementView();

	AlbumController albumController = new AlbumController();
	AlbumView albumView = new AlbumView();

	PlaylistController playlistController = new PlaylistController();
	PlaylistView playlistView = new PlaylistView();
	
	SimpleAudioPlayer simpleAudioPlayer = new SimpleAudioPlayer();
	
	public void readAudio(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		simpleAudioPlayer.readAudioFile(filePath);
	}

	/**
	 * function that calls getAlbumSongs and getAudioElements
	 * @param albumTitle
	 * @return
	 * @throws NoAlbumFoundException
	 */
	public List<AudioElement> getAlbumSongs(String albumTitle) throws NoAlbumFoundException {
		return albumView.getAlbumSongs(albumTitle, albumController.getAlbums(),
				audioElementController.getAudioElements());
	}

	public String getAlbumsTitlesSortedByDate() {
		return albumView.getAlbumsTitlesSortedByDate(albumController.getAlbums());
	}

	public List<Song> getAlbumSongsSortedByGenre(String albumTitle) throws NoAlbumFoundException {
		return albumView.getAlbumSongsSortedByGenre(albumTitle, albumController.getAlbums(),
				audioElementController.getAudioElements());
	}

	public Iterator<AudioElement> elements() {
		return audioElementController.elements();
	}

	public String getAudiobooksTitlesSortedByAuthor() {
		return audioElementView.getAudiobooksTitlesSortedByAuthor(audioElementController.getAudioElements());
	}
	
	/**
	 * function that calls the function read an element
	 * @param elementTitle
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 * @throws NoElementFoundException 
	 */
	public void readElement(String elementTitle) throws UnsupportedAudioFileException, IOException, LineUnavailableException, NoElementFoundException {
		simpleAudioPlayer.readAudioFile(audioElementController.readElement(elementTitle));
	}
	
	/**
	 * function to rate an element
	 * @param elementTitle
	 * @param val
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 * @throws NoElementFoundException 
	 */
	public void rateElement(String elementTitle, String val) throws UnsupportedAudioFileException, IOException, LineUnavailableException, NoElementFoundException {
		audioElementController.rateElement(elementTitle, val);
	}

	public void saveElements() {
		audioElementController.saveElements();
	}

	/**
	 * function so an user can save an album
	 */
	public void saveAlbums() {
		albumController.saveAlbums();
	}

	/**
	 * function to save a playlist
	 */
	public void savePlayLists() {
		playlistController.savePlayLists();
	}

	/**
	 * function to delete a playlist
	 * @param plTitle
	 * @throws NoPlayListFoundException
	 */
	public void deletePlayList(String plTitle) throws NoPlayListFoundException {
		playlistController.deletePlayList(plTitle);
	}

	public Iterator<PlayList> playlists() {
		return playlistController.playlists();
	}

	/**
	 * function to add a playlist
	 * @param pl
	 */
	public void addPlaylist(PlayList pl) {
		playlistController.addPlaylist(pl);
	}

	/**
	 * function to add an element to a playlist
	 * @param elementTitle
	 * @param playListTitle
	 * @throws NoPlayListFoundException
	 * @throws NoElementFoundException
	 */
	public void addElementToPlayList(String elementTitle, String playListTitle)
			throws NoPlayListFoundException, NoElementFoundException {
		playlistController.addElementToPlayList(elementTitle, playListTitle, audioElementController.getAudioElements());
	}

	/**
	 * function to add an element
	 * @param a
	 */
	public void addElement(AudioElement a) {
		audioElementController.addElement(a);
	}

	public Iterator<Album> albums() {
		return albumController.albums();
	}

	/**
	 * function to add an element to an Album
	 * @param songTitle
	 * @param titleAlbum
	 * @throws NoAlbumFoundException
	 * @throws NoElementFoundException
	 */
	public void addElementToAlbum(String songTitle, String titleAlbum)
			throws NoAlbumFoundException, NoElementFoundException {
		albumController.addElementToAlbum(songTitle, titleAlbum, audioElementController.getAudioElements());
	}

	/**
	 * function to add an album
	 * @param a
	 */
	public void addAlbum(Album a) {
		albumController.addAlbum(a);
	}

}