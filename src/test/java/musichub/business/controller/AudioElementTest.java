package test.java.musichub.business.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.musichub.business.controller.AudioElementController;
import main.java.musichub.business.view.AudioElementView;

class AudioElementTest {

	AudioElementController audioElementController = new AudioElementController();
	AudioElementView audioElementView = new AudioElementView();

	@Test
	void testingDir() {
		assertEquals(AudioElementController.DIR, System.getProperty("user.dir"));
		assertEquals(AudioElementController.ELEMENTS_FILE_PATH,
				System.getProperty("user.dir") + "\\files\\elements.xml");
	}

}
