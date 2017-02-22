
package ekshiksha.vclab.equipment;

import ekshiksha.vclab.lab.Cupboard;
import ekshiksha.vclab.lab.WorkBench;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * @author Aviral Nigam
 */
public class TestTube extends Equipment
{
    Equipment filling;
    private Vector< Point2D.Float > coords;
    Polygon chemical;
    Polygon trace;
    WorkBench environ;
    boolean isPour=false;
    boolean isTrace=false;
    Point2D.Float mouthLeft=new Point2D.Float(0,0);
    Point2D.Float mouthRight=new Point2D.Float(6,0);
    Point2D.Float tempLeft=new Point2D.Float(0.0f,0.0f);
    Point2D.Float tempRight=new Point2D.Float(0.0f,0.0f);
    float Awidth;
    float h,w,h1,w1;
    private Cupboard environ2;
    private int ht =50;

    /**
     * The constructor for WorkBench.
     * @param id : id of the equipment.
     * @param eqname : type of the equipment.
     * @param origin : initial position of the equipment.
     * @param quantity : initial quantity.
     * @param size : size of the equipment.
     * @param contents : name of the chemical present initially in the equipment.
     * @param contentColor : colour of the chemical present initially in the equipment.
     * @param strength : strength of the chemical present initially in the equipment.
     * @param environ : object of WorkBench.java
     */
    public TestTube(int id,String eqname, Point2D.Float origin,float quantity,int size,String contents,Color contentColor,float strength,WorkBench environ)
    {
        super(id,eqname,origin,quantity,size,contents,contentColor,strength);
        this.setCupboardFlag(false);
        isDrawable=true;
        coords = new Vector< Point2D.Float >();
        this.environ=environ;
        originShift = origin;
        h=(float) ((double)(environ.getHeight())/(double)600.0);
        w=(float) ((double)(environ.getWidth())/(double)600.0);
        
        this.size=size;
        for(int x=0;x<5;x++)
        {
            sizearr[x]=5+x*5;
        }
        chooseSize(size);

        fraction=0.0f;
        capacity=5.0f*size;

        angle=0.0;


        tempLeft=new Point2D.Float((coords.get(0).x),(coords.get(1).y+coords.get(0).y)*(1.0f-fraction));
        tempRight=new Point2D.Float((coords.get(6).x),(coords.get(6).y+coords.get(7).y)*(1.0f-fraction));

    }
    
    /**
    * The constructor for store.
    * @param origin : initial position of the equipment.
    * @param e : object of Cupboard.java 
    */
    public TestTube(Point2D.Float origin,Cupboard e)
    {

       super("TestTube",origin);
       this.setCupboardFlag(true);
       isDraggable=false;
       isDrawable=true;
       size=2;
        coords = new Vector< Point2D.Float >();
        this.environ2=e;
        originShift = origin;
        h=(float) ((double)(environ2.getHeight())/(double)600.0);
        w=(float) ((double)(environ2.getWidth())/(double)600.0);
        chooseSize(size);
        for(int x=0;x<5;x++)
        {
            sizearr[x]=5+x*5;
        }
        
        angle=0.0;

        tempLeft=new Point2D.Float((coords.get(0).x),(coords.get(1).y+coords.get(0).y)*(1.0f-fraction));
        tempRight=new Point2D.Float((coords.get(6).x),(coords.get(6).y+coords.get(7).y)*(1.0f-fraction));
    }
    
    /**
     * this function redraws the equipment for the present size of the window and the current angle. 
     */
    @Override
    public void initEquipment()
    {
        h1=h;
        w1=w;
        boolean temp=getCupboardFlag();
        if(!temp)
        {

         h=(float) ((double)(environ.getHeight())/(double)600.0);
         w=(float) ((double)(environ.getWidth())/(double)600.0);
        }
        else
        {
            h=(float) ((double)(environ2.getHeight())/(double)600.0);
            w=(float) ((double)(environ2.getWidth())/(double)600.0);
        }
        Point2D.Float px ;
        if(coords.size()>0)
        {
            for(int i=0;i<9;i++)
            {

                px = coords.elementAt( i );
                double nx = ((px.x*Math.cos( Math.toRadians( -angle ))) + (px.y*Math.sin( Math.toRadians( -angle ))) );
                double ny = ((px.y*Math.cos( Math.toRadians( -angle ))) - (px.x*Math.sin( Math.toRadians( -angle ))) );
                coords.setElementAt( new Point2D.Float( (float)nx, (float)ny ),i);
            }

            tempLeft=new Point2D.Float((coords.get(0).x)*(w/w1),(coords.get(1).y+coords.get(0).y)*(h/h1)*(1.0f-fraction));
            tempRight=new Point2D.Float((coords.get(6).x)*(w/w1),(coords.get(6).y+coords.get(7).y)*(h/h1)*(1.0f-fraction));

            for(int i=0;i<9;i++)
            {
                px = coords.elementAt( i );
                double nx = ((px.x*(w/w1)*Math.cos( Math.toRadians( angle ))) + (px.y*(h/h1)*Math.sin( Math.toRadians( angle ))) );
                double ny = ((px.y*(h/h1)*Math.cos( Math.toRadians( angle ))) - (px.x*(w/w1)*Math.sin( Math.toRadians( angle ))) );
                coords.setElementAt( new Point2D.Float( (float)nx, (float)ny ),i);
            }
            Awidth=(int)((2.9+size)*w);
            width=((5+(size-1))*w);
            height=((50+(size-1)*2)*h);
            if(!isDrawable)coords.clear();
        }
    }
    
