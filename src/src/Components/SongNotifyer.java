package Components;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JWindow;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * This class is used to show song title in bottom of the screen.
 * 
 * @author Vojislav Pankovic
 *
 */
public class SongNotifyer extends JWindow {

	private static final long serialVersionUID = 1L;
	
	/**
	 * window is showing
	 */
	public static final int ME_SHOWING = 0;
	/**
	 * window is standing
	 */
	public static final int ME_STANDING = 1;
	/**
	 *  window is hiding
	 */
	public static final int ME_HIDING = 2;
	
	private Timer t;

	private JPanel jContentPane = null;
	
	public JLabel lblSong = null;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	
	
	/**
	 * this field indicates which mode is window in.
	 */
	private int animState = SongNotifyer.ME_STANDING;

	/**
	 * Initializes window and sets timer for animation.
	 * 
	 */
	public SongNotifyer() {		
		super();
		initialize();
		t = new Timer(75, al);
	}

	/**
	 * Initializes window.
	 * 
	 */
	private void initialize() {
		this.setSize(300, 100);
		this.setContentPane(getJContentPane());
		this.setAlwaysOnTop(true);
		this.setInitialPosition(ME_SHOWING);
		this.setVisible(true);
	}
	/**
	 * Sets window into initial position depending on animation state
	 * (showing or hiding).
	 * 
	 * @param state state of animation
	 */
	private void setInitialPosition(int state){
		if (state == ME_SHOWING){
			this.setLocation(screenSize.width - getWidth() - 20, screenSize.height);
		} else {
			this.setLocation(screenSize.width - getWidth() - 20, screenSize.height - getHeight() - 20);
		}
	}
	/**
	 * Sets components.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			lblSong = new JLabel();
			lblSong.setFont(new Font("Arial", Font.BOLD, 12));
			lblSong.setHorizontalTextPosition(SwingConstants.CENTER);
			lblSong.setHorizontalAlignment(SwingConstants.CENTER);
			lblSong.setBounds(0, 0, getWidth(), getHeight());
			
			jContentPane.add(lblSong);
		}
		return jContentPane;
	}

	ActionListener al = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (t.getDelay() == 2000){
            	if(animState != ME_STANDING){
            		animState = ME_STANDING;
            	} else {
            		t.setDelay(75);
            		hideMe();
            	}
            }
        	next();
        }
    };
    /**
     * Moves window up or down depending on animation state.
     *
     */
	private void next(){
		int x = this.getLocation().x;
		int y = this.getLocation().y;
		if (animState == ME_SHOWING){
    		y-= 5;
    		this.setLocation(x, y);
    		if (y < screenSize.height - getHeight() - 20){
    			t.setDelay(2000);
    		}
    	} else if (animState == ME_HIDING){
    		y += 5;
    		this.setLocation(x, y);
    		if (y > screenSize.height){
    			t.stop();
    			animState = ME_STANDING;
    		}
    	}
    }
	/**
	 * Shows message on screen (with appropriate animation)
	 * 
	 * @param message showing message
	 */
    public void showMe(String message){
    	this.lblSong.setText(message);
    	if ((animState != ME_STANDING)){
    		t.stop();
    	}
    	animState = ME_SHOWING;
    	setInitialPosition(ME_SHOWING);
    	t.start();
    }
    /**
     * Hides window after message is being shown
     *
     */
    public void hideMe(){
    	if(animState != ME_STANDING){
    		t.stop();
    	}
    	animState = ME_HIDING;
    	setInitialPosition(ME_HIDING);
    	t.start();
    }
}
