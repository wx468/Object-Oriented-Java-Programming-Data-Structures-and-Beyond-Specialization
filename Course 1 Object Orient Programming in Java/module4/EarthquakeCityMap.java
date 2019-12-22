package module4;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

/***************************************************************
 *  Name:    Wei Xu
 *
 *  Date: Dec 21th, 2019
 *
 *  Description:  ------------EarthquakeCityMap--------------
 *                An application with an interactive map displaying earthquake data.
 *                * Author: UC San Diego Intermediate Software Development MOOC team
 *                * Date: July 17, 2015
 *
 * WEI XU COMMENT:
 *    location is in "PointFeature" but not in "Feature" class
 *    todo: still confuse about the map.draw()
 *    will it call the draw(PGraphics pg, float x, float y) automatically?
 *
 *
 *
 ****************************************************************/

public class EarthquakeCityMap extends PApplet {
    
    // We will use member variables, instead of local variables, to store the data
    // that the setUp and draw methods will need to access (as well as other methods)
    // You will use many of these variables, but the only one you should need to add
    // code to modify is countryQuakes, where you will store the number of earthquakes
    // per country.
    
    // You can ignore this.  It's to get rid of eclipse warnings
    private static final long serialVersionUID = 1L;
    
    // IF YOU ARE WORKING OFFILINE, change the value of this variable to true
    private static final boolean offline = false;
    
    /**
     * This is where to find the local tiles, for working without an Internet connection
     */
    public static String mbTilesString = "blankLight-1-3.mbtiles";
    
    
    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
    
    // The files containing city names and info and country names and info
    private String cityFile = "/Users/paradox/Desktop/JAVA/DUKE/Course 6- Object Oriented Programming/UCSDUnfoldingMaps/data/city-data.json";
    private String countryFile = "/Users/paradox/Desktop/JAVA/DUKE/Course 6- Object Oriented Programming/UCSDUnfoldingMaps/data/countries.geo.json";
    
    // The map
    private UnfoldingMap map;
    
    // Markers for each city
    private List<Marker> cityMarkers;
    // Markers for each earthquake
    private List<Marker> quakeMarkers;//((EarthquakeMarker)a).isOnLand()
    
    // A List of country markers
    private List<Marker> countryMarkers;
    
