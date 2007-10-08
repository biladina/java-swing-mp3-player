package Components.Playlist;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import Entities.Song;


public class SongListCellRenderer extends JLabel implements ListCellRenderer {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4485333288602178710L;
	private PLEditor pleditor = null; 
		
	 // This is the only method defined by ListCellRenderer.
     // We just reconfigure the JLabel each time we're called.
    public SongListCellRenderer(PLEditor ple){
    	super();
    	this.pleditor = ple; 
    }
    
     public Component getListCellRendererComponent(
       JList list,
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // the list and the cell have the focus
     {
         String s = index + 1 + "."; //addBrackets(value.toString());
         s += " " + addBrackets(((Song) value).getSongTime());
         s += ((Song) value).getSongName();
         setText(s);
         if (index == pleditor.playingIndex){
        	 setForeground(Color.DARK_GRAY);
         } else {
        	 setBackground(list.getBackground());
        	 setForeground(list.getForeground());
         }
         if ((pleditor.songListModel != null)){
        	 int qIndex = pleditor.songListModel.queueIndex.indexOf(index);
        	 if (qIndex != -1){
            	 setText("{" + ++qIndex + "}" + s);
            	 setForeground(Color.YELLOW);
        	 }
         }
         if (isSelected) {
             setBackground(list.getSelectionBackground());
             setForeground(list.getSelectionForeground());
         }
         setEnabled(list.isEnabled());
         setFont(list.getFont());
         setOpaque(true);
         return this;
     }
 	/*
 	 * formating the time output
 	 */
 	private String addBrackets(String s){
 		if (s.length() != 0){
 			return "[" + s + "]";
 		}  
 		return " --:-- ";
 	}

 }