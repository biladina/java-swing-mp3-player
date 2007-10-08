package Player;
import java.awt.event.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.*;

import Components.ScrollSongTitle;
import Components.SongNotifyer;
import Components.Playlist.PLEditor;
import Components.Visualization.VisualisationFrame;
import Entities.Settings;

import javax.swing.JMenuItem;

import Themes.GreenMetalTheme;
import Themes.KhakiMetalTheme;
import Themes.MetalThemeMenu;
import Themes.PurpleMetalTheme;
import Utility.LoadSkin;
import Utility.MusicPlayer;

/**
 * GUI for music reproduction
 * 
 * @author Danijel Predarski and Vojislav Pankovic
 *
 */
public class Player extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -866296202892520998L;
	/**
	 * normal song reproduction mode
	 */
	public static final int PLAY_NORMAL = 0;
	/**
	 * random song reproduction mode
	 */
	public static final int PLAY_SHUFFLE = 1;
	/**
	 * song reproduction without repeat mode
	 */
	public static final int REPEAT_OFF = 0;
	/**
	 * song reproduction mode that repeats selected song
	 */
	public static final int REPEAT_ONE= 1;
	/**
	 * song reproduction mode that repeats entire list
	 */
	public static final int REPEAT_ALL= 2;
	/**
	 * elapsed time mode
	 */
	public static final int TIME_ELAPSED = 0;
	/**
	 * remained time mode
	 */
	public static final int TIME_REMAINED = 1;
	/**
	 * total time mode
	 */
	public static final int TIME_TOTAL = 2;
	/**
	 * playlist showing
	 */	
	public boolean showPl = true;
	/**
	 * current song window notifyer
	 */
	public SongNotifyer sNotifyer = null;
	/**
	 * player width
	 */
	final int frameX = 360;
	/**
	 * player height
	 */
	final int frameY = 200;
	/**
	 * player always on top
	 */
	boolean onTop = true;
	/**
	 * player display
	 */	
	public JPanel display = new JPanel();
	/**
	 * song title panel
	 */
	public JPanel songTitlePanel = null;
	/**
	 * panel for sound volume components
	 */
	public JPanel volumePanel = new JPanel();
	/**
	 * time showing label
	 */
	public JLabel time = new JLabel("00:00");
	/**
	 * shuffle mode label
	 */
	public JLabel lblshuffle = new JLabel();
	/**
	 * label thad displays character "-" in coresponding time mode
	 */
	public JLabel lblMinus = new JLabel("-");
	/**
	 * repeat mode label
	 */
	public JLabel lblrepeat = new JLabel();
	/**
	 * time mode label
	 */
	public JLabel lbltimeMode = new JLabel();

	// set playing mode
	public int playingMode = Player.PLAY_NORMAL;
	//set repeat mode
	public int repeatMode = Player.REPEAT_OFF;
	
	public boolean DRAGGING = false;
	
	private PLEditor ple = null;
	private MusicPlayer mp3 = null;
	private JSlider slider = null;	
	private JProgressBar progressBar = null;	
	private int timeMode = Player.TIME_ELAPSED;	
	private JButton btnPlay, btnPause, btnStop, btnNext, btnPrevious, btnRepeat, btnShuffle, showList;	
	private final String ABOUTMSG = "Java Swing Mp3 Player \n \nAplikacija namenjena za pustanje \nMPEG layer 3 kompresovanih audio fajlova \n \n " 
						 			+ "Autori:\n Predarski Danijel,   e-mail: danijel_2@yahoo.com\n Vojislav Pankovic,   e-mail: ludja85@gmail.com";
	private JMenuItem menuJump = null;
	private JMenuItem menuQueue = null;
	private JMenuItem menuFileInfo = null;
	
	private VisualisationFrame visualFrame = null;
	
	private boolean showVisual = false;
	
	public Settings settings = null;
	private JMenuItem menuArhive = null;
	

