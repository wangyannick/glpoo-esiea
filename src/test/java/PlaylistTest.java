package test.java;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import musichub.business.NoAlbumFoundException;
import musichub.business.NoElementFoundException;
import musichub.business.controller.AlbumController;
import musichub.business.controller.AudioElementController;
import musichub.business.model.Album;
import musichub.business.view.AlbumView;
import musichub.business.view.AudioElementView;

class PlaylistTest {

	AlbumController albumController = new AlbumController();
	AlbumView albumView = new AlbumView();

	AudioElementController audioElementController = new AudioElementController();
	AudioElementView audioElementView = new AudioElementView();

	@Test
	void testPathAlbum() {
		assertEquals(System.getProperty("user.dir"), AlbumController.DIR);
		assertEquals(System.getProperty("user.dir") + "\\files\\albums.xml", AlbumController.ALBUMS_FILE_PATH);
	}

	@Test
	void addAlbums() {
		Album a = new Album("Titre Album", "Artiste", 123, "2020-10-10");
		assertEquals(3, albumController.getAlbums().size());
		albumController.addAlbum(a);
		assertEquals(4, albumController.getAlbums().size());
		albumController.addAlbum(a);
		assertEquals(5, albumController.getAlbums().size());
	}

	@Test
	void addElementToAlbum() throws NoAlbumFoundException, NoElementFoundException {
		assertEquals(2, albumView
				.getAlbumSongs("Album test", albumController.getAlbums(), audioElementController.getAudioElements())
				.size());
		albumController.addElementToAlbum("toto", "Album test", audioElementController.getAudioElements());
		assertEquals(3, albumView
				.getAlbumSongs("Album test", albumController.getAlbums(), audioElementController.getAudioElements())
				.size());
		albumController.addElementToAlbum("toto", "Album test", audioElementController.getAudioElements());
		assertEquals(4, albumView
				.getAlbumSongs("Album test", albumController.getAlbums(), audioElementController.getAudioElements())
				.size());
	}

	@Test
	void failAddElementToAlbum() throws NoAlbumFoundException, NoElementFoundException {
		assertThrows(NoElementFoundException.class, () -> {
			albumController.addElementToAlbum("doesnt exist song", "Album test",
					audioElementController.getAudioElements());
		});

		assertThrows(NoAlbumFoundException.class, () -> {
			albumController.addElementToAlbum("toto", "album doesnt exist", audioElementController.getAudioElements());
		});

	}

}
