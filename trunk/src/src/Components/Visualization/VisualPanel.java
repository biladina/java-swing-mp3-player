package Components.Visualization;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VisualPanel extends JPanel implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8798649108314295412L;
	private JFrame parent = null;
	private int w, h;
	private float curX, curY;
	
	private BufferedImage imgScreen = null;
	private BufferedImage imgDancer = null;
	
	private GeneralPath line1 = null;
	private GeneralPath line2 = null;
	
	private GradientPaint gradient1 = null;
	private GradientPaint gradient2 = null;
	private int directionFactor = 1;
	
	private Thread thread;
	
	private final int num_frames = 30;
	private final int sleeping_time = 100;
	private final int tpf = 1;
	private int cur_frame = 0;
	private int cur_rep = 0;
	private int clearing_interval = 40;
	private int clear = 0;
	private int frame_delta = 1;
	
	private Color c1;
	private Color c2;
	private Color c3 = Color.WHITE;
	
	
	private boolean clearing = false;
	
	
	private Image [] frames = new Image[num_frames];
	
	
	public VisualPanel(JFrame parent){
		this.parent = parent;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(parent.getSize());
		this.w = parent.getSize().width;
		this.h = parent.getSize().height;
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				w = parent.getSize().width;
				h = parent.getSize().height;
				imgScreen = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				line1 = createRandomLine();
				line2 = createRandomLine();
			}
		});
		this.imgScreen = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		setGradients();
		line1 = createRandomLine();
		line2 = createRandomLine();
		loadFrames();
		Image frame1 = frames[0];
		this.imgDancer = new BufferedImage(frame1.getWidth(null), frame1.getHeight(null), BufferedImage.TYPE_INT_RGB);
		start();
	}
	
	private void loadFrames(){
		File f;
		for (int i = 1; i <= num_frames; i++){
			try {
				f = new File("resources/images/visualisation/nomber/Movie_" + i + ".gif");
				frames[i - 1] = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setGradients() {
		this.gradient1 = new GradientPaint(0,0,Color.blue,50,50,Color.white, true);
		this.gradient2 = new GradientPaint(0,0,Color.green,50,50,Color.white, true);
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		if (this.imgScreen != null){
			drawImages(imgDancer);
			drawLines(imgScreen);
			
			rotateLines(10);
			g2.drawImage(imgScreen, 0, 0, null);
			
			g2.drawImage(imgDancer, 0, 0, null);
		}
	}
	
	private void drawImages(BufferedImage image){
		Graphics2D g2 = (Graphics2D)image.getGraphics();

		if(frames[cur_frame] != null){
						
			g2.clearRect(0, 0, imgDancer.getWidth(), imgDancer.getHeight());
			g2.drawImage(imgScreen.getSubimage(0, 0, imgDancer.getWidth(), imgDancer.getHeight()), 0, 0, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(frames[cur_frame], 0, 0, null);
			
			g2.drawImage(imgDancer, 0, 0, null);
		}
		
	}
	private void drawLines(BufferedImage image){
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		
		if(line1 != null && line2 != null){
			//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
			g2.setPaint(gradient1);
			g2.draw(line1);
			g2.setPaint(gradient2);
			g2.draw(line2);
			
		}
		
	}
	
	private void rotateLines(double angle){
		line1.transform(AffineTransform.getRotateInstance(degToRad(angle), w / 2, h / 2));
		line2.transform(AffineTransform.getRotateInstance(degToRad(-angle), w / 2, h / 2));
	}
	
	private GeneralPath createRandomLine(){
	  curX = 0;
	  curY = w / 2;
	  GeneralPath gp = new GeneralPath(GeneralPath.WIND_NON_ZERO);	  
	  gp.moveTo(curX, curY);
      for (; curX < this.getSize().width; ){
    	  directionFactor *= -1;
    	  curX += (Math.random() * 10);
    	  curY = 200 + directionFactor * (float)(Math.random() * 50);
    	  gp.lineTo(curX, curY);
      }
      return gp;
	}
	
	private void clearScreen(){
		clearing = ! clearing;

		if (clearing){
			c1 = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
			c2 = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
			c3 = Color.WHITE;
		} else {
			c1 = Color.BLACK;
			c2 = Color.BLACK;
			c3 = Color.BLACK;
		}
		this.gradient1 = new GradientPaint(0,0,c1,50,50,c3, true);
		this.gradient2 = new GradientPaint(0,0,c2,50,50,c3, true);
		
	}
	
	private double degToRad(double degree){
		return (degree * Math.PI) / 180;
	}
 
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		}
	}

	public synchronized void stop() {
		if (thread != null) {
			thread.interrupt();
		}
		thread = null;
		notifyAll();
	}

	public void run() {

		Thread me = Thread.currentThread();

		while (thread == me && !isShowing() || getSize().width == 0) {
			try {
				Thread.sleep(sleeping_time);
			} catch (InterruptedException e) {
			}
		}

		while (thread == me) {
			repaint();
			cur_rep++;
			cur_rep = cur_rep % tpf;
			if (cur_rep == 0){
				cur_frame+= frame_delta;
				cur_frame = cur_frame % num_frames;
				if ((cur_frame == 0) || (cur_frame == num_frames - 1)){
					frame_delta *= -1;
				}
			}
			clear++;
			clear = clear % clearing_interval;
			if(clear == 0){
				clearScreen();
			}
			try {
				Thread.sleep(sleeping_time);
			} catch (InterruptedException e) {
			}
		}
		thread = null;
	}
}