/**
 * Creates player with initialized playlist, labels and panels
 *
 */
	public Player() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.settings = new Settings();
        settings.Load("/resources/settings/settings.txt");
        this.setResizable (false);       
        this.setLayout(null);
        this.setSize(frameX, frameY);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/images/icon.jpg"));
        this.setTitle("VODA Player");
        this.setFocusable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.getWidth()) / 3,
                 (screenSize.height - this.getHeight()) / 3);
        initiatePlayList();
        buildMenu();
		buildButtonBar();			
		buildPanel();		
		buildLabels();
		buildTimeLabel();
		buildVolumePanel();

		addMouseListener(new MouseListener() {			
			public void mouseClicked(MouseEvent arg0) {}

			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}

			public void mousePressed(MouseEvent arg0) {}
			// when clicked on a player, activate playlist window
			// if that option is on
			public void mouseReleased(MouseEvent arg0) {
				getPlayList().setVisible(showPl);
				getVisualisation().setVisible(showVisual);
			}
		});	
		
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			//if player is moved move playlist window
			public void componentMoved(java.awt.event.ComponentEvent e) {
					getPlayList().setLocation(getLocation().x, getLocation().y + getHeight());
					getVisualisation().setLocation(getLocation().x + getWidth(), getLocation().y);
					if (showPl){
						getPlayList().setVisible(showPl);
					}
			}
		});
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			// If player is closed save current state
			public void windowClosing(java.awt.event.WindowEvent e) {
				getPlayList().saveTmpList();
			}
			// Iconifying player window iconifies playlist window
			public void windowIconified(WindowEvent arg0) {
				getPlayList().setVisible(false);
				getVisualisation().setVisible(false);
			}
			// Deiconifying player window deiconifies playlist window
			public void windowDeiconified(WindowEvent arg0) {
				getPlayList().setVisible(showPl);
				getVisualisation().setVisible(showVisual);
			}			
		});
		