    public void setup() {
        // (1) Initializing canvas and map tiles
        size(900, 700, OPENGL);
        if (offline) {
            map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
            earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
        } else {
            map = new UnfoldingMap(this, 200, 50, 650, 600, new Google.GoogleMapProvider());
            // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
            //earthquakesURL = "2.5_week.atom";
        }
        MapUtils.createDefaultEventDispatcher(this, map);
        
        // FOR TESTING: Set earthquakesURL to be one of the testing files by uncommenting
        // one of the lines below.  This will work whether you are online or offline
        //earthquakesURL = "/Users/paradox/Desktop/JAVA/DUKE/Course 6- Object Oriented Programming/UCSDUnfoldingMaps/data/test1.atom";
        //earthquakesURL = "/Users/paradox/Desktop/JAVA/DUKE/Course 6- Object Oriented Programming/UCSDUnfoldingMaps/data/test2.atom";
        
        // WHEN TAKING THIS QUIZ: Uncomment the next line
        //earthquakesURL = "quiz1.atom";
        
        
        // (2) Reading in earthquake data and geometric properties
        //     STEP 1: load country features and markers
        //?countries is a Feature or PointFeature?
        List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
        countryMarkers = MapUtils.createSimpleMarkers(countries);
        System.out.println("--------------------Print Feature Countries Test-----------------");
        System.out.println(countries);
        for (Feature i : countries) {
            System.out.println(i.getId() + "\t" + i.getProperties() + i.toString());
        }
        
        /** COUNTRY JSON
         * {"type":"Feature","properties":{"name":"Afghanistan"},"geometry":{"type":"Polygon","coordinates":[[[61.210817,35.650072],[62.230651,35.270664],[62.984662,35.404041],[63.193538,35.857166],[63.982896,36.007957],[64.546479,36.312073],[64.746105,37.111818],[65.588948,37.305217],[65.745631,37.661164],[66.217385,37.39379],[66.518607,37.362784],[67.075782,37.356144],[67.83,37.144994],[68.135562,37.023115],[68.859446,37.344336],[69.196273,37.151144],[69.518785,37.608997],[70.116578,37.588223],[70.270574,37.735165],[70.376304,38.138396],[70.806821,38.486282],[71.348131,38.258905],[71.239404,37.953265],[71.541918,37.905774],[71.448693,37.065645],[71.844638,36.738171],[72.193041,36.948288],[72.63689,37.047558],[73.260056,37.495257],[73.948696,37.421566],[74.980002,37.41999],[75.158028,37.133031],[74.575893,37.020841],[74.067552,36.836176],[72.920025,36.720007],[71.846292,36.509942],[71.262348,36.074388],[71.498768,35.650563],[71.613076,35.153203],[71.115019,34.733126],[71.156773,34.348911],[70.881803,33.988856],[69.930543,34.02012],[70.323594,33.358533],[69.687147,33.105499],[69.262522,32.501944],[69.317764,31.901412],[68.926677,31.620189],[68.556932,31.71331],[67.792689,31.58293],[67.683394,31.303154],[66.938891,31.304911],[66.381458,30.738899],[66.346473,29.887943],[65.046862,29.472181],[64.350419,29.560031],[64.148002,29.340819],[63.550261,29.468331],[62.549857,29.318572],[60.874248,29.829239],[61.781222,30.73585],[61.699314,31.379506],[60.941945,31.548075],[60.863655,32.18292],[60.536078,32.981269],[60.9637,33.528832],[60.52843,33.676446],[60.803193,34.404102],[61.210817,35.650072]]]},"id":"AFG"},
         *
         *  WHY OTHER FEATURES SUCH AS "GEO" NOT IN THE FEATURE? HOW IT LOAD DATA?
         *         AFG	{name=Afghanistan}de.fhpotsdam.unfolding.data.ShapeFeature@37d54ca2
         *         AGO	{name=Angola}de.fhpotsdam.unfolding.data.MultiFeature@4a63e106
         *         ALB	{name=Albania}de.fhpotsdam.unfolding.data.ShapeFeature@36eb2638
         *
         */
        
        
        //     STEP 2: read in city data,similar to "createMarker" in Module 3
        List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
        System.out.println("--------------------Print Feature CITY Test-----------------");
        System.out.println(cities);
        for (Feature i : cities) {
            System.out.println(i.getId() + "\t" + i.getProperties() + i.toString());
        }
        /**
         *         f9e941ca-9ba5-46bf-8ecc-990526131d23	{country=Germany, name=Berlin, coastal=false, population=3.375}de.fhpotsdam.unfolding.data.PointFeature@38d998c8
         *         ccec016e-2680-404d-834f-6f1415363573	{country=Japan, name=Tokyo, coastal=true, population=13.35}de.fhpotsdam.unfolding.data.PointFeature@659c7ad2
         *         12a49a03-3dbf-498d-91ed-c8184b3f3068	{country=India, name=New Delhi, coastal=false, population=9.88}de.fhpotsdam.unfolding.data.PointFeature@4db247bd
         * */
        
        /**
         * TODO: WHY DON'T LIKE COUNTRIES TO CREATE CITYMARKER?
         * */
        cityMarkers = new ArrayList<Marker>();
        for (Feature city : cities) {
            cityMarkers.add(new CityMarker(city));
        }
        
        //     STEP 3: read in earthquake RSS feed
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
        quakeMarkers = new ArrayList<Marker>();
        /** IN MODULE 3
         *  for (PointFeature p : earthquakes) {
         *             markers.add(createMarker(p));
         *         }
         *   createMarker(PF p){
         *        SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
         *        }
         * */
        for (PointFeature feature : earthquakes) {
            //check if LandQuake
            if (isLand(feature)) {
                quakeMarkers.add(new LandQuakeMarker(feature));
            }
            // OceanQuakes
            else {
                quakeMarkers.add(new OceanQuakeMarker(feature));
            }
        }
        
        // could be used for debugging
        //printQuakes();
        
        // (3) Add markers to map
        //     NOTE: Country markers are not added to the map.  They are used
        //           for their geometric properties
        map.addMarkers(quakeMarkers);//nothing shown in the beginning
        map.addMarkers(cityMarkers);//nothing shown in the beginning
        //map.addMarkers(countryMarkers);//all countries shown in grey in the beginning
        
        System.out.println("------------test country markers------------");
        for (Marker c : countryMarkers) {
            System.out.println("id = " + c.getId() + ", location = " + c.getLocation() + ", proterties = " + c.getProperties());
        }
        /**
         * id = AFG, location = (33.857, 66.087), proterties = {name=Afghanistan}
         * id = AGO, location = (-8.670, 14.939), proterties = {name=Angola}
         *
         * */
        System.out.println("------------test city markers------------");
        for (Marker c : cityMarkers) {
            System.out.println("id = " + c.getId() + ", location = " + c.getLocation() + ", proterties = " + c.getProperties());
        }
        /**
         * id = null, location = (52.517, 13.383), proterties = {country=Germany, name=Berlin, coastal=false, population=3.375}
         * id = null, location = (35.683, 139.683), proterties = {country=Japan, name=Tokyo, coastal=true, population=13.35}
         * */
        System.out.println("------------test quake markers------------");
        for (Marker c : quakeMarkers) {
            System.out.println("id = " + c.getId() + ", location = " + c.getLocation() + ", proterties = " + c.getProperties());
        }
        /**
         * id = null, location = (-5.105, 134.009), proterties = {depth=10.0, magnitude=4.5, title=M 4.5 - 76km NNW of Dobo, Indonesia, radius=9.0, age=Past Day}
         * id = null, location = (-3.057, 150.269), proterties = {depth=10.0, magnitude=4.5, title=M 4.5 - 79km SW of Kavieng, Papua New Guinea, radius=9.0, age=Past Day}
         * */
        //        System.out.println("------------test Landquake markers------------");
        //        for (Marker c : LandQuakeMarker) {
        //            System.out.println("id = " + c.getId() + ", location = " + c.getLocation() + ", proterties = " + c.getProperties());
        //        }
        //        System.out.println("------------test Oceanquake markers------------");
        //        for (Marker c : quakeMarkers) {
        //            System.out.println("id = " + c.getId() + ", location = " + c.getLocation() + ", proterties = " + c.getProperties());
        //        }
        /**
         * Afghanistan : 4
         * Angola : 0
         * Albania : 3
         * United Arab Emirates : 0
         * Argentina : 7
         * */
    }  // End setup
    
    
    public void draw() {
        background(0);
        map.draw();
        addKey();
        //printQuakes();
        
    }
    
