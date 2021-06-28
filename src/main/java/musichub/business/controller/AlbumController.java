package main.java.musichub.business.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import main.java.musichub.business.model.Album;
import main.java.musichub.business.model.AudioElement;
import main.java.musichub.exception.NoAlbumFoundException;
import main.java.musichub.exception.NoElementFoundException;
import main.java.musichub.util.XMLHandler;

public class AlbumController {

	private List<Album> albums;

	public static final String DIR = System.getProperty("user.dir");
	public static final String ALBUMS_FILE_PATH = DIR + "\\files\\albums.xml";

	private XMLHandler xmlHandler = new XMLHandler();

	public AlbumController() {
		albums = new LinkedList<Album>();
		this.loadAlbums();
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public Iterator<Album> albums() {
		return albums.listIterator();
	}

	/**
	 * function to load the albums
	 */
	private void loadAlbums() {
		NodeList albumNodes = xmlHandler.parseXMLFile(ALBUMS_FILE_PATH);
		if (albumNodes == null)
			return;

		for (int i = 0; i < albumNodes.getLength(); i++) {
			if (albumNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element albumElement = (Element) albumNodes.item(i);
				if (albumElement.getNodeName().equals("album")) {
					try {
						this.addAlbum(new Album(albumElement));
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML album element");
					}
				}
			}
		}
	}

	/**
	 * function to add an album
	 * @param album
	 */
	public void addAlbum(Album album) {
		albums.add(album);
	}

	/**
	 * function to add an element to an album
	 * @param elementTitle
	 * @param albumTitle
	 * @param elements
	 * @throws NoAlbumFoundException
	 * @throws NoElementFoundException
	 */
	public void addElementToAlbum(String elementTitle, String albumTitle, List<AudioElement> elements)
			throws NoAlbumFoundException, NoElementFoundException {
		Album theAlbum = null;
		int i = 0;
		boolean found = false;
		for (i = 0; i < albums.size(); i++) {
			if (albums.get(i).getTitle().toLowerCase().equals(albumTitle.toLowerCase())) {
				theAlbum = albums.get(i);
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
				theAlbum.addSong(theElement.getUUID());
				// replace the album in the list
				albums.set(i, theAlbum);
			} else
				throw new NoElementFoundException("Element " + elementTitle + " not found!");
		} else
			throw new NoAlbumFoundException("Album " + albumTitle + " not found!");

	}

	/**
	 * function to save an album
	 */
	public void saveAlbums() {
		Document document = xmlHandler.createXMLDocument();
		if (document == null)
			return;

		// root element
		Element root = document.createElement("albums");
		document.appendChild(root);

		// save all albums
		for (Iterator<Album> albumsIter = this.albums(); albumsIter.hasNext();) {
			Album currentAlbum = albumsIter.next();
			currentAlbum.createXMLElement(document, root);
		}
		xmlHandler.createXMLFile(document, ALBUMS_FILE_PATH);
	}

}
