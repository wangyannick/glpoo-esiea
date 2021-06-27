package test.java.musichub.business.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.musichub.business.controller.AlbumController;
import main.java.musichub.business.view.AlbumView;

class AlbumTest {

	AlbumController albumController = new AlbumController();
	AlbumView albumView = new AlbumView();

	@Test
	void testingDir() {
		assertEquals(AlbumController.DIR, System.getProperty("user.dir"));
		assertEquals(AlbumController.ALBUMS_FILE_PATH, System.getProperty("user.dir") + "\\files\\albums.xml");
	}

}