//		this.addFocusListener(new java.awt.event.FocusAdapter() {
//			public void focusGained(java.awt.event.FocusEvent e) {
//					System.out.println("Focus gained");
//					getPlayList().setVisible(showPl);
//					getVisualisation().setVisible(showVisual);
//			}
//		});

	}
	/**
	 * Main menu initialization
	 */
	private void buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		JMenu file = buildFileMenu();
		JMenu play = buildPlayMenu();
		JMenu options = buildOptionsMenu();
		JMenu view = buildViewMenu();
		JMenu about = buildAboutMenu();
		menuBar.add(file);
		menuBar.add(play);
		menuBar.add(options);
		menuBar.add(view);
		menuBar.add(about);
		setJMenuBar(menuBar);
	}
	/**
	 * File menu initialization
	 * 
	 */
	private JMenu buildFileMenu() {
		JMenu file = new JMenu("File");
		JMenuItem playFile = new JMenuItem("Play file...                           ");
		playFile.setAccelerator(KeyStroke.getKeyStroke("L"));
		playFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				getPlayList().showAddFileDialog();
			}
		});
		
		JMenuItem playFolder = new JMenuItem("Play folder...");
		playFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.SHIFT_MASK));
		playFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				getPlayList().showAddDirDialog();
			}
		});
		
		JMenuItem openPlaylist = new JMenuItem("Open playlist");
		openPlaylist.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				getPlayList().showLoadListDialog();
			}
		});

		JMenuItem savePlaylist = new JMenuItem("Save playlist");
		savePlaylist.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		savePlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				getPlayList().showSaveListDialog();
			}
		});

		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		file.add(playFile);
		file.add(playFolder);
		file.addSeparator();
		file.add(openPlaylist);
		file.add(savePlaylist);
		file.addSeparator();
		file.add(getMenuFileInfo());
		file.addSeparator();
		file.add(exit);
		return file;
	}
	/**
	 * Play menu initialization
	 * 
	 */
	private JMenu buildPlayMenu() {
		JMenu menyPlay = new JMenu("Play");
		
		JMenuItem prev = new JMenuItem("Previous               ");
		prev.setAccelerator(KeyStroke.getKeyStroke("Z"));
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnPrevious.doClick();
			}
		});
		
		JMenuItem itemPlay = new JMenuItem("Play");
		itemPlay.setAccelerator(KeyStroke.getKeyStroke("X"));
		itemPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnPlay.doClick();
			}
		});
		
		JMenuItem pause = new JMenuItem("Pause");
		pause.setAccelerator(KeyStroke.getKeyStroke("C"));
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnPause.doClick();
			}
		});

		JMenuItem stop = new JMenuItem("Stop");
		stop.setAccelerator(KeyStroke.getKeyStroke("V"));
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnStop.doClick();
			}
		});
		
		JMenuItem next = new JMenuItem("Next");
		next.setAccelerator(KeyStroke.getKeyStroke("B"));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnNext.doClick();
			}
		});

		JMenuItem repeat = new JMenuItem("Repeat");
		repeat.setAccelerator(KeyStroke.getKeyStroke("R"));
		repeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnRepeat.doClick();
			}
		});

		JMenuItem shuffle = new JMenuItem("Shuffle");
		shuffle.setAccelerator(KeyStroke.getKeyStroke("S"));
		shuffle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnShuffle.doClick();
			}
		});

		JMenuItem volumeUp = new JMenuItem("Volume Up");
		volumeUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		volumeUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int val = getSliderVolume().getValue();
				getSliderVolume().setValue(val + 5);
			}
		});
	
		JMenuItem volumeDown = new JMenuItem("Volume Down");
		volumeDown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		volumeDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int val = getSliderVolume().getValue();
				getSliderVolume().setValue(val - 5);
			}
		});
		
		menyPlay.add(prev);
		menyPlay.add(itemPlay);
		menyPlay.add(pause);
		menyPlay.add(stop);
		menyPlay.add(next);
		menyPlay.addSeparator();
		menyPlay.add(repeat);
		menyPlay.add(shuffle);
		menyPlay.addSeparator();
		menyPlay.add(volumeUp);
		menyPlay.add(volumeDown);
		menyPlay.add(getMenuJump());
		menyPlay.add(getMenuQueue());
		menyPlay.add(getMenuArhive());
		return menyPlay;
	}	
	/**
	 * Options menu initialization
	 * 
	 */
	private JMenu buildOptionsMenu() {
		JMenu options = new JMenu("Options");
		// creating skin array
	    MetalTheme[] themes = { new OceanTheme(),
	                            new PurpleMetalTheme(),
	                            new GreenMetalTheme(),
								new KhakiMetalTheme(),
								};

		// put skins in menu
		JMenu skins = new MetalThemeMenu("Skins          ", themes, this, ple);				
		skins.addSeparator();		
		
		// load skin from file
		JMenuItem skinBrowser = new JMenuItem ("Skin Browser");
		skinBrowser.addActionListener(new LoadSkin(this));

    	JMenuItem alwaysOnTop = new JMenuItem("Always On Top");
		alwaysOnTop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		alwaysOnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				onTop = !onTop;
				setAlwaysOnTop(onTop);
			}
		});
		
		skins.add(skinBrowser);
		options.add(skins);		
		options.add(alwaysOnTop);
		return options;
	}
	/**
	 * View menu initialization
	 * 
	 */	
	private JMenu buildViewMenu() {
		JMenu view = new JMenu("View");
		final JCheckBoxMenuItem playListEditor = new JCheckBoxMenuItem("Playlist Editor         ");
		playListEditor.setSelected(true);
		playListEditor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		playListEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showList.doClick();
			}
		});
		final JCheckBoxMenuItem visualisationFrame = new JCheckBoxMenuItem("Visualisation ");
		visualisationFrame.setSelected(false);
		visualisationFrame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
		visualisationFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showVisual = visualisationFrame.getState();
				getVisualisation().setVisible(showVisual);
			}
		});
		view.add(playListEditor);
		view.add(visualisationFrame);
		
		return view;
		
	}
	
	/**
	 * About menu initialization
	 * 
	 */
	private JMenu buildAboutMenu() {
		JMenu meni = new JMenu("Info");
		JMenuItem about = new JMenuItem("About...               ");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showAboutBox();
			}
		});
		meni.add(about);
		return meni;
	}
	/**
	 * Shows autors info
	 * 
	 */
	private void showAboutBox() {
		JOptionPane.showMessageDialog(null, ABOUTMSG);
	}
	/**
	 * Button bar initialization
	 * 
	 */
	private void buildButtonBar() {
		JPanel buttonBar = new JPanel();
		buttonBar.setBounds(0, 105, 170, 40);
		buttonBar.setLayout(new GridLayout());
		btnPrevious = new JButton();
		btnPrevious.setIcon(new ImageIcon("resources/images/previous.gif"));
		btnPrevious.setRolloverIcon(new ImageIcon("resources/images/previousFocus.gif"));
		btnPrevious.setFocusPainted(false);
		btnPrevious.setBorderPainted(false);
		btnPrevious.setBackground(this.getBackground());
		btnPrevious.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				playPrew();
			}
		});
		
		btnPlay = new JButton();
		btnPlay.setIcon(new ImageIcon("resources/images/play.gif"));
		btnPlay.setRolloverIcon(new ImageIcon("resources/images/playFocus.gif"));
		btnPlay.setFocusPainted(false);
		btnPlay.setBorderPainted(false);
		btnPlay.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				getMp3().resume();
			}
		});

		btnPause = new JButton();
		btnPause.setIcon(new ImageIcon("resources/images/pause.gif"));
		btnPause.setRolloverIcon(new ImageIcon("resources/images/pauseFocus.gif"));
		btnPause.setFocusPainted(false);
		btnPause.setBorderPainted(false);
		btnPause.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				getMp3().pause();
			}
		});
		
		btnStop = new JButton();
		btnStop.setIcon(new ImageIcon("resources/images/stop.gif"));
		btnStop.setRolloverIcon(new ImageIcon("resources/images/stopFocus.gif"));
		btnStop.setFocusPainted(false);
		btnStop.setBorderPainted(false);	
		btnStop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				getMp3().stop();
			}
		});
		
		btnNext = new JButton();
		btnNext.setIcon(new ImageIcon("resources/images/next.gif"));
		btnNext.setRolloverIcon(new ImageIcon("resources/images/nextFocus.gif"));
	    btnNext.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnNext.setFocusPainted(false);
		btnNext.setBorderPainted(false);
		btnNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				playNext();
			}
		});
				
		btnRepeat = new JButton ();
		btnRepeat.setBounds(300, 10, 50, 35);
		btnRepeat.setIcon(new ImageIcon("resources/images/repeat0.gif"));
		btnRepeat.setRolloverIcon(new ImageIcon("resources/images/repeatFocus0.gif"));
		btnRepeat.setFocusPainted(false);
		btnRepeat.setBorderPainted(false);		
		btnRepeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				repeatMode++;
				if (repeatMode > 2) {
					repeatMode = 0;
					setRepeatMode("REPEAT OFF", MetalLookAndFeel.getPrimaryControlDarkShadow());
				}else if (repeatMode == 1) {
					setRepeatMode("REPEAT ONE", MetalLookAndFeel.getControlHighlight());
				}else {
					setRepeatMode("REPEAT ALL", MetalLookAndFeel.getControlHighlight());
				}
				btnRepeat.setIcon(new ImageIcon("resources/images/repeat" + repeatMode + ".gif"));
				btnRepeat.setRolloverIcon(new ImageIcon("resources/images/repeatFocus" + repeatMode + ".gif"));
				System.out.println("Repeat mode : " + repeatMode);
			}
		});

		btnShuffle = new JButton ();
		btnShuffle.setBounds(300, 45, 50, 35);
		btnShuffle.setIcon(new ImageIcon("resources/images/pictureShuffle" + playingMode +".gif"));
		btnShuffle.setRolloverIcon(new ImageIcon("resources/images/pictureShuffleFocus" + playingMode +".gif"));
		btnShuffle.setFocusPainted(false);
		btnShuffle.setBorderPainted(false);
		btnShuffle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (playingMode == PLAY_SHUFFLE){
					playingMode = PLAY_NORMAL;
					setShuffleMode("SHUFFLE OFF", MetalLookAndFeel.getPrimaryControlDarkShadow());
				} else {
					playingMode = PLAY_SHUFFLE;
					setShuffleMode("SHUFFLE ON", MetalLookAndFeel.getControlHighlight());
				}
				btnShuffle.setIcon(new ImageIcon("resources/images/pictureShuffle" + playingMode +".gif"));
				btnShuffle.setRolloverIcon(new ImageIcon("resources/images/pictureShuffleFocus" + playingMode +".gif"));
				System.out.println("Playing mode: " + playingMode);
			}
		});
		
		showList = new JButton();
		showList.setBounds(327, 130, 20, 10);
		showList.setFont(new Font ("Arial", 0, 8));
		showList.setMargin(new Insets(0,0,0,0));
		showList.setText("\u25B2");
		showList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				showPl = !showPl;
				getPlayList().setVisible(showPl);
				if (showPl) {
					showList.setText("\u25B2");
				}else {
					showList.setText("\u25BC");
				}
			}
		});

		buttonBar.add(btnPrevious);
		buttonBar.add(btnPlay);
		buttonBar.add(btnPause);
		buttonBar.add(btnStop);
		buttonBar.add(btnNext);
		getContentPane().add(buttonBar);
		getContentPane().add(getProgress());				
		getContentPane().add(btnRepeat);
		getContentPane().add(btnShuffle);
		getContentPane().add(showList);
	}
	
	/**
	 * Builds player display
	 *
	 */
	private void buildPanel() {
		display.setLayout(null);
		display.setBorder(BorderFactory.createLoweredBevelBorder());
		display.setBounds(10, 10, 290, 70);
		display.setBackground(MetalLookAndFeel.getPrimaryControl());		
		buildSongTitlePanel(display);
		getContentPane().add(display);
	}
	
	/**
	 * Builds volume panel with mute button on it
	 *
	 */
	private void buildVolumePanel() {		
		volumePanel.setLayout(null);
		volumePanel.setBorder(BorderFactory.createLoweredBevelBorder());
		volumePanel.setBounds(175, 105, 150, 37);		
		volumePanel.add(getSliderVolume());

		JButton mute = new JButton ();
		mute.setBounds(2, 2, 43, 33);
		mute.setBackground(volumePanel.getBackground());
		mute.setIcon(new ImageIcon("resources/images/muteButton.gif"));
		mute.setRolloverIcon(new ImageIcon("resources/images/muteButtonFocus.gif"));
		mute.setFocusPainted(false);
		mute.setBorderPainted(false);
		mute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				slider.setValue(0);
			}
		});
		
		volumePanel.add(mute);
		getContentPane().add(volumePanel);
	}
	
	/**
	 * Builds scrolling title panel and adds it to player display
	 * @param panel
	 */
	private void buildSongTitlePanel(JPanel panel) {
		songTitlePanel = new ScrollSongTitle("", 0, 50, 288, 20);
		songTitlePanel.setBackground(MetalLookAndFeel.getPrimaryControlDarkShadow());
		songTitlePanel.setForeground(MetalLookAndFeel.getPrimaryControlHighlight());
		panel.add(songTitlePanel);	
	}
	
	/**
	 * Builds display labels for time, repeat and shuffle mode
	 *
	 */
	private void buildLabels() {
		lbltimeMode.setFont(new Font("Impact", 0, 12));
		lbltimeMode.setBounds(50, 15, 100, 30);
		lbltimeMode.setText("ELAPSED");
		lbltimeMode.setForeground(MetalLookAndFeel
				.getPrimaryControlDarkShadow());
		lbltimeMode.setHorizontalTextPosition(JLabel.CENTER);
		lblMinus.setBounds(103, 20, 50, 20);
		lblMinus.setVisible(false);
		try {
			Font font = Font.createFont(0, new FileInputStream(
					"resources/fonts/DIGITALDREAMSKEWNARROW.ttf"));
			Font f = font.deriveFont(23f);
			lblMinus.setFont(f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lblrepeat.setFont(new Font("Impact", 0, 12));
		setRepeatMode("REPEAT OFF", MetalLookAndFeel
				.getPrimaryControlDarkShadow());
		lblrepeat.setBounds(200, 10, 100, 30);
		lblshuffle.setFont(new Font("Impact", 0, 12));
		setShuffleMode("SHUFFLE OFF", MetalLookAndFeel
				.getPrimaryControlDarkShadow());
		lblshuffle.setBounds(200, 25, 100, 30);
		display.add(lblMinus);
		display.add(lbltimeMode);
		display.add(lblrepeat);
		display.add(lblshuffle);
	}

	/**
	 * This metod writes repeat mode changes on a label
	 * 
	 * @param s mode name
	 * @param primaryControlDarkShadow text foreground color
	 */
	public void setRepeatMode(String s, ColorUIResource primaryControlDarkShadow) {
		lblrepeat.setText(s);
		lblrepeat.setForeground(primaryControlDarkShadow);
	}
	/**
	 * This metod writes shuffle mode changes on a label
	 * 
	 * @param string mode name
	 * @param primaryControlDarkShadow text foreground color
	 */
	public void setShuffleMode(String string, ColorUIResource primaryControlDarkShadow) {
		lblshuffle.setText(string);
		lblshuffle.setForeground(primaryControlDarkShadow);
	}
	/**
	 * Returns panel containing song title
	 * 
	 * @return
	 */
	public JPanel getSongTitlePanel(){
		return songTitlePanel;
	}
	/**
	 * Returns progress bar. Progress bar is refreshed
	 * with new values during reproduction
	 * 
	 * @return progress bar
	 */
	public JProgressBar getProgress(){
		if (this.progressBar == null){
			progressBar = new JProgressBar();
			progressBar.setBorder(BorderFactory.createLoweredBevelBorder());
			progressBar.setBounds(10, 90, 330, 10);
			progressBar.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					int percent = (int)((float)e.getX() / progressBar.getWidth() * 100);
					progressBar.setValue(percent);
					getMp3().seek(percent);
				}
				
				public void mouseReleased(MouseEvent e) {
					int percent = (int)((float)e.getX() / progressBar.getWidth() * 100);
					progressBar.setValue(percent);
					getMp3().seek(percent);	
					DRAGGING = false;
				}
				
			});
			progressBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseDragged(java.awt.event.MouseEvent e) {
					int percent = (int)((float)e.getX() / progressBar.getWidth() * 100);
					progressBar.setValue(percent);
					DRAGGING = true;
				}
			});
			
		}
		return progressBar;
	}
	/**
	 * Returns slider for sound volume control
	 * 
	 * @return jslider
	 */
	public JSlider getSliderVolume(){
		if(this.slider == null){
			slider = new JSlider();
			slider.setMinimum(0);
			slider.setMaximum(100);
			slider.setBounds(45, 3, 100, 32);
			slider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					getMp3().setVolume(slider.getValue());
				}
			});
		}
		return slider;
	}
	/**
	 * Initializes playlist
	 * 
	 */
	private void initiatePlayList(){
		this.ple = new PLEditor();
		this.ple.setSize(settings.playlistW, settings.playlistH);
		ple.setLocation(this.getLocation().x, this.getLocation().y + this.getHeight());
		ple.setPlayer(getMp3());
		ple.setParent(this);
		ple.setVisible(true);
	}
	
	/**
	 * Returns music player
	 * 
	 * @return music player
	 */
	public MusicPlayer getMp3(){
		if(this.mp3 == null){
			this.mp3 = new MusicPlayer(this);
		}
		return this.mp3;
	}
	/**
	 * Returns playlist editor
	 * 
	 * @return playlist editor
	 */
	public PLEditor getPlayList(){
		if (this.ple == null){
			initiatePlayList();
		} 
		return ple;
	}
	/**
	 * Plays next song
	 *
	 */
	public void playNext(){
		if (getPlayList().selectNext()){
			getMp3().resume();
		} else {
			getMp3().stop();
		}
	}	
	/**
	 * Plays previous song
	 *
	 */
	public void playPrew(){
		if (getPlayList().selectPrew()){
			getMp3().resume();
		} else {
			getMp3().stop();
		}
	}	
	/**
	 * Initializes time label. Changes value on label each time
	 * time label is clicked.
	 * 
	 */
	public void buildTimeLabel() {
		time.setBounds(118, 10, 100, 40);
		try {
			Font font = Font.createFont(0, new FileInputStream("resources/fonts/DIGITALDREAMFATSKEWNARROW.ttf"));
			Font f = font.deriveFont(22f);
			time.setFont(f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final String array[] = {"ELAPSED", "REMAINED", "DURATION"};
		time.addMouseListener(new MouseListener() {			
			public void mouseClicked(MouseEvent arg0) {}

			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}

			public void mousePressed(MouseEvent arg0) {}

			public void mouseReleased(MouseEvent arg0) {
				timeMode++;
				if (timeMode > 2) {
					timeMode = 0;
				}
				lbltimeMode.setText(array[timeMode]);
			}
			
		});	
		display.add(time);
	}
	/**
	 * Writes time on time label (elapsed or remained)
	 * 
	 * @param elapsed time elapsed from the begining of song
	 * @param remain time remained to the end of song
	 */
	public void writeTimes(String elapsed, String remain){							 
		if (timeMode == TIME_ELAPSED) {
			time.setText(elapsed);
			lblMinus.setVisible(false);
		}else if (timeMode == TIME_REMAINED) {
			time.setText(remain);
			lblMinus.setVisible(true);
		}else {
			time.setText(getMp3().getTotalTime());
			lblMinus.setVisible(false);
		}		
	}
	/**
	 * Creates song notifyer
	 * 
	 * @return song notifyer
	 */
	public SongNotifyer getSongNotifyer(){
		if (this.sNotifyer == null){
			this.sNotifyer = new SongNotifyer();
			sNotifyer.getContentPane().setBackground(MetalLookAndFeel.getPrimaryControlDarkShadow());
			sNotifyer.lblSong.setForeground(MetalLookAndFeel.getControlHighlight());
		}
		return this.sNotifyer;
	}
	private VisualisationFrame getVisualisation(){
		if(visualFrame == null){
			visualFrame = new VisualisationFrame();
			visualFrame.setLocation(this.getLocation().x + this.getWidth(), this.getLocation().y);
		}
		return visualFrame;
	}
	/**
	 * Shows changed song info
	 *
	 */
	public void showPlayingSong(String songName){
		getSongNotifyer().showMe(songName);
	}
	/**
	 * This method initializes menuJump	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuJump() {
		if (menuJump == null) {
			menuJump = new JMenuItem("Jump to file");
			menuJump.setAccelerator(KeyStroke.getKeyStroke("J"));
			menuJump.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPlayList().showSearchDialog();
				}
			});
		}
		return menuJump;
	}
	
	/**
	 * This method initializes menuFileInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuFileInfo() {
		if (menuFileInfo == null) {
			menuFileInfo = new JMenuItem("Show file info");
			menuFileInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, KeyEvent.ALT_DOWN_MASK));
			menuFileInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPlayList().showFilwInfoDialog();
				}
			});
		}
		return menuFileInfo;
	}

	/**
	 * This method initializes menuQueue	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuQueue() {
		if (menuQueue == null) {
			menuQueue = new JMenuItem("Queue file");
			menuQueue.setAccelerator(KeyStroke.getKeyStroke("Q"));
			menuQueue.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPlayList().enqueueSelSong();
				}
			});
		}
		return menuQueue;
	}
	/**
	 * This method initializes menuArhive	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuArhive() {
		if (menuArhive == null) {
			menuArhive = new JMenuItem("Arhive songs");
			menuArhive.setAccelerator(KeyStroke.getKeyStroke("H"));
			menuArhive.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPlayList().showArhiveDialog();
				}
			});
		}
		return menuArhive;
	}
	/**
	 * Main metod
	 * 
	 * @param args
	 */
	public static void main (String args []) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		Player player = new Player();		
        player.setVisible(true);
	}
}
