package main.java.musichub.business.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import main.java.musichub.business.NoAlbumFoundException;
import main.java.musichub.business.model.Album;
import main.java.musichub.business.model.AudioElement;
import main.java.musichub.business.model.Song;
import main.java.musichub.util.SortByDate;
import main.java.musichub.util.SortByGenre;

public class AlbumView {

	public List<AudioElement> getAlbumSongs(String albumTitle, List<Album> albums, List<AudioElement> elements)
			throws NoAlbumFoundException {
		Album theAlbum = null;
		ArrayList<AudioElement> songsInAlbum = new ArrayList<AudioElement>();
		for (Album al : albums) {
			if (al.getTitle().toLowerCase().equals(albumTitle.toLowerCase())) {
				theAlbum = al;
				break;
			}
		}
		if (theAlbum == null)
			throw new NoAlbumFoundException("No album with this title in the MusicHub!");

		List<UUID> songIDs = theAlbum.getSongs();
		for (UUID id : songIDs)
			for (AudioElement el : elements) {
				if (el instanceof Song) {
					if (el.getUUID().equals(id))
						songsInAlbum.add(el);
				}
			}
		return songsInAlbum;
	}

	public String getAlbumsTitlesSortedByDate(List<Album> albums) {
		StringBuffer titleList = new StringBuffer();
		Collections.sort(albums, new SortByDate());
		for (Album al : albums)
			titleList.append(al.getTitle() + "\n");
		return titleList.toString();
	}

	public List<Song> getAlbumSongsSortedByGenre(String albumTitle, List<Album> albums, List<AudioElement> elements)
			throws NoAlbumFoundException {
		Album theAlbum = null;
		ArrayList<Song> songsInAlbum = new ArrayList<Song>();
		for (Album al : albums) {
			if (al.getTitle().toLowerCase().equals(albumTitle.toLowerCase())) {
				theAlbum = al;
				break;
			}
		}
		if (theAlbum == null)
			throw new NoAlbumFoundException("No album with this title in the MusicHub!");

		List<UUID> songIDs = theAlbum.getSongs();
		for (UUID id : songIDs)
			for (AudioElement el : elements) {
				if (el instanceof Song) {
					if (el.getUUID().equals(id))
						songsInAlbum.add((Song) el);
				}
			}
		Collections.sort(songsInAlbum, new SortByGenre());
		return songsInAlbum;

	}

}
