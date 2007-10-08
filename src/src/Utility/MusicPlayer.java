package Utility;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import Entities.Song;
import Player.Player;
import Components.ScrollSongTitle;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
/**
 * This class implements reproduction of MP3 audio files 
 * 
 * @author Vojislav Pankovic
 *
 */
public class MusicPlayer implements BasicPlayerListener{
	
	private Player parent = null;
	
	private BasicPlayer player = null;
	
	private BasicController control = null;
	
	private MpegAudioFileReader mpegFileReader = null;
	// song length in bites
	private double songLength = 0;
	// song length in seconds
	private int songTime;
	// da li je preskakano
	private boolean seeked = false;
	
	
	/**
	 * Creates music player with specified parent component 
	 * @param parent parent component
	 */
	public MusicPlayer(Player parent){
		this.parent = parent;
		// class for playing
		this.player = new BasicPlayer();
		// reading MP3 file informacion
		this.mpegFileReader = new MpegAudioFileReader();
		this.control = (BasicController) player;
		// registering events that BasicPlayer implements		
		player.addBasicPlayerListener(this);
	}
	/**
	 * Plays song on specified location
	 * @param filename song location
	 */
	public void play(String filename){
		try
		{			
			if (player.getStatus() == BasicPlayer.PLAYING){
				control.stop();
			}

			File f = new File(filename);
			String time = mpegFileReader.getAudioFileFormat(f).properties().get("duration").toString();
			// reading song length in seconds
			this.songTime = Integer.parseInt(time) / 1000000;
			// postavaljamo vreme pesme u listi
			Song s = parent.getPlayList().getSelectedSong();
			if (s.getSongTime() == ""){
				updateSongTime(s, parent.getPlayList().getListSongs().getSelectedIndex());
			}
			
			
			// reading length in bites
			songLength = f.length();
			// opening file
			control.open(f);
			// start of reproduction
			control.play();
			// setting sound volume
			control.setGain((float)parent.getSliderVolume().getValue() / 100);

			control.setPan(0.0);
		}
		catch (BasicPlayerException e)
		{
			JOptionPane.showMessageDialog(null, "Player can not play selected file", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedAudioFileException e) {
			JOptionPane.showMessageDialog(null, "Player can not play selected file", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Player can not play selected file", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	/*
	 * Refreshing time in list
	 */
	private void updateSongTime(Song s, int index){
		s.setSongTime(timeToString(this.songTime));
		parent.getPlayList().songListModel.setElementAt(s, index);
	}
	/**
	 * Changes sound volume
	 * @param value volume value (0-100)
	 */
	public void setVolume(int value){
		if (player.getStatus() == BasicPlayer.PLAYING){
			try {
				control.setGain((double)value / 100);
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Pauses reproduction
	 *
	 */
	public void seek(long bytes){
		try {
			long val = (long)(songLength * ((float)bytes / 100));
			((BasicPlayer)control).seek(val);
			seeked = true;
			setVolume(parent.getSliderVolume().getValue());
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	public void pause(){
		if(player.getStatus() == BasicPlayer.PLAYING){
			try {
				control.pause();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Continues reproduction
	 *
	 */
	public void resume(){
		if(player.getStatus() == BasicPlayer.PAUSED){
			try {
				control.resume();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		} else {
			Song s = parent.getPlayList().getSelectedSong();
			// int index = parent.getPlayList().getListSongs().getSelectedIndex();
			if (s != null){
				// start reproduction
				play(s.getFilePath());
				// showing song
				((ScrollSongTitle)parent.getSongTitlePanel()).setSongTitle(s.getSongName());
				// information about current song
				parent.showPlayingSong(s.getSongName());
			}
		}
	}
	/**
	 * Stops reproduction
	 *
	 */
	public void stop(){
		if (player.getStatus() == BasicPlayer.PLAYING){	
			try {
				control.stop();
				parent.getProgress().setValue(0);
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Event that occures while opening song
	 */
	public void opened(Object stream, Map properties) {
	}
	/**
	 * Event that occures each time buffer reads new array from 
	 * stream. It provides information about read bytes length, 
	 * microseconds that passed from the begining of reproduction...
	 * 
	 */
	public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
		
		if (seeked){
			microseconds = (long)(bytesread / songLength * songTime * 1000000);
		}
		// calculating song progress
		float percentage = (float)bytesread / (float)songLength * 100.0f;
		// refreshing progress bar
		if (!parent.DRAGGING){
			this.parent.getProgress().setValue(((int)percentage));
		}
		// elapsed time calculating
		int sec = (int)((float)microseconds / 1000000f + 0.5f);
		// passing information about elapsed time
		parent.writeTimes(timeToString(sec), timeToString(songTime - sec));
	}
	/**
	 * Event that occures each time player state is changed
	 * (opened song, stoped, continued, end of song...)
	 */
	public void stateUpdated(BasicPlayerEvent event) {
		// if song came to an end, play next
		if (event.getCode() == BasicPlayerEvent.EOM){
			parent.playNext();
		}
	}
	/**
	 * Controler setting
	 */
	public void setController(BasicController arg0) {
	
	}
	/**
	 * Converts seconds into desirable format min:sec
	 * 
	 * @param time number of seconds
	 * @return time in format min:sec
	 */
	private String timeToString(int time){
		String rez = "";
		String min = time / 60 + "";
		if (min.length() == 1){
			min = "0" + min;
		}
		rez += min;
		rez += ":";
		min = time % 60 + "";
		if (min.length() == 1){
			min = "0" + min;
		}
		rez += min;
		return rez;
	}
	/**
	 * Returns song duration
	 * @return song duration
	 */
	public String getTotalTime(){
		return timeToString(this.songTime);
	}

}
