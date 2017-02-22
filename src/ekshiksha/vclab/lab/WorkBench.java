/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekshiksha.vclab.lab;

/**
 *
 * @author mayur12,anirban
 */
import ekshiksha.vclab.activity.Activities;
import ekshiksha.vclab.activity.Activity;
import ekshiksha.vclab.activity.Move;
import ekshiksha.vclab.activity.Pour;
import ekshiksha.vclab.activity.Wash;
import java.awt.*;
import ekshiksha.vclab.equipment.Equipment;
import ekshiksha.vclab.equipment.Equipments;
import ekshiksha.vclab.equipment.Burette;
import ekshiksha.vclab.util.Constants;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkBench extends JPanel implements Serializable, MouseListener, Runnable, ActionListener, MouseMotionListener, ComponentListener {

    public StopWatchRunner watch;
    private Vector<Line> hlines;
    public static ArrayList<Equipment> equipments;//new for test
    private Rectangle shelves[];
    private Point[] table;
    private Point plcmntStart, plcmntEnd;
    Color borderColor = Color.BLUE;
    public boolean canMove;
    public int currentIndex;
    Table tables = new Table(this);
    Basin basin = new Basin(this);
    Tiles tile = new Tiles(this);
    Shelf shelf = new Shelf(this);
    public Graphics gh;
    public BufferedImage img;
    public int tableBounds[] = new int[4];
    //Table tab;
    //Shelf shelf;
    //Basin basin;
    int w;
    int h;
    boolean isSet;
    boolean shouldBeResized;
    boolean Fill_bur;
    /*
     * public static ArrayList<Equipment> equipments;//new for test public
     * static ArrayList<Activity> activities;
     */
    public Point2D.Float initial;
    public boolean canPour;
    public int flag;
    //  public Equipment equip,equipment;
    public int actIndex;
    public int x;
    public int y;
    public int x1;
    public int y1;
    public int vol;
    public float temp_vol;
    public ImageIcon icon;
    public JFrame frame;
    public GridLayout layout = new GridLayout(2, 2);
    //  public Thread par;
    public Equipment e1, e2, burner, eqHeated;
    public JTextField tx, vx;
    //  public Thread p;
    public JButton b, b1, b2;
    private double ang = 0;
    private boolean isRotate;
    private boolean addOnce;
    private JLabel quan;
    private float b_rx;

    public float getb_rx() {
        return b_rx;
    }

    public float getb_ry() {
        return b_ry;
    }
    private float b_w;
    private boolean canWash;
    private float b_ry;
    private float b_h;
    private boolean pouring;
    private boolean filling;
    public int mode;
    boolean isHeating;
    Point2D.Float burnDestination;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public WorkBench(StopWatchRunner watch) {

        setBounds(0, 0, 100, 100);
        setLayout(null);
        ImageIcon icon = new ImageIcon("./Images/hand.gif");
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon.getImage(), new Point(0, 0), "Hand"));
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addComponentListener(this);
        this.setPreferredSize(new Dimension(700, 800));
        hlines = new Vector<Line>();
        equipments = new ArrayList<Equipment>();
        shelves = new Rectangle[2];
        table = new Point[4];
        w = this.getWidth();
        h = this.getHeight();
        isSet = false;
        addOnce = false;
        shouldBeResized = false;
        Fill_bur = false;
        canWash = false;
        flag = 0;
        canMove = false;
        vol = 0;
        quan = new JLabel();
        add(quan);
        frame = new JFrame("Pour Prompt");
        frame.setBounds(60, 60, 200, 100);
        frame.setLayout(layout);
        b = new JButton("Yes");
        b1 = new JButton("No");
        tx = new JTextField("Enter volume");
        vx = new JTextField("");
        frame.add(tx);
        frame.add(vx);
        frame.add(b);
        frame.add(b1);

        frame.setVisible(false);
        b.addActionListener(this);
        b1.addActionListener(this);
        isRotate = false;
        pouring = false;
        this.watch = watch;
        eqHeated = null;
        burner = null;
        isHeating = false;
    }
