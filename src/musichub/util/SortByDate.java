package musichub.util;

import java.util.Comparator;

import musichub.business.model.Album;

public class SortByDate implements Comparator<Album> {
	public int compare(Album a1, Album a2) {
		return a1.getDate().compareTo(a2.getDate());
	}
}
