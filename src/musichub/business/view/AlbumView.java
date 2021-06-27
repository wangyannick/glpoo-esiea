package musichub.business.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import musichub.business.NoAlbumFoundException;
import musichub.business.model.Album;
import musichub.business.model.AudioElement;
import musichub.business.model.Song;
import musichub.util.SortByDate;
import musichub.util.SortByGenre;

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
