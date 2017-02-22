/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekshiksha.vclab.lab;

/**
 *
 * @author mayur12
 */
import java.awt.*;
import ekshiksha.vclab.util.Constants;
import java.util.*;
/**
 * Class to draw Tiles
 * @author mayur12
 */
public class Tiles {

    WorkBench environ;
    Cupboard environ2;
    boolean cupboardFlag;
/**
 * Constructor for Table class used by Workbench
 * @param environ Object of workbench
 * 
 */
    public Tiles(WorkBench environ)
    {
        this.environ=environ;
        cupboardFlag=false;
    }
/**
 * Constructor for Table class used by Cupboard
 * @param environ Object of Cupboard
 * 
 */
    public Tiles(Cupboard environ)
    {
        this.environ2=environ;
        cupboardFlag=true;
    }
/**
 * This Method is used to set the coordinates of tiles
 * @return Vector of tile coordinates
 */    

    public Vector< Vector < Point > >  getTileCoordinates()
    {
    	//vlines = new Vector< Line >();
        Vector <Line> hlines;
	hlines = new Vector< Line >();
    	int hx;
    	int wx;

        if(!cupboardFlag)
        {
            hx = environ.getHeight();
            wx = environ.getWidth();
        }
        else
        {
            hx = environ2.getHeight();
            wx = environ2.getWidth();
        };
    	int floorLimit = (int)(hx/Constants.FLOOR_LIMIT);//Constants.FLOOR_LIMIT;
        int dtheta = 0;
        int sx, sy, ex, ey;
        dtheta = 8;
        Line l;
        Vector< Line > lx = new Vector< Line >();
        for( int k = (int)(hx*Constants.TILE_SEPEEATION_AT_LOWEST_LEVEL); k < floorLimit; k += (int)(hx*Constants.TILE_SEPEEATION_AT_LOWEST_LEVEL) )
        {
        	l = new Line( new Point( 0, hx - k ), new Point( (int)( (floorLimit - k)/Math.tan( Math.toRadians( Constants.TILT_ANGLE_WALL - dtheta ) ) ), hx - floorLimit ) );
        	dtheta += 8;
        	lx.addElement( l );
        	//g.drawLine(l.s.x, l.s.y, l.e.x, l.e.y );
        }

        hlines.addElement( new Line(new Point(0, hx), new Point(0, hx - floorLimit ) ) );
        for( int idx = lx.size() - 1; idx >= 0; idx-- )
        {
        	 Line m = lx.elementAt( idx );
        	 hlines.addElement( new Line( new Point( m.s ), new Point( m.e ) ) );
        }
        dtheta = 0;
        for( int i = 0; i <= wx/2; i+= (int)(hx*Constants.TILE_SEPEEATION_AT_LOWEST_LEVEL) )
        {
        	sx = i;
        	sy = hx;
        	ey = hx - floorLimit;
        	int mx = 1;

        	if( i <= wx/2 )mx = 1;
        	else mx = -1;

        	ex = sx + (int)( mx*(floorLimit/ Math.tan( Math.toRadians( Constants.TILT_ANGLE + dtheta ) ) ) );
        	hlines.addElement( new Line( new Point( sx, sy ), new Point( ex, ey ) ) );
           // g.drawLine( sx, sy, ex, ey );

            dtheta += (int)(((90 - Constants.TILT_ANGLE )*(2*(int)(hx*Constants.TILE_SEPEEATION_AT_LOWEST_LEVEL)))/ (float)wx);
        }

        int sz = hlines.size();

        l = new Line( new Point( wx/2, hx ), new Point( wx/2, hx - floorLimit ) );
        hlines.addElement( l );
        //g.drawLine( l.s.x, l.s.y, l.e.x, l.e.y );

        for( int j = sz - 1 ; j >= 0; j-- )
        {
        	l = hlines.elementAt( j );
        	sx = wx - l.s.x;
        	sy = l.s.y;
        	ex = wx - l.e.x;
        	ey = l.e.y;
        	hlines.addElement( new Line( new Point( sx, sy ), new Point( ex, ey ) ) );
        	//g.drawLine( sx, sy, ex, ey );
        }


        for( int ix = 0; ix < hlines.size(); ix++ )
    	{
    	   Point s = hlines.elementAt( ix ).s;
    	   Point e = hlines.elementAt( ix ).e;

    	  
    	}

        Line ly = new Line( new Point( 0, hx - floorLimit ), new Point ( wx, hx - floorLimit ) );
        //g.setColor( Color.RED );
        //g.drawLine( 0, hx - floorLimit, wx, hx - floorLimit );

        Vector< Vector < Point > > polygon = new Vector< Vector< Point > > ();
        int ptr = 0, cx = 0;
        while( true )
        {
        	Point pre = ly.s;
        	int y, x;
        	Vector< Point >px = new Vector< Point >();
        	for( int ix = 0; ix < hlines.size(); ix++)
        	{
               y = ly.s.y;
               
               Point s = hlines.elementAt( ix ).s;
        	   Point e = hlines.elementAt( ix ).e;

               x = (((y - e.y)*(e.x - s.x))/(e.y - s.y)) + e.x;

               if( y <= s.y && y >= e.y )
               {
            	
            	if( pre.x == x && pre.y == y )px.remove( pre );
            	px.addElement( new Point( x, y ) );
                pre = new Point( x, y );
  
               }
               cx = 1 - cx;
        	}
        	
        	polygon.addElement( px );

        	if( ly.s.y == hx )break;

        	ly.s.y = hlines.elementAt( ptr+1 ).s.y;
        	ly.e.y = hlines.elementAt( ptr+1 ).s.y;
        	ptr += 1;
        }
        return polygon;
    }
    /**
     * This method fills the tiles alternately with black and white colours
     * @param px vector containing the coordinates of tiles
     * @param g Graphics object
     */
    public void fillTiles( Vector< Vector< Point > >px, Graphics g )
    {
    	int rs = 0;
    	for( int i = 0; i < px.size() - 1; i++ )
    	{
    		int cs = rs;
    		for( int j = 0, k = 0; j < px.elementAt( i ).size() - 1; j++ )
    		{
    			if( cs == 0 )g.setColor( Constants.TILE_COLOR_FIRST );
   	                else if(cs == 1)g.setColor( Constants.TILE_COLOR_SECOND );

    			Point p1 = px.elementAt( i ).elementAt( j );
   			    Point p2 = px.elementAt( i ).elementAt( j + 1 );
   			    Point p3 = px.elementAt( i + 1 ).elementAt( k );
   			    Point p4 = new Point();
    			if( j == 0 || j == px.elementAt( i ).size() - 2 )
    			    p4 = new Point( p3 );
    			else
    			{
    				p4 = new Point( p3 );
    				p3 = px.elementAt( i + 1 ).elementAt( k + 1 );
    				k += 1;
    			}
    		        int xpts[] = { p1.x, p2.x, p3.x, p4.x };
   	                int ypts[] = { p1.y, p2.y, p3.y, p4.y };

   	                g.fillPolygon( xpts, ypts, 4);
    			cs = 1 - cs;
    		}
    	}
    	rs = 1 - rs;
    }

}
