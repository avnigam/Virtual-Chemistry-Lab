/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekshiksha.vclab.equipment;

import ekshiksha.vclab.lab.WorkBench;
import ekshiksha.vclab.util.Constants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JWindow;
import ekshiksha.vclab.lab.Cupboard;
import ekshiksha.vclab.lab.StopWatchRunner;
import java.awt.*;
import javax.swing.*;

/**
 * this class is responsible for equipment burner.
 * this class can do the following:
 * <ul>
 *     <li> initialization of burner.
 *     <li> drawing of burner.
 *     <li> creating flames.
 *     <li> showing flames with button.
 *     <li> showing flames automatically.
 * </ul>
 
 * @author vikram verma
 */
public class Burner extends Equipment implements Runnable, Serializable {

    private Vector< Point2D.Float> coords;  /*for holding the coordinates of burner*/ 
    private Vector< Point2D.Float> coordst; /*for holding the coordinates of tripod stand*/
    private Vector< Point2D.Float> coordsg;  /*for holding the coordinates of wire gauze*/
    float h; /* resizing factor of height*/
    float w; /* resizing factor of width*/
    Point2D.Float mouthLeft;   /*left point of flame */
    Point2D.Float mouthRight;  /*right point of flame */
    private float hx, wx;      /*apparatus height and width*/
    private Thread burn;       /*for automatic flame*/
    private WorkBench environ; /*object of workbench*/
    private Cupboard environ2;         /*object of store*/
    private Color lineColor;   /*linecolor of equipment*/
    public StopWatchRunner watch;/*To calculate time*/

   /* public Burner(Point2D.Float origin, WorkBench e) {
        super("burner", origin);
        // environ=e;
        originShift = origin;
        lineColor = Color.BLACK;
        this.setCupboardFlag(false);
        isDrawable = true;
        // properties = prop;
        coords = new Vector< Point2D.Float>();
        coordst = new Vector< Point2D.Float>();
        coordsg = new Vector< Point2D.Float>();
        onState = false;
        environ = e;
        flameHeight = 0;
        burn = new Thread(this);
        //h = (float) ((double) (environ.getHeight()) / (double) 592.0);
        //w = (float) ((double) (environ.getWidth()) / (double) 523.0);
        Icon uIcon = new ImageIcon("up.png");
        Icon dIcon = new ImageIcon("down.png");
        up = new JButton();
        down = new JButton();
        up.setIcon(uIcon);
        down.setIcon(dIcon);
        up.addActionListener(this.environ);
        down.addActionListener(this);

        initEquipment();
        burn.start();
        watch = new StopWatchRunner(new Dimension(200, 100));
    }*/
    /**
     * 
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
    public Burner(int id, String eqname, Point2D.Float origin, float quantity, int size, String contents, Color contentColor, float strength, WorkBench environ) throws IOException {
        super(id, eqname, origin, quantity, size, contents, contentColor, strength);
        this.setCupboardFlag(false);
        isDrawable = true;
        this.environ = environ;
        coords = new Vector< Point2D.Float>();
        coordst = new Vector< Point2D.Float>();
        coordsg = new Vector< Point2D.Float>();
        onState = false;
        // environ = e;
        flameHeight = 0;
        // h = (float) ((double) (environ.getHeight()) / (double) 592.0);
        //w = (float) ((double) (environ.getWidth()) / (double) 523.0);
        Image nextImage;
        nextImage = ImageIO.read(this.getClass().getResource("up.png"));
        Icon uIcon = new ImageIcon(nextImage, "up");
        nextImage = ImageIO.read(this.getClass().getResource("down.png"));
        Icon dIcon = new ImageIcon(nextImage, "down");
        up = new JButton();
        down = new JButton();
        up.setIcon(uIcon);
        down.setIcon(dIcon);
        up.addActionListener(this.environ);
        down.addActionListener(this.environ);
        up.setEnabled(false);
        down.setEnabled(false);

        burn = new Thread(this);
        initEquipment();
        burn.start();
        watch = new StopWatchRunner(new Dimension(200, 100));

    }
    /**
     * 
     * @param origin  equipment origin
     * @param e  object of store
     * @throws IOException 
     */
    public Burner(Point2D.Float origin, Cupboard e) throws IOException {

        super("Burner", origin);
        this.setCupboardFlag(true);
        isDraggable = false;
        isDrawable = true;
        environ2 = e;
        coords = new Vector< Point2D.Float>();
        coordst = new Vector< Point2D.Float>();
        coordsg = new Vector< Point2D.Float>();
        onState = false;
        // environ = e;
        flameHeight = 0;
        contents = "";
        //h = (float) ((double) (environ2.getHeight()) / (double) 592.0);
        //w = (float) ((double) (environ2.getWidth()) / (double) 523.0);
        Image nextImage;
        nextImage = ImageIO.read(this.getClass().getResource("up.png"));
        Icon uIcon = new ImageIcon(nextImage, "up");
        nextImage = ImageIO.read(this.getClass().getResource("down.png"));
        Icon dIcon = new ImageIcon(nextImage, "down");
        up = new JButton();
        down = new JButton();
        up.setIcon(uIcon);
        down.setIcon(dIcon);
        up.setEnabled(false);
        down.setEnabled(false);



        burn = new Thread(this);
        initEquipment();
        burn.start();


    }

