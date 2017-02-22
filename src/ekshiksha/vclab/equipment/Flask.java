/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.awt.geom.Path2D;

/**
 *
 * @author Malleswari
 */

public class Flask extends Equipment {

    Equipment filling;
    private Vector< Point2D.Float> coords;
    Path2D.Float path;
    WorkBench environ;
    float h, w, h1, w1;
    float Awidth;
    boolean isPour = false;
    Point2D.Float mouthLeft = new Point2D.Float(10, 0);
    Point2D.Float mouthRight = new Point2D.Float(16, 0);
    Point2D.Float tempLeft = new Point2D.Float(0.0f, 0.0f);
    Point2D.Float tempRight = new Point2D.Float(0.0f, 0.0f);
    Graphics g;
    int j;
    boolean cupboardFlag;
    Cupboard environ2;
    private int ht = 19;
    
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
    public Flask(int id, String eqname, Point2D.Float origin, float quantity, int size, String contents, Color contentColor, float strength, WorkBench environ) {
        super(id, eqname, origin, quantity, size, contents, contentColor, strength);
        this.setCupboardFlag(false);
        isDrawable = true;
        coords = new Vector< Point2D.Float>();
        this.environ = environ;
        originShift = origin;
        h = (float) ((double) (environ.getHeight()) / (double) 600.0);
        w = (float) ((double) (environ.getWidth()) / (double) 600.0);

        /*
         * For the size of the equipment
         */
        this.size = size;
        for (int x = 0; x < 5; x++) {
            sizearr[x] = 100 + x * 50;
        }
        chooseSize(size);

        this.quantity = 0.0f;
        if (size == 0) {
            size = 1;
        }
        capacity = 100 + (size - 1) * 50;
        fraction = 0.0f;
        tempLeft = new Point2D.Float((coords.get(0).x), (coords.get(45).y + coords.get(0).y) * (1.1f - fraction));
        tempRight = new Point2D.Float((coords.get(60).x), (coords.get(96).y + coords.get(60).y) * (1.1f - fraction));

    }
    
    /**
    * The constructor for store.
    * @param origin : initial position of the equipment.
    * @param e : object of Cupboard.java 
    */
    public Flask(Point2D.Float origin, Cupboard e) {

        super("Flask", origin);
        this.setCupboardFlag(true);
        isDraggable = false;
        isDrawable = true;
        size = 1;
        environ2 = e;
        coords = new Vector< Point2D.Float>();
        //this.environ=environ;
        originShift = origin;//setOriginShift(environ);
        h = (float) ((double) (environ2.getHeight()) / (double) 600.0);
        w = (float) ((double) (environ2.getWidth()) / (double) 600.0);
        chooseSize(size);
        for (int x = 0; x < 5; x++) {
            sizearr[x] = 100 + x * 50;
        }
        tempLeft = new Point2D.Float((coords.get(0).x), (coords.get(45).y + coords.get(0).y) * (1.1f - fraction));
        tempRight = new Point2D.Float((coords.get(60).x), (coords.get(96).y + coords.get(60).y) * (1.1f - fraction));
        if (!isDrawable) {
            coords.clear();
        }

    }

    /**
     * this function redraws the equipment for the present size of the window and the current angle. 
     */
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

        for (int i = 0; i < coords.size() - 1; i++) {
            px = coords.elementAt(i);
            double nx = ((px.x * Math.cos(Math.toRadians(-angle))) + (px.y * Math.sin(Math.toRadians(-angle))));
            double ny = ((px.y * Math.cos(Math.toRadians(-angle))) - (px.x * Math.sin(Math.toRadians(-angle))));
            coords.setElementAt(new Point2D.Float((float) nx, (float) ny), i);
            

        }

        tempLeft = new Point2D.Float((coords.get(0).x) * (w / w1), (coords.get(45).y + coords.get(0).y) * (h / h1) * (1.1f - fraction));
        tempRight = new Point2D.Float((coords.get(60).x) * (w / w1), (coords.get(96).y + coords.get(60).y) * (h / h1) * (1.1f - fraction));

