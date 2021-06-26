package musichub.business;

import java.util.*;

import musichub.business.controller.AlbumController;
import musichub.business.controller.AudioElementController;
import musichub.business.controller.PlaylistController;
import musichub.business.model.AudioElement;
import musichub.business.model.Song;
import musichub.business.view.AlbumView;
import musichub.business.view.AudioElementView;
import musichub.business.view.PlaylistView;

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

}