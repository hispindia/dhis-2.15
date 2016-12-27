package org.hisp.dhis.system.util;

/*
 * Copyright (c) 2004-2014, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringReader;

import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.referencing.GeodeticCalculator;
import org.hisp.dhis.organisationunit.OrganisationUnit;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lars Helge Overland
 */
public class GeoUtils
{    
    private static final Pattern SVG_TEXT_PATTERN = Pattern.compile( "text=\"(.*?)\"", Pattern.DOTALL );
    
    /**
     * Returns boundaries of a box shape which centre is the point defined by the 
     * given longitude and latitude. The distance between the center point and the
     * edges of the box is defined in meters by the given distance. Based on standard
     * EPSG:4326 long/lat projection. The result is an array of length 4 where
     * the values at each index are:
     * 
     * <ul>
     * <li>Index 0: Maximum latitude (north edge of box shape).</li>
     * <li>Index 1: Maxium longitude (east edge of box shape).</li>
     * <li>Index 2: Minimum latitude (south edge of box shape).</li>
     * <li>Index 3: Minumum longitude (west edge of box shape).</li>
     * </ul>
     * 
     * @param longitude the longitude.
     * @param latitude the latitude.
     * @param distance the distance in meters to each box edge.
     * @return an array of length 4.
     */
    public static double[] getBoxShape( double longitude, double latitude, double distance )
    {
        double[] box = new double[4];
        
        GeodeticCalculator calc = new GeodeticCalculator();
        calc.setStartingGeographicPoint( longitude, latitude );
        
        calc.setDirection( 0, distance );
        Point2D north = calc.getDestinationGeographicPoint();
        
        calc.setDirection( 90, distance );
        Point2D east = calc.getDestinationGeographicPoint();
        
        calc.setDirection( 180, distance );
        Point2D south = calc.getDestinationGeographicPoint();
        
        calc.setDirection( -90, distance );
        Point2D west = calc.getDestinationGeographicPoint();
        
        box[0] = north.getY();
        box[1] = east.getX();
        box[2] = south.getY();
        box[3] = west.getX();
        
        return box;
    }
    
    /**
     * Creates the distance between two points.
     */
    public static double getDistanceBetweenTwoPoints( Point2D from, Point2D to)
    {                        
        GeodeticCalculator calc = new GeodeticCalculator();
        calc.setStartingGeographicPoint( from );
        calc.setDestinationGeographicPoint( to);
        
        return calc.getOrthodromicDistance();
    }

    /**
     * Get GeometryJSON point.
     */
    public static Point getGeoJsonPoint( double longitude, double latitude )
        throws IOException
    {
        Point point = null;

        GeometryJSON gtjson = new GeometryJSON();

        point = gtjson.readPoint( new StringReader( "{\"type\":\"Point\", \"coordinates\":[" + longitude + ","
            + latitude + "]}" ) );

        return point;
    }

    /**
     * Check if GeometryJSON point created with this coordinate is valid.
     */
    public static boolean checkGeoJsonPointValid( double longitude, double latitude )
    {
        try
        {
            return getGeoJsonPoint( longitude, latitude ).isValid();
        }
        catch ( Exception ex )
        {
            return false;
        }
    }

    /**
     * Check if the point coordinate falls within the polygon/MultiPolygon Shape
     */
    public static boolean checkPointWithMultiPolygon( double longitude, double latitude, 
        String multiPolygonJson, String featureType )
    {
        try
        {
            boolean contains = false;

            GeometryJSON gtjson = new GeometryJSON();

            Point point = getGeoJsonPoint( longitude, latitude );

            if ( point != null && point.isValid() )
            {
                if ( featureType.compareTo( OrganisationUnit.FEATURETYPE_POLYGON ) == 0 )
                {
                    Polygon polygon = gtjson.readPolygon( new StringReader( 
                        "{\"type\":\"Polygon\", \"coordinates\":" + multiPolygonJson + "}" ) );

                    contains = polygon.contains( point );
                }
                else if ( featureType.compareTo( OrganisationUnit.FEATURETYPE_MULTIPOLYGON ) == 0 )
                {
                    MultiPolygon multiPolygon = gtjson.readMultiPolygon( new StringReader(
                        "{\"type\":\"MultiPolygon\", \"coordinates\":" + multiPolygonJson + "}" ) );

                    contains = multiPolygon.contains( point );
                }
            }

            return contains;
        }
        catch ( Exception ex )
        {
            return false;
        }
    }
    
    public static final String replaceUnsafeSvgText( String svg )
    {
        if ( svg == null )
        {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        
        Matcher matcher = SVG_TEXT_PATTERN.matcher( svg );
        
        while ( matcher.find() )
        {
            String text = matcher.group( 1 );
            
            if ( text != null && !text.isEmpty() )
            {
                text = "text=\"" + text.replaceAll( "[<>&]", "" ) + "\"";                
                matcher.appendReplacement( sb, text );
            }
        }
        
        return matcher.appendTail( sb ).toString();
    }
}
