package test.java.musichub.business.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.musichub.business.NoAlbumFoundException;
import main.java.musichub.business.NoElementFoundException;
import main.java.musichub.business.controller.AlbumController;
import main.java.musichub.business.controller.AudioElementController;
import main.java.musichub.business.model.Album;
import main.java.musichub.business.view.AlbumView;
import main.java.musichub.business.view.AudioElementView;

class AlbumTest {

	AlbumController albumController = new AlbumController();
	AlbumView albumView = new AlbumView();

	AudioElementController audioElementController = new AudioElementController();
	AudioElementView audioElementView = new AudioElementView();

	@Test
	void testingDir() {
		assertEquals(AlbumController.DIR, System.getProperty("user.dir"));
		assertEquals(AlbumController.ALBUMS_FILE_PATH, System.getProperty("user.dir") + "\\files\\albums.xml");
	}

	@Test
	void addingAlbum() {
		assertEquals(3, albumController.getAlbums().size());
		Album a = new Album("Title", "Artist", 20, "2020-10-10");
		albumController.addAlbum(a);
		assertEquals(4, albumController.getAlbums().size());
	}

	@Test
	void addingElementToAlbum() throws NoAlbumFoundException, NoElementFoundException {
		assertEquals(2, albumView
				.getAlbumSongs("Album test", albumController.getAlbums(), audioElementController.getAudioElements())
				.size());
		albumController.addElementToAlbum("toto", "Album test", audioElementController.getAudioElements());
		assertEquals(3, albumView
				.getAlbumSongs("Album test", albumController.getAlbums(), audioElementController.getAudioElements())
				.size());
	}

}