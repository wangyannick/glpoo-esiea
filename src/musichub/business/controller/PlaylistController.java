package musichub.business.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import musichub.business.NoElementFoundException;
import musichub.business.NoPlayListFoundException;
import musichub.business.model.AudioElement;
import musichub.business.model.PlayList;
import musichub.util.XMLHandler;

public class PlaylistController {
	private List<PlayList> playlists;

	private XMLHandler xmlHandler = new XMLHandler();

	public static final String DIR = System.getProperty("user.dir");
	public static final String PLAYLISTS_FILE_PATH = DIR + "\\files\\playlists.xml";

	public PlaylistController() {
		playlists = new LinkedList<PlayList>();
		this.loadPlaylists();
	}

	public List<PlayList> getPlaylists() {
		return playlists;
	}

	public void addPlaylist(PlayList playlist) {
		playlists.add(playlist);
	}

	public void deletePlayList(String playListTitle) throws NoPlayListFoundException {

		PlayList thePlayList = null;
		boolean result = false;
		for (PlayList pl : playlists) {
			if (pl.getTitle().toLowerCase().equals(playListTitle.toLowerCase())) {
				thePlayList = pl;
				break;
			}
		}

		if (thePlayList != null)
			result = playlists.remove(thePlayList);
		if (!result)
			throw new NoPlayListFoundException("Playlist " + playListTitle + " not found!");
	}

	public Iterator<PlayList> playlists() {
		return playlists.listIterator();
	}

	public void addElementToPlayList(String elementTitle, String playListTitle, List<AudioElement> elements)
			throws NoPlayListFoundException, NoElementFoundException {
		PlayList thePlaylist = null;
		int i = 0;
		boolean found = false;

		for (i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().toLowerCase().equals(playListTitle.toLowerCase())) {
				thePlaylist = playlists.get(i);
				found = true;
				break;
			}
		}

		if (found == true) {
			AudioElement theElement = null;
			for (AudioElement ae : elements) {
				if (ae.getTitle().toLowerCase().equals(elementTitle.toLowerCase())) {
					theElement = ae;
					break;
				}
			}
			if (theElement != null) {
				thePlaylist.addElement(theElement.getUUID());
				// replace the album in the list
				playlists.set(i, thePlaylist);
			} else
				throw new NoElementFoundException("Element " + elementTitle + " not found!");

		} else
			throw new NoPlayListFoundException("Playlist " + playListTitle + " not found!");

	}

	private void loadPlaylists() {
		NodeList playlistNodes = xmlHandler.parseXMLFile(PLAYLISTS_FILE_PATH);
		if (playlistNodes == null)
			return;

		for (int i = 0; i < playlistNodes.getLength(); i++) {
			if (playlistNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element playlistElement = (Element) playlistNodes.item(i);
				if (playlistElement.getNodeName().equals("playlist")) {
					try {
						this.addPlaylist(new PlayList(playlistElement));
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML playlist element");
					}
				}
			}
		}
	}

	public void savePlayLists() {
		Document document = xmlHandler.createXMLDocument();
		if (document == null)
			return;

		// root element
		Element root = document.createElement("playlists");
		document.appendChild(root);

		// save all playlists
		for (Iterator<PlayList> playlistsIter = this.playlists(); playlistsIter.hasNext();) {
			PlayList currentPlayList = playlistsIter.next();
			currentPlayList.createXMLElement(document, root);
		}
		xmlHandler.createXMLFile(document, PLAYLISTS_FILE_PATH);
	}
}