    // helper method to draw key in GUI
    // TODO: Update this method as appropriate
    private void addKey() {
        // Remember you can use Processing's graphics methods here
        fill(255, 250, 240);
        rect(25, 50, 150, 250);
        
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Earthquake Key", 50, 75);
        
        fill(color(255, 0, 0));
        triangle(45, 115, 55, 115, 50, 106.4f);
        fill(color(255, 255, 255));
        ellipse(50, 135, 10, 10);
        fill(color(255, 255, 255));
        rect(45, 155, 10, 10);
        
        fill(0);
        text("City Marker", 75, 111);
        text("Land Quake", 75, 133);
        text("Ocean Quake", 75, 160);
        text("Size ~ Magnitude", 50, 185);
        text("Shallow", 75, 215);
        text("Intermediate", 75, 235);
        text("Deep", 75, 255);
        text("Past hour", 75, 275);
        line(45, 270, 55, 280);
        line(55, 270, 45, 280);
        
        fill(255, 255, 0);
        ellipse(50, 215, 10, 10);
        fill(0, 0, 255);
        ellipse(50, 235, 10, 10);
        fill(255, 0, 0);
        ellipse(50, 255, 10, 10);
        
    }
    
    
    // Checks whether this quake occurred on land.  If it did, it sets the
    // "country" property of its PointFeature to the country where it occurred
    // and returns true.  Notice that the helper method isInCountry will
    // set this "country" property already.  Otherwise it returns false.
    private boolean isLand(PointFeature earthquake) {
        
        
        // Loop over all the country markers.
        // For each, check if the earthquake PointFeature is in the
        // country in m.  Notice that isInCountry takes a PointFeature
        // and a Marker as input.
        // If isInCountry ever returns true, isLand should return true.
        boolean island = false;
        for (Marker m : countryMarkers) {
            // TODO: Finish this method using the helper method isInCountry
            if (isInCountry(earthquake, m)) {
                island = true;
                break;
            }
        }
        
        
        // not inside any country
        return island;
    }
    