/**
 * This method is used to paint the components and equipments of the virtual lab
 * @param g Graphics object
 * This method over rides the paintComponent method of the JPanel class. It automatically gets called 
 * once on execution. And it also gets called when repaint method is called. This method is used draw the 
 * different parts of workbench by calling the drawLab(Graphics g) method. It also initializes the initial origins 
 * of all equipments in the equipment list and calls their drawEquipment(Graphics g) method.Hence this method 
 * controls the drawing of graphics of the project. 
 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Constants.WALL_COLOR);
        gh = g;
        drawLab(g);
        //Newly added code
        for (int i = 0; i < Equipments.getEquipments().size(); i++) {
            Equipment equipment = Equipments.getEquipments().get(i);
            if (!isSet) {
                equipment.setOriginShift(new Point2D.Float((equipment.getX() * this.getWidth()), (equipment.getY() * this.getHeight())));

            }



            equipment.initEquipment();
            g.setColor(Color.black);
            equipment.drawEquipment(g);

        }
        isSet = true;
        b_rx = (434.0f / 600.0f) * this.getWidth();
        b_ry = (376.0f / 529.0f) * this.getHeight();
        b_w = (89.0f / 600.0f) * this.getWidth();
        b_h = (27.0f / 529.0f) * this.getHeight();

    }
/**
 * This method is called by paintComponent to draw the different parts of the lab.
 * @param g Graphics object
 * To draw the different parts of the lab it calls different methods for the different parts i.e. 
     * table(using drawTable(Graphics g) of Table class),shelf(using drawShelf(Graphics g) of Shelf class),
     * basin(using drawBasin(Graphics g) of Basin class),Tiles(using fillTiles(Graphics g) of Tiles class).
 */
    public void drawLab(Graphics g) {
        Vector<Vector<Point>> polygon = new Vector<Vector<Point>>();
        polygon = tile.getTileCoordinates();
        tile.fillTiles(polygon, g);
        //tab.drawTable( g ); //new one
        tables.drawTable(g);
        //shelf.drawShelf( g ); //new one
        shelf.drawShelf(g);
        //basin.drawBasin( g ); //new one
        basin.drawBasin(g);
    }

    public Point getTableCoordinates(int idx) {
        return table[idx];
    }

    public Point getRandomPoint() {
        Random rand = new Random();
        int x = plcmntStart.x + rand.nextInt(plcmntEnd.x - plcmntStart.x - Constants.SEPERATION_BASINLEFT_PLCMNTEND);
        int y = plcmntStart.y;

        return new Point(x, y);
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    /**
     * This method extracts coordinates of where the mouse has been pressed
     * @param me MouseEvent object
     * This mouse Listener works only in the Store and Perform experiment mode and not in the Demonstration mode. 
     * After extracting coordinates of where the mouse has been pressed, it checks if that point is within the Equipment 
     * List using the function public boolean contains(Point2D.Float c,Point2D.Float org,float wt,float ht).
     * If it finds that the selected Equipment object is rotated by an angle due to previous Activity like pouring, 
     * it re-rotates it back to zero degrees. It also sets the value of currentIndex of the selected Equipment from 
     * the arraylist Equipments.
     */ 
    public void mousePressed(MouseEvent me) {
        if (mode == 3) {
            return;
        }
        if (e1 != null && (e1.fraction == 0.0f || e1.isFull)) {
            pouring = false;
        }
        if (pouring) {
            return;
        }
        currentIndex = -1;
        addOnce = false;
        Point2D.Float l = new Point2D.Float(me.getX(), me.getY());
        for (int i = 0; i < Equipments.getEquipments().size(); i++) {
            Equipment equipment = (Equipment) Equipments.getEquipments().get(i);


            if (equipment.contains(l, equipment.originShift, equipment.width, equipment.height)) {
                if ((equipment.type.equals("Bottle") || equipment.type.equals("TestTube") || equipment.type.equals("Beaker") || equipment.type.equals("Flask")) && !isRotate) {
                    equipment.rotate(-equipment.angle);
                    repaint();

                }







                canMove = true;
                canPour = false;
                canWash = false;

                currentIndex = i;
                // setCursor( Toolkit.getDefaultToolkit().createCustomCursor(icon.getImage(), new Point(l.x,l.y), "Hand"));
                if (flag == 0) {


                    initial = new Point2D.Float(equipment.originShift.x, equipment.originShift.y);

                    flag = 1;
                } else {
                    flag = 0;
                }


            } // commented

        }
    }
/**
 * This mouseListener detects a click and release of the mouse button
 * @param me MouseEvent object
 * This listener is the most important one as it has the function of identifying if an Activity is about to take place. By iterating across the list Equipments, it checks using public boolean contains(Point2D.Float c,Point2D.Float org,float wt,float ht) whether 2 equipment regions are coinciding. 


a)If the equipment dragged is a Pipette it sets it to middle top region of the other equipment.
    A JOptionPane appears and takes the user input of Yes/No and the act is added to Activities list.

b) When a beaker/Flask is dragged to the lower region of a Burette it sets the former equipment to lower middle region of the Burette and Enables the Burette pour buttons to be functional.

c) Otherwise after performing some constraint checks it sets the currentIndex equipment to top left corner of the coinciding equipment.

d) If Equipment regions are not coinciding but the equipment is dragged to an empty space the Activity is registered as a Move.

e) If Equipment is dragged to Basin Region and the user inputs yes to a JOptionPane prompt the Activity is registered as a Wash.
 */
    
    public void mouseReleased(MouseEvent me) {

        if (mode == 3) {
            return;
        }




        if (Equipments.getEquipments().size() == 0 || currentIndex == -1) {
            return;
        }


        Equipment equipment = Equipments.getEquipments().get(currentIndex);
        int i;
        Equipment equip = Equipments.getEquipments().get(currentIndex);
        String type = equip.type;
        x = me.getX();
        y = me.getY();
        x1 = me.getX() + (int) equip.width;
        y1 = me.getY() + (int) equip.height;
        Point2D.Float l = new Point2D.Float(x, y);
        Point2D.Float l1 = new Point2D.Float(x1, y1);
        if ((equipment.originShift.x >= b_rx && equipment.originShift.x <= b_rx + b_w
                && (equipment.originShift.y + equipment.height) <= b_ry && (equipment.originShift.y + equipment.height) >= b_ry - b_h)) {
            
            
            if(!(((equipment.type).equals("Burner"))||((equipment.type).equals("Bottle"))))
            {
                equipment.borderColor = Color.GREEN;
                
            canWash = true;
            Object[] options = {"Yes",
                "No"
            };
            int n = JOptionPane.showOptionDialog(this,
                    "Would you like to wash the equipment? ",
                    "Wash Prompt",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0) {

                Activity act = new Wash(equipment.id, new Point2D.Float(initial.x / this.getWidth(), initial.y / this.getHeight()), equipment.quantity);//,equipment.contents,equipment.contentColor);
                Activities.add(act);

                equipment.fraction = 0.0f;
                equipment.contents = null;
                equipment.quantity = 0.0f;
                equipment.strength = 0.0f;


                repaint();
            }


            }
        }

        for (i = 0; i < Equipments.getEquipments().size(); i++) {

            Equipment equipment1 = (Equipment) Equipments.getEquipments().get(i);

            if (i != currentIndex) {
                if (!equipment1.contains(l, equipment1.originShift, equipment1.width, equipment1.height)
                        && equipment1.contains(l1, equipment1.originShift, equipment1.width, equipment1.height)) {
                    e1 = equip;
                    e2 = equipment1;
                    if (e1.type.equals("Pipette") && (e2.type.equals("Beaker") || e2.type.equals("Flask"))) {
                        canPour = true;
                        pouring = true;
                        if ((e1.isFull)) {

                            Equipments.getEquipments().get(currentIndex).originShift.x = (equipment1.width) / 2 + equipment1.originShift.x-5;
                            Equipments.getEquipments().get(currentIndex).originShift.y = equipment1.originShift.y - Equipments.getEquipments().get(currentIndex).height;
                            repaint();

                            Object[] options = {"Yes",
                                "No"
                            };
                            int n = JOptionPane.showOptionDialog(this,
                                    "Would you like to pour? ",
                                    "Pour Prompt",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[1]);
                            if (n == 0) {
                                e1.pour(this.getGraphics(), e2);
                            }

                            Activity act = new Pour(e1.id, e2.id, e1.capacity, new Point2D.Float((initial.x / this.getWidth()), (initial.y / this.getHeight())), e1.contents);
                            Activities.add(act);
                        } else {
                            if ((e2.fraction * e2.capacity) >= e1.capacity) {
                                Equipments.getEquipments().get(currentIndex).originShift.x = (equipment1.width) / 2 + equipment1.originShift.x;
                                Equipments.getEquipments().get(currentIndex).originShift.y = e2.originShift.y + e2.height - e1.height;
                                repaint();
                                Object[] options = {"Yes",
                                    "No"
                                };
                                int n = JOptionPane.showOptionDialog(this,
                                        "Would you like to fill? ",
                                        "Fill Prompt",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        options,
                                        options[1]);
                                if (n == 0) {
                                    e1.fill(this.getGraphics(), e2);
                                }


                                Activity act = new Pour(e1.id, e2.id, -(e1.capacity), new Point2D.Float((initial.x / this.getWidth()), (initial.y / this.getHeight())), e1.contents);
                                Activities.add(act);
                            }
                        }


                        repaint();




                    }

                    if (((e1.type.equals("Bottle")) && (e2.type.equals("Beaker") || e2.type.equals("TestTube") || e2.type.equals("Flask")))
                            || ((e1.type.equals("Beaker")) && (e2.type.equals("Beaker") || e2.type.equals("TestTube") || e2.type.equals("Flask")))
                            || (e1.type.equals("TestTube") && (e2.type.equals("Beaker") || e2.type.equals("TestTube") || e2.type.equals("Flask"))) || ((e1.type.equals("Flask")) && (e2.type.equals("Beaker") || e2.type.equals("TestTube") || e2.type.equals("Flask")))) {

                        canPour = true;
                        e2.setContentColor(e1.getContentColor());
                        Equipments.getEquipments().get(currentIndex).originShift.x = (equipment1.width) + equipment1.originShift.x;
                        Equipments.getEquipments().get(currentIndex).originShift.y = equipment1.originShift.y - Equipments.getEquipments().get(currentIndex).height + 0.9f * (equip.height);
                    }

                    if (e2.type.equals("Burette") && (e1.type.equals("Flask") || e1.type.equals("Beaker"))) {



                        if (e1.originShift.y > (e2.originShift.y + (e2.height) / 3)) {
                            Equipments.getEquipments().get(currentIndex).originShift.x = e1.originShift.x + 10;
                            Equipments.getEquipments().get(currentIndex).originShift.y = e2.originShift.y + (e2.height);
                            canPour = false;
                            Fill_bur = true;
                            temp_vol = (e1.fraction * e1.capacity);
                            e2.down.setEnabled(true);

                        } else {
                            Fill_bur = false;
                            canPour = true;

                            Equipments.getEquipments().get(currentIndex).originShift.x = (equipment1.width) + equipment1.originShift.x;
                            Equipments.getEquipments().get(currentIndex).originShift.y = equipment1.originShift.y - Equipments.getEquipments().get(currentIndex).height + 0.9f * (equip.height);
                        }
                    }
                    if (e2.type.equals("Burner") && ((e1.type.equals("Beaker")) || (e1.type.equals("Flask")) || (e1.type.equals("TestTube")))) {
                        Equipments.getEquipments().get(currentIndex).originShift.x = e2.originShift.x + (e2.width / 2) - (e1.width / 2) - 5;
                        Equipments.getEquipments().get(currentIndex).originShift.y = e2.originShift.y + 10 - (e2.height);
                        Object[] options = {"Yes",
                            "No"
                        };
                        int n = JOptionPane.showOptionDialog(this,
                                "Would you like to heat? ",
                                "Heat Prompt",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);
                        if (n == 0) {
                            e2.up.setEnabled(true);
                            e2.down.setEnabled(true);
                            burner = e2;
                            eqHeated = e1;
                            isHeating = true;
                            burnDestination = initial;
                        }

                    }


                }
            }

            this.repaint();



        }


        if (canMove == true && canPour == false && canWash == false) {
            Activity act = new Move(equip.id, new Point2D.Float((initial.x / this.getWidth()), (initial.y / this.getHeight())), new Point2D.Float((equip.originShift.x / this.getWidth()), (equip.originShift.y / this.getHeight())));
            Activities.add(act);
        }

        if (canMove == true && canPour == true && !(e1.type.equals("Pipette"))) {
            frame.setVisible(true);

        }




        canMove = false;
        canPour = false;
        canWash = false;



        if (flag == 1) {
            flag = 0;

        }
        currentIndex = -1;


        tx.setText("Enter volume");
        tx.setEditable(false);



    }

    /**
     * This Mouse Listener detects pure motion of the mouse without button clicks
     * * @param me MouseEvent object
     * The main purpose of this mouse Listener is to change the border color parameter of an equipment to red 
     * when the mouse is hovered within the equipment. It also sets the color to green if the equipment is 
     * hovered in the wash basin region of the Lab. It also provides the user control to rotate equipment 
     * objects and filling or de-filing the Pipette when pouring such that each time the mouse is moved an 
     * increment in angle or change in volume of the Pipette takes place.

     */
    public void mouseMoved(MouseEvent me) {
        quan.setBounds(0, 0, 0, 0);
        quan.setVisible(false);
        remove(quan);

        Point2D.Float l = new Point2D.Float(me.getX(), me.getY());

        for (int i = 0; i < Equipments.getEquipments().size(); i++) {
            Equipment equipment = (Equipment) Equipments.getEquipments().get(i);

            if (equipment.contains(l, equipment.originShift, equipment.width, equipment.height) && equipment.originShift.x >= b_rx && equipment.originShift.x <= b_rx + b_w
                    && (equipment.originShift.y + equipment.height) <= b_ry && (equipment.originShift.y + equipment.height) >= b_ry - b_h) {
                equipment.borderColor = Color.GREEN;
                repaint();
                continue;
            }
            if (equipment.contains(l, equipment.originShift, equipment.width, equipment.height)) {
                   if (mode == 2) {
                    
                    equipment.borderColor = Color.red;
                    repaint();
                }
                  if ((!equipment.type.equals("Burner"))) {
                    quan.setVisible(true);

                    quan = new JLabel(Integer.toString((int) (equipment.capacity * equipment.fraction)) + "ml");
                    quan.setBackground(new Color(153, 255, 153));
                    quan.setBounds((int) (equipment.originShift.x + equipment.width), (int) (equipment.originShift.y + equipment.height), 50, 25);
                    quan.setForeground((Color.black));
                    quan.setOpaque(true);
                    this.add(quan);
                    //repaint();
                    if(mode==3)return;
                    break;
                }

                
              

            } else {
                if (mode == 2) {
                    equipment.borderColor = Color.black;
                    repaint();
                }
            }


        }
        
        if ((ang >= 0 && ang <= 45) && isRotate == true) {
            if (mode == 3) {
            return;
        }

            e1.rotate(1);
            ang += 1;

        } else if (ang > 45) {

            isRotate = false;
        }

    }

    /**
     * This mouseListener is used to detect dragging motion of the mouse
     * * @param me MouseEvent object
     * The major action of this mouse Listener is to keep redrawing each equipment every instant 
     * as it is dragged across the Lab by changing the originShift coordinates of them to the getX() getY() 
     * coordinates of the mouse. The pour activity from Burette to Beaker or Flask is added to the Activities 
     * arrayList within this block.
     */ 
    public void mouseDragged(MouseEvent me) {
        if (mode == 3) {
            return;
        }
        if (pouring) {
            return;
        }

        quan.setBounds(0, 0, 0, 0);
        quan.setVisible(false);
        remove(quan);

        if (canMove && !canPour && !isRotate) {
            actIndex++;
            x = me.getX();
            y = me.getY();

            Point2D.Float l = new Point2D.Float(me.getX(), me.getY());
            int flag = 0;
            if (Equipments.getEquipments().size() > 0 && x > this.getX() + 20 & x < getX() + this.getWidth() - 20 && y > this.getY() + 20 & y < this.getY() + this.getHeight() - 20) {
                Equipment equipment = Equipments.getEquipments().get(currentIndex);

                if (!(e1 == null) && !(e2 == null) && (equipment.id == e1.id || equipment.id == e2.id)) {//&&((burner!=null&&eqHeated!=null)&&(equipment.id!=burner.id || equipment.id!=eqHeated.id))) {
                    if ("Burette".equals(e2.type)) {
                        e2.down.setEnabled(false);
                        if ((e1.type.equals("Beaker") || "Flask".equals(e1.type) )&& e2.type.equals("Burette") && addOnce == false && Fill_bur == true) {
                            e1.setContentColor(e2.getContentColor());
                            double v = Math.ceil((e1.fraction * e1.capacity) - temp_vol);
                            Activity act = new Pour(e2.id, e1.id, (float) (v), new Point2D.Float((initial.x / this.getWidth()), (initial.y / this.getHeight())), e1.contents);
                            Activities.add(act);
                            Fill_bur = false;
                            addOnce = true;
                        }


                    }

                }
                //equipment.originShift.x = x;
                //equipment.originShift.y = y;


                //repaint();
                if ((burner != null && eqHeated != null)) {
                    if ((equipment.id == eqHeated.id) || (equipment.id == burner.id)) {
                        if (!isHeating) {
                            equipment.originShift.x = x;
                            equipment.originShift.y = y;
                            repaint();
                        }
                    } else {
                        equipment.originShift.x = x;
                        equipment.originShift.y = y;
                        repaint();
                    }
                } else if (burner == null && eqHeated == null && equipment != null) {
                    equipment.originShift.x = x;
                    equipment.originShift.y = y;
                    repaint();
                }



            }


        }

        //  addOnce=false;

    }

  /**
     * This method is used to resize the components of the lab on resizing the window
     * 
     * @param ComponentEvent object
     * This method is a method of the ComponentListener interface and gets called whenever the Workbench is re sized.
     * It sets the new origins of the equipments on the Workbench according to the new size of the Workbench.The other 
     * methods of ComponentListener interface are not being used.
     */
    public void componentResized(ComponentEvent e) {

        for (int i = 0; i < Equipments.getEquipments().size() && shouldBeResized; i++) {

            Equipment equipment = Equipments.getEquipments().get(i);
            equipment.originShift.x = (float) ((equipment.originShift.x) * (float) ((float) this.getWidth() / (float) w));
            equipment.originShift.y = (float) (equipment.originShift.y) * ((float) this.getHeight() / (float) h);
            // equipment.ob.start()

        }

        w = this.getWidth();
        h = this.getHeight();
        repaint();
        shouldBeResized = true;
    }

    public void componentMoved(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentShown(ComponentEvent e) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentHidden(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * This method is for capturing actions for the Pour Prompt
     * @param e ActionEvent object
     * This block of code is to perform actions when the buttons of Yes or No on the JFrame pour prompt 
     * is pressed by the user. The frame is set visible in mouseReleased when a pour is about to take place. 
     * The user inputs the desired quantity and the particular equipment is rotated accordingly and consistent 
     * volume changes are made across two equipments. Filling from a burette and other Pour activities are registered 
     * within here.Also checks to prevent a user from entering garbage values, negative quantities, quantities which are not available in the equipment or quantities which will cause one equipment to overflow are implemented with unique JOptionPane warning messages being displayed for each.
     */
    public void actionPerformed(ActionEvent e) {



        if (e.getSource() == b) {



            boolean isCorrect = true;

            int iterator = 0, len = vx.getText().length();
            String inp = vx.getText();
            while (iterator < len) {
                if (!(inp.charAt(iterator) >= '0' && inp.charAt(iterator) <= '9')) {
                    isCorrect = false;
                }
                iterator++;
            }

            if (!isCorrect) {
                JOptionPane.showMessageDialog(this,
                        "Wrong input");
            } else {
                vol = Integer.parseInt(vx.getText());

                // vx.setVisible(false);
                vx.setText("");
                tx.setText("");

                if (!(e1.capacity * e1.fraction < vol || (e2.capacity * e2.fraction + vol) > e2.capacity)) {
                    if (!(e2.type.equals("Burette"))) {
                        
                        e1.fill(-vol);
                        isRotate = true;
                        ang = 0.0;
                        e2.fill(+vol);
                        Activity act = new Pour(e1.id, e2.id, vol, new Point2D.Float((initial.x / this.getWidth()), (initial.y / this.getHeight())), e1.contents);
                        Activities.add(act);

                    } else if (Fill_bur == false) {
                        e2.setContentColor(e1.getContentColor());
                        e1.fill(-vol);
                        e1.rotate(1.0);
                        e2.fill(vol);
                        Activity act = new Pour(e1.id, e2.id, vol, new Point2D.Float((initial.x / this.getWidth()), (initial.y / this.getHeight())), e1.contents);
                        Activities.add(act);
                    }




                    frame.setVisible(false);
                } else {
                    if (e1.capacity * e1.fraction < vol) {
                        JOptionPane.showMessageDialog(this,
                                "Not enough quantity");
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Capacity exceeded");
                    }

                }
            }
            frame.setVisible(false);

            for (int i = 0; i < Activities.getActivities().size(); i++) {

                Activity act1 = (Activities.getActivities().get(i));
                if (act1.type.equals("Move")) {
                } else if (act1.type.equals("Pour")) {
                } else if (act1.type.equals("Wash")) {
                }
            }
        } else if (e.getSource() == b1) {

            tx.setText("");
            frame.setVisible(false);
        } else if (burner != null && e.getSource() == burner.up) {

            if (burner.intensity + Constants.FLAME_INTENSITY_DECREAMENT <= Constants.MAX_FLAME_INTENSITY) {
                burner.intensity += Constants.FLAME_INTENSITY_DECREAMENT;

                if (burner.flameHeight + (burner.intensity * 5) <= Constants.MAX_FLAME_HEIGHT) {
                    burner.flameHeight += (burner.intensity * 5);
               }
                if (burner.flameHeight >= Constants.MAX_FLAME_HEIGHT) {
                    burner.up.setEnabled(false);
                }
                if (burner.flameHeight > 0) {
                    burner.onState = true;
                }

                watch.timeStart(e.getWhen());
                repaint();
            }
        } else if (burner != null && e.getSource() == burner.down) {
            if (burner.intensity - Constants.FLAME_INTENSITY_DECREAMENT >= 0) {
                burner.intensity -= Constants.FLAME_INTENSITY_DECREAMENT;
                if (burner.flameHeight - (burner.intensity * 5) >= 0) {
                    burner.flameHeight -= 3;

                    if (burner.flameHeight <= 1) {
                        burner.flameHeight = 0;
                        burner.onState = false;
                        burner.intensity = 0.0;
                    }
                    if (burner.flameHeight > 0 && burner.flameHeight < Constants.MAX_FLAME_HEIGHT) {
                        burner.up.setEnabled(true);
                    }
                }

                if (!burner.onState) {
                    watch.timeStop(e.getWhen());
                    isHeating = false;
                    burner.up.setEnabled(false);
                    burner.down.setEnabled(false);

                }
                repaint();
            }

        }
    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}