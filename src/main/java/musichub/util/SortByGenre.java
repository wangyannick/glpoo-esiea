package main.java.musichub.util;

import java.util.Comparator;

import main.java.musichub.business.model.Song;

/**
 * @author ali
 *	function that sorts songs by genre
 */
public class SortByGenre implements Comparator<Song> {
	public int compare(Song s1, Song s2) {
		return s1.getGenre().compareTo(s2.getGenre());
	}
}

