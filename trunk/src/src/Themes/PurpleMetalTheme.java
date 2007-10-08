package Themes;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
 * This class describes a theme using "purple" colors.
 * 
 * @author Danijel Predarski
 *
 */
public class PurpleMetalTheme extends DefaultMetalTheme {

	/**
	 * Returns skin name
	 */
    public String getName() { return "Purple"; } 
 
    private final ColorUIResource primary3 = new ColorUIResource(157, 157, 255);
    /**
     * Returns primary3 color 
     */
    protected ColorUIResource getPrimary3() { return primary3; }  
    
    
    //set control shadow to null to disable button selection color
    /**
     * Returns control shadow color
     */ 
    public ColorUIResource getControlShadow() {return null;}
}
