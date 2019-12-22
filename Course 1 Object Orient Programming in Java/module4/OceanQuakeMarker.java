package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/**
 * Implements a visual marker for ocean earthquakes on an earthquake map
 *
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 */
public class OceanQuakeMarker extends EarthquakeMarker {
    
    public OceanQuakeMarker(PointFeature quake) {
        super(quake);
        
        // setting field in earthquake marker
        isOnLand = false;
    }
    
    
    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        // Drawing a centered square for Ocean earthquakes
        // DO NOT set the fill color.  That will be set in the EarthquakeMarker
        // class to indicate the depth of the earthquake.
        // Simply draw a centered square.
        
        // HINT: Notice the radius variable in the EarthquakeMarker class
        // and how it is set in the EarthquakeMarker constructor
        
        // TODO: Implement this method
        float mag = this.getMagnitude();//todo: use "this"?
        
        if (mag < 4) {
            pg.rect(x - 2.5f, y - 2.5f, 5f, 5f);
        } else if (mag > 5) {
            pg.rect(x - 7.5f, y - 7.5f, 15f, 15f);
        } else {
            pg.rect(x - 5f, y - 5f, 10f, 10f);
        }
    }
}
