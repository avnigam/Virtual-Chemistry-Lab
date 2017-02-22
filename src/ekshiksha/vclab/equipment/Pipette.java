package ekshiksha.vclab.equipment;

import ekshiksha.vclab.lab.WorkBench;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import ekshiksha.vclab.lab.Cupboard;
import java.awt.geom.Rectangle2D;

/**
 * this class is responsible for equipment pipette.
 * this class can do the following:
 * <ul>
 *     <li> initialization of pipette.
 *     <li> drawing of pipette.     
 * </ul>
 
 * @author mayur
 */
public class Pipette extends Equipment {

    WorkBench environ;
    Cupboard environ2;
    private Vector< Point2D.Float> coords;
    private Vector< Color> colors;
    float h, w, h1, w1;
    //Thread ob;
    Graphics g;
    Point2D.Float p;
    int imght, imgwt, imgx, imgy;
    private BufferedImage img;
    private int k;
    private int o = 1;
    private int het;
    int j;
    int j1 = 0;
    boolean isPour = false;
    float s;
    private Equipment toFill;
    private Equipment toPour;

    public Pipette(Point2D.Float origin, WorkBench e) {
        super("Pipette", origin);
        this.setCupboardFlag(false);
        isDrawable = true;
        environ = e;
        coords = new Vector< Point2D.Float>();
        colors = new Vector< Color>();
        //ob=new Thread(this);
        initEquipment();
        //ob.start();
        h1 = environ.getHeight();
        w1 = environ.getWidth();

    }
/**
     *  This is the WorkBench constructor for the Bottle object.
     * @param id  equipment id
     * @param eqname  equipment name
     * @param origin  equipment origin
     * @param quantity  equipment quantity
     * @param size  equipment size
     * @param contents contents of equipment
     * @param contentColor color of content
     * @param strength strength of equipment
     * @param environ environ
     * @throws IOException 
     */
    public Pipette(int id, String eqname, Point2D.Float origin, float quantity, int size, String contents, Color contentColor, float strength, WorkBench environ) {
        super(id, eqname, origin, quantity, size, contents, contentColor, strength);
        this.setCupboardFlag(false);
        isDrawable = true;
        this.environ = environ;
        coords = new Vector< Point2D.Float>();
        colors = new Vector< Color>();
        //ob=new Thread(this);
        isFull = false;
        isFill = false;
        j = 34;
        initEquipment();
        capacity = 20.0f;
        fraction = quantity / capacity;
        //ob.start();
        h1 = environ.getHeight();
        w1 = environ.getWidth();
        sizearr[0] = 10;
        sizearr[1] = 15;
        sizearr[2] = 20;
        sizearr[3] = 25;
        sizearr[4] = 30;



    }
 /**
     *  This is the Cupboard constructor for the Bottle object.
     * @param origin  equipment origin
     * @param e  object of store
     * @throws IOException 
     */
    public Pipette(Point2D.Float origin, Cupboard e) {

        super("Pipette", origin);
        this.setCupboardFlag(true);
        isDraggable = false;
        isDrawable = true;
        environ2 = e;
        coords = new Vector< Point2D.Float>();
        colors = new Vector< Color>();
        //ob=new Thread(this);
        initEquipment();
        //ob.start();
        h1 = environ2.getHeight();
        w1 = environ2.getWidth();
        sizearr[0] = 10;
        sizearr[1] = 15;
        sizearr[2] = 20;
        sizearr[3] = 25;
        sizearr[4] = 30;


    }
/**
     * This Method initializes the pipette
     * 
     * This function sets the coordinates of the equipment object in the array of points coords according to the angle parameter of the object.
     */
    public void initEquipment() {
        boolean temp = getCupboardFlag();
        if (!temp) {

            h = (float) ((double) (environ.getHeight()) / (double) 600.0);
            w = (float) ((double) (environ.getWidth()) / 600.0);
            s = chooseSize(size);
        } else {
            h = (float) ((double) (environ2.getHeight()) / (double) 600.0);
            w = (float) ((double) (environ2.getWidth()) / (double) 600.0);
            s = chooseSize(size);
        }
        coords = new Vector< Point2D.Float>();
        coords.clear();

        coords.addElement(new Point2D.Float((3 * w * s), (0 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (5 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (10 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (15 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (20 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (25 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (30 * h * s)));


        coords.addElement(new Point2D.Float((3 * w * s), (35 * h * s)));

        coords.addElement(new Point2D.Float((2 * w * s), (36 * h * s)));
        coords.addElement(new Point2D.Float((1 * w * s), (37 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (38 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (39 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (40 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (41 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (42 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (43 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (44 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (45 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (46 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (47 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (48 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (49 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (50 * h * s)));
        coords.addElement(new Point2D.Float((0 * w * s), (51 * h * s)));

        coords.addElement(new Point2D.Float((0 * w * s), (52 * h * s)));
        coords.addElement(new Point2D.Float((1 * w * s), (53 * h * s)));
        coords.addElement(new Point2D.Float((2 * w * s), (54 * h * s)));

        coords.addElement(new Point2D.Float((3 * w * s), (55 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (60 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (65 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (70 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (75 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (80 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (85 * h * s)));
        coords.addElement(new Point2D.Float((3 * w * s), (88 * h * s)));

        coords.addElement(new Point2D.Float((4 * w * s), (89 * h * s)));
        coords.addElement(new Point2D.Float((4 * w * s), (92 * h * s)));
        coords.addElement(new Point2D.Float((5 * w * s), (92 * h * s)));
        coords.addElement(new Point2D.Float((5 * w * s), (89 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (88 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (85 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (80 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (75 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (70 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (65 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (60 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (55 * h * s)));
        coords.addElement(new Point2D.Float((7 * w * s), (54 * h * s)));
        coords.addElement(new Point2D.Float((8 * w * s), (53 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (52 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (51 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (50 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (49 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (48 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (47 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (46 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (45 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (44 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (43 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (42 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (41 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (40 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (39 * h * s)));
        coords.addElement(new Point2D.Float((9 * w * s), (38 * h * s)));
        coords.addElement(new Point2D.Float((8 * w * s), (37 * h * s)));
        coords.addElement(new Point2D.Float((7 * w * s), (36 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (35 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (30 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (25 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (20 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (15 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (10 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (5 * h * s)));
        coords.addElement(new Point2D.Float((6 * w * s), (0 * h * s)));
        width = (9 * w * s);
        height = (92 * h * s);


        if (!isDrawable) {
            coords.clear();
        }


    }
/**
 * This method draws the equipment
 * @param g Graphics object
 * This function draws straight lines across the points stored in coords to generate the equipment image.
 *If the pipette is full then the contents are drawn.For filling the equipment this method calls the fill method of this equipment and calls pour method of the equipment that is pouring into this equipment.For pouring the equipment this method calls the fill method of this equipment and calls fill method of the equipment that is pouring into this equipment.
 */
    @Override
    public void drawEquipment(Graphics g) {


        if (fraction == 0.0f) {
            isFull = false;
        }
        g.setColor(borderColor);
        if (!isDrawable) {
            return;
        }

        for (int i = 0; i < coords.size() - 1; i++) {

            Point2D.Float s = coords.elementAt(i);
            Point2D.Float d = coords.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }

        if (isFill == true) {

            toPour.fill(-(capacity / 34.0f));

            fill(g, toPour);
        }
        if (isPour == true) {

            toFill.fill((capacity / 34.0f));
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(this.getContentColor());
            g2.fill((new Rectangle2D.Float((float) (originShift.x + (4 * s * ((double) (environ.getWidth()) / 600.0))), (originShift.y + height), (float) (s * ((double) (environ.getWidth()) / 600.0)), (toFill.height))));
            pour(g, toFill);
        }
        if (isFull == true) {
            fraction = 1.0f;
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(this.contentColor);
            Path2D.Float path = new Path2D.Float();
            Point2D.Float s = coords.elementAt(2);
            path.moveTo(s.x + originShift.x, s.y + originShift.y);
            for (int i = 0; i < coords.size() - 1; i++) {

                Point2D.Float s1 = coords.elementAt(i + 1);
                if(i<37)
                   path.lineTo(s1.x + originShift.x, s1.y + originShift.y);
                else
                   path.lineTo(s1.x + originShift.x-1, s1.y + originShift.y); 

            }
            path.lineTo(coords.elementAt(0).x + originShift.x, coords.elementAt(1).y + originShift.y);


            g2.fill(path);
        }


    }
/**
 * This method fills the pipette
 * @param g Graphics object
 * @param eq Equipment from which pipette is being filled
 * This method fills the equipment increasing quantity step wise. It uses the Path2D.Float object to fill the equipment.Accordingly the quantity and fraction of the equipment are changed. 
 */
    public void fill(Graphics g, Equipment eq) {

        isFill = true;

        toPour = eq;
        Graphics2D g2 = (Graphics2D) g;
        if (j >= 0) {
            this.setContentColor(toPour.contentColor);
            g2.setColor(toPour.contentColor);
            Path2D.Float path = new Path2D.Float();
            Point2D.Float s = coords.elementAt(j);
            path.moveTo(s.x + originShift.x, s.y + originShift.y);
            for (int i = j; i < coords.size() - j - 1; i++) {

                Point2D.Float s1 = coords.elementAt(i + 1);
                if(i<37)
                   path.lineTo(s1.x + originShift.x, s1.y + originShift.y);
                else
                   path.lineTo(s1.x + originShift.x-1, s1.y + originShift.y); 

            }
            path.lineTo(coords.elementAt(j).x + originShift.x, coords.elementAt(j).y + originShift.y);


            g2.fill(path);
            fraction += (1.0f / 34);


            j--;
            delaySimple(50);
            if (j == 0) {

                isFill = false;
                isFull = true;
                fraction = 1.0f;
            }


        }
    }
/**
 * This pipette is used to pour liquid from pipette
 * @param g Graphics object
 * @param eq Equipment to which liquid is poured
 * This method fills the equipment with decreasing quantity step wise. It uses the Path2D.Float object to fill the equipment.Accordingly the quantity and fraction of the equipment are changed. 
 */
    public void pour(Graphics g, Equipment eq) {
        toFill = eq;
        isPour = true;
        isFull = false;
        Graphics2D g2 = (Graphics2D) g;
        if (j1 < 35) {
            eq.setContentColor( this.getContentColor() );
            g2.setColor(this.contentColor);
            Path2D.Float path = new Path2D.Float();
            Point2D.Float s = coords.elementAt(j1);
            path.moveTo(s.x + originShift.x, s.y + originShift.y);
            for (int i = j1; i < coords.size() - j1 - 1; i++) {

                Point2D.Float s1 = coords.elementAt(i + 1);
                if(i<37)
                   path.lineTo(s1.x + originShift.x, s1.y + originShift.y);
                else
                   path.lineTo(s1.x + originShift.x-1, s1.y + originShift.y); 

            }
            path.lineTo(coords.elementAt(j1).x + originShift.x, coords.elementAt(j1).y + originShift.y);


            g2.fill(path);
            fraction -= (1.0f / 34);
            j1++;
            if (j1 == 34) {
                isPour = false;
                j1 = 0;
                j = 34;
                fraction = 0.0f;
            }
        }

        delaySimple(50);

    }
private void delaySimple(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) { /*
             * Do Nothing
             */ }
    }
/**
 * this method sets the originShift
 * @param pt new origin
 */
    public void setOriginShift(Point2D.Float pt) {
        originShift = pt;

    }
/**
 * This method returns the no of steps used for filling/pouring for a particular quantity
 * @param quantity quantity
 * @param eq Equipment
 * @return 
 * This function is used by the Demonstration module for stepwise filling and pouring into and from an equipment.
 */
    public float getIterationSteps(float quantity, Equipment eq) {
        toFill = eq;
        return 34;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
        if (this.quantity > capacity - 1) {
            isFull = true;
            fraction = 1.0f;
        } else {
            isFull = false;
            fraction = 0.0f;
        }
    }
/**
 * This methods initializes the equipment according to size and sets the capacity
 * @param size size of equipment  
 * @return size fraction according to current size of workbench
 * This sets the capacity of the equipment.And it initialises the variable s(size fraction).
 */
    private float chooseSize(int size) {
        capacity = (size * 5) + 5;
        float k;
        k = 0.75f + ((size - 1) * 0.15f);
        return k;

    }
/**
 * This method sets size
 * @param size size required
 */
    public void setSize(int size) {
        this.size = size;
        height = (92.0f * h * (0.75f + ((size - 1) * 0.15f)));
        width = (9f * w * (0.75f + ((size - 1) * 0.15f)));
    }
}