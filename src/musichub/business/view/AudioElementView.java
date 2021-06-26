package musichub.business.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import musichub.business.model.AudioBook;
import musichub.business.model.AudioElement;
import musichub.util.SortByAuthor;

public class AudioElementView {

	public String getAudiobooksTitlesSortedByAuthor(List<AudioElement> elements) {
		StringBuffer titleList = new StringBuffer();
		List<AudioElement> audioBookList = new ArrayList<AudioElement>();
		for (AudioElement ae : elements)
			if (ae instanceof AudioBook)
				audioBookList.add(ae);
		Collections.sort(audioBookList, new SortByAuthor());
		for (AudioElement ab : audioBookList)
			titleList.append(ab.getArtist() + "\n");
		return titleList.toString();
	}

}
