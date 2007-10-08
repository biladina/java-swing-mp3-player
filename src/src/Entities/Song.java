package Entities;
/**
 * Song reprezentation
 * 
 * @author Vojislav Pankovic
 *
 */
public class Song {

	private String songName;

	private String filePath;
	
	private String songTime;
	/**
	 * Creating new song with specified song name, and path
	 * @param songName - song name
	 * @param filePath - song location
	 */
	public Song(String songName, String filePath){
		this.songName = songName;
		this.filePath = filePath;
		this.songTime = "";
	}
	/**
	 * Returns song path
	 * @return song path
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * Sets song path
	 * @param filePath location
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * Returns song name
	 * @return song name
	 */
	public String getSongName() {
		return songName;
	}
	/**
	 * Sets song name
	 * @param songName song name
	 */
	public void setSongName(String songName) {
		this.songName = songName;
	}
	/**
	 * Returns song duration
	 * @return
	 */
	public String getSongTime() {
		return songTime;
	}
	/**
	 * Sets song duration 
	 * @param songTime
	 */
	public void setSongTime(String songTime) {
		this.songTime = songTime;
	}
	@Override
	public String toString() {
		return this.songName;
	}
	
	
}