    /* prints countries with number of earthquakes as
     * Country1: numQuakes1
     * Country2: numQuakes2
     * ...
     * OCEAN QUAKES: numOceanQuakes
     * */
    private void printQuakes() {
        // TODO: Implement this method
        // One (inefficient but correct) approach is to:
        //   Loop over all of the countries, e.g. using
        //        for (Marker cm : countryMarkers) { ... }
        //
        //      Inside the loop, first initialize a quake counter.
        //      Then loop through all of the earthquake
        //      markers and check to see whether (1) that marker is on land
        //     	and (2) if it is on land, that its country property matches
        //      the name property of the country marker.   If so, increment
        //      the country's counter.
        int totalcount = 0;
        for (Marker m : countryMarkers) {
            int count = 0;
            int total = 0;
            
            for (Marker eq : quakeMarkers) {
                // if (eq.isOnLand) { why have no method isOnLand?
                if (((EarthquakeMarker) eq).isOnLand()) {
                    if (((LandQuakeMarker) eq).getCountry().equals(m.getProperty("name"))) {
                        count++;
                    }
                }
            }
            totalcount += count;
            if (count > 0)
                System.out.println(m.getProperty("name") + " : " + count);
            
        }
        System.out.println("OCEAN QUAKES" + " : " + (quakeMarkers.size() - totalcount));
        /**
         * Nepal: 1
         * United States of America: 9
         * Uzbekistan: 1
         * OCEAN QUAKES: 26
         * */
        // Here is some code you will find useful:
        //
        //  * To get the name of a country from a country marker in variable cm, use:
        //     String name = (String)cm.getProperty("name");
        //  * If you have a reference to a Marker m, but you know the underlying object
        //    is an EarthquakeMarker, you can cast it:
        //       EarthquakeMarker em = (EarthquakeMarker)m;
        //    Then em can access the methods of the EarthquakeMarker class
        //       (e.g. isOnLand)
        //  * If you know your Marker, m, is a LandQuakeMarker, then it has a "country"
        //      property set.  You can get the country with:
        //        String country = (String)m.getProperty("country");
        
        
    }
    
    
    // helper method to test whether a given earthquake is in a given country
    // This will also add the country property to the properties of the earthquake
    // feature if it's in one of the countries.
    // You should not have to modify this code
    private boolean isInCountry(PointFeature earthquake, Marker country) {
        // getting location of feature
        Location checkLoc = earthquake.getLocation();
        
        // some countries represented it as MultiMarker
        // looping over SimplePolygonMarkers which make them up to use isInsideByLoc
        if (country.getClass() == MultiMarker.class) {
            
            // looping over markers making up MultiMarker
            for (Marker marker : ((MultiMarker) country).getMarkers()) {
                
                // checking if inside
                if (((AbstractShapeMarker) marker).isInsideByLocation(checkLoc)) {
                    earthquake.addProperty("country", country.getProperty("name"));
                    
                    // return if is inside one
                    return true;
                }
            }
        }
        
        // check if inside country represented by SimplePolygonMarker
        else if (((AbstractShapeMarker) country).isInsideByLocation(checkLoc)) {
            earthquake.addProperty("country", country.getProperty("name"));
            
            return true;
        }
        return false;
    }
    
}
