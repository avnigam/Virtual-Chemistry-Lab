/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.lab;


import java.awt.*;
import ekshiksha.vclab.util.Constants;
/**
 * This class is used to draw the table of workbench
 * @author mayur12
 */
public class Table {

     private Point[]table;
     private Point plcmntStart, plcmntEnd;
     WorkBench environ;
     Cupboard environ2;
     boolean cupboardFlag;
/**
 * Constructor for Table class used by Workbench
 * @param environ Object of workbench
 * 
 */
     public Table(WorkBench environ) {

         table = new Point[4];
         this.environ= environ;
         cupboardFlag=false;
     }
/**
 * Constructor for Table class used by Cupboard
 * @param environ Object of Cupboard
 * 
 */
     public Table(Cupboard environ) {

         table = new Point[4];
         this.environ2= environ;
         cupboardFlag=true;
     }
/**
 * This Method draws the table
 * @param g 
 * The coordinates of table are decided according to the size of workbench/cupboard and then the table is drawn accordingly.
 */
     public void drawTable( Graphics g )
     {
    	g.setColor( Color.BLACK );
    	double hx;
    	double wx;

        if(!cupboardFlag)
        {
            hx = (double)environ.getHeight();
            wx = (double)environ.getWidth();
        }
        else
        {
            hx = (double)environ2.getHeight();
            wx = (double)environ2.getWidth();
        }

    	int floorLimit = (int)(hx/Constants.FLOOR_LIMIT);

    	int sx = 0;
    	int sy = (int)(hx - floorLimit - (int)(hx*Constants.LENGTH_EDGE_HORIZONTAL_LINE));
    	int ey = sy - (int)(hx*Constants.TABLE_HEIGHT_FROM_EDGE_STARTINGS);
    	int ex = (int)(wx*Constants.FRACTION_FIRST);
    	table[0] = new Point( sx, sy );
    	table[1] = new Point( ex, ey );
    	g.drawLine( sx, sy, ex, ey );

    	sx = ex;
    	sy = ey;
    	ex = sx + (int)(wx*Constants.FRACTION_SECOND);
    	ey = sy;
    	table[2] = new Point( ex, ey );
    	g.drawLine( sx, sy, ex, ey );

    	sx = ex;
    	sy = ey;
    	ex = sx + (int)(wx*Constants.FRACTION_FIRST);
    	ey = (int)(hx - floorLimit - (int)(hx*Constants.LENGTH_EDGE_HORIZONTAL_LINE));

    	table[3] = new Point( ex, ey );
    	g.drawLine( sx, sy, ex, ey );

    	g.drawLine( table[0].x, table[0].y, table[3].x, table[3].y );

    	int xarr[] = {table[0].x, table[3].x, table[3].x, table[0].x};
    	int yarr[] = {table[0].y, table[3].y, table[3].y + (int)(hx*Constants.TABLE_CROSS_SECION_WIDTH), table[0].y + (int)(hx*Constants.TABLE_CROSS_SECION_WIDTH)};
    	g.setColor( Constants.LEG_COLOR );
    	g.fillPolygon( xarr, yarr, 4 );

    	xarr[0] = table[0].x;
    	xarr[1] = table[1].x;
    	xarr[2] = table[2].x;
    	xarr[3] = table[3].x;
        

    	yarr[0] = table[0].y;
    	yarr[1] = table[1].y;
    	yarr[2] = table[2].y;
    	yarr[3] = table[3].y;
        
    	g.setColor( Constants.LEG_EFFECT );
    	g.fillPolygon( xarr, yarr, 4 );

    	xarr[0] = (int)(wx*Constants.EDGE_LEG_DISTANCE);
    	xarr[1] = (int)(xarr[0] + (int)(wx*Constants.LEG_WIDTH));
    	xarr[2] = xarr[1];
    	xarr[3] = xarr[0];

    	yarr[0] = table[0].y + (int)(hx*Constants.TABLE_CROSS_SECION_WIDTH);
    	yarr[1] = table[0].y + (int)(hx*Constants.TABLE_CROSS_SECION_WIDTH);
    	yarr[2] = yarr[1] + (int)(hx*Constants.FRONT_LEG_HEIGTH);
    	yarr[3] = yarr[2];

    	g.setColor( Constants.LEG_COLOR );
    	g.fillPolygon( xarr, yarr, 4 );

    	xarr[0] = (int)(wx - xarr[0]);
    	xarr[1] = (int)(wx - xarr[1]);
    	xarr[2] = xarr[1];
    	xarr[3] = xarr[0];

    	g.setColor( Constants.LEG_COLOR );
    	g.fillPolygon( xarr, yarr, 4 );

    	xarr[0] = (int)(wx*Constants.EDGE_LEG_DISTANCE) + (int)(wx*Constants.LEG_WIDTH);
    	xarr[1] = xarr[0] + 10;
    	xarr[2] = xarr[1];
    	xarr[3] = xarr[0];

    	yarr[0] = yarr[0];
    	yarr[1] = yarr[1];
    	yarr[2] = yarr[2] - 8;

    	g.setColor( Constants.LEG_EFFECT );
    	g.fillPolygon( xarr, yarr, 4 );

    	xarr[0] = (int)(wx - xarr[0]);
    	xarr[1] = (int)(wx - xarr[1]);
    	xarr[2] = (int)(wx - xarr[2]);
    	xarr[3] = (int)(wx - xarr[3]);

    	g.setColor( Constants.LEG_EFFECT );
    	g.fillPolygon( xarr, yarr, 4 );

    	plcmntStart = new Point( (table[0].x + table[1].x)/2, (table[0].y + table[1].y)/2 );
    }
    

}
