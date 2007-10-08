package Themes;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
 * This class describes a theme using "green" colors.
 * 
 * @author Danijel Predarski
 *
 */
public class GreenMetalTheme extends DefaultMetalTheme {

	/**
	 * Returns skin name
	 */
    public String getName() { return "Emerald"; }

  // greenish colors
    private final ColorUIResource primary1 = new ColorUIResource(51, 102, 51);
    private final ColorUIResource primary2 = new ColorUIResource(102, 153, 102);
    private final ColorUIResource primary3 = new ColorUIResource(153, 204, 153); 
    
    /**
     * Returns primary1 color
     */
    protected ColorUIResource getPrimary1() { return primary1; }  
    /**
     * Returns primary2 color
     */

    protected ColorUIResource getPrimary2() { return primary2; } 
    /**
     * Returns primary3 color
     */
    protected ColorUIResource getPrimary3() { return primary3; } 

    
    // set control shadow to null to disable button selection color
    /**
     * Returns control shadow color
     */ 
    public ColorUIResource getControlShadow() {return null;}

}
