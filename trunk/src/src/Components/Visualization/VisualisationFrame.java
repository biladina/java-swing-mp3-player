package Components.Visualization;

import javax.swing.JFrame;

public class VisualisationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1964206448766370941L;
	private VisualPanel vp = null;
	
	
	public VisualisationFrame(){
		super();
		initialize();
	}
	
	private void initialize(){
		//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);
		this.vp = new VisualPanel(this);
		this.setContentPane(vp);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VisualisationFrame vf = new VisualisationFrame();
		vf.setVisible(true);

	}
	

}
