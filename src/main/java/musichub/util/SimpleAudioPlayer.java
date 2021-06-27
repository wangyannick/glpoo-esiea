package main.java.musichub.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

import javax.swing.*;

import main.java.musichub.logger.Level;
import main.java.musichub.logger.SingletonFileLogger;

public class SimpleAudioPlayer {

	/**
	 * function to read audio files
	 * @param filePath
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
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
				SingletonFileLogger.getInstance().write(Level.INFO, "Playing music");
				clip.start();
				break;
			case ("S"):
				SingletonFileLogger.getInstance().write(Level.INFO, "Stopping music");
				clip.stop();
				break;
			case ("R"):
				SingletonFileLogger.getInstance().write(Level.INFO, "Restarting music");
				clip.setMicrosecondPosition(0);
				break;
			case ("Q"):
				SingletonFileLogger.getInstance().write(Level.INFO, "Closing music");
				clip.close();
				break;
			default:
				System.out.println("Invalid Answer");
			}
		}

		System.out.println("Music stopped");
	}
}