        for (int i = 0; i < coords.size() - 1; i++) {
            px = coords.elementAt(i);
            double nx = ((px.x * (w / w1) * Math.cos(Math.toRadians(angle))) + (px.y * (h / h1) * Math.sin(Math.toRadians(angle))));
            double ny = ((px.y * (h / h1) * Math.cos(Math.toRadians(angle))) - (px.x * (w / w1) * Math.sin(Math.toRadians(angle))));
            coords.setElementAt(new Point2D.Float((float) nx, (float) ny), i);
        }
        this.Awidth = ((24 + (size - 1) * 2) * w);
        this.width = ((26 + (size - 1) * 2) * w);
        this.height = ((50 + (size - 1) * 2) * h);

    }

    /**
     * This function rotates the equipment by the given angle(positive or negative)(in degrees). 
     * @param theta : the angle by which the equipment has to be rotated. 
     */
    public void rotate(double theta) {
        //  filling=eq;
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
     * This function fills the equipment by the given quantity.
     * @param quant : quantity to be filled. 
     */
    public void fill(float quant) {
        fraction = fraction + (quant / capacity);
        quantity = fraction * capacity;
        tempLeft = new Point2D.Float((coords.get(0).x), (coords.get(45).y + coords.get(0).y) * (1.1f - fraction));
        tempRight = new Point2D.Float((coords.get(60).x), (coords.get(96).y + coords.get(60).y) * (1.1f - fraction));

    }

    /**
     * This function  draws lines between the points plotted in chooseSize(or initEquipment), thereby drawing the equipment.
     * @param g : Object of Graphics class. 
     */
    @Override
    public void drawEquipment(Graphics g) {
        g.setColor(borderColor);
        if (!isDrawable) {
            return;
        }
        for (int i = 0; i < coords.size() - 2; i++) {
            Point2D.Float s = coords.elementAt(i);
            Point2D.Float d = coords.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }
        if (fraction > 0) {
            drawContents(g);
        }
    }

    /**
     * Sets the quantity to the given value.
     * @param quant : the value to which the quantity of the equipment has to be set.
     */
    @Override
    public void setQuantity(float quant) {
        quantity = quant;
        fraction = quantity / capacity;
        tempLeft = new Point2D.Float((coords.get(0).x), (coords.get(45).y + coords.get(0).y) * (1.1f - fraction));
        tempRight = new Point2D.Float((coords.get(60).x), (coords.get(96).y + coords.get(60).y) * (1.1f - fraction));
    }

    /**
     * this function calculates the angle to which the equipment has to be rotated for pouring the given quantity.
     * @param quant : quantity to be poured.
     * @param eq : object of the destination equipment.
     * @return : angle, to which the equipment has to be rotated for pouring the given quantity.
     */
    @Override
    public float getIterationSteps(float quant, Equipment eq) {
        filling = eq;
        float temp_angle;
        float final_quant = (capacity * fraction) - quant;
        if (final_quant > (capacity * 0.5)) {
            temp_angle = (float) Math.atan(((capacity - final_quant) / capacity) * height * 2 / Awidth);
        } else {
            temp_angle = (float) Math.atan((height * height) / ((final_quant / capacity) * height * Awidth * 2));
        }
        return (float) Math.toDegrees(temp_angle);
    }

    /**
     * This function draws the liquid contained in the equipment according to the quantity and the angle.
     * it is also responsible for reducing the quantity of the current equipment and filling the destination equipment by the appropriate quantity.  
     * @param g : Object of Graphics class.
     */
    public void drawContents(Graphics g) {
        int i;
        Graphics2D g2 = (Graphics2D) g;
        path = new Path2D.Float();
        float dec;
        float diff = (float) (((Awidth * Math.tan(Math.toRadians(angle))) / 2));
        float len_t = (float) (Math.pow(Awidth * height * fraction * 2 * Math.tan(Math.toRadians(angle)), 0.5));

        float base = (float) (len_t / (Math.tan(Math.toRadians(angle))));
        if (isPour == false) {
            if (coords.size() > 0) {
                j = (int) (46 - (fraction * 46));
                Point2D.Float s = coords.elementAt(j);
                path.moveTo(s.x + originShift.x, s.y + originShift.y);
                for (i = j; i < coords.size() - j - 3; i++) {
                    Point2D.Float s1 = coords.elementAt(i + 1);
                    path.lineTo(s1.x + originShift.x, (s1.y) + originShift.y);
                }
                path.lineTo(coords.elementAt(j).x + originShift.x, coords.elementAt(j).y + originShift.y);
                g2.setColor(this.contentColor);
                g2.fill(path);
            }
        } else {
            Point2D.Float px;
            double nx;
            double ny;
            px = tempRight;
            nx = ((px.x * Math.cos(Math.toRadians(angle))) + (px.y * Math.sin(Math.toRadians(angle))));
            if (((int) (nx + originShift.x + (diff * Math.sin(Math.toRadians(angle)))) < (int) (originShift.x + coords.elementAt(60).x)) || (base > width)) {
                px = tempRight;
                nx = ((px.x * Math.cos(Math.toRadians(angle))) + (px.y * Math.sin(Math.toRadians(angle))));
                if ((int) (nx + originShift.x - (diff * Math.sin(Math.toRadians(angle)))) < (int) (originShift.x + coords.elementAt(0).x)) {
                    path.moveTo((originShift.x + coords.elementAt(0).x), (originShift.y + coords.elementAt(0).y));
                    for (i = 44; i < 60; i++) {
                        px = coords.get(i);
                        path.lineTo((px.x + originShift.x), (px.y + originShift.y));
                    }
                    dec = (float) ((0.5 * Awidth * (diff - (height * (1 - fraction)))) / (Awidth * height));
                    fraction -= dec;
                    filling.fill(dec * capacity);
                    filling.setContentColor(this.getContentColor());
                    tempRight.y = tempRight.y * (1 - fraction) / (1 - (fraction + dec));
                    tempLeft.y = tempLeft.y * (1 - fraction) / (1 - (fraction + dec));
                    px = tempRight;
                    nx = ((px.x * Math.cos(Math.toRadians(angle))) + (px.y * Math.sin(Math.toRadians(angle))));
                    ny = ((px.y * Math.cos(Math.toRadians(angle))) - (px.x * Math.sin(Math.toRadians(angle))));
                    path.lineTo((nx + originShift.x + (diff * Math.sin(Math.toRadians(angle)))), (ny + originShift.y + (diff * Math.cos(Math.toRadians(angle)))));
                } else {
                    if (angle > 0 && angle <= 20) {
                        int f;
                        f = (int) (47 - (47 * fraction));
                        Point2D.Float s = coords.elementAt(f - 1);
                        path.moveTo(s.x + originShift.x, s.y + originShift.y);
                        for (i = f; i < coords.size() - f - 20; i++) {
                            Point2D.Float s1 = coords.elementAt(i + 1);
                            path.lineTo(s1.x + originShift.x, (s1.y) + originShift.y);
                        }
                        path.lineTo(coords.elementAt(f - 1).x + originShift.x, coords.elementAt(f - 1).y + originShift.y);
                        g2.setColor(this.contentColor);
                        g2.fill(path);
                    }
                    if (angle > 20 && angle <= 40) {
                        int f;
                        f = (int) (47 - (47 * fraction));
                        Point2D.Float s = coords.elementAt(f - 6);
                        path.moveTo(s.x + originShift.x, s.y + originShift.y);
                        for (i = f - 6; i < coords.size() - f - 25; i++) {
                            Point2D.Float s1 = coords.elementAt(i + 1);
                            path.lineTo(s1.x + originShift.x, (s1.y) + originShift.y);
                        }
                        path.lineTo(coords.elementAt(f - 6).x + originShift.x, coords.elementAt(f - 6).y + originShift.y);
                        g2.setColor(this.contentColor);
                        g2.fill(path);
                    }
                    if (angle > 40 && angle <= 75) {
                        int f;
                        f = (int) (47 - (47 * fraction));
                        Point2D.Float s = coords.elementAt(f - 10);
                        path.moveTo(s.x + originShift.x, s.y + originShift.y);
                        for (i = f - 10; i < coords.size() - f - 30; i++) {
                            Point2D.Float s1 = coords.elementAt(i + 1);
                            path.lineTo(s1.x + originShift.x, (s1.y) + originShift.y);
                        }
                        path.lineTo(coords.elementAt(f - 10).x + originShift.x, coords.elementAt(f - 10).y + originShift.y);
                        g2.setColor(this.contentColor);
                        g2.fill(path);
                    }
                }

            } else {
                if (len_t > height) {
                    path.moveTo((originShift.x + coords.elementAt(0).x), (originShift.y + coords.elementAt(0).y));
                    for (i = 1; i < 58; i++) {
                        px = coords.get(i);
                        path.lineTo((px.x + originShift.x), (px.y + originShift.y));
                    }
                    dec = (float) ((height / (2 * Math.tan(Math.toRadians(angle)))) / (Awidth));
                    filling.fill(-(dec - fraction) * capacity);
                    filling.setContentColor(this.getContentColor());
                    tempRight.y = (tempRight.y) * (1 - dec) / ((1 - fraction));
                    tempLeft.y = tempLeft.y * (1 - dec) / (1 - fraction);
                    fraction = dec;
                    base = (float) (height / (Math.tan(Math.toRadians(angle))));
                    path.lineTo((int) (originShift.x + coords.elementAt((coords.size() / 2)).x + (base / 2) * Math.cos(Math.toRadians(angle))), (int) (originShift.y + coords.elementAt((coords.size() / 2)).y - (base / 2) * Math.sin(Math.toRadians(angle))));
                } else {
                    path.moveTo((originShift.x + coords.elementAt(40).x - (len_t / 3) * Math.sin(Math.toRadians(angle))), (originShift.y + coords.elementAt(40).y - len_t * Math.cos(Math.toRadians(angle))));
                    for (i = 30; i < 55; i++) {
                        px = coords.get(i);
                        path.lineTo((px.x + originShift.x), (px.y + originShift.y));
                    }
                    base = (float) (len_t / (Math.tan(Math.toRadians(angle))));
                    path.lineTo((originShift.x + coords.elementAt(50).x + (base / 3) * Math.cos(Math.toRadians(angle))), (originShift.y + coords.elementAt(50).y - (base / 3) * Math.sin(Math.toRadians(angle))));
                }
            }
        }
        g2.setColor(this.contentColor);
        g2.fill(path);
        g2.setColor(Color.black);

    }

    /**
     * Called in the constructor, it draws the equipment according to the given size and calculates all the required fields.
     * @param size : the size of the equipment to be drawn.
     */
    private void chooseSize(int size) {
        capacity = 100 + (size - 1) * 50;
        Awidth = (int) ((24 + (size - 1) * 1) * w);
        mouthRight = new Point2D.Float(16 + (size - 1) * 1, 0);
        ht = (int) ((19 + (size - 1) * 2));
        Awidth = ((24 + (size - 1) * 1) * w);
        width = ((26 + (size - 1) * 1) * w);
        height = ((50 + (size - 1) * 1) * h);
        coords.clear();
        /*
         * coordinate points for drawing the equipment
         */
        coords.addElement(new Point2D.Float(((mouthLeft.x) * w), ((mouthLeft.y) * h)));//0
        for (int i = 1; i <= 19; i++) {
            coords.addElement(new Point2D.Float(((mouthLeft.x) * w), ((mouthLeft.y + i) * h)));
        }                                                       //19
        coords.addElement(new Point2D.Float(((mouthLeft.x) * w), ((mouthLeft.y + ht) * h)));//21
        coords.addElement(new Point2D.Float(((mouthLeft.x - 1) * w), ((mouthLeft.y + 1 + ht) * h)));//22
        coords.addElement(new Point2D.Float(((mouthLeft.x - 1) * w), ((mouthLeft.y + 2 + ht) * h)));//23
        coords.addElement(new Point2D.Float(((mouthLeft.x - 2) * w), ((mouthLeft.y + 3 + ht) * h)));//24
        coords.addElement(new Point2D.Float(((mouthLeft.x - 3) * w), ((mouthLeft.y + 4 + ht) * h)));//25
        coords.addElement(new Point2D.Float(((mouthLeft.x - 3) * w), ((mouthLeft.y + 5 + ht) * h)));//26
        coords.addElement(new Point2D.Float(((mouthLeft.x - 4) * w), ((mouthLeft.y + 6 + ht) * h)));//27
        coords.addElement(new Point2D.Float(((mouthLeft.x - 4) * w), ((mouthLeft.y + 7 + ht) * h)));//28
        coords.addElement(new Point2D.Float(((mouthLeft.x - 5) * w), ((mouthLeft.y + 8 + ht) * h)));//29
        coords.addElement(new Point2D.Float(((mouthLeft.x - 5) * w), ((mouthLeft.y + 9 + ht) * h)));//30
        coords.addElement(new Point2D.Float(((mouthLeft.x - 6) * w), ((mouthLeft.y + 10 + ht) * h)));//31
        coords.addElement(new Point2D.Float(((mouthLeft.x - 7) * w), ((mouthLeft.y + 11 + ht) * h)));//32
        coords.addElement(new Point2D.Float(((mouthLeft.x - 7) * w), ((mouthLeft.y + 12 + ht) * h)));//33
        coords.addElement(new Point2D.Float(((mouthLeft.x - 8) * w), ((mouthLeft.y + 13 + ht) * h)));//34
        coords.addElement(new Point2D.Float(((mouthLeft.x - 8) * w), ((mouthLeft.y + 14 + ht) * h)));//35
        coords.addElement(new Point2D.Float(((mouthLeft.x - 9) * w), ((mouthLeft.y + 15 + ht) * h)));//36
        coords.addElement(new Point2D.Float(((mouthLeft.x - 9) * w), ((mouthLeft.y + 17 + ht) * h)));//37
        coords.addElement(new Point2D.Float(((mouthLeft.x - 10) * w), ((mouthLeft.y + 18 + ht) * h)));//38
        coords.addElement(new Point2D.Float(((mouthLeft.x - 10) * w), ((mouthLeft.y + 23 + ht) * h)));//39
        coords.addElement(new Point2D.Float(((mouthLeft.x - 9) * w), ((mouthLeft.y + 24 + ht) * h)));//40
        coords.addElement(new Point2D.Float(((mouthLeft.x - 9) * w), ((mouthLeft.y + 25 + ht) * h)));//41
        coords.addElement(new Point2D.Float(((mouthLeft.x - 9) * w), ((mouthLeft.y + 26 + ht) * h)));//42
        coords.addElement(new Point2D.Float(((mouthLeft.x - 8) * w), ((mouthLeft.y + 27 + ht) * h)));//43
        coords.addElement(new Point2D.Float(((mouthLeft.x - 8) * w), ((mouthLeft.y + 28 + ht) * h)));//44
        coords.addElement(new Point2D.Float(((mouthLeft.x - 5) * w), ((mouthLeft.y + 31 + ht) * h)));//45
        for (int i = 6; i <= 21; i++) {
            coords.addElement(new Point2D.Float(((mouthLeft.x - (mouthLeft.x - i)) * w), ((mouthLeft.y + ht + 31) * h)));
        }                                                       //60
        coords.addElement(new Point2D.Float(((mouthLeft.x + 11) * w), ((mouthLeft.y + 31 + ht) * h)));//61
        coords.addElement(new Point2D.Float(((mouthRight.x + 8) * w), ((mouthRight.y + 28 + ht) * h)));//62
        coords.addElement(new Point2D.Float(((mouthRight.x + 8) * w), ((mouthRight.y + 27 + ht) * h)));//63
        coords.addElement(new Point2D.Float(((mouthRight.x + 9) * w), ((mouthRight.y + 26 + ht) * h)));//64
        coords.addElement(new Point2D.Float(((mouthRight.x + 9) * w), ((mouthRight.y + 25 + ht) * h)));//65
        coords.addElement(new Point2D.Float(((mouthRight.x + 9) * w), ((mouthRight.y + 24 + ht) * h)));//66
        coords.addElement(new Point2D.Float(((mouthRight.x + 10) * w), ((mouthRight.y + 23 + ht) * h)));//67
        coords.addElement(new Point2D.Float(((mouthRight.x + 10) * w), ((mouthRight.y + 18 + ht) * h)));//68
        coords.addElement(new Point2D.Float(((mouthRight.x + 9) * w), ((mouthRight.y + 17 + ht) * h)));//69
        coords.addElement(new Point2D.Float(((mouthRight.x + 9) * w), ((mouthRight.y + 15 + ht) * h)));//70
        coords.addElement(new Point2D.Float(((mouthRight.x + 8) * w), ((mouthRight.y + 14 + ht) * h)));//71
        coords.addElement(new Point2D.Float(((mouthRight.x + 8) * w), ((mouthRight.y + 13 + ht) * h)));//72
        coords.addElement(new Point2D.Float(((mouthRight.x + 7) * w), ((mouthRight.y + 12 + ht) * h)));//73
        coords.addElement(new Point2D.Float(((mouthRight.x + 7) * w), ((mouthRight.y + 11 + ht) * h)));  //74
        coords.addElement(new Point2D.Float(((mouthRight.x + 6) * w), ((mouthRight.y + 10 + ht) * h)));  //75
        coords.addElement(new Point2D.Float(((mouthRight.x + 5) * w), ((mouthRight.y + 9 + ht) * h)));//76
        coords.addElement(new Point2D.Float(((mouthRight.x + 5) * w), ((mouthRight.y + 8 + ht) * h)));//77
        coords.addElement(new Point2D.Float(((mouthRight.x + 4) * w), ((mouthRight.y + 7 + ht) * h)));//78
        coords.addElement(new Point2D.Float(((mouthRight.x + 4) * w), ((mouthRight.y + 6 + ht) * h)));//79
        coords.addElement(new Point2D.Float(((mouthRight.x + 3) * w), ((mouthRight.y + 5 + ht) * h)));//80
        coords.addElement(new Point2D.Float(((mouthRight.x + 3) * w), ((mouthRight.y + 4 + ht) * h)));//81
        coords.addElement(new Point2D.Float(((mouthRight.x + 2) * w), ((mouthRight.y + 3 + ht) * h)));//82
        coords.addElement(new Point2D.Float(((mouthRight.x + 1) * w), ((mouthRight.y + 2 + ht) * h)));//83
        coords.addElement(new Point2D.Float(((mouthRight.x + 1) * w), ((mouthRight.y + 1 + ht) * h)));//84
        coords.addElement(new Point2D.Float(((mouthRight.x) * w), ((mouthRight.y + ht) * h)));//85
        for (int i = 20; i >= 0; i--) {
            coords.addElement(new Point2D.Float(((mouthRight.x) * w), ((mouthRight.y + ht + (i - ht)) * h)));
        }//95
        coords.addElement(new Point2D.Float(((mouthRight.x) * w), ((mouthRight.y) * h)));//96
    }

    /**
     * Sets the size of the equipment.
     * @param size : size of the equipment.
     */ 
    @Override
    public void setSize(int size) {
        this.size = size;
        chooseSize(size);

    }
}
