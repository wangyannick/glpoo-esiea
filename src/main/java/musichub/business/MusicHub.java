package main.java.musichub.business;

import java.util.*;

import main.java.musichub.business.controller.AlbumController;
import main.java.musichub.business.controller.AudioElementController;
import main.java.musichub.business.controller.PlaylistController;
import main.java.musichub.business.model.Album;
import main.java.musichub.business.model.AudioBook;
import main.java.musichub.business.model.AudioElement;
import main.java.musichub.business.model.PlayList;
import main.java.musichub.business.model.Song;
import main.java.musichub.business.view.AlbumView;
import main.java.musichub.business.view.AudioElementView;
import main.java.musichub.business.view.PlaylistView;

public class MusicHub {

	AudioElementController audioElementController = new AudioElementController();
	AudioElementView audioElementView = new AudioElementView();

	AlbumController albumController = new AlbumController();
	AlbumView albumView = new AlbumView();

	PlaylistController playlistController = new PlaylistController();
	PlaylistView playlistView = new PlaylistView();

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

	public void saveElements() {
		audioElementController.saveElements();
	}

	public void saveAlbums() {
		albumController.saveAlbums();
	}

	public void savePlayLists() {
		playlistController.savePlayLists();
	}

	public void deletePlayList(String plTitle) throws NoPlayListFoundException {
		playlistController.deletePlayList(plTitle);
	}

	public Iterator<PlayList> playlists() {
		return playlistController.playlists();
	}

	public void addPlaylist(PlayList pl) {
		playlistController.addPlaylist(pl);
	}

	public void addElementToPlayList(String elementTitle, String playListTitle)
			throws NoPlayListFoundException, NoElementFoundException {
		playlistController.addElementToPlayList(elementTitle, playListTitle, audioElementController.getAudioElements());
	}

	public void addElement(AudioElement a) {
		audioElementController.addElement(a);
	}

	public Iterator<Album> albums() {
		return albumController.albums();
	}

	public void addElementToAlbum(String songTitle, String titleAlbum)
			throws NoAlbumFoundException, NoElementFoundException {
		albumController.addElementToAlbum(songTitle, titleAlbum, audioElementController.getAudioElements());
	}

	public void addAlbum(Album a) {
		albumController.addAlbum(a);
	}

}