    /**
     * This function rotates the equipment by the given angle(positive or negative)(in degrees). 
     * @param theta : the angle by which the equipment has to be rotated. 
     */
    @Override
    public void rotate( double theta )
    {
        angle=angle+theta;
    	for( int i = 0; i < coords.size(); i++ )
    	{
            Point2D.Float px = coords.elementAt( i );
            double nx = ((px.x*Math.cos( Math.toRadians( theta ))) + (px.y*Math.sin( Math.toRadians( theta ))) );
            double ny = ((px.y*Math.cos( Math.toRadians( theta ))) - (px.x*Math.sin( Math.toRadians( theta ))) );
            coords.setElementAt( new Point2D.Float( (float)nx, (float)ny ),i);
    	}
    	double mltx = mouthLeft.x;
    	double mlty = mouthLeft.y;
        mouthLeft.x = (float)((mltx*Math.cos( Math.toRadians( theta ))) + (mlty*Math.sin( Math.toRadians( theta ))) );
        mouthLeft.y = (float)((mlty*Math.cos( Math.toRadians( theta ))) - (mltx*Math.sin( Math.toRadians( theta ))) );
        mltx = mouthRight.x;
        mlty = mouthRight.y;
        mouthRight.x = (float)((mltx*Math.cos( Math.toRadians( theta ))) + (mlty*Math.sin( Math.toRadians( theta ))) );
        mouthRight.y = (float)((mlty*Math.cos( Math.toRadians( theta ))) - (mltx*Math.sin( Math.toRadians( theta ))) );
        isPour=true;
        if(theta<0)
        {
            isTrace=false;
        }
        if(angle==0)
        {
            isPour=false;
        }
    }
    
    /**
     * This function fills the equipment by the given quantity.
     * @param quant : quantity to be filled. 
     */
    @Override
    public void fill(float quant)
    {
        fraction=fraction+(quant/capacity);
        quantity=fraction*capacity;
        tempLeft=new Point2D.Float((coords.get(0).x),(coords.get(1).y+coords.get(0).y)*(1.0f-fraction));
        tempRight=new Point2D.Float((coords.get(6).x),(coords.get(6).y+coords.get(7).y)*(1.0f-fraction));
        isTrace=true;
    }

    /**
     * This function  draws lines between the points plotted in chooseSize(or initEquipment), thereby drawing the equipment.
     * @param g : Object of Graphics class. 
     */
    @Override
    public void drawEquipment(Graphics g)
    {
        g.setColor(borderColor);
        if(!isDrawable)return;
        for( int i = 0; i < coords.size() - 1; i++ )
        {
            Point2D.Float s = coords.elementAt( i );
            Point2D.Float d = coords.elementAt( i + 1 );
            Graphics2D g2=(Graphics2D)g;
            g2.draw(new Line2D.Float( s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y ));
        }
        if(fraction>0)
        {
            drawContents(g);
        }
    }

    /**
     * Sets the quantity to the given value.
     * @param quant : the value to which the quantity of the equipment has to be set.
     */
    @Override
    public void setQuantity(float quant)
    {
        quantity = quant;
        fraction=quantity/capacity;
        tempLeft=new Point2D.Float((coords.get(0).x),(coords.get(1).y+coords.get(0).y)*(1.0f-fraction));
        tempRight=new Point2D.Float((coords.get(6).x),(coords.get(6).y+coords.get(7).y)*(1.0f-fraction));
    }

    /**
     * this function calculates the angle to which the equipment has to be rotated for pouring the given quantity.
     * @param quant : quantity to be poured.
     * @param eq : object of the destination equipment.
     * @return : angle, to which the equipment has to be rotated for pouring the given quantity.
     */
    @Override
    public float getIterationSteps(float quant,Equipment eq)
    {
        filling=eq;
        float temp_angle;
        float final_quant=(capacity*fraction) - quant;
        if(final_quant>(capacity*0.5))
        {
            temp_angle=(float)Math.atan(((capacity-final_quant)/capacity)*height*2/Awidth);
        }
        else
        {
            temp_angle=(float)Math.atan((height*height)/((final_quant/capacity)*height*Awidth*2));
        }
        return (float)Math.toDegrees(temp_angle);
    }

