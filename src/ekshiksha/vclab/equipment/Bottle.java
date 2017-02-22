/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekshiksha.vclab.equipment;

import ekshiksha.vclab.lab.WorkBench;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Vector;
import ekshiksha.vclab.lab.Cupboard;

/**
 * this class is responsible for equipment bottle.
 * this class can do the following:
 * <ul>
 *     <li> initialization of bottle.
 *     <li> drawing of bottle.     
 * </ul>
 
 * @author anirban
 */
public class Bottle extends Equipment {

    WorkBench environ;
    Equipment filling;
    private Vector< Point2D.Float> coords;
    float h, w, h1, w1;
    boolean isPour = false;
    Point2D.Float mouthLeft = new Point2D.Float(0, 0);
    Point2D.Float mouthRight = new Point2D.Float(0, 0);
    Polygon chemical;
    Cupboard environ2;
/**
     * This is the WorkBench constructor for the Bottle object.
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
    public Bottle(int id, String eqname, Point2D.Float origin, float quantity, int size, String contents, Color contentColor, float strength, WorkBench environ) {
        super(id, eqname, origin, quantity, size, contents, contentColor, strength);
        this.setCupboardFlag(false);
        isDrawable = true;
        this.environ = environ;
        originShift = origin;
        angle = 0.0;
        coords = new Vector< Point2D.Float>();

        h = (float) ((double) (environ.getHeight()) / (double) 600.0);
        w = (float) ((double) (environ.getWidth()) / (double) 600.0);

        for (int x = 0; x < 3; x++) {
            sizearr[x] = 100.0f * (x + 1);
        }

        chooseSize(size);
        fraction = 0.5f;
        this.quantity = fraction * capacity;

        //  capacity=100.0f;


    }
/**
     *  This is the Cupboard constructor for the Bottle object.
     * @param origin  equipment origin
     * @param e  object of store
     * @throws IOException 
     */
    public Bottle(Point2D.Float origin, Cupboard e) {

        super("Bottle", origin);
        this.setCupboardFlag(true);
        isDraggable = false;
        isDrawable = true;
        environ2 = e;
        coords = new Vector< Point2D.Float>();
        originShift = origin;
        angle = 0.0;

        h = (float) ((double) (environ2.getHeight()) / (double) 600.0);
        w = (float) ((double) (environ2.getWidth()) / (double) 600.0);

        // size=1;
        for (int x = 0; x < 3; x++) {
            sizearr[x] = 100.0f * (x + 1);
        }
        size = 1;
        chooseSize(size);

        //size=3;

        // this.quantity=quantity;
        fraction = 0.50f;
        quantity = fraction * capacity;

        if (!isDrawable) {
            coords.clear();
        }

    }
/**
 * This Method initializes the bottle
 * This function sets the coordinates of the equipment object in the array of points coords according to the angle parameter of the object.
 */
    @Override
    public void initEquipment() {

        h1 = h;
        w1 = w;
        boolean temp = getCupboardFlag();
        if (!temp) {
            h = (float) ((double) (environ.getHeight()) / (double) 600.0);
            w = (float) ((double) (environ.getWidth()) / (double) 600.0);
        } else {
            h = (float) ((double) (environ2.getHeight()) / (double) 600.0);
            w = (float) ((double) (environ2.getWidth()) / (double) 600.0);
        }
        Point2D.Float px;

        for (int i = 0; i < 9; i++) {
            px = coords.elementAt(i);
            double nx = ((px.x * Math.cos(Math.toRadians(-angle))) + (px.y * Math.sin(Math.toRadians(-angle))));
            double ny = ((px.y * Math.cos(Math.toRadians(-angle))) - (px.x * Math.sin(Math.toRadians(-angle))));
            coords.setElementAt(new Point2D.Float((float) nx, (float) ny), i);
        }

        //  tempLeft=new Point2D.Float((coords.get(0).x)*(w/w1),(coords.get(1).y+coords.get(0).y)*(h/h1)*(1.0f-fraction));
        //   tempRight=new Point2D.Float((coords.get(6).x)*(w/w1),(coords.get(6).y+coords.get(7).y)*(h/h1)*(1.0f-fraction));

        for (int i = 0; i < 9; i++) {
            px = coords.elementAt(i);
            double nx = ((px.x * (w / w1) * Math.cos(Math.toRadians(angle))) + (px.y * (h / h1) * Math.sin(Math.toRadians(angle))));
            double ny = ((px.y * (h / h1) * Math.cos(Math.toRadians(angle))) - (px.x * (w / w1) * Math.sin(Math.toRadians(angle))));
            coords.setElementAt(new Point2D.Float((float) nx, (float) ny), i);
        }




    }
/**
 * This method draws equipment
 * @param g Graphics Object
 * This function draws straight lines across the points stored in coords to generate the equipment image and also sets the string content label beside the Equipment.
 */
    public void drawEquipment(Graphics g) {

        g.setColor(borderColor);
        if (!isDrawable) {
            return;
        }
        for (int i = 0; i < coords.size() - 1; i += 1) {
            Point2D.Float s = coords.elementAt(i);
            Point2D.Float d = coords.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }

        drawContents(g);

        int offsetY = 20, printX;
        printX = (int) (originShift.x);
        // contents= "H2SO4";
        if (!(strength == 0.0f || contents == null)) {
            g.drawString(Float.toString(strength) + "M " + contents, printX, (int) (originShift.y - offsetY));
        } else if (contents != null) {
            g.drawString(contents, printX, (int) (originShift.y - offsetY));
        }

    }
/**
 * This method is used to rotate the equipment
 * @param theta angle 
 * This geometrically rotates any given equipment object across the desired angle which has to be submitted in terms of degrees.
 */
    @Override
    public void rotate(double theta) {
        //filling=eq;
        angle = angle + theta;
        for (int i = 0; i < coords.size(); i++) {
            Point2D.Float px = coords.elementAt(i);
            double nx = ((px.x * Math.cos(Math.toRadians(theta))) + (px.y * Math.sin(Math.toRadians(theta))));
            double ny = ((px.y * Math.cos(Math.toRadians(theta))) - (px.x * Math.sin(Math.toRadians(theta))));
            coords.setElementAt(new Point2D.Float((float) nx, (float) ny), i);
        }
        double mltx = mouthLeft.x;
        double mlty = mouthLeft.y;
        mouthLeft.x = (float) ((mltx * Math.cos(Math.toRadians(theta))) + (mlty * Math.sin(Math.toRadians(theta))));
        mouthLeft.y = (float) ((mlty * Math.cos(Math.toRadians(theta))) - (mltx * Math.sin(Math.toRadians(theta))));
        mltx = mouthRight.x;
        mlty = mouthRight.y;
        mouthRight.x = (float) ((mltx * Math.cos(Math.toRadians(theta))) + (mlty * Math.sin(Math.toRadians(theta))));
        mouthRight.y = (float) ((mlty * Math.cos(Math.toRadians(theta))) - (mltx * Math.sin(Math.toRadians(theta))));
        isPour = true;
        if (angle == 0) {
            isPour = false;
        }
    }
/**
 * This method draws the contents of the equipment
 * @param g Graphics object
 * This function uses a polygon object to fill the equipment according to its current fraction and capacity by initializing the corner points of the polygon chemical suitably. It has blocks for various fractions of liquid to be filled for the equipment. It finally uses a fill polygon method.
 */
    public void drawContents(Graphics g) {


        chemical = new Polygon();
        int fx, fy;



        if (isPour == false) {
            Point2D.Float temp = new Point2D.Float(0.0f, 0.0f);

            temp = coords.get(2);
            chemical.addPoint((int) (temp.x + originShift.x + 1), (int) (temp.y + originShift.y));

            if (fraction > 0.0f && fraction <= 0.8f) {
                temp = coords.get(3);
                chemical.addPoint((int) (temp.x + originShift.x + 1), (int) (temp.y + originShift.y));


                temp = coords.get(4);
                chemical.addPoint((int) (temp.x + originShift.x), (int) (temp.y + originShift.y));

                float ch = coords.get(4).y - coords.get(5).y;
                float ch1 = coords.get(5).y - coords.get(0).y;
                chemical.addPoint((int) (temp.x + originShift.x), (int) (ch1 + (0.8f - fraction) * ch + originShift.y));

                chemical.addPoint((int) (coords.get(3).x + originShift.x), (int) (ch1 + (0.8f - fraction) * ch + originShift.y));
                g.setColor(contentColor);
                g.fillPolygon(chemical);
                g.setColor(Color.black);


            }

            if (fraction > 0.8f && fraction <= 0.93f) {
                float temp_frac = fraction;

                temp = coords.get(3);
                chemical.addPoint((int) (temp.x + originShift.x), (int) (temp.y + originShift.y));

                temp = coords.get(4);
                chemical.addPoint((int) (temp.x + originShift.x), (int) (temp.y + originShift.y));


                temp = coords.get(5);
                chemical.addPoint((int) (temp.x + originShift.x), (int) (temp.y + originShift.y));

                temp_frac = fraction - 0.8f;
                temp_frac = temp_frac / 0.13f;
                temp_frac *= 10;


                fx = (int) (temp.x + originShift.x) - (int) (temp_frac);
                fy = (int) (temp.y + originShift.y) - (int) (temp_frac);

                chemical.addPoint(fx, fy);


                temp = coords.get(2);

                fx = (int) (temp.x + originShift.x) + (int) (temp_frac);
                fy = (int) (temp.y + originShift.y) - (int) (temp_frac);

                chemical.addPoint(fx, fy);

                chemical.addPoint((int) (temp.x + originShift.x), (int) (temp.y + originShift.y));

                g.fillPolygon(chemical);


            }

            if (fraction > 0.93f) {
                float t = fraction;

                for (int i = 1; i <= 6; i++) {
                    chemical.addPoint((int) (coords.get(i).x + originShift.x), (int) (coords.get(i).y + originShift.y));
                }


                t = fraction - 0.93f;
                t /= 0.07f;
                t *= 10;

                chemical.addPoint((int) (coords.get(6).x + originShift.x), (int) (coords.get(6).y + originShift.y - t));

                chemical.addPoint((int) (coords.get(1).x + originShift.x), (int) (coords.get(6).y + originShift.y - t));
                g.setColor(this.contentColor);
                g.fillPolygon(chemical);

            }

        }

        if (isPour == true) {


            float temp_frac = fraction;

            if (fraction <= 0.4f) {
                temp_frac = fraction / 0.4f;
                temp_frac = 1 - temp_frac;

                int t1 = (int) (coords.get(3).x - coords.get(2).x);
                int t2 = (int) (coords.get(4).x - coords.get(3).x);
                int t3 = (int) (coords.get(3).y - coords.get(2).y);
                int t4 = (int) (coords.get(3).y - coords.get(4).y);




                chemical.addPoint((int) (coords.get(2).x + originShift.x + (temp_frac * t1)), (int) (coords.get(2).y + originShift.y + (temp_frac * t3)));
                //  temp_frac/=fraction;
                chemical.addPoint((int) (coords.get(3).x + originShift.x), (int) (coords.get(3).y + originShift.y));
                chemical.addPoint((int) (coords.get(4).x + originShift.x - (temp_frac * t2)), (int) (coords.get(4).y + originShift.y + (temp_frac * t4)));
                g.setColor(this.contentColor);
                g.fillPolygon(chemical);


            } else if (fraction > 0.4f && fraction <= 0.8f) {
                for (int i = 2; i <= 4; i++) {
                    chemical.addPoint((int) (coords.get(i).x + originShift.x), (int) (coords.get(i).y + originShift.y));
                }


                temp_frac = fraction - 0.4f;
                temp_frac /= 0.4f;

                int t1 = (int) (coords.get(4).x - coords.get(5).x);
                int t2 = (int) (coords.get(4).y - coords.get(5).y);


                chemical.addPoint((int) (coords.get(4).x + originShift.x - (temp_frac * t1)), (int) (coords.get(4).y + originShift.y - (temp_frac * t2)));
                g.setColor(this.contentColor);
                g.fillPolygon(chemical);


            } else if (fraction > 0.8f) {
                for (int i = 2; i <= 5; i++) {
                    chemical.addPoint((int) (coords.get(i).x + originShift.x), (int) (coords.get(i).y + originShift.y));
                }
                g.setColor(this.contentColor);
                g.fillPolygon(chemical);
            }
        }
    }
/**
 * This method is used to fill the equipment
 * @param quant Quantity
 * This function updates the fraction parameter and hence quantity of the equipment when the user calls it. Volumes are usually denoted in ml.
 */
    public void fill(float quant) {
        fraction = fraction + (quant / capacity);
        quantity = fraction * capacity;

    }

