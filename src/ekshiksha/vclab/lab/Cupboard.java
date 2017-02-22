/*
 * 
 * harsh12
 */

package ekshiksha.vclab.lab;

import ekshiksha.vclab.equipment.Equipment;
import ekshiksha.vclab.util.Constants;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JPanel;

public class Cupboard extends JPanel implements ComponentListener
{
        private boolean showBasin;
        private boolean showTable; // no table in the cupboard panel
        private int w,h;
        public boolean shouldBeResized;
        
        public ArrayList<Equipment> cupboardEquipmentList;
        Table tables;
        Basin basin;
        Tiles tile;
        Shelf shelf;
        
        
	public Cupboard(boolean q,boolean r)
	{
                shouldBeResized=false;
                cupboardEquipmentList=new ArrayList<Equipment>();
                showBasin=q;
                setBounds( 0, 0,600,600);
                setLayout( null );
                tables=new Table(this);
                basin=new Basin(this);
                tile=new Tiles(this);
                shelf=new Shelf(this);
                w=this.getWidth();
                h=this.getHeight();
                addComponentListener(this);
		
	}
	
        
        public void setDrawThree(boolean q)
        {
            shelf.setDrawThree(q);
        }
        
    /**
     * This method calls the initEquipment() and drawEquipment() functions for each equipment in the cupboardEquipmentList.
     */        
    @Override
    public void paintComponent( Graphics g )
    {
        
    	super.paintComponent( g );
    	setBackground( Constants.WALL_COLOR );
        drawLab( g );
        for(int i=0;i<cupboardEquipmentList.size();i++)
        {
            (cupboardEquipmentList.get(i)).initEquipment();
            (cupboardEquipmentList.get(i)).drawEquipment(g);
        }
    	
        
    }
   
    /**
     * This method is used to draw tiles and tables.
     */
    public void drawLab( Graphics g )
    {
    	Vector< Vector < Point > > polygon = new Vector< Vector< Point > > ();
    	polygon = tile.getTileCoordinates();
        tile.fillTiles( polygon, g);
        if(showTable)tables.drawTable( g );
        shelf.drawShelf( g );
        if(showBasin) basin.drawBasin( g );
       
    }
   
    
    
   
    public void addApparatus( Equipment bk)
    {
        bk.initEquipment();
        cupboardEquipmentList.add(bk);
        
    }

    
    public void setShowTable(boolean st)
    {
        showTable=st;
    }
    
    public int getListSize()
    {
        return cupboardEquipmentList.size();
    }
    
    /**
     * This method is used resize all the equipments whenever the Applet window is resized.
     */
    public void componentResized(ComponentEvent ce) 
    {
        
        
        Point2D.Float pt;
        for(int i=0;i<getListSize()&&shouldBeResized;i++)
        {
            pt=new Point2D.Float( (int)((double)this.getWidth()*cupboardEquipmentList.get(i).originShift.x/w) , (int)((double)this.getHeight()*cupboardEquipmentList.get(i).originShift.y/h ));
            cupboardEquipmentList.get(i).setOriginShift(pt);
        }
        
        w=this.getWidth();
        h=this.getHeight();
        repaint();
        shouldBeResized=true;
    }

    public void componentMoved(ComponentEvent ce) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentShown(ComponentEvent ce) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentHidden(ComponentEvent ce) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
