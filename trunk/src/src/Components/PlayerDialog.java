package Components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.ItemSelectable;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ComponentSampleModel;

import javax.swing.JDialog;

import Player.Player;

public abstract class PlayerDialog extends JDialog{

	
	protected Player parent;
	
	public PlayerDialog(Player owner){
		super(owner);
		this.parent = owner;
	}
	
	protected void SetLocation(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.getWidth()) / 2,
                 (screenSize.height - this.getHeight()) / 2);
	}
	
	protected void SetClosing(){
		AddListenerToComponent(getContentPane());
	}
	
	private void AddListenerToComponent(Component component){
		component.addKeyListener(this.closer);
		if ( ! (component instanceof ItemSelectable)) {
			Component [] members = ( (Container) component).getComponents();
			for (Component component2 : members) {
				AddListenerToComponent(component2);
			}
		} 
	}
	
	private KeyListener closer = new KeyListener() {
		public void keyTyped(KeyEvent arg0) {
			
		}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				setVisible(false);
				parent.setVisible(true);
			}
		}

		public void keyReleased(KeyEvent arg0) {
			
		}
		
	};

	protected abstract void initialize();
}
