package test.java.musichub.business.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.musichub.business.NoAlbumFoundException;
import main.java.musichub.business.NoElementFoundException;
import main.java.musichub.business.controller.AudioElementController;
import main.java.musichub.business.view.AudioElementView;

class AudioElementTest {

	AudioElementController audioElementController = new AudioElementController();
	AudioElementView audioElementView = new AudioElementView();

	/**
	 * test the directory of the audio file
	 */
	@Test
	void testingDir() {
		assertEquals(AudioElementController.DIR, System.getProperty("user.dir"));
		assertEquals(AudioElementController.ELEMENTS_FILE_PATH,
				System.getProperty("user.dir") + "\\files\\elements.xml");
	}

	/**
	 * test for saving Elements
	 */
	@Test
	void savingElements() {
		assertAll(() -> audioElementController.saveElements());
	}

	/**
	 * testing reading elements
	 * @throws NoElementFoundException 
	 */
	@Test
	void testingReadingElement() throws NoElementFoundException {
		assertEquals("123.txt", audioElementController.readElement("toto"));
	}

	/**
	 * testing reading with element that doesn't exist
	 */
	@Test
	void testingReadingFakeElement() {
		assertThrows(NoElementFoundException.class, () -> {
			audioElementController.readElement("FAKE ELEMENT");
		});
	}

	/**
	 * testing rating elements
	 * 
	 * @throws NoElementFoundException
	 */
	@Test
	void testingRatingElement() throws NoElementFoundException {
		assertEquals("", audioElementController.getAudioElements().get(0).getGrade());
		audioElementController.rateElement(audioElementController.getAudioElements().get(0).getTitle(), "5");
		assertEquals("5", audioElementController.getAudioElements().get(0).getGrade());
	}

	/**
	 * testing rating with element that doesn't exist
	 */
	@Test
	void testingRatingFakeElement() {
		assertThrows(NoElementFoundException.class, () -> {
			audioElementController.rateElement("FAKE ELEMENT", "5");
		});
	}

}
