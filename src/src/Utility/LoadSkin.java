package Utility;
import javax.swing.*;
import javax.swing.plaf.metal.*;

import Player.Player;
import Themes.PropertiesMetalTheme;

import Components.Playlist.PLEditor;

import java.awt.event.*;
import java.io.*;

/**
 * This class loads skin from a file on a hard disk.
 * It opens file chooser dialog and allows user to select external skin.
 * After selecting a file it applies skin to player and its components, and playlist.
 * If an error occures while loading skin, error message is shown.
 * 
 * @author Danijel Predarski
 *
 */
public class LoadSkin implements ActionListener {
	String filename = null;
	Player p;
	MetalTheme myTheme = null;
	PLEditor ple;
	
	/**
	 * 
	 * 
	 * @param p owner player
	 */
	public LoadSkin(Player p){
		this.p = p;
		this.ple = p.getPlayList();
	}
		
	//Creates a file pointing to default skin directory.
    // Opens file chooser for external skin selection
	 	
	public void actionPerformed(ActionEvent e) {		
		File file = new File("resources/skins/SampleSkin.skin");
		JFileChooser chooser = new JFileChooser(file);
		int rVal = chooser.showOpenDialog(null);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				filename = chooser.getSelectedFile().getName();
				setNewSkin();
			}
	}

	/**
	 * Loads skin from file and applies it to player.
	 * Also changes color of player display, song title panel, 
	 * display labels and playlist.
	 * 
	 */
	private void setNewSkin() {
		try {
		    InputStream istream = getClass().getResourceAsStream("resources/skins/"+filename);
		    myTheme =  new PropertiesMetalTheme(istream);
		    MetalLookAndFeel.setCurrentTheme(myTheme);						//apply it to player
		    try {
		    	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		    } catch (Exception ex) {
		        System.out.println("Failed loading Metal");
		        System.out.println(ex);
		    }
		    
		    SwingUtilities.updateComponentTreeUI(p);		//refresh parent frame 
		    SwingUtilities.updateComponentTreeUI(ple); 
		    p.display.setBackground(myTheme.getPrimaryControl());    
		     p.songTitlePanel.setBackground(myTheme.getPrimaryControlDarkShadow());
		     p.songTitlePanel.setForeground(myTheme.getControlHighlight());
		     p.lblrepeat.setForeground(myTheme.getPrimaryControlDarkShadow());
		     p.lblshuffle.setForeground(myTheme.getPrimaryControlDarkShadow());     
		     ple.getJContentPane().setBorder(BorderFactory.createLineBorder(MetalLookAndFeel.getPrimaryControlDarkShadow(), 5));
		     ple.getListSongs().setBackground(myTheme.getPrimaryControlDarkShadow());
		     ple.getListSongs().setForeground(myTheme.getControlHighlight());

		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Error loading skin!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