    /**
     * This function draws the liquid contained in the equipment according to the quantity and the angle.
     * it is also responsible for reducing the quantity of the current equipment and filling the destination equipment by the appropriate quantity.  
     * @param g : Object of Graphics class.
     */
    public void drawContents(Graphics g)
    {
        int i;
        chemical=new Polygon();
        trace=new Polygon();
        float dec;
        float diff=(float)((Awidth*Math.tan(Math.toRadians(angle)))/2);
        float len_t=(float)(Math.pow(Awidth*height*fraction*2*Math.tan(Math.toRadians(angle)),0.5));
        float base=(float)(len_t/(Math.tan(Math.toRadians(angle))));
        if(isPour==false)
        {
            Point2D.Float temp=new Point2D.Float(0.0f,0.0f);
            temp=coords.get(0);
            chemical.addPoint((int)(temp.x+ originShift.x+1), (int)(((coords.get(0).y+ coords.get(1).y)*(1.0f-fraction))+originShift.y));
            for(i=1;i<coords.size()-2;i++)
            {
                temp=coords.get(i);
                if(i< coords.size()/4)
                    chemical.addPoint((int)(temp.x+ originShift.x+1), (int)(temp.y+ originShift.y));
                else
                    chemical.addPoint((int)(temp.x+ originShift.x), (int)(temp.y+ originShift.y));
            }
            temp=coords.get(i);
            chemical.addPoint((int)(temp.x+ originShift.x), (int)(((coords.get(0).y+ coords.get(1).y)*(1.0f-fraction))+originShift.y));
        }
       else
        {
            Point2D.Float px ;
            double nx;
            double ny;
            px= tempRight;
            nx= ((px.x*Math.cos( Math.toRadians( angle ))) + (px.y*Math.sin( Math.toRadians( angle ))) );
            if(((int)(nx+originShift.x+(diff*Math.sin(Math.toRadians(angle))))<(int)(originShift.x+coords.elementAt(6).x))||(base>width))
            {
                px = tempLeft;
                nx = ((px.x*Math.cos( Math.toRadians( angle ))) + (px.y*Math.sin( Math.toRadians( angle ))) );
                if((int)(nx+originShift.x-(diff*Math.sin(Math.toRadians(angle))))<(int)(originShift.x+coords.elementAt(0).x))
                {
                    chemical.addPoint((int)(originShift.x+coords.elementAt(0).x),(int)(originShift.y+coords.elementAt(0).y));
                    for(i=1;i<coords.size()-2;i++)
                    {
                        px=coords.get(i);
                        chemical.addPoint((int)(px.x+ originShift.x), (int)(px.y+ originShift.y));
                    }
                    dec=(float)((0.5*Awidth*(diff-(height*(1-fraction))))/(Awidth*height));
                    fraction-=dec;
                    quantity-=fraction*capacity;
                    filling.fill(dec*capacity);
                    filling.setContentColor(this.getContentColor());
                    tempRight.y=tempRight.y*(1-fraction)/(1-(fraction+dec));
                    tempLeft.y=tempLeft.y*(1-fraction)/(1-(fraction+dec));
                    px= tempRight;
                    nx= ((px.x*Math.cos( Math.toRadians( angle ))) + (px.y*Math.sin( Math.toRadians( angle ))) );
                    ny= ((px.y*Math.cos( Math.toRadians( angle ))) - (px.x*Math.sin( Math.toRadians( angle ))) );
                    chemical.addPoint((int)(nx+originShift.x+(diff*Math.sin(Math.toRadians(angle)))),(int)(ny+originShift.y+(diff*Math.cos(Math.toRadians(angle)))));
                }
                else
                {
                    ny = ((px.y*Math.cos( Math.toRadians( angle ))) - (px.x*Math.sin( Math.toRadians( angle ))) );
                    chemical.addPoint((int)(nx+originShift.x-(diff*Math.sin(Math.toRadians(angle)))),(int)(ny+originShift.y-(diff*Math.cos(Math.toRadians(angle)))));
                    for(i=1;i<coords.size()-2;i++)
                    {
                        px=coords.get(i);
                        chemical.addPoint((int)(px.x+ originShift.x), (int)(px.y+ originShift.y));
                    }
                    px= tempRight;
                    nx= ((px.x*Math.cos( Math.toRadians( angle ))) + (px.y*Math.sin( Math.toRadians( angle ))) );
                    ny= ((px.y*Math.cos( Math.toRadians( angle ))) - (px.x*Math.sin( Math.toRadians( angle ))) );
                    chemical.addPoint((int)(nx+originShift.x+(diff*Math.sin(Math.toRadians(angle)))),(int)(ny+originShift.y+(diff*Math.cos(Math.toRadians(angle)))));
                }
            }
            else
            {
                if(len_t>height)
                {
                    chemical.addPoint((int)(originShift.x+coords.elementAt(0).x),(int)(originShift.y+coords.elementAt(0).y));
                    for(i=1;i<coords.size()-5;i++)
                    {
                        px=coords.get(i);
                        chemical.addPoint((int)(px.x+ originShift.x), (int)(px.y+ originShift.y));
                    }
                    dec=(float)((height/(2*Math.tan(Math.toRadians(angle))))/(Awidth));
                    filling.fill(-(dec-fraction)*capacity);
                    filling.setContentColor(this.getContentColor());
                    tempRight.y=tempRight.y*(1-dec)/(1-fraction);
                    tempLeft.y=tempLeft.y*(1-dec)/(1-fraction);
                    fraction=dec;
                    quantity=fraction*capacity;
                    base=(float)(height/(Math.tan(Math.toRadians(angle))));
                    chemical.addPoint((int)(originShift.x+coords.elementAt(3).x+base*Math.cos(Math.toRadians(angle))),(int)(originShift.y+coords.elementAt(3).y-base*Math.sin(Math.toRadians(angle))));
                }
                else
                {
                    chemical.addPoint((int)(originShift.x+coords.elementAt(1).x-len_t*Math.sin(Math.toRadians(angle))),(int)(originShift.y+coords.elementAt(1).y-len_t*Math.cos(Math.toRadians(angle))));
                    for(i=1;i<coords.size()-5;i++)
                    {
                        px=coords.get(i);
                        chemical.addPoint((int)(px.x+ originShift.x), (int)(px.y+ originShift.y));
                    }
                    base=(float)(len_t/(Math.tan(Math.toRadians(angle))));
                    chemical.addPoint((int)(originShift.x+coords.elementAt(3).x+base*Math.cos(Math.toRadians(angle))),(int)(originShift.y+coords.elementAt(3).y-base*Math.sin(Math.toRadians(angle))));
                }
            }
        }
        if(isTrace==true)
        {
            for(i=8;i>3;i--)
            {
                trace.addPoint((int)(coords.get(i).x+ originShift.x), (int)(coords.get(i).y+ originShift.y));
            }
            for(i=4;i<8;i++)
            {
                trace.addPoint((int)(coords.get(i).x+ originShift.x-3), (int)(coords.get(i).y+ originShift.y-3));
            }
            isTrace=false;
        }
        g.setColor(this.contentColor);
        g.fillPolygon(chemical);
        g.fillPolygon(trace);
    }
    