    private void delaySimple(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) { /*
             * Do Nothing
             */ }
    }
/**
 *Called in the constructor, it draws the equipment according to the given size and calculates all the required fields.
 * @param size 
 * This is to initialize the capacity,mouthLeft and mouthRight coordinates of the equipment along with storing the various coordinate elements in the coords array. 
 */
    private void chooseSize(int size) {
        this.capacity = size * 100;
        this.mouthLeft = new Point2D.Float(2 + (size - 1) * 2, 0);
        this.mouthRight = new Point2D.Float(15 + (size - 1) * 2, 0);
        float k = 2 * size;
        coords.clear();
        coords.addElement(new Point2D.Float((mouthLeft.x * w), ((mouthLeft.y) * h)));
        coords.addElement(new Point2D.Float((mouthLeft.x * w), ((mouthLeft.y + 10 + k) * h)));
        coords.addElement(new Point2D.Float(((mouthLeft.x - 10 - 2 * k) * w), ((mouthLeft.y + 20 + 1.5f * k) * h)));
        coords.addElement(new Point2D.Float(((mouthLeft.x - 10 - 2 * k) * w), ((mouthLeft.y + 70 + 2f * k) * h)));
        coords.addElement(new Point2D.Float(((mouthRight.x + 10 + 2 * k) * w), ((mouthRight.y + 70 + 2f * k) * h)));
        coords.addElement(new Point2D.Float(((mouthRight.x + 10 + 2 * k) * w), ((mouthRight.y + 20 + 1.5f * k) * h)));
        coords.addElement(new Point2D.Float(((mouthRight.x) * w), ((mouthRight.y + 10 + k) * h)));
        coords.addElement(new Point2D.Float(((mouthRight.x) * w), ((mouthRight.y) * h)));
        coords.addElement(new Point2D.Float((mouthLeft.x * w), ((mouthLeft.y) * h)));

        width = ((33 + 2 * k) * w);
        height = ((70 + 3f * k) * h);
    }
 /**
     * this function calculates the angle to which the equipment has to be rotated for pouring the given quantity.
     * @param quant : quantity to be poured.
     * @param eq : object of the destination equipment.
     * @return : angle, to which the equipment has to be rotated for pouring the given quantity.
     */
    public float getIterationSteps(float quant, Equipment eq) {
        filling = eq;
        this.fill(-quant);
        filling.fill(quant);filling.setContentColor(this.getContentColor());

        return 45;
    }

    public void setQuantity(float quant) {
        quantity = quant;
        fraction = quantity / capacity;
    }

    public void setSize(int size) {
        this.size = size;
        chooseSize(size);
    }
}
