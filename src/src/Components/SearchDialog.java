package Components;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;


import javax.swing.JDialog;

import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


import javax.swing.JTextField;
import javax.swing.JButton;

import javax.swing.border.TitledBorder;

import Components.Playlist.PLEditor;
import Components.Playlist.SongListModel;
import Entities.Song;

import Player.Player;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

public class SearchDialog extends PlayerDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	
	// private Player parent;
	
	private JList searchedList = null;
	
	private JScrollPane scrollPaneSongs = null;
	
	private PLEditor pleditor = null;
	
	private SongListModel originalListModel = null;

	private JTextField txtCriteria = null;

	private JButton btnEnqueue = null;

	private JButton btnPlay = null;

	private JPanel pnlSearchTxt = null;

	private JButton btnMode = null;

	private JPanel pnlQueueMode = null;  //  @jve:decl-index=0:visual-constraint="510,187"

	private JButton Up = null;

	private JButton btnDown = null;

	private JButton btnRemove = null;

	private JButton SwitchMode = null;

	private JScrollPane jScrollQueue = null;

	private JList jListQueue = null;

	/**
	 * @param owner
	 */
	public SearchDialog(Player owner) {
		super(owner);
		// this.parent = owner;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.pleditor = parent.getPlayList(); 
		this.searchedList = new JList();
		//this.searchedList.setNextFocusableComponent(getTxtCriteria());
		// check if ENTER is pressed
		this.searchedList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					playSelectedSong();
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					setVisible(false);
					parent.setVisible(true);
				}
			}
		});
		this.originalListModel = pleditor.songListModel; 
		fillSearchList(getTxtCriteria().getText().trim());
		this.setSize(415, 340);
		SetLocation();
		SetClosing();
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowActivated(java.awt.event.WindowEvent e) {
				getTxtCriteria().setText("");
				getJContentPane().transferFocus();
			}
		});
	}

	/**
	 * Fills the list of songs by passed criteria
	 */
	private void fillSearchList(String criteria){
		searchedList.setListData(originalListModel.getElementsWhere(criteria).toArray());
		searchedList.setSelectedIndex(0);
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
			jContentPane.add(getScrollPaneSongs(), null);
			jContentPane.add(getBtnEnqueue(), null);
			jContentPane.add(getBtnPlay(), null);
			jContentPane.add(getPnlSearchTxt(), null);
			jContentPane.add(getBtnMode(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes scrollPaneSongs	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollPaneSongs() {
		if (scrollPaneSongs == null) {
			scrollPaneSongs = new JScrollPane();
			scrollPaneSongs.setBounds(new Rectangle(10, 74, 386, 177));
			scrollPaneSongs.setViewportView(this.searchedList);
			scrollPaneSongs.setBorder(BorderFactory.createTitledBorder(null, "Results", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));

		}
		return scrollPaneSongs;
	}

	/**
	 * This method initializes txtCriteria	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtCriteria() {
		if (txtCriteria == null) {
			txtCriteria = new JTextField();
			txtCriteria.setBounds(new Rectangle(6, 18, 379, 30));
			txtCriteria.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					fillSearchList(txtCriteria.getText().trim());
				}
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						playSelectedSong();
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN){
						searchedList.getParent().transferFocus();
						System.out.println("Usao sam u ovaj metod");
					}
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
						setVisible(false);
						parent.setVisible(true);
					}
				}
			});
		}
		return txtCriteria;
	}
	/**
	 * 
	 *
	 */
	private void playSelectedSong(){
		int index = originalListModel.indexOfSong(searchedList.getModel().getElementAt(searchedList.getSelectedIndex()).toString());
		pleditor.playSongAt(index);
		setVisible(false);
		parent.setVisible(true);
		
	}

	/**
	 * This method initializes btnEnqueue	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEnqueue() {
		if (btnEnqueue == null) {
			btnEnqueue = new JButton();
			btnEnqueue.setBounds(new Rectangle(135, 260, 115, 25));
			btnEnqueue.setText("Enqueue");
			btnEnqueue.setMnemonic('q');
			btnEnqueue.setFocusable(false);
			btnEnqueue.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Song song = (Song)searchedList.getSelectedValue();
					originalListModel.enqueue(song);
				}
			});
		}
		return btnEnqueue;
	}

	/**
	 * This method initializes btnPlay	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPlay() {
		if (btnPlay == null) {
			btnPlay = new JButton();
			btnPlay.setBounds(new Rectangle(10, 260, 115, 25));
			btnPlay.setText("Play");
			btnPlay.setFocusable(false);
		}
		return btnPlay;
	}

	/**
	 * This method initializes pnlSearchTxt	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlSearchTxt() {
		if (pnlSearchTxt == null) {
			pnlSearchTxt = new JPanel();
			pnlSearchTxt.setLayout(null);
			pnlSearchTxt.setBounds(new Rectangle(10, 10, 390, 55));
			pnlSearchTxt.setBorder(BorderFactory.createTitledBorder(null, "Search text", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			pnlSearchTxt.add(getTxtCriteria(), null);
		}
		return pnlSearchTxt;
	}

	/**
	 * This method initializes btnMode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnMode() {
		if (btnMode == null) {
			btnMode = new JButton();
			btnMode.setText("Swich mode");
			btnMode.setBounds(new Rectangle(260, 260, 129, 25));
			btnMode.setFocusable(false);
			btnMode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJListQueue().repaint();
					setContentPane(getPnlQueueMode());
				}
			});
		}
		return btnMode;
	}

	/**
	 * This method initializes pnlQueueMode	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlQueueMode() {
		if (pnlQueueMode == null) {
			pnlQueueMode = new JPanel();
			pnlQueueMode.setLayout(null);
			pnlQueueMode.setBounds(getJContentPane().getBounds());
			pnlQueueMode.add(getUp(), null);
			pnlQueueMode.add(getBtnDown(), null);
			pnlQueueMode.add(getBtnRemove(), null);
			pnlQueueMode.add(getSwitchMode(), null);
			pnlQueueMode.add(getJScrollQueue(), null);
		}
		return pnlQueueMode;
	}

	/**
	 * This method initializes Up	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getUp() {
		if (Up == null) {
			Up = new JButton();
			Up.setBounds(new Rectangle(10, 260, 80, 25));
			Up.setText("Up");
		}
		return Up;
	}

	/**
	 * This method initializes btnDown	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDown() {
		if (btnDown == null) {
			btnDown = new JButton();
			btnDown.setBounds(new Rectangle(100, 260, 80, 25));
			btnDown.setText("Down");
		}
		return btnDown;
	}

	/**
	 * This method initializes btnRemove	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton();
			btnRemove.setBounds(new Rectangle(190, 260, 80, 25));
			btnRemove.setText("Remove");
		}
		return btnRemove;
	}

	/**
	 * This method initializes SwitchMode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSwitchMode() {
		if (SwitchMode == null) {
			SwitchMode = new JButton();
			SwitchMode.setBounds(new Rectangle(280, 260, 110, 25));
			SwitchMode.setText("Switch mode");
			SwitchMode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setContentPane(getJContentPane());
				}
			});
		}
		return SwitchMode;
	}

	/**
	 * This method initializes jScrollQueue	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollQueue() {
		if (jScrollQueue == null) {
			jScrollQueue = new JScrollPane();
			jScrollQueue.setBounds(new Rectangle(12, 15, 375, 230));
			jScrollQueue.setBorder(BorderFactory.createTitledBorder(null, "Queued songs", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jScrollQueue.setViewportView(getJListQueue());
		}
		return jScrollQueue;
	}

	/**
	 * This method initializes jListQueue	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListQueue() {
		if (jListQueue == null) {
			jListQueue = new JList();
		}
		jListQueue.setListData(originalListModel.queue.toArray());
		return jListQueue;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
