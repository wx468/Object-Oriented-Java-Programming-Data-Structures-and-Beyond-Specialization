package module3;

//Java utilities libraries

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

//import java.util.Collections;
//import java.util.Comparator;
//Processing library
//Unfolding libraries
//Parsing library

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Dec 19th, 2019
 *
 *  Description:  ------------EarthquakeCityMap--------------
 *                An application with an interactive map displaying earthquake data.
 *                * Author: UC San Diego Intermediate Software Development MOOC team
 *                * Date: July 17, 2015
 *
 ****************************************************************/

public class EarthquakeCityMap extends PApplet {
    
    // You can ignore this.  It's to keep eclipse from generating a warning.
    private static final long serialVersionUID = 1L;
    
    // IF YOU ARE WORKING OFFLINE, change the value of this variable to true
    private static final boolean offline = false;
    
    // Less than this threshold is a light earthquake
    public static final float THRESHOLD_MODERATE = 5;
    // Less than this threshold is a minor earthquake
    public static final float THRESHOLD_LIGHT = 4;
    
    /**
     * This is where to find the local tiles, for working without an Internet connection
     */
    public static String mbTilesString = "blankLight-1-3.mbtiles";
    
    // The map
    private UnfoldingMap map;
    
    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
    
    
    public void setup() {
        size(950, 600, OPENGL);
        
        if (offline) {
            map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
            earthquakesURL = "2.5_week.atom";    // Same feed, saved Aug 7, 2015, for working offline
        } else {
            //map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
            // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
            //earthquakesURL = "2.5_week.atom";
            map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.HybridProvider());
        }
        
        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);
        
        // The List you will populate with new SimplePointMarkers
        List<Marker> markers = new ArrayList<Marker>();
        
        //Use provided parser to collect properties for each earthquake
        //PointFeatures have a getLocation method
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
        
        //TODO (Step 3): Add a loop here that calls createMarker (see below)
        // to create a new SimplePointMarker for each PointFeature in
        // earthquakes.  Then add each new SimplePointMarker to the
        // List markers (so that it will be added to the map in the line below)
        
        for (PointFeature p : earthquakes) {
            markers.add(createMarker(p));
        }
        // Add the markers to the map so that they are displayed
        map.addMarkers(markers);
    }
    
    /* createMarker: A suggested helper method that takes in an earthquake
     * feature and returns a SimplePointMarker for that earthquake
     *
     * In step 3 You can use this method as-is.  Call it from a loop in the
     * setp method.
     *
     * TODO (Step 4): Add code to this method so that it adds the proper
     *  styling to each marker based on the magnitude of the earthquake.
     
     * */// one feature means one earthquake         Feature<loc,hashmap properties>
    
    private SimplePointMarker createMarker(PointFeature feature) {
        // To print all of the features in a PointFeature (so you can see what they are)
        // uncomment the line below.  Note this will only print if you call createMarker
        // from setup
        System.out.println("loc = " + feature.getLocation() + " :" + feature.getProperties());
        //loc = (-21.213, -70.123) :{depth=32.9, magnitude=4.0, title=M 4.0 - 97km N of Tocopilla, Chile, age=Past Hour}
        
        // Create a new SimplePointMarker at the location given by the PointFeature
        SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
        
        Object magObj = feature.getProperty("magnitude");
        float mag = Float.parseFloat(magObj.toString());
        
        //mark on the map based on the "age": hour, day,week..
        Object ageObj = feature.getProperty("age");
        String age = ageObj.toString();
        // Here is an example of how to use Processing's color method to generate
        // an int that represents the color yellow.
        int blue = color(0, 0, 255);
        
        int yellow = color(255, 255, 0);
        int red = color(255, 0, 0);
        
        // TODO (Step 4): Add code below to style the marker's size and color
        // according to the magnitude of the earthquake.
        // Don't forget about the constants THRESHOLD_MODERATE and
        // THRESHOLD_LIGHT, which are declared above.
        // Rather than comparing the magnitude to a number directly, compare
        // the magnitude to these variables (and change their value in the code
        // above if you want to change what you mean by "moderate" and "light")
   /*     if (mag < THRESHOLD_LIGHT) {
            marker.setRadius(5);
            marker.setColor(blue);
        } else if (mag > THRESHOLD_MODERATE) {
            marker.setRadius(20);
            marker.setColor(red);
            
        } else {
            marker.setRadius(10);
            marker.setColor(yellow);
            
        }*/
   
  /*      if (age.equals("Past Week")) {
            marker.setRadius(5);
            marker.setColor(blue);
        } else if (age.equals("Past Hour")) {
            marker.setRadius(20);
            marker.setColor(red);
            
        } else {
            marker.setRadius(10);
            marker.setColor(yellow);
            
        }*/
        //map to color range, not only to three colors;
        int c = (int) map(mag, 1, 7, 0, 255);
        float r = map(mag, 1, 7, 5, 20);
        marker.setColor(color(c, 100, 255 - c));
        marker.setRadius(r);
        // Finally return the marker
        return marker;
    }
    
    public void draw() {
        background(10);
        map.draw();//after step 3, why the market is already grey without giving the color? by default?
        addKey();
    }
    
    
    // helper method to draw key in GUI
    // TODO: Implement this method to draw the key
    private void addKey() {
        // 950,600)  200,50,700,500)
        // Remember you can use Processing's graphics methods here
        fill(230);
        rect(25, 50, 150, 250);
        fill(50);
        //textFont();
        textSize(14);
        text("Earthquake Key", 50, 75, 150, 250);
        textSize(12);
        text("Higher Magnitude", 70, 150);
        text("7+", 70, 162);
        text("1+", 70, 213);
        
        text("Lower Magnitude", 70, 223);
        //text("Below 4.0", 75, 225);
        fill(255, 100, 0);
        ellipse(50, 142, 20, 20);//125-(20/2)
        triangle(45, 160, 55, 160, 50, 155);
        line(50, 160, 50, 205);
        triangle(45, 205, 55, 205, 50, 210);
        
        fill(1, 100, 254);
        ellipse(50, 223, 5, 5);//225-(5/2)
    }
}
