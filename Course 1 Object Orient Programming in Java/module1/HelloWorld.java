package module1;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Dec 17th, 2019
 *
 *  Description:  --------------------------
 *                Setup in IntelliJ take me a lot of time...
 *                1. Still don't know what's Choose Runner can work or not.
 *                2. Download JAVA 8 from Oracle, and change the JDK for this project.
 *                   Since IntelliJ has a built-in Java 11 but don't work.
 *                3. Change to several map providers for this assignment.
 *
 *                4. Still don't know why keep saying this after run:
 *
 *               objc[67407]: Class JavaLaunchHelper is implemented in both /Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home/bin/java (0x1049b34c0) and /Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home/jre/lib/libinstrument.dylib (0x1059bc4e0). One of the two will be used. Which one is undefined.
 *                Warning: Temporarily overwriting system property at user's request: key: http.proxyPort old value: 80 new value: 1087
 *                Warning: Temporarily overwriting system property at user's request: key: http.proxyHost old value:  new value: 127.0.0.1
 *                2019-12-17 19:05:40.700 java[67407:3485652] WARNING: NSWindow drag regions should only be invalidated on the Main Thread! This will throw an exception in the future. Called from (
 *              	0   AppKit                              0x00007fff4de507eb -[NSWindow(NSWindow_Theme) _postWindowNeedsToResetDragMarginsUnlessPostingDisabled] + 378
 *              	1   AppKit                              0x00007fff4de4dbdb -[NSWindow _initContent:styleMask:backing:defer:contentView:] + 1479
 *              	2   AppKit                              0x00007fff4de4d60e -[NSWindow initWithContentRect:styleMask:backing:defer:] + 45
 *               	3   libnativewindow_macosx.jnilib       0x000000010fd76f9e Java_jogamp_nativewindow_macosx_OSXUtil_CreateNSWindow0 + 398
 *                	4   ???                                 0x0000000113b8e954 0x0 + 4625852756
 *                )
 *
 ****************************************************************/

public class HelloWorld extends PApplet {
    /**
     * Your goal: add code to display second map, zoom in, and customize the background.
     * Feel free to copy and use this code, adding to it, modifying it, etc.
     * Don't forget the import lines above.
     */
    
    // You can ignore this.  It's to keep eclipse from reporting a warning
    private static final long serialVersionUID = 1L;
    
    /**
     * This is where to find the local tiles, for working without an Internet connection
     */
    public static String mbTilesString = "blankLight-1-3.mbtiles";
    
    // IF YOU ARE WORKING OFFLINE: Change the value of this variable to true
    private static final boolean offline = false;
    
    /**
     * The map we use to display our home town: La Jolla, CA
     */
    UnfoldingMap map1;
    
    /**
     * The map you will use to display your home town
     */
    UnfoldingMap map2;
    
    public void setup() {
        size(800, 600, P2D);  // Set up the Applet window to be 800x600
        // The OPENGL argument indicates to use the
        // Processing library's 2D drawing
        // You'll learn more about processing in Module 3
        
        // This sets the background color for the Applet.
        // Play around with these numbers and see what happens!
        //this.background(240); //grey
        this.background(100, 200, 200);
        
        // Select a map provider
        AbstractMapProvider provider = new Google.GoogleTerrainProvider();
        //AbstractMapProvider provider = new OpenWeatherProvider.Clouds();
        //AbstractMapProvider provider = new ThunderforestProvider.Landscape();
        
        // Set a zoom level
        int zoomLevel = 10;
        
        if (offline) {
            // If you are working offline, you need to use this provider
            // to work with the maps that are local on your computer.
            provider = new MBTilesMapProvider(mbTilesString);
            // 3 is the maximum zoom level for working offline
            zoomLevel = 3;
        }
        
        // Create a new UnfoldingMap to be displayed in this window.
        // The 2nd-5th arguments give the map's x, y, width and height
        // When you create your map we want you to play around with these
        // arguments to get your second map in the right place.
        // The 6th argument specifies the map provider.
        // There are several providers built-in.
        // Note if you are working offline you must use the MBTilesMapProvider
        map1 = new UnfoldingMap(this, 50, 50, 350, 500, provider);
        map2 = new UnfoldingMap(this, 400, 80, 350, 500, provider);
        
        // The next line zooms in and centers the map at
        // 32.9 (latitude) and -117.2 (longitude)
        map1.zoomAndPanTo(zoomLevel, new Location(32.9f, -117.2f));
        //map2.zoomAndPanTo(zoomLevel, new Location(28.2f, 112.97f));
        
        // This line makes the map interactive
        MapUtils.createDefaultEventDispatcher(this, map1);
        
        // TODO: Add code here that creates map2
        // Then you'll modify draw() below
        
    }
    
    /**
     * Draw the Applet window.
     */
    public void draw() {
        // So far we only draw map1...
        // TODO: Add code so that both maps are displayed
        map1.draw();
        map2.draw();
        
    }
    
    
}
