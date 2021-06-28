package main.java.musichub.main;

import java.io.IOException;
import java.util.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.java.musichub.business.*;
import main.java.musichub.business.model.Album;
import main.java.musichub.business.model.AudioBook;
import main.java.musichub.business.model.AudioElement;
import main.java.musichub.business.model.PlayList;
import main.java.musichub.business.model.Song;
import main.java.musichub.logger.Level;
import main.java.musichub.logger.SingletonConsoleLogger;
import main.java.musichub.logger.SingletonFileLogger;

public class Main {
	public static void main(String[] args) {

		MusicHub theHub = new MusicHub();

		System.out.println("Type h for available commands");

		Scanner scan = new Scanner(System.in);
		String choice = scan.nextLine();

		String albumTitle = null;
		String musicTitle = null;
		String answer = null;
		String grade = null;

		if (choice.length() == 0)
			System.exit(0);

		SingletonFileLogger.getInstance().write(Level.INFO, "Starting the hub");

		while (choice.charAt(0) != 'q') {
			switch (choice.charAt(0)) {
			case 'h':
				printAvailableCommands();
				SingletonFileLogger.getInstance().write(Level.INFO, "Displayed commands lists.");
				choice = scan.nextLine();
				break;
			case 'r':
				// On rentre la musique qu'on veut jouer
				System.out.println("Choose the music you want to play");
				musicTitle = scan.nextLine();
				SingletonFileLogger.getInstance().write(Level.INFO, "Trying to read music");
				try {
					theHub.readElement(musicTitle);
					SingletonFileLogger.getInstance().write(Level.INFO, "Reading music " + musicTitle);
					System.out.println("Would you like to rate the music you listened to ? y/n");
					answer = scan.nextLine();
					SingletonFileLogger.getInstance().write(Level.INFO, "Rating music");
					if (answer.charAt(0) == 'y') {
						try {
							System.out.println("Give a grade from 1 to 5");
							grade = scan.nextLine();
							while (grade.charAt(0) != '1' && grade.charAt(0) != '2' && grade.charAt(0) != '3'
									&& grade.charAt(0) != '4' && grade.charAt(0) != '5') {
								System.out.println("Please type a valid grade from 1 to 5");
								grade = scan.nextLine();
							}
							theHub.rateElement(musicTitle, grade);
						} catch (UnsupportedAudioFileException e) {
							// TODO Auto-generated catch block
							System.out.println("Error! - " + e.getMessage());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("Error! - " + e.getMessage());
						} catch (LineUnavailableException e) {
							// TODO Auto-generated catch block
							System.out.println("Error! - " + e.getMessage());
						}
					}
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					System.out.println("Invalid Title" + e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Error! - " + e.getMessage());
					;
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					System.out.println("Error! - " + e.getMessage());
				} catch (NoElementFoundException e) {
					System.out.println("Error! - " + e.getMessage());
				}

				// une fois musique finie demander de la noter ici
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 't':
				// album titles, ordered by date
				System.out.println(theHub.getAlbumsTitlesSortedByDate());
				SingletonFileLogger.getInstance().write(Level.INFO, "Displayed album titles ordered by date.");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'g':
				// songs of an album, sorted by genre
				System.out.println(
						"Songs of an album sorted by genre will be displayed; enter the album name, available albums are:");
				System.out.println(theHub.getAlbumsTitlesSortedByDate());
				SingletonFileLogger.getInstance().write(Level.INFO, "Displayed albums.");
				albumTitle = scan.nextLine();
				SingletonFileLogger.getInstance().write(Level.INFO, "Trying to display " + albumTitle);
				try {
					System.out.println(theHub.getAlbumSongsSortedByGenre(albumTitle));
					SingletonFileLogger.getInstance().write(Level.INFO,
							"Displayed " + albumTitle + " album sorted by Genre.");
				} catch (NoAlbumFoundException ex) {
					SingletonFileLogger.getInstance().write(Level.ERROR,
							"No album found with the requested title " + ex.getMessage());
					SingletonConsoleLogger.getInstance().write(Level.ERROR,
							"No album found with the requested title " + ex.getMessage());
				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'd':
				// songs of an album
				System.out.println("Songs of an album will be displayed; enter the album name, available albums are:");
				System.out.println(theHub.getAlbumsTitlesSortedByDate());
				SingletonFileLogger.getInstance().write(Level.INFO, "Displaying albums sorted by date.");

				albumTitle = scan.nextLine();
				try {
					System.out.println(theHub.getAlbumSongs(albumTitle));
					SingletonFileLogger.getInstance().write(Level.INFO, "Displaying song of " + albumTitle);

				} catch (NoAlbumFoundException ex) {
					System.out.println("No album found with the requested title " + ex.getMessage());
					SingletonFileLogger.getInstance().write(Level.ERROR,
							"No album found with the requested title " + ex.getMessage());
					SingletonConsoleLogger.getInstance().write(Level.ERROR,
							"No album found with the requested title " + ex.getMessage());
				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'u':
				// audiobooks ordered by author
				System.out.println(theHub.getAudiobooksTitlesSortedByAuthor());
				SingletonFileLogger.getInstance().write(Level.INFO, "Displaying audiobooks ordered by author.");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'c':
				// add a new song
				System.out.println("Enter a new song: ");
				System.out.println("Song title: ");
				String title = scan.nextLine();
				System.out.println("Song genre (jazz, classic, hiphop, rock, pop, rap):");
				String genre = scan.nextLine();
				System.out.println("Song artist: ");
				String artist = scan.nextLine();
				System.out.println("Song length in seconds: ");
				int length = Integer.parseInt(scan.nextLine());
				System.out.println("Song content: ");
				String content = scan.nextLine();
				Song s = new Song(title, artist, length, content, genre);
				theHub.addElement(s);
				System.out.println("New element list: ");
				Iterator<AudioElement> it = theHub.elements();
				while (it.hasNext())
					System.out.println(it.next().getTitle());
				SingletonFileLogger.getInstance().write(Level.INFO, "Song " + title + " created.");
				System.out.println("Song created!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'a':
				// add a new album
				System.out.println("Enter a new album: ");
				System.out.println("Album title: ");
				String aTitle = scan.nextLine();
				System.out.println("Album artist: ");
				String aArtist = scan.nextLine();
				System.out.println("Album length in seconds: ");
				int aLength = Integer.parseInt(scan.nextLine());
				System.out.println("Album date as YYYY-DD-MM: ");
				String aDate = scan.nextLine();
				Album a = new Album(aTitle, aArtist, aLength, aDate);
				theHub.addAlbum(a);
				System.out.println("New list of albums: ");
				Iterator<Album> ita = theHub.albums();
				while (ita.hasNext())
					System.out.println(ita.next().getTitle());
				SingletonFileLogger.getInstance().write(Level.INFO, "Album " + aTitle + " created.");
				System.out.println("Album created!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case '+':
				// add a song to an album:
				System.out.println("Add an existing song to an existing album");
				System.out.println("Type the name of the song you wish to add. Available songs: ");
				Iterator<AudioElement> itae = theHub.elements();
				while (itae.hasNext()) {
					AudioElement ae = itae.next();
					if (ae instanceof Song)
						System.out.println(ae.getTitle());
				}
				String songTitle = scan.nextLine();

				System.out.println("Type the name of the album you wish to enrich. Available albums: ");
				Iterator<Album> ait = theHub.albums();
				while (ait.hasNext()) {
					Album al = ait.next();
					System.out.println(al.getTitle());
				}
				String titleAlbum = scan.nextLine();
				try {
					theHub.addElementToAlbum(songTitle, titleAlbum);
					SingletonFileLogger.getInstance().write(Level.INFO,
							"Added " + songTitle + " to " + titleAlbum + ".");
				} catch (NoAlbumFoundException ex) {
					SingletonFileLogger.getInstance().write(Level.ERROR, ex.getMessage());
					SingletonConsoleLogger.getInstance().write(Level.ERROR, ex.getMessage());
				} catch (NoElementFoundException ex) {
					SingletonFileLogger.getInstance().write(Level.ERROR, ex.getMessage());
					SingletonConsoleLogger.getInstance().write(Level.ERROR, ex.getMessage());
				}
				System.out.println("Song added to the album!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'l':
				// add a new audiobook
				System.out.println("Enter a new audiobook: ");
				System.out.println("AudioBook title: ");
				String bTitle = scan.nextLine();
				System.out.println("AudioBook category (youth, novel, theater, documentary, speech)");
				String bCategory = scan.nextLine();
				System.out.println("AudioBook artist: ");
				String bArtist = scan.nextLine();
				System.out.println("AudioBook length in seconds: ");
				int bLength = Integer.parseInt(scan.nextLine());
				System.out.println("AudioBook content: ");
				String bContent = scan.nextLine();
				System.out.println("AudioBook language (french, english, italian, spanish, german)");
				String bLanguage = scan.nextLine();
				AudioBook b = new AudioBook(bTitle, bArtist, bLength, bContent, bLanguage, bCategory);
				theHub.addElement(b);
				SingletonFileLogger.getInstance().write(Level.INFO, "Audiobook " + bTitle + " created.");
				System.out.println("Audiobook created! New element list: ");
				Iterator<AudioElement> itl = theHub.elements();
				while (itl.hasNext())
					System.out.println(itl.next().getTitle());
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'p':
				// create a new playlist from existing elements
				System.out.println("Add an existing song or audiobook to a new playlist");
				System.out.println("Existing playlists:");
				Iterator<PlayList> itpl = theHub.playlists();
				while (itpl.hasNext()) {
					PlayList pl = itpl.next();
					System.out.println(pl.getTitle());
				}
				System.out.println("Type the name of the playlist you wish to create:");
				String playListTitle = scan.nextLine();
				PlayList pl = new PlayList(playListTitle);
				theHub.addPlaylist(pl);
				SingletonFileLogger.getInstance().write(Level.INFO, "Created playlist :" + playListTitle + ".");
				System.out.println("Available elements: ");

				Iterator<AudioElement> itael = theHub.elements();
				while (itael.hasNext()) {
					AudioElement ae = itael.next();
					System.out.println(ae.getTitle());
				}
				while (choice.charAt(0) != 'n') {
					System.out.println("Type the name of the audio element you wish to add or 'n' to exit:");
					String elementTitle = scan.nextLine();
					SingletonFileLogger.getInstance().write(Level.INFO,
							"Trying to add " + elementTitle + " to " + playListTitle + ".");

					try {
						theHub.addElementToPlayList(elementTitle, playListTitle);
						SingletonFileLogger.getInstance().write(Level.INFO,
								"Added " + elementTitle + " to " + playListTitle + ".");
					} catch (NoPlayListFoundException ex) {
						SingletonFileLogger.getInstance().write(Level.ERROR, ex.getMessage());
						SingletonConsoleLogger.getInstance().write(Level.ERROR, ex.getMessage());
					} catch (NoElementFoundException ex) {
						SingletonFileLogger.getInstance().write(Level.ERROR, ex.getMessage());
						SingletonConsoleLogger.getInstance().write(Level.ERROR, ex.getMessage());
					}

					System.out.println("Type y to add a new one, n to end");
					choice = scan.nextLine();
				}
				System.out.println("Playlist created!");
				SingletonFileLogger.getInstance().write(Level.INFO, "Playlist " + playListTitle + " created.");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case '-':
				// delete a playlist
				System.out.println("Delete an existing playlist. Available playlists:");
				Iterator<PlayList> itp = theHub.playlists();
				while (itp.hasNext()) {
					PlayList p = itp.next();
					System.out.println(p.getTitle());
				}
				String plTitle = scan.nextLine();
				SingletonFileLogger.getInstance().write(Level.INFO, "Trying to delete playlist :" + plTitle + ".");
				try {
					theHub.deletePlayList(plTitle);
					SingletonFileLogger.getInstance().write(Level.INFO, "Playlist " + plTitle + " deleted.");
				} catch (NoPlayListFoundException ex) {
					SingletonFileLogger.getInstance().write(Level.ERROR, ex.getMessage());
					SingletonConsoleLogger.getInstance().write(Level.ERROR, ex.getMessage());
				}
				System.out.println("Playlist deleted!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'n':
				// create a playlist with specific genre
				System.out.println("Type the name of the playlist you wish to create:");
				String playListTitleGenre = scan.nextLine();
				System.out.println("Type song genre (jazz, classic, hiphop, rock, pop, rap):");
				String genrePlaylist = scan.nextLine();

				PlayList plGenre = new PlayList(playListTitleGenre);
				theHub.addPlaylist(plGenre);

				Iterator<AudioElement> itaelGenre = theHub.elements();
				while (itaelGenre.hasNext()) {
					AudioElement ae = itaelGenre.next();
					if (ae instanceof Song) {
						if (((Song) ae).getGenre().contains(genrePlaylist)) {
							try {
								theHub.addElementToPlayList(ae.getTitle(), playListTitleGenre);
							} catch (NoPlayListFoundException | NoElementFoundException ex) {
								SingletonFileLogger.getInstance().write(Level.ERROR, ex.getMessage());
								SingletonConsoleLogger.getInstance().write(Level.ERROR, ex.getMessage());
							}

						}
					}
				}

				System.out.println("Playlist created");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 's':
				// save elements, albums, playlists
				theHub.saveElements();
				theHub.saveAlbums();
				theHub.savePlayLists();
				System.out.println("Elements, albums and playlists saved!");
				SingletonFileLogger.getInstance().write(Level.INFO, "Saved elements, albums, playlists.");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			default:

				break;
			}
		}
		scan.close();
	}

	/**
	 * function to print the available commands
	 */
	private static void printAvailableCommands() {
		System.out.println("r: listen to a music");
		System.out.println("n: create a playlist with a specific genre");
		System.out.println("t: display the album titles, ordered by date");
		System.out.println("g: display songs of an album, ordered by genre");
		System.out.println("d: display songs of an album");
		System.out.println("u: display audiobooks ordered by author");
		System.out.println("c: add a new song");
		System.out.println("a: add a new album");
		System.out.println("+: add a song to an album");
		System.out.println("l: add a new audiobook");
		System.out.println("p: create a new playlist from existing songs and audio books");
		System.out.println("-: delete an existing playlist");
		System.out.println("s: save elements, albums, playlists");
		System.out.println("q: quit program");
	}
}