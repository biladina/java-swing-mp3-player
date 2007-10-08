package FileInfo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;


import javax.swing.JPanel;
import javax.swing.JDialog;

import Components.PlayerDialog;
import Entities.Song;
import Player.Player;

public class FileInfoDialog extends PlayerDialog implements LoadInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8873134247192099031L;
	private JPanel jContentPane = null;
	private FileInfoPanel fileInfoPanel = null;
	//private Player parent = null;

	/**
	 * This is the default constructor
	 */
	public FileInfoDialog(Player owner) {
		super(owner);
		//this.parent = owner;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.setSize(574, 369);
		this.setContentPane(getJContentPane());
		SetLocation();
		SetClosing();
	}

	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getFileInfoPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes fileInfoPanel	
	 * 	
	 * @return FileInfo.FileInfoPanel	
	 */
	private FileInfoPanel getFileInfoPanel() {
		if (fileInfoPanel == null) {
			fileInfoPanel = new FileInfoPanel();
			fileInfoPanel.setBounds(new java.awt.Rectangle(1,5,564,329));
		}
		return fileInfoPanel;
	}

	public void LoadBasicInfo(Song s) {
		getFileInfoPanel().LoadBasicInfo(s);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
