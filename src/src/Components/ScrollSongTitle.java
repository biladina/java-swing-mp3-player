package Components;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.*;

/**
 * This class scrolls specified song title.
 * 
 * @author Danijel Predarski
 *
 */
public class ScrollSongTitle extends JPanel implements Runnable {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 8465120944094750717L;
	Timer counter;
	  int titlePosition = -1;
	  String title;
	  int titleWidth, titleHeight;
	  int charWidth;
	  int panelX;
	  int panelY;
	  Font titleFont;
	  FontMetrics fm;
	  
	  /**
	   * Creates a panel with scrolling title, X coord, Y coord, 
	   * width and height of the panel 
	   * 
	   * @param title song title
	   * @param panelX panel's X coord
	   * @param panelY panel's Y coord
	   * @param width width of panel
	   * @param height height panel 	
	   */
	  public ScrollSongTitle(String title, int panelX, int panelY, int width, int height) {
		  this.panelX = panelX;
		  this.panelY = panelY;
		  this.title = title;
		  this.setBounds(new Rectangle(panelX, panelY, width, height));
		  try {
			Font f = Font.createFont(0, new FileInputStream("resources/fonts/SUBWT.ttf"));
			titleFont = f.deriveFont(2, 20f);
			fm = getFontMetrics(titleFont);
			this.setFont(titleFont);
		  } catch (FontFormatException e) {
			  e.printStackTrace();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  titleWidth = fm.stringWidth(title);
		  titleHeight = fm.getAscent();
		  charWidth = fm.charWidth('l');
		  run();
	  }

      public void paintComponent(Graphics g)
      {
        super.paintComponent(g);
        g.drawString(title, getSize().width - titlePosition, getSize().height/2 + titleHeight/2);
      }

      /**
       * Sets song title
       * @param songTitle song title
       */
      public void setSongTitle(String songTitle) {
    	  this.title = songTitle;
    	  repaint();
      }
       /**
       * Scrolls song title
       */
	  public void run() {
		    if(counter == null) {
		      ActionListener al = new ActionListener() {
		        public void actionPerformed(ActionEvent e){
		        	titlePosition += charWidth/2;
		          if ((getSize().width - titlePosition) + titleWidth<0) {
		        	  titlePosition = -1;
		          }
		          repaint();
		        }  
		      };
		      counter = new Timer(100, al);
		      counter.start();
		    }else{
		    	counter.restart();
		    }	
	  }
}
