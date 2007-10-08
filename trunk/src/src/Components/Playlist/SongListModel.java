package Components.Playlist;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import Entities.Song;

/**
 * ListModel used by song list
 *
 * @author Vojislav Pankovic
 *
 */
public class SongListModel extends DefaultListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5417523483515360936L;
	static final int SWAP_DOWN = 0;
	static final int SWAP_UP = 1;
	
	private volatile ArrayList<Song> elem;
	
	private ArrayList<String> subSet;
	
	public ArrayList<String> queue;
	
	public ArrayList<Integer> queueIndex;
	
	
	/**
	 * Constructs song list model with specified array list
	 * @param al list of songs
	 */
	public SongListModel(ArrayList<Song> al){
		super();
		this.elem = al;
		this.subSet = new ArrayList<String>();
		this.queue = new ArrayList<String>();
		this.queueIndex = new ArrayList<Integer>();
	}
	/**
	 * Returns element at required position
	 * 
	 * @param index index of song that is returned
	 */
	public Object getElementAt(int index) {
		if (this.elem != null){
			Song s = elem.get(index);
			return s;
		} 
		return null;
	}
	/**
	 * Number of list elements
	 */
	public int getSize() {
		return this.elem.size();
	}
	/**
	 * Adding the element in a list
	 */
	public void addElement(Song s) {
		super.addElement(s);
		this.elem.add(s);
	}
	/**
	 * Removing the elemtnt from a specified index
	 */
	public void removeElementAt(int index) {
		Object obj = elem.get(index);
		super.remove(index);
		elem.remove(obj);
		fireIntervalRemoved(obj, index, index);
	}
	
	@Override
	public boolean removeElement(Object song) {
		super.removeElement(song);
		return elem.remove(song);
	}
	
	/**
	 * Swaping the elements from a list
	 * @param index1
	 * @param index2
	 */
	public void swapElements(int index1, int index2){
		Song temp = elem.get(index1);
		elem.set(index1, elem.get(index2));
		elem.set(index2, temp);
		fireContentsChanged(elem.get(index1), index1, index1);
		fireContentsChanged(elem.get(index2), index2, index2);
	}
	/**
	 * Setting the elemetn on specified index
	 */
	public void setElementAt(Song song, int index) {
		super.setElementAt(song, index);
		elem.set(index, song);
		fireContentsChanged(song, index, index);

	}
	/**
	 * Returns an array of elements that contain specified
	 * substring
	 * @param criteria substring contained by the list elements
	 * @return
	 */
	public ArrayList getElementsWhere(String criteria){
		this.subSet.clear();
		Song s;
		criteria = criteria.toLowerCase();
		String songName;
		String songPath;
		for (int i = 0; i < elem.size(); i++){
			s = elem.get(i);
			songName = s.getSongName().toLowerCase();
			songPath = s.getFilePath().toLowerCase();
			if ((songName.indexOf(criteria) != -1) || (songPath.indexOf(criteria) != -1)){
				subSet.add(s.getSongName());
			}
		}
		return subSet;
	}
	/**
	 * Retunrs index of specified song
	 * @param song
	 * @return
	 */
	public int indexOfSong(String song) {
		for (int i = 0; i < elem.size(); i++){
			if (elem.get(i).getSongName() == song){
				return i;
			}
		}
		return -1;
	}
	/**
	 * Put a song to a queue 
	 * @param value
	 */
	public void enqueue(Song value){
		this.queue.add(value.toString());
		this.queueIndex.add(indexOfSong(value.toString()));
	}
	/**
	 * Gets a song from a queue
	 * @return
	 */
	public String dequeue(){
		if (this.queue.size() != 0){
			String rez = this.queue.get(0).toString();
			this.queue.remove(0);
			this.queueIndex.remove(0);
			return rez;
		} 
		return null;
	}
}
