package musichub.main;

import musichub.business.*;
import musichub.business.model.Album;
import musichub.business.model.AudioBook;
import musichub.business.model.AudioElement;
import musichub.business.model.PlayList;
import musichub.business.model.Song;

import musichub.util.*;

import java.io.IOException;
import java.util.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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

		while (choice.charAt(0) != 'q') {
			switch (choice.charAt(0)) {
			case 'h':
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'r':
				// On rentre la musique qu'on veut jouer
				System.out.println("Choose the music you want to play");
				musicTitle = scan.nextLine();
				try {
					theHub.readElement(musicTitle);
					System.out.println("Would you like to rate the music you listened to ? y/n");
					answer = scan.nextLine();
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
				}

				// une fois musique finie demander de la noter ici
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 't':
				// album titles, ordered by date
				System.out.println(theHub.getAlbumsTitlesSortedByDate());
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'g':
				// songs of an album, sorted by genre
				System.out.println(
						"Songs of an album sorted by genre will be displayed; enter the album name, available albums are:");
				System.out.println(theHub.getAlbumsTitlesSortedByDate());

				albumTitle = scan.nextLine();
				try {
					System.out.println(theHub.getAlbumSongsSortedByGenre(albumTitle));
				} catch (NoAlbumFoundException ex) {
					System.out.println("No album found with the requested title " + ex.getMessage());
				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'd':
				// songs of an album
				System.out.println("Songs of an album will be displayed; enter the album name, available albums are:");
				System.out.println(theHub.getAlbumsTitlesSortedByDate());

				albumTitle = scan.nextLine();
				try {
					System.out.println(theHub.getAlbumSongs(albumTitle));
				} catch (NoAlbumFoundException ex) {
					System.out.println("No album found with the requested title " + ex.getMessage());
				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'u':
				// audiobooks ordered by author
				System.out.println(theHub.getAudiobooksTitlesSortedByAuthor());
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
				String rate = "";
				Song s = new Song(title, artist, length, content, genre, rate);
				theHub.addElement(s);
				System.out.println("New element list: ");
				Iterator<AudioElement> it = theHub.elements();
				while (it.hasNext())
					System.out.println(it.next().getTitle());
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
				} catch (NoAlbumFoundException ex) {
					System.out.println(ex.getMessage());
				} catch (NoElementFoundException ex) {
					System.out.println(ex.getMessage());
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
				String bRate = "";
				AudioBook b = new AudioBook(bTitle, bArtist, bLength, bContent, bLanguage, bCategory, bRate);
				theHub.addElement(b);
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
				System.out.println("Available elements: ");

				Iterator<AudioElement> itael = theHub.elements();
				while (itael.hasNext()) {
					AudioElement ae = itael.next();
					System.out.println(ae.getTitle());
				}
				while (choice.charAt(0) != 'n') {
					System.out.println("Type the name of the audio element you wish to add or 'n' to exit:");
					String elementTitle = scan.nextLine();
					try {
						theHub.addElementToPlayList(elementTitle, playListTitle);
					} catch (NoPlayListFoundException ex) {
						System.out.println(ex.getMessage());
					} catch (NoElementFoundException ex) {
						System.out.println(ex.getMessage());
					}

					System.out.println("Type y to add a new one, n to end");
					choice = scan.nextLine();
				}
				System.out.println("Playlist created!");
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
				try {
					theHub.deletePlayList(plTitle);
				} catch (NoPlayListFoundException ex) {
					System.out.println(ex.getMessage());
				}
				System.out.println("Playlist deleted!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 's':
				// save elements, albums, playlists
				theHub.saveElements();
				theHub.saveAlbums();
				theHub.savePlayLists();
				System.out.println("Elements, albums and playlists saved!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			default:

				break;
			}
		}
		scan.close();
	}

	private static void printAvailableCommands() {
		System.out.println("r: listen to a music");
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