    /**
     * for getting preferred size
     * @return  dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
    /**
     * for setting intensity
     * @param d intensity
     */
    public void setIntensity(double d) {
        intensity = d;
    }
    /**
     * for getting intensity
     * @return intensity
     */
    public double getIntensity() {
        return intensity;
    }
    /**
     * for setting state
     * @param b  boolean onState
     */
    public void setState(boolean b) {
        onState = b;
        if (onState) {
            flameHeight = 15;
        } else {
            flameHeight = 0;
        }
    }
    /**
     * for getting state
     * @return onState
     */
    public boolean getState() {
        return onState;
    }
    /**
     * This Method initializes the pipette
     * 
     * This function sets the coordinates of the equipment object in the array of points coords<br> according to the angle parameter of the object.
     */
    @Override
    public void initEquipment() {
        boolean temp = getCupboardFlag();
        if (!temp) {
            h = (float) ((double) (environ.getHeight()) / (double) 600.0);
            w = (float) ((double) (environ.getWidth()) / (double) 600.0);
        } else {
            h = (float) ((double) (environ2.getHeight()) / (double) 600.0);
            w = (float) ((double) (environ2.getWidth()) / (double) 600.0);
        }

        coords = new Vector< Point2D.Float>();
        coordst = new Vector< Point2D.Float>();
        coordsg = new Vector< Point2D.Float>();
        coords.clear();
        coordst.clear();
        coordsg.clear();
        mouthLeft = new Point2D.Float((20 * w), (15 * h));
        mouthRight = new Point2D.Float((27 * w), (15 * h));
        up.setBounds((int) (mouthLeft.x + originShift.x - (12 * w)), (int) (mouthLeft.y + originShift.y + (10 * h)), (int) (12 * w), (int) (10 * h));
        down.setBounds((int) (mouthLeft.x + originShift.x - (12 * w)), (int) (mouthLeft.y + originShift.y + (22 * h)), (int) (12 * w), (int) (10 * h));
        // up.setBounds(30, 100, (int)(3*(w/100)), (int)(3*(h/100)));
        if (!temp) {
            environ.add(up);
            environ.add(down);
        }
        /*
         * else { environ2.add(up); environ2.add(down);
        }
         */


        up.setFocusPainted(false);
        up.setBorderPainted(false);
        //  up.setRolloverEnabled(true);
        down.setFocusPainted(false);
        down.setBorderPainted(false);
        // down.setRolloverEnabled(false);
        up.setContentAreaFilled(false);
        down.setContentAreaFilled(false);
        /**
         * coordinates of burner
         */
        coords.addElement(new Point2D.Float((20 * w), (15 * h)));
        coords.addElement(new Point2D.Float((20 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (20.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (20.3 * w), (15 * h)));
        coords.addElement(new Point2D.Float((float) (20.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (20.6 * w), (15 * h)));
        coords.addElement(new Point2D.Float((21 * w), (15 * h)));
        coords.addElement(new Point2D.Float((21 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (15 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (15 * h)));
        coords.addElement(new Point2D.Float((22 * w), (15 * h)));
        coords.addElement(new Point2D.Float((22 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (15 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (15 * h)));
        coords.addElement(new Point2D.Float((23 * w), (15 * h)));
        coords.addElement(new Point2D.Float((23 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (15 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (15 * h)));
        coords.addElement(new Point2D.Float((24 * w), (15 * h)));
        coords.addElement(new Point2D.Float((24 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (15 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (15 * h)));
        coords.addElement(new Point2D.Float((25 * w), (15 * h)));
        coords.addElement(new Point2D.Float((25 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (15 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (15 * h)));
        coords.addElement(new Point2D.Float((26 * w), (15 * h)));
        coords.addElement(new Point2D.Float((26 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (26.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (26.3 * w), (15 * h)));
        coords.addElement(new Point2D.Float((float) (26.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (26.6 * w), (15 * h)));
        coords.addElement(new Point2D.Float((27 * w), (15 * h)));
        coords.addElement(new Point2D.Float((27 * w), (20 * h)));

        coords.addElement(new Point2D.Float((21 * w), (20 * h)));
        coords.addElement(new Point2D.Float((21 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (42 * h)));

        coords.addElement(new Point2D.Float((22 * w), (20 * h)));
        coords.addElement(new Point2D.Float((22 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (42 * h)));
        coords.addElement(new Point2D.Float((23 * w), (20 * h)));
        coords.addElement(new Point2D.Float((23 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (42 * h)));
        coords.addElement(new Point2D.Float((24 * w), (20 * h)));
        coords.addElement(new Point2D.Float((24 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (42 * h)));
        coords.addElement(new Point2D.Float((25 * w), (20 * h)));
        coords.addElement(new Point2D.Float((25 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (42 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (20 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (42 * h)));
        coords.addElement(new Point2D.Float((26 * w), (20 * h)));
        coords.addElement(new Point2D.Float((26 * w), (42 * h)));

        coords.addElement(new Point2D.Float((21 * w), (38 * h)));
        coords.addElement(new Point2D.Float((21 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (38 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (38 * h)));
        coords.addElement(new Point2D.Float((22 * w), (38 * h)));
        coords.addElement(new Point2D.Float((22 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (38 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (38 * h)));
        coords.addElement(new Point2D.Float((23 * w), (38 * h)));
        coords.addElement(new Point2D.Float((23 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (38 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (38 * h)));
        coords.addElement(new Point2D.Float((24 * w), (38 * h)));
        coords.addElement(new Point2D.Float((24 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (38 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (38 * h)));
        coords.addElement(new Point2D.Float((25 * w), (38 * h)));
        coords.addElement(new Point2D.Float((25 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (38 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (38 * h)));
        coords.addElement(new Point2D.Float((26 * w), (38 * h)));
        coords.addElement(new Point2D.Float((26 * w), (50 * h)));
        coords.addElement(new Point2D.Float((13 * w), (61 * h)));
        coords.addElement(new Point2D.Float((13 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (13.3 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (13.3 * w), (float) (60.5 * h)));
        coords.addElement(new Point2D.Float((float) (13.6 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (13.6 * w), (float) (60.5 * h)));
        coords.addElement(new Point2D.Float((float) (14 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((14 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (14.3 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (14.3 * w), (float) (60 * h)));
        coords.addElement(new Point2D.Float((float) (14.6 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (14.6 * w), (float) (60 * h)));
        coords.addElement(new Point2D.Float((15 * w), (59 * h)));
        coords.addElement(new Point2D.Float((15 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (15.3 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (15.3 * w), (float) (58.5 * h)));
        coords.addElement(new Point2D.Float((float) (15.6 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (15.6 * w), (float) (58.5 * h)));
        coords.addElement(new Point2D.Float((16 * w), (58 * h)));
        coords.addElement(new Point2D.Float((16 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (16.3 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (16.3 * w), (float) (57.5 * h)));
        coords.addElement(new Point2D.Float((float) (16.6 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (16.6 * w), (float) (57.5 * h)));
        coords.addElement(new Point2D.Float((17 * w), (57 * h)));
        coords.addElement(new Point2D.Float((17 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (17.3 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (17.3 * w), (float) (56 * h)));
        coords.addElement(new Point2D.Float((float) (17.6 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (17.6 * w), (float) (56 * h)));
        coords.addElement(new Point2D.Float((18 * w), (55 * h)));
        coords.addElement(new Point2D.Float((18 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (18.3 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (18.3 * w), (float) (52.5 * h)));
        coords.addElement(new Point2D.Float((float) (18.6 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (18.6 * w), (float) (52.5 * h)));
        coords.addElement(new Point2D.Float((19 * w), (50 * h)));
        coords.addElement(new Point2D.Float((19 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (19.3 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (19.3 * w), (float) (50 * h)));
        coords.addElement(new Point2D.Float((float) (19.6 * w), (float) (61 * h)));
        coords.addElement(new Point2D.Float((float) (19.6 * w), (float) (50 * h)));
        coords.addElement(new Point2D.Float((18 * w), (50 * h)));
        coords.addElement(new Point2D.Float((18 * w), (52 * h)));
        coords.addElement(new Point2D.Float((float) (17.5 * w), (float) (52 * h)));
        coords.addElement(new Point2D.Float((float) (17.5 * w), (float) (50 * h)));
        coords.addElement(new Point2D.Float((17 * w), (50 * h)));
        coords.addElement(new Point2D.Float((17 * w), (52 * h)));
        coords.addElement(new Point2D.Float((float) (16.5 * w), (float) (52 * h)));
        coords.addElement(new Point2D.Float((float) (16.5 * w), (float) (50 * h)));
        coords.addElement(new Point2D.Float((16 * w), (50 * h)));
        coords.addElement(new Point2D.Float((16 * w), (52 * h)));
        coords.addElement(new Point2D.Float((float) (15.5 * w), (float) (52 * h)));
        coords.addElement(new Point2D.Float((float) (15.5 * w), (float) (50 * h)));
        coords.addElement(new Point2D.Float((15 * w), (50 * h)));
        coords.addElement(new Point2D.Float((15 * w), (52 * h)));
        coords.addElement(new Point2D.Float((float) (14.5 * w), (float) (52 * h)));
        coords.addElement(new Point2D.Float((float) (14.5 * w), (float) (50 * h)));
        coords.addElement(new Point2D.Float((14 * w), (50 * h)));
        coords.addElement(new Point2D.Float((14 * w), (52 * h)));
        coords.addElement(new Point2D.Float((float) (13.5 * w), (float) (52 * h)));
        coords.addElement(new Point2D.Float((float) (13.5 * w), (float) (50 * h)));
        coords.addElement(new Point2D.Float((13 * w), (50 * h)));
        coords.addElement(new Point2D.Float((13 * w), (52 * h)));
        coords.addElement(new Point2D.Float((float) (12.5 * w), (float) (52 * h)));
        coords.addElement(new Point2D.Float((float) (12.5 * w), (float) (50 * h)));
        coords.addElement(new Point2D.Float((12 * w), (50 * h)));
        coords.addElement(new Point2D.Float((12 * w), (52 * h)));

        coords.addElement(new Point2D.Float((20 * w), (49 * h)));
        coords.addElement(new Point2D.Float((20 * w), (55 * h)));
        coords.addElement(new Point2D.Float((20 * w), (59 * h)));
        coords.addElement(new Point2D.Float((20 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (20.3 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (20.3 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (20.3 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (20.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (20.6 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (20.6 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (20.6 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (20.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((21 * w), (49 * h)));
        coords.addElement(new Point2D.Float((21 * w), (55 * h)));
        coords.addElement(new Point2D.Float((21 * w), (59 * h)));
        coords.addElement(new Point2D.Float((21 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (21.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (21.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((22 * w), (49 * h)));
        coords.addElement(new Point2D.Float((22 * w), (55 * h)));
        coords.addElement(new Point2D.Float((22 * w), (59 * h)));
        coords.addElement(new Point2D.Float((22 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (22.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (22.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((23 * w), (49 * h)));
        coords.addElement(new Point2D.Float((23 * w), (55 * h)));
        coords.addElement(new Point2D.Float((23 * w), (59 * h)));
        coords.addElement(new Point2D.Float((23 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (23.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (23.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((24 * w), (49 * h)));
        coords.addElement(new Point2D.Float((24 * w), (55 * h)));
        coords.addElement(new Point2D.Float((24 * w), (59 * h)));
        coords.addElement(new Point2D.Float((24 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (24.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (24.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((25 * w), (49 * h)));
        coords.addElement(new Point2D.Float((25 * w), (55 * h)));
        coords.addElement(new Point2D.Float((25 * w), (59 * h)));
        coords.addElement(new Point2D.Float((25 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (25.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (25.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((26 * w), (49 * h)));
        coords.addElement(new Point2D.Float((26 * w), (55 * h)));
        coords.addElement(new Point2D.Float((26 * w), (59 * h)));
        coords.addElement(new Point2D.Float((26 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (26.3 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (26.3 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (26.3 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (26.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (26.6 * w), (49 * h)));
        coords.addElement(new Point2D.Float((float) (26.6 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (26.6 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (26.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((27 * w), (49 * h)));
        coords.addElement(new Point2D.Float((27 * w), (55 * h)));
        coords.addElement(new Point2D.Float((27 * w), (59 * h)));
        coords.addElement(new Point2D.Float((27 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (27.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (27.3 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (27.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (27.6 * w), (50 * h)));
        // coords.addElement( new Point2D.Float((float)(28*w), (50*h)));
        coords.addElement(new Point2D.Float((28 * w), (50 * h)));
        coords.addElement(new Point2D.Float((28 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (28.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (28.3 * w), (53 * h)));
        coords.addElement(new Point2D.Float((float) (28.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (28.6 * w), (53 * h)));
        coords.addElement(new Point2D.Float((29 * w), (53 * h)));
        coords.addElement(new Point2D.Float((29 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (29.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (29.3 * w), (55 * h)));
        coords.addElement(new Point2D.Float((float) (29.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (29.6 * w), (55 * h)));
        coords.addElement(new Point2D.Float((30 * w), (56 * h)));
        coords.addElement(new Point2D.Float((30 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (30.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (30.3 * w), (57 * h)));
        coords.addElement(new Point2D.Float((float) (30.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (30.6 * w), (57 * h)));
        coords.addElement(new Point2D.Float((31 * w), (58 * h)));
        coords.addElement(new Point2D.Float((31 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (31.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (31.3 * w), (59 * h)));
        coords.addElement(new Point2D.Float((float) (31.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (31.6 * w), (59 * h)));
        coords.addElement(new Point2D.Float((32 * w), (59 * h)));
        coords.addElement(new Point2D.Float((32 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (32.3 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (32.3 * w), (60 * h)));
        coords.addElement(new Point2D.Float((float) (32.6 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (32.6 * w), (60 * h)));
        coords.addElement(new Point2D.Float((33 * w), (60 * h)));
        coords.addElement(new Point2D.Float((33 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (33.5 * w), (61 * h)));
        coords.addElement(new Point2D.Float((float) (33.5 * w), (61 * h)));
        coords.addElement(new Point2D.Float((34 * w), (61 * h)));
        coords.addElement(new Point2D.Float((34 * w), (61 * h)));
        coords.addElement(new Point2D.Float((22 * w), (57 * h)));
        coords.addElement(new Point2D.Float((24 * w), (57 * h)));
        coords.addElement(new Point2D.Float((24 * w), (57 * h)));
        coords.addElement(new Point2D.Float((24 * w), (56 * h)));
        coords.addElement(new Point2D.Float((25 * w), (57 * h)));
        coords.addElement(new Point2D.Float((26 * w), (57 * h)));

        coords.addElement(new Point2D.Float((14 * w), (48 * h)));
        coords.addElement(new Point2D.Float((14 * w), (50 * h)));
        coords.addElement(new Point2D.Float((14 * w), (52 * h)));
        coords.addElement(new Point2D.Float((14 * w), (54 * h)));
        coords.addElement(new Point2D.Float((float) (13.5 * w), (48 * h)));
        coords.addElement(new Point2D.Float((float) (13.5 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (13.5 * w), (52 * h)));
        coords.addElement(new Point2D.Float((float) (13.5 * w), (54 * h)));
        coords.addElement(new Point2D.Float((13 * w), (48 * h)));
        coords.addElement(new Point2D.Float((13 * w), (50 * h)));
        coords.addElement(new Point2D.Float((13 * w), (52 * h)));
        coords.addElement(new Point2D.Float((13 * w), (54 * h)));
        coords.addElement(new Point2D.Float((float) (12.5 * w), (48 * h)));
        coords.addElement(new Point2D.Float((float) (12.5 * w), (50 * h)));
        coords.addElement(new Point2D.Float((float) (12.5 * w), (52 * h)));
        coords.addElement(new Point2D.Float((float) (12.5 * w), (54 * h)));
        coords.addElement(new Point2D.Float((6 * w), (49 * h)));
        coords.addElement(new Point2D.Float((12 * w), (49 * h)));
        coords.addElement(new Point2D.Float((12 * w), (float) (49.5 * h)));
        coords.addElement(new Point2D.Float((6 * w), (float) (49.5 * h)));
        coords.addElement(new Point2D.Float((6 * w), (50 * h)));
        coords.addElement(new Point2D.Float((12 * w), (50 * h)));
        coords.addElement(new Point2D.Float((12 * w), (float) (50.5 * h)));
        coords.addElement(new Point2D.Float((6 * w), (float) (50.5 * h)));
        coords.addElement(new Point2D.Float((6 * w), (51 * h)));
        coords.addElement(new Point2D.Float((12 * w), (51 * h)));
        coords.addElement(new Point2D.Float((12 * w), (float) (51.5 * h)));
        coords.addElement(new Point2D.Float((6 * w), (float) (51.5 * h)));
        coords.addElement(new Point2D.Float((6 * w), (52 * h)));
        coords.addElement(new Point2D.Float((12 * w), (52 * h)));
        coords.addElement(new Point2D.Float((12 * w), (float) (52.5 * h)));
        coords.addElement(new Point2D.Float((6 * w), (float) (52.5 * h)));
        coords.addElement(new Point2D.Float((6 * w), (53 * h)));
        coords.addElement(new Point2D.Float((12 * w), (53 * h)));

        /*
         * coordinates of tripod
         */
        coordst.addElement(new Point2D.Float((3 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((3 * w), (64 * h)));
        coordst.addElement(new Point2D.Float((float) (3.4 * w), (64 * h)));
        coordst.addElement(new Point2D.Float((float) (3.4 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((float) (3.7 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((float) (3.7 * w), (64 * h)));
        coordst.addElement(new Point2D.Float((4 * w), (64 * h)));
        coordst.addElement(new Point2D.Float((4 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((30 * w), (-6 * h)));
        coordst.addElement(new Point2D.Float((30 * w), (50 * h)));
        coordst.addElement(new Point2D.Float((float) (30.4 * w), (50 * h)));
        coordst.addElement(new Point2D.Float((float) (30.4 * w), (-6 * h)));
        coordst.addElement(new Point2D.Float((float) (30.7 * w), (-6 * h)));
        coordst.addElement(new Point2D.Float((float) (30.7 * w), (50 * h)));
        coordst.addElement(new Point2D.Float((31 * w), (50 * h)));
        coordst.addElement(new Point2D.Float((31 * w), (-6 * h)));
        coordst.addElement(new Point2D.Float((41 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((41 * w), (64 * h)));
        coordst.addElement(new Point2D.Float((float) (41.4 * w), (64 * h)));
        coordst.addElement(new Point2D.Float((float) (41.4 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((float) (41.7 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((float) (41.7 * w), (64 * h)));
        coordst.addElement(new Point2D.Float((42 * w), (64 * h)));
        coordst.addElement(new Point2D.Float((42 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((3 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((3 * w), (2 * h)));
        coordst.addElement(new Point2D.Float((42 * w), (2 * h)));
        coordst.addElement(new Point2D.Float((42 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((float) (30.5 * w), (-6 * h)));
        coordst.addElement(new Point2D.Float((float) (30.5 * w), (-7 * h)));
        coordst.addElement(new Point2D.Float((float) (42.5 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((float) (43 * w), (3 * h)));
        coordst.addElement(new Point2D.Float((float) (30.5 * w), (-8 * h)));
        coordst.addElement(new Point2D.Float((3 * w), (2 * h)));
        coordst.addElement(new Point2D.Float((3 * w), (float) (2.5 * h)));
        coordst.addElement(new Point2D.Float((float) (30.5 * w), (float) (-7.5 * h)));
        coordst.addElement(new Point2D.Float((float) (30.5 * w), (float) (-8 * h)));
        coordst.addElement(new Point2D.Float((3 * w), (3 * h)));
        /*
         * coordinates of gauze
         */
        coordsg.addElement(new Point2D.Float((6 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((12 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((14 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((8 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((10 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((16 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((18 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((12 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((14 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((20 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((22 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((16 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((18 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((24 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((26 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((20 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((22 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((28 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((30 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((24 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((26 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((32 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((34 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((28 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((30 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((36 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((38 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((32 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((34 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((40 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((42 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((36 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((6 * w), (6 * h)));
        coordsg.addElement(new Point2D.Float((7 * w), (4 * h)));
        coordsg.addElement(new Point2D.Float((37 * w), (4 * h)));
        coordsg.addElement(new Point2D.Float((38 * w), (2 * h)));
        coordsg.addElement(new Point2D.Float((8 * w), (2 * h)));
        coordsg.addElement(new Point2D.Float((9 * w), (0 * h)));
        coordsg.addElement(new Point2D.Float((39 * w), (0 * h)));
        coordsg.addElement(new Point2D.Float((40 * w), (-2 * h)));
        coordsg.addElement(new Point2D.Float((10 * w), (-2 * h)));
        coordsg.addElement(new Point2D.Float((11 * w), (-4 * h)));
        coordsg.addElement(new Point2D.Float((41 * w), (-4 * h)));
        coordsg.addElement(new Point2D.Float((42 * w), (-7 * h)));
        coordsg.addElement(new Point2D.Float((12 * w), (-7 * h)));

        width = (w * 60);
        height = (h * 60);
        if (!isDrawable) {
            coords.clear();
        }
    }
    /**
     * for getting apparatus height
     * @return height
     */
    public float getApparatusHeight() {
        return hx;
    }
    /**
     * for getting apparatus width
     * @return width
     */
    public float getApparatusWidth() {
        return wx;
    }
    /**
     * for getting origin
     * @return originShift
     */
    public Point2D.Float getOrigin() {
        return originShift;
    }
    /**
     * this function is responsible for automatic flames
     * @param time  this shows the time in seconds for which we want the burner to burn
     */
    public void flame(int time) {
        up.setVisible(false);
        down.setVisible(false);
        for (int i = 0; i < 12; i++) {

            if (intensity + Constants.FLAME_INTENSITY_DECREAMENT <= Constants.MAX_FLAME_INTENSITY) {
                intensity += Constants.FLAME_INTENSITY_DECREAMENT;
                if (flameHeight + (intensity * 5) <= Constants.MAX_FLAME_HEIGHT) {
                    flameHeight += (intensity * 5);
                }
                if (flameHeight > 0) {
                    onState = true;
                }

            }
            environ.repaint();
            delaySimple(500);

        }
        delaySimple(1000 * time);
        while (intensity != 0.0) {

            if (intensity - Constants.FLAME_INTENSITY_DECREAMENT >= 0) {
                intensity -= Constants.FLAME_INTENSITY_DECREAMENT;
                if (flameHeight - (intensity * 5) >= 0) {
                    flameHeight -= 3;
                    //flameHeight -= (intensity*5);
                    if (flameHeight <= 1) {
                        flameHeight = 0;
                        onState = false;
                        intensity = 0.0;
                    }
                }
            }
            environ.repaint();
            delaySimple(500);
        }
        up.setVisible(true);
        down.setVisible(true);
    }
    /**
     * for getting mouth left
     * @return  mouthLeft
     */
    public Point2D.Float getMouthLeft() {
        return mouthLeft;
    }
    /**
     * for getting mouth right
     * @return mouthRight
     */
    public Point2D.Float getMouthRight() {
        return mouthRight;
    }
    /**
     * this function is responsible for drawing equipment
     * @param g object of graphics class
     */
    @Override
    public void drawEquipment(Graphics g) {


        g.setColor(borderColor);
        if (!isDrawable) {
            return;
        }
        /**
             * drawing wire gauze
             */
        for (int i = 0; i < coordsg.size() - 1; i++) {
            Point2D.Float s = coordsg.elementAt(i);
            Point2D.Float d = coordsg.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }
        /**
             * drawing tripod stand
             */
        for (int i = 0; i < coordst.size() - 1; i++) {
            Point2D.Float s = coordst.elementAt(i);
            Point2D.Float d = coordst.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }

        /**
             * drawing burner
             */
        for (int i = 0; i < coords.size() - 1; i++) {
            Point2D.Float s = coords.elementAt(i);
            Point2D.Float d = coords.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }
        if (!getCupboardFlag()) {
            createFlames(g);
        }



    }
    /**
     * for setting line color
     * @param col  color
     */
    public void setLineColor(Color col) {
        lineColor = col;
    }
    /**
     * this function is responsible for creating flames
     * @param g  object of graphics
     */
    private void createFlames(Graphics g) {

        g.setColor(Constants.FLAME_COLOR);
        float dh = 2 * (flameHeight * ((float) h / 1.5f)) / (float) (mouthRight.x - mouthLeft.x);
        float h = 0;
        for (float i = mouthLeft.x; i <= mouthRight.x; i++) {
            Point2D.Float pt = new Point2D.Float(originShift.x + i, originShift.y + mouthLeft.y);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(pt.x, pt.y, pt.x, (pt.y - h)));
            if (i <= (mouthLeft.x + mouthRight.x) / 2) {
                h += dh;
            } else {
                h -= dh;
            }
        }
        g.setColor(Color.black);
    }
    /**
     * for getting flame height
     * @return  flameHeight
     */
    public int getFlameHeight() {
        return flameHeight;
    }
    /**
     * thread function
     * @param delay 
     */
    private void delaySimple(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) { /*
             * Do Nothing
             */ }
    }

    public void run() {
        try {

            delaySimple(5000);
            Thread.sleep(500);
            // flame(4);


        } catch (InterruptedException ex) {
        }
    }

   
    /*
     * public void heatContent( ) { Content content = (glassWare).getContent();
     * if( onState ) { double t = content.getTemperature(); t += intensity;
     * content.setTemperature( t ); } }
     */

  /*  public void actionPerformed(ActionEvent ae) {
        JButton btn = (JButton) ae.getSource();

        if (btn == up) {
            if (intensity + Constants.FLAME_INTENSITY_DECREAMENT <= Constants.MAX_FLAME_INTENSITY) {
                intensity += Constants.FLAME_INTENSITY_DECREAMENT;

                if (flameHeight + (intensity * 5) <= Constants.MAX_FLAME_HEIGHT) {
                    flameHeight += (intensity * 5);
                }
                if (flameHeight > 0) {
                    onState = true;
                }

                environ.watch.timeStart(ae.getWhen());

            }

        } else {

            if (intensity - Constants.FLAME_INTENSITY_DECREAMENT >= 0) {
                intensity -= Constants.FLAME_INTENSITY_DECREAMENT;
                if (flameHeight - (intensity * 5) >= 0) {
                    flameHeight -= 3;

                    if (flameHeight <= 1) {
                        flameHeight = 0;
                        onState = false;
                        intensity = 0.0;
                    }
                }
                if (!onState) {
                    environ.watch.timeStop(ae.getWhen());
                }

            }

        }
        environ.repaint();
    }*/


    /*
     * private void setLayout(Object object) { throw new
     * UnsupportedOperationException("Not yet implemented"); }
     */
    public void draw(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}