package Utility;

import java.io.File;

import Components.ArhiveDialog;

public class ArhiveLoader extends Thread {
	
	private String arhivePath;
	private ArhiveDialog parent;
	private int totalFiles = 0;
	private long totalSize = 0;
	
	public ArhiveLoader(String arhivePath, ArhiveDialog parent){
		super();
		this.arhivePath = arhivePath;
		this.parent = parent;
		start();
	}

	@Override
	public void run() {
		File f = new File(arhivePath);
		loadArhive(f);
		System.out.println("Total files " + totalFiles);
		System.out.println("Total size " + totalSize);
	}

	private void loadArhive(File folder){
		File [] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()){
				loadArhive(file);
			} else {
				totalFiles ++;
				totalSize += file.length();
			}
		}
		
	}
}
