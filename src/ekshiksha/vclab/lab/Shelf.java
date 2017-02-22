/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.lab;

/**
 *
 * @author harsh12
 */
import ekshiksha.vclab.util.Constants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This class is used to draw shelves in cupboard and workbench.
 * @author harsh12
 */
public class Shelf
{

    private Point[] table;
    public Rectangle shelves[];
    private boolean drawThree;
    Cupboard environ;
    WorkBench environ2;
    boolean cupboardFlag;

    /**
     * Constructor for Cupboard.
     * For cupboard, an extra third shelf is to be drawn
     * @param environ 
     */
    public Shelf(Cupboard environ)
    {
        this.environ = environ;
        shelves = new Rectangle[2];
        table = new Point[4];
        drawThree=false;
        cupboardFlag=true;
    }
    
    /**
     * Constructor for workbench
     * 
     * @param environ 
     */
    public Shelf(WorkBench environ)
    {
        this.environ2 = environ;
        shelves = new Rectangle[2];
        table = new Point[4];
        drawThree=false;
        cupboardFlag=false;
    }
    
    public void setDrawThree(boolean q)
    {
        drawThree=q;
    }
    
    
    
    /**
     * This method is to draw Shelves mainly using fillPolygon() function
     * For cupboard extra third shelf is to be drawn
     * @param g 
     */
    public void drawShelf( Graphics g )
    {
    double hx;
    	double wx;
        
        if(cupboardFlag)
        {
            hx = (double)environ.getHeight();
            wx = (double)environ.getWidth();
        }
        else
        {
            hx = (double)environ2.getHeight();
            wx = (double)environ2.getWidth();
        }

        float multFactor=(float) 1.5;
        int offset=(int)(multFactor*hx*Constants.TABLE_HEIGHT_FROM_EDGE_STARTINGS);

        if(!drawThree)offset=0;
        //setBounds(0,0,1024,700);
    	int floorLimit = (int)(hx/Constants.FLOOR_LIMIT);

    	int sx = 0;
    	int sy = (int)(hx - floorLimit - (hx*Constants.LENGTH_EDGE_HORIZONTAL_LINE));
    	int ey = sy - (int)(hx*Constants.TABLE_HEIGHT_FROM_EDGE_STARTINGS);
    	int ex = (int)(wx*Constants.FRACTION_FIRST);
    	table[0] = new Point( sx, sy );
    	table[1] = new Point( ex, ey );

        sx = ex;
    	sy = ey;
    	ex = sx + (int)(wx*Constants.FRACTION_SECOND);
    	ey = sy;
    	table[2] = new Point( ex, ey );
    	int ax[] = new int[4];
    	int ay[] = new int[4];

    	int bx[] = new int[4];
    	int by[] = new int[4];

    	ax[0] = table[1].x;
    	ax[1] = table[2].x;
    	ax[2] = ax[1];
    	ax[3] = ax[0];

    	ay[0] = (int)(hx*Constants.TOP_SHELF_MARGIN);
    	ay[1] = ay[0];
    	ay[2] = table[1].y - Constants.BOTTOM_SHELF_MARGIN;
    	ay[3] = ay[2];

    	bx[0] = ax[0] + (int)(wx*Constants.SHELF_DX);
    	bx[1] = ax[1] - (int)(wx*Constants.SHELF_DX);
    	bx[2] = ax[2] - (int)(wx*Constants.SHELF_DX);
    	bx[3] = ax[3] + (int)(wx*Constants.SHELF_DX);

    	by[0] = ay[0] + (int)(hx*Constants.SHELF_DY);
    	by[1] = ay[1] + (int)(hx*Constants.SHELF_DY);
    	by[2] = ay[2] - (int)(hx*Constants.SHELF_DY);
    	by[3] = ay[3] - (int)(hx*Constants.SHELF_DY);

    	g.setColor( Color.LIGHT_GRAY );
    	int pxarr[] = {ax[0], bx[0], bx[3], ax[3]};
    	int pyarr[] = {ay[0], by[0], by[3]+offset, ay[3]+offset};
        // store ax[0] from here
    	g.fillPolygon(pxarr, pyarr, 4 );   //  left side wall

    	pxarr[0] = ax[0];
    	pxarr[1] = ax[1];
    	pxarr[2] = bx[1];
    	pxarr[3] = bx[0];

    	pyarr[0] = ay[0];
    	pyarr[1] = ay[1];
    	pyarr[2] = by[1];
    	pyarr[3] = by[0];

    	g.setColor( Constants.TOP_BOTTOM_COLOR );
    	g.fillPolygon(pxarr, pyarr, 4 );  // topmost wall

    	pxarr[0] = bx[1];
    	pxarr[1] = ax[1];
    	pxarr[2] = ax[2];
    	pxarr[3] = bx[2];

    	pyarr[0] = by[1];
    	pyarr[1] = ay[1];
    	pyarr[2] = ay[2]+offset;
    	pyarr[3] = by[2]+offset;

    	g.setColor( Color.LIGHT_GRAY );
    	g.fillPolygon(pxarr, pyarr, 4 ); // right side wall

    	pxarr[0] = bx[3];
    	pxarr[1] = bx[2];
    	pxarr[2] = ax[2];
    	pxarr[3] = ax[3];

    	pyarr[0] = by[3]+offset;
    	pyarr[1] = by[2]+offset;
    	pyarr[2] = ay[2]+offset;
    	pyarr[3] = ay[3]+offset;

    	g.setColor( Constants.TOP_BOTTOM_COLOR );
    	g.fillPolygon(pxarr, pyarr, 4 );  // bottom wall

        if(drawThree) // in case of three shelves additional middle horiza=ontal plane is to be drawn
        {
            pxarr[0] = bx[3];
    	pxarr[1] = bx[2];
    	pxarr[2] = ax[2];
    	pxarr[3] = ax[3];

    	pyarr[0] = by[3];
    	pyarr[1] = by[2];
    	pyarr[2] = ay[2];
    	pyarr[3] = ay[3];

    	g.setColor( Constants.TOP_BOTTOM_COLOR );
    	g.fillPolygon(pxarr, pyarr, 4 );

        }

    	int spaceLength = by[3] - by[0];
    	int spaceWidth = ax[1] - ax[0];

    	int midy = by[0] + (int)(spaceLength/Constants.MID_SHELF_SLIDER);

    	pxarr[0] = ax[0];
    	pxarr[1] = bx[0];
    	pxarr[2] = bx[1];
    	pxarr[3] = ax[1];

    	pyarr[0] = midy;
    	pyarr[1] = pyarr[0] - (int)(hx*Constants.SHELF_DY);
    	pyarr[2] = pyarr[1];
    	pyarr[3] = pyarr[0];

    	g.setColor( Constants.TOP_BOTTOM_COLOR );
    	g.fillPolygon( pxarr, pyarr, 4 );  // middle horizontal plane



    	shelves[0] = new Rectangle(bx[0], by[0], bx[1] - bx[0], pyarr[1] - by[0] + (int)(hx*Constants.SHELF_DY)/2 );
    	shelves[1] = new Rectangle(bx[0], pyarr[0], bx[1] - bx[0], by[3] - pyarr[0] + (int)(hx*Constants.SHELF_DY)/2 );

    	g.setColor( Color.BLACK );
    	g.drawLine( pxarr[0], pyarr[0], pxarr[1], pyarr[1] );
    	g.drawLine( pxarr[2], pyarr[2], pxarr[3], pyarr[3] );
    	g.drawLine( ax[0], ay[0], bx[0], by[0] );
    	g.drawLine( ax[1], ay[1], bx[1], by[1] );

    	g.drawLine( ax[3], ay[3], bx[3], by[3] );
        if(drawThree) g.drawLine( ax[3], ay[3]+offset, bx[3], by[3]+offset );

    	g.drawLine( ax[2], ay[2], bx[2], by[2] );
        if(drawThree) g.drawLine( ax[2], ay[2]+offset, bx[2], by[2]+offset );

    	g.drawLine( ax[0], ay[0], ax[3], ay[3] );
        if(drawThree) g.drawLine( ax[0], ay[0]+offset, ax[3], ay[3] +offset);

    	if(drawThree)g.drawLine( ax[1], ay[1], ax[2], ay[2]+offset );
        else g.drawLine( ax[1], ay[1], ax[2], ay[2]);

    	g.drawLine( ax[0], ay[0], ax[1], ay[1] );

    	g.drawLine( ax[2], ay[2], ax[3], ay[3] );
        if(drawThree) g.drawLine( ax[2], ay[2]+offset, ax[3], ay[3]+offset );

    	g.drawLine( bx[0], by[0], pxarr[1], pyarr[1] );
    	g.drawLine( bx[1], by[1], pxarr[2], pyarr[2] );

    	g.drawLine( pxarr[1], pyarr[1] + (int)(hx*Constants.SHELF_DY), bx[3], by[3] );
        if(drawThree) g.drawLine( pxarr[1], pyarr[1] + (int)(multFactor*hx*Constants.SHELF_DY) + offset , bx[3], by[3] + offset );

    	g.drawLine( pxarr[2], pyarr[2] + (int)(hx*Constants.SHELF_DY), bx[2], by[2] );
        if(drawThree) g.drawLine( pxarr[2], pyarr[2] + (int)(multFactor*hx*Constants.SHELF_DY)+offset, bx[2], by[2]+offset );

    	g.drawLine( pxarr[0], pyarr[0], pxarr[3], pyarr[3] );
    	g.drawLine( pxarr[1], pyarr[1], pxarr[2], pyarr[2] );
    	g.drawLine( bx[0], by[0], bx[1], by[1] );

    	g.drawLine( bx[2], by[2], bx[3], by[3] );
        if(drawThree) g.drawLine( bx[2], by[2]+offset, bx[3], by[3] +offset);




    }

}