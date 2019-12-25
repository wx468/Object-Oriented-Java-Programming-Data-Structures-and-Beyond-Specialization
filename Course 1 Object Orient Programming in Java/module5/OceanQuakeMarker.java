package module5;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PGraphics;

import java.util.ArrayList;

/**
 * Implements a visual marker for ocean earthquakes on an earthquake map
 *
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 */
public class OceanQuakeMarker extends EarthquakeMarker {
    
    //    public OceanQuakeMarker(PointFeature quake) {
    //        super(quake);
    //
    //        // setting field in earthquake marker
    //        isOnLand = false;
    //    }
    //
    //
    //    /**
    //     * Draw the earthquake as a square
    //     */
    //    @Override
    //    public void drawEarthquake(PGraphics pg, float x, float y) {
    //        pg.rect(x - radius, y - radius, 2 * radius, 2 * radius);
    //        // ScreenPosition a = new ScreenPosition();
    //        //        for (Marker c : city) {
    //        //            pg.line(x, y, c.getLocation().x, c.getLocation().y);
    //        //        }
    //    }
    
    public static ArrayList<Marker> affectedCities = new ArrayList<Marker>();
    private static UnfoldingMap map;
    
    public OceanQuakeMarker(PointFeature quake) {
        super(quake);
        
        // setting field in earthquake marker
        isOnLand = false;
        
    }
    
    
    /**
     * Draw the earthquake as a square
     */
    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.rect(x - radius, y - radius, 2 * radius, 2 * radius);
        // Draw line(s) on the canvas between the ocean earthquake and the affected city(cities).
        // setAffectedCities();
        for (Marker currCity : affectedCities) {
            ScreenPosition pos = ((CityMarker) currCity).getScreenPosition(map);
            pg.fill(0, 0, 0);
            pg.line(x, y, pos.x, pos.y);
        }
        affectedCities.clear();
    }
    
    public void setAffectedCities(ArrayList<Marker> markers) {
        for (Marker currMarker : markers) {
            affectedCities.add(currMarker);
        }
    }
    
    public static void setMap(UnfoldingMap currMap) {
        map = currMap;
    }
    
    
}
