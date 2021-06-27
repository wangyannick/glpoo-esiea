package musichub.business;

import java.io.IOException;
import java.util.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import musichub.business.controller.AlbumController;
import musichub.business.controller.AudioElementController;
import musichub.business.controller.PlaylistController;
import musichub.business.model.Album;
import musichub.business.model.AudioBook;
import musichub.business.model.AudioElement;
import musichub.business.model.PlayList;
import musichub.business.model.Song;
import musichub.business.view.AlbumView;
import musichub.business.view.AudioElementView;
import musichub.business.view.PlaylistView;
import musichub.util.SimpleAudioPlayer;

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
	
	public void readElement(String elementTitle) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		simpleAudioPlayer.readAudioFile(audioElementController.readElement(elementTitle));
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