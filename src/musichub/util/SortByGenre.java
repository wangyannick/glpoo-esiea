package musichub.util;

import java.util.Comparator;

import musichub.business.model.Song;

public class SortByGenre implements Comparator<Song> {
	public int compare(Song s1, Song s2) {
		return s1.getGenre().compareTo(s2.getGenre());
	}
}

