package Themes;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
 * This class describes a theme using "khaki" colors.
 * 
 * @author Danijel Predarski
 *
 */
public class KhakiMetalTheme extends DefaultMetalTheme {

	/**
	 * Returns skin name
	 */
    public String getName() { return "Sandstone"; }

    private final ColorUIResource primary1 = new ColorUIResource( 87,  87,  47);
    private final ColorUIResource primary2 = new ColorUIResource(159, 151, 111);
    private final ColorUIResource primary3 = new ColorUIResource(199, 183, 143);

    private final ColorUIResource secondary1 = new ColorUIResource( 111,  111,  111);
    private final ColorUIResource secondary2 = new ColorUIResource(159, 159, 159);
    private final ColorUIResource secondary3 = new ColorUIResource(231, 215, 183);

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

    /**
     * Returns secondary1 color
     */
    protected ColorUIResource getSecondary1() { return secondary1; }
    /**
     * Returns secondary2 color
     */
    protected ColorUIResource getSecondary2() { return secondary2; }
    /**
     * Returns secondary3 color
     */
    protected ColorUIResource getSecondary3() { return secondary3; }
    

     // set control shadow to null to disable button selection color
    /**
     * Returns control shadow color
     */
    public ColorUIResource getControlShadow() {return null;}

}
