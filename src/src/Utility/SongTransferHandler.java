package Utility;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import Components.Playlist.SongListModel;
import Entities.Song;

public class SongTransferHandler extends TransferHandler {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7508727711413722454L;
	private DataFlavor fileFlavor, stringFlavor;
    private JList list;
    protected String newline = "\n";
    

    public SongTransferHandler(JList l) {
        this.list = l;
    	fileFlavor = DataFlavor.javaFileListFlavor;
        stringFlavor = DataFlavor.stringFlavor;
     }
    public boolean importData(JComponent c, Transferable t) {

        //A real application would load the file in another
        //thread in order to not block the UI.  This step
        //was omitted here to simplify the code.
        try {
            if (hasFileFlavor(t.getTransferDataFlavors())) {
                java.util.List files =
                     (java.util.List)t.getTransferData(fileFlavor);
                String songName;
                Song s;
                for (int i = 0; i < files.size(); i++) {
                    File file = (File)files.get(i);
                    songName = file.getName();
                    if (songName.endsWith(".mp3")){
                    	s = new Song(songName, file.getAbsolutePath());
                    	((SongListModel)list.getModel()).addElement(s);
                    	
                    }
                    System.out.println("Ubacicu nesto u listu " + file.getName());
                }
                return true;
            } else if (hasStringFlavor(t.getTransferDataFlavors())) {
                System.out.println("Ok, dodajem nesto drugo");
            	return true;
            }
        } catch (UnsupportedFlavorException ufe) {
            System.out.println("importData: unsupported data flavor");
        } catch (IOException ieo) {
            System.out.println("importData: I/O exception");
        }
        return false;
    }
    
    public boolean canImport(JComponent c, DataFlavor[] flavors) {
        if (hasFileFlavor(flavors))   { return true; }
        if (hasStringFlavor(flavors)) { return true; }
        return false;
    }

    private boolean hasFileFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (fileFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasStringFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (stringFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }


}
