package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/**
 * Implements a visual marker for earthquakes on an earthquake map
 *
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 */
public abstract class EarthquakeMarker extends SimplePointMarker {
    
    // Did the earthquake occur on land?  This will be set by the subclasses.
    protected boolean isOnLand;
    
    // SimplePointMarker has a field "radius" which is inherited
    // by Earthquake marker:
    // protected float radius;
    //
    // You will want to set this in the constructor, either
    // using the thresholds below, or a continuous function
    // based on magnitude.
    
    
    /**
     * Greater than or equal to this threshold is a moderate earthquake
     */
    public static final float THRESHOLD_MODERATE = 5;
    /**
     * Greater than or equal to this threshold is a light earthquake
     */
    public static final float THRESHOLD_LIGHT = 4;
    
    /**
     * Greater than or equal to this threshold is an intermediate depth
     */
    public static final float THRESHOLD_INTERMEDIATE = 70;
    /**
     * Greater than or equal to this threshold is a deep depth
     */
    public static final float THRESHOLD_DEEP = 300;
    
    // ADD constants for colors
    
    
    // abstract method implemented in derived classes
    public abstract void drawEarthquake(PGraphics pg, float x, float y);
    
    
    // constructor
    public EarthquakeMarker(PointFeature feature) {
        super(feature.getLocation());
        // Add a radius property and then set the properties
        java.util.HashMap<String, Object> properties = feature.getProperties();
        float magnitude = Float.parseFloat(properties.get("magnitude").toString());
        properties.put("radius", 2 * magnitude);
        setProperties(properties);
        this.radius = 1.75f * getMagnitude();
    }
    
    /**
     * public abstract void draw(processing.core.PGraphics pg, float x, float y)
     * Draws a visual representation of this marker. The given x,y coordinates are already converted into the local coordinate system, so no need for further conversion. That is, a position of (0, 0) is the origin of this marker. Subclasses must override this method to draw a marker.
     * Parameters:
     * pg - The PGraphics to draw on
     * x - The x position in outer object coordinates.
     * y - The y position in outer object coordinates.
     */
    
    
    // calls abstract method drawEarthquake and then checks age and draws X if needed
    public void draw(PGraphics pg, float x, float y) { //should be override in other classes
        // save previous styling
        pg.pushStyle();
        
        // determine color of marker from depth
        colorDetermine(pg);
        
        // call abstract method implemented in child class to draw marker shape
        drawEarthquake(pg, x, y);
        
        // OPTIONAL TODO: draw X over marker if within past day
        Xage(pg, x, y);
        // reset to previous styling
        pg.popStyle();
        
    }
    
    // determine color of marker from depth, and set pg's fill color
    // using the pg.fill method.
    // We suggest: Deep = red, intermediate = blue, shallow = yellow
    // But this is up to you, of course.
    // You might find the getters below helpful.
    private void colorDetermine(PGraphics pg) {
        //TODO: Implement this method
        float dep = this.getDepth();
        if (dep >= 0 && dep < 70) {
            pg.fill(255, 255, 0);//todo:why use pg.color is not correct, become all white inside?
        } else if (dep >= 70 && dep < 300) {
            pg.fill(0, 0, 255);
        } else {
            pg.fill(255, 0, 0);
        }
        
    }
    
    private void Xage(PGraphics pg, float x, float y) {
        String age = this.getProperty("age").toString();
        if (age.equals("Past Day")) {
            if (getMagnitude() < THRESHOLD_LIGHT) {
                //pg.scale(10);
                pg.line(x - 2.5f, y - 2.5f, x + 2.5f, y + 2.5f);
                pg.line(x + 2.5f, y - 2.5f, x - 2.5f, y + 2.5f);
            } else if (getMagnitude() > THRESHOLD_MODERATE) {
                //pg.scale(10);
                
                pg.line(x - 7.5f, y - 7.5f, x + 7.5f, y + 7.5f);
                pg.line(x + 7.5f, y - 7.5f, x - 7.5f, y + 7.5f);
            } else {
                // pg.scale(10);
                
                pg.line(x - 5, y - 5, x + 5, y + 5);
                pg.line(x + 5, y - 5, x - 5, y + 5);
            }
        }
        
    }
    
    /*
     * getters for earthquake properties
     */
    
    public float getMagnitude() {
        return Float.parseFloat(getProperty("magnitude").toString());
    }
    
    public float getDepth() {
        return Float.parseFloat(getProperty("depth").toString());
    }
    
    public String getTitle() {
        return (String) getProperty("title");
        
    }
    
    public float getRadius() {
        return Float.parseFloat(getProperty("radius").toString());
    }
    
    public boolean isOnLand() {
        return isOnLand;
    }
    
    
}
