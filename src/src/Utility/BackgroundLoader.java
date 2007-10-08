package Utility;

import java.util.ArrayList;

import Components.Playlist.PLEditor;
import Entities.Song;



public class BackgroundLoader extends Thread {

	private PLEditor parent = null;
	
	public BackgroundLoader(PLEditor parent){
		this.parent = parent;
		start();
	}

	public void run() {
		ArrayList<String> filesForLoad;
		String songName;
		String filePath;
		while (true){
			filesForLoad = parent.getFilesForLoad();
			filePath = filesForLoad.get(0);
			filesForLoad.remove(0);
			songName = filesForLoad.get(0);
			filesForLoad.remove(0);
			try {
				parent.addSong(new Song(songName, filePath));
			} catch (Exception ex){
				System.out.print("Doslo je do neispravnosti ali idemo dalje :))");
			}
		}
	}
}
