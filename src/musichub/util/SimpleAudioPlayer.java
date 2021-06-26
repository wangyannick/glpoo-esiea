package musichub.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

import javax.swing.*;

public class SimpleAudioPlayer {

	public void readAudioFile(String filePath)throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Scanner scanner = new Scanner(System.in);

		// Chargement du fichier musique en .wav
		//File file = new File("audioFiles\\logiciel-triste.wav");
		File file = new File("audioFiles\\" + filePath);

		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);

		String response = "";

		while (!response.equals("Q")) {
			System.out.println("P = Play, S = Stop, R = Restart, Q = Quit");
			System.out.print("Make your choice: ");

			response = scanner.next();
			response = response.toUpperCase();

			switch (response) {
			case ("P"):
				clip.start();
				break;
			case ("S"):
				clip.stop();
				break;
			case ("R"):
				clip.setMicrosecondPosition(0);
				break;
			case ("Q"):
				clip.close();
				break;
			default:
				System.out.println("Invalid Answer");
			}
		}

		System.out.println("Music stopped");
	}
}