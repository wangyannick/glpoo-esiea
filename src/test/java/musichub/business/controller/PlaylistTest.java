package test.java.musichub.business.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.musichub.business.controller.PlaylistController;
import main.java.musichub.business.view.PlaylistView;

class PlaylistTest {

	PlaylistController playlistController = new PlaylistController();
	PlaylistView playlistView = new PlaylistView();

	@Test
	void testingDir() {
		assertEquals(PlaylistController.DIR, System.getProperty("user.dir"));
		assertEquals(PlaylistController.PLAYLISTS_FILE_PATH, System.getProperty("user.dir") + "\\files\\playlists.xml");
	}

}