    /**
     * Called in the constructor, it draws the equipment according to the given size and calculates all the required fields.
     * @param size : the size of the equipment to be drawn.
     */
    private void chooseSize(int size)
    {      
        capacity = (size)*5;
	
        mouthRight=new Point2D.Float(5+(size-1),0);
        
        ht=(int) ((50+(size-1)*2));
        Awidth=(int)((2.9+size)*w);
        width=((5+(size-1))*w);
        height=((50+(size-1)*2)*h);

        coords.clear();
        coords.addElement( new Point2D.Float((mouthLeft.x)*w, mouthLeft.y*h));     
    	coords.addElement( new Point2D.Float((mouthLeft.x)*w, (mouthLeft.y+ht) *h));
    	coords.addElement( new Point2D.Float(((mouthLeft.x)+1)*w,(mouthLeft.y+ht+1) *h));
    	coords.addElement( new Point2D.Float(((mouthLeft.x)+2)*w, (mouthLeft.y+ht+2) *h));
    	coords.addElement( new Point2D.Float(((mouthRight.x)-2)*w, (mouthRight.y+ht+2)*h));
    	coords.addElement( new Point2D.Float(((mouthRight.x)-1)*w, (mouthRight.y+ht+1) *h));
    	coords.addElement( new Point2D.Float(((mouthRight.x))*w, (mouthRight.y+ht) *h));
    	coords.addElement( new Point2D.Float(((mouthRight.x))*w,((mouthRight.y))*h) );
    	coords.addElement( new Point2D.Float((mouthRight.x)*w, mouthRight.y*h) ); 
    }
    
    /**
     * Sets the size of the equipment.
     * @param size : size of the equipment.
     */
    @Override
    public void setSize(int size)
    {
        this.size=size;
        chooseSize(size);
    }
}