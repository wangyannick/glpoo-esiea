package musichub.business.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import musichub.business.model.AudioBook;
import musichub.business.model.AudioElement;
import musichub.business.model.Song;
import musichub.util.XMLHandler;

public class AudioElementController {

	private List<AudioElement> elements;

	public static final String DIR = System.getProperty("user.dir");
	public static final String ELEMENTS_FILE_PATH = DIR + "\\files\\elements.xml";
	private XMLHandler xmlHandler = new XMLHandler();

	public AudioElementController() {
		elements = new LinkedList<AudioElement>();
		this.loadElements();
	}
	
	public List<AudioElement> getAudioElements() {
		return elements;
	}

	public void addElement(AudioElement element) {
		elements.add(element);
	}

	private void loadElements() {
		NodeList audioelementsNodes = xmlHandler.parseXMLFile(ELEMENTS_FILE_PATH);
		if (audioelementsNodes == null)
			return;

		for (int i = 0; i < audioelementsNodes.getLength(); i++) {
			if (audioelementsNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element audioElement = (Element) audioelementsNodes.item(i);
				if (audioElement.getNodeName().equals("song")) {
					try {
						AudioElement newSong = new Song(audioElement);
						this.addElement(newSong);
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML song element");
					}
				}
				if (audioElement.getNodeName().equals("audiobook")) {
					try {
						AudioElement newAudioBook = new AudioBook(audioElement);
						this.addElement(newAudioBook);
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML audiobook element");
					}
				}
			}
		}
	}

	public void saveElements() {
		Document document = xmlHandler.createXMLDocument();
		if (document == null)
			return;

		// root element
		Element root = document.createElement("elements");
		document.appendChild(root);

		// save all AudioElements
		Iterator<AudioElement> elementsIter = elements.listIterator();
		while (elementsIter.hasNext()) {

			AudioElement currentElement = elementsIter.next();
			if (currentElement instanceof Song) {
				((Song) currentElement).createXMLElement(document, root);
			}
			if (currentElement instanceof AudioBook) {
				((AudioBook) currentElement).createXMLElement(document, root);
			}
		}
		xmlHandler.createXMLFile(document, ELEMENTS_FILE_PATH);
	}

}
