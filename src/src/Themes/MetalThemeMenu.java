package Themes;
import javax.swing.plaf.metal.*;
import javax.swing.*;

import Components.Playlist.PLEditor;
import Player.Player;

import java.awt.event.*;

/**
 * This class builds menu with radio buttons and default skins names.
 * It handles changing skin event, and applies new skin to player and
 * it's components, and to playlist.
 * 
 * 
 * @author Danijel Predarski
 *
 */
public class MetalThemeMenu extends JMenu implements ActionListener{

  MetalTheme[] themes;
  Player p;				//parent frame
  PLEditor ple;
  
  /**
   * Creates metal theme menu with specified name
   * 
   * @param name name of skin
   * @param themeArray array of skins
   * @param p player component
   * @param ple playlist component
   */
  public MetalThemeMenu(String name, MetalTheme[] themeArray, Player p, PLEditor ple) {
    super(name);
    themes = themeArray;
    this.p = p;
    this.ple = ple;
    ButtonGroup group = new ButtonGroup();						//add button group to skin menu
    for (int i = 0; i < themes.length; i++) {
        JRadioButtonMenuItem item = new JRadioButtonMenuItem( themes[i].getName() );
	group.add(item);
        add( item );
	item.setActionCommand(i+"");
	item.addActionListener(this);
	if ( i == 0)
	    item.setSelected(true);
    }
  }

   // Sets selected skin

  public void actionPerformed(ActionEvent e) {
    String numStr = e.getActionCommand();
    MetalTheme selectedTheme = themes[ Integer.parseInt(numStr) ];			//get theme that user selected and
    MetalLookAndFeel.setCurrentTheme(selectedTheme);						//apply it to player
    try {
    	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
     } catch (Exception ex) {
        System.out.println("Failed loading Metal");
        System.out.println(ex);
    }
     SwingUtilities.updateComponentTreeUI(p);		//refresh parent frame 
     SwingUtilities.updateComponentTreeUI(ple);     
     p.display.setBackground(selectedTheme.getPrimaryControl());    
     p.songTitlePanel.setBackground(selectedTheme.getPrimaryControlDarkShadow());
     p.songTitlePanel.setForeground(selectedTheme.getControlHighlight());
     p.lblrepeat.setForeground(selectedTheme.getPrimaryControlDarkShadow());
     p.lblshuffle.setForeground(selectedTheme.getPrimaryControlDarkShadow());     
     p.lbltimeMode.setForeground(selectedTheme.getPrimaryControlDarkShadow());
     p.getSongNotifyer().getContentPane().setBackground(selectedTheme.getPrimaryControlDarkShadow());
     p.getSongNotifyer().lblSong.setForeground(selectedTheme.getControlHighlight());
     ple.getJContentPane().setBorder(BorderFactory.createLineBorder(MetalLookAndFeel.getPrimaryControlDarkShadow(), 5));
     ple.getListSongs().setBackground(selectedTheme.getPrimaryControlDarkShadow());
     ple.getListSongs().setForeground(selectedTheme.getControlHighlight());
  }
}  
