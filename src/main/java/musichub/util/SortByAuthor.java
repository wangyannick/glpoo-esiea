package main.java.musichub.util;

import java.util.Comparator;

import main.java.musichub.business.model.AudioElement;

public class SortByAuthor implements Comparator<AudioElement> {
	public int compare(AudioElement e1, AudioElement e2) {
		return e1.getArtist().compareTo(e2.getArtist());
	}